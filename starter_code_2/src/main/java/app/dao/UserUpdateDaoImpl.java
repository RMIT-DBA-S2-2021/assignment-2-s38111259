package app.dao;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;

import app.MongoDBConnection;
public class UserUpdateDaoImpl {
    public boolean isUpdateDB(String hotel_name, String user_name, String text){
        MongoDBConnection mongodb = MongoDBConnection.getConnection();
        MongoCollection<Document> allListings = mongodb.getAllListings();
        System.out.println("hname: "+hotel_name+"\nuname: "+user_name+"\nText: "+text);
        UpdateResult updateResult = allListings.updateOne((and(eq("reviews.reviewer_name", user_name),eq("name",hotel_name))),set("reviews.$.comments", text));
        boolean updated=false;
        if(updateResult.getModifiedCount()==1){
            updated=true;
            System.out.println("Coming inside if");
        }
        System.out.println("Came at USER UPDATE DB");
        System.out.println(updateResult.getModifiedCount());
        return updated;
    }
}