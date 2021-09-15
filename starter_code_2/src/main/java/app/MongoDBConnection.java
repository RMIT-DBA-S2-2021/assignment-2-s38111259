package app;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Projections.*;

import org.bson.Document;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.*;

public class MongoDBConnection {
   /** TODO update the DATABASE_URL value below 
    *  format: mongodb+srv://username:password@atlas.server.address/database
    *  Goto https://cloud.mongodb.com/ 
    *  click connect > connect your application > Driver Java > Version 4.3 or later
    *  copy connection string
    *  if you have time out issues (due to firewalls) choose verion 3.3 or earlier instead
    **/
   private static final String DATABASE_URL = "YOUR ATLAS SERVER CONNECTION STRING HERE";
   static MongoClient client;
   MongoDatabase database;
   MongoCollection<Document> allListings;
   private static MongoDBConnection mongodb = null;

   public static MongoDBConnection getConnection(){
      //check that MongoDBConnection is available (if not establish)
      if(mongodb==null){
         mongodb = new MongoDBConnection();
      }
      return mongodb;
   }
   public MongoDBConnection() {
      System.out.println("Creating MongoDB Connection Object");
      
      try {
         client = MongoClients.create(DATABASE_URL);
         database = client.getDatabase("sample_airbnb");
         allListings = database.getCollection("listingsAndReviews");
         } catch (Exception e) {
         // If there is an error, lets just print the error
         System.err.println(e.getMessage());
      }
   }
   
   public static void closeConnection(){
      try {
         if (client != null) {
            client.close();
            System.out.println("Database Connection closed");
         }
      } catch (Exception e) {
         // connection close failed.
         System.err.println(e.getMessage());
      }
   }

   public ArrayList<String> getAllApartmentNames(){
      MongoCursor<Document> cursor = allListings.find(eq("property_type", "Apartment")).projection(fields(include("name"), exclude("_id"))).iterator();
       ArrayList<String> apartments = new ArrayList<String>();
       try {
          while (cursor.hasNext()) {
            apartments.add(cursor.next().get("name").toString());
          }
       }
       finally {
          cursor.close();
       }
       return apartments;
    }

   public ArrayList<String> getApartmentNamesByAccomodates(int accomodates){
     MongoCursor<Document> cursor = allListings.find(and(eq("accommodates", accomodates), eq("property_type", "Apartment"))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
      ArrayList<String> apartments = new ArrayList<String>();
      try {
         while (cursor.hasNext()) {
            Document record = cursor.next();
            String name = record.get("name").toString();
            String address = record.get("address").toString();
            apartments.add(name);
//            apartments.add(cursor.next().toJson());
         }
      }
      finally {
         cursor.close();
      }
      return apartments;
   }

}
