package app.dao;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import org.bson.Document;

import java.util.ArrayList;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import app.MongoDBConnection;
public class UserCheckDaoImpl {
    public boolean getCheckDB(String name, String id){
        MongoDBConnection mongodb = MongoDBConnection.getConnection();
        MongoCursor<Document> cursor;
        MongoCollection<Document> allListings = mongodb.getAllListings();
        cursor = allListings.find(and(eq("reviews.reviewer_name", name),eq("reviews.reviewer_id",id))).projection(fields(include("name","reviews"), exclude("_id"))).iterator();
        ArrayList<String> result = new ArrayList<String>();
        boolean it_has = false;
        try {
         while (cursor.hasNext()) {
            Document record = cursor.next();
            // String rec_name = record.get("name").toString();
            // String rev_comment = record.get("reviews").toString();
            // System.out.println(rev_comment);
            // result.add(rec_name+"~~"+rev_name.get("reviewer_name"));
            // result.add(rec_name);
            // System.out.println(result);
            System.out.println("It is Valid");
            it_has=true;
         }
         if(it_has==false){
            System.out.println("It is Valid");
         }
      }
      finally {
         cursor.close();
      }
        return it_has;
    }
}