package app;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
public class MongoDBConnection {
   /** TODO update the DATABASE_URL value below 
    *  format: mongodb+srv://username:password@atlas.server.address/database
    *  Goto https://cloud.mongodb.com/ 
    *  click connect > connect your application > Driver Java > Version 4.3 or later
    *  copy connection string
    *  if you have time out issues (due to firewalls) choose verion 3.3 or earlier instead
    **/
   private static final String DATABASE_URL = "mongodb+srv://s3811259:Mongo%4045@dba-cluster.k7pnl.mongodb.net/test?authSource=admin&replicaSet=atlas-3kiige-shard-0&readPreference=primary&appname=MongoDB%20Compass&ssl=true";
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
   /**
    * @return the allListings
    */
   public MongoCollection<Document> getAllListings() {
      return allListings;
   }
   /**
    * @param allListings the allListings to set
    */
   public void setAllListings(MongoCollection<Document> allListings) {
      this.allListings = allListings;
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
}
