package app.dao;
import static com.mongodb.client.model.Filters.*;
import org.bson.Document;
import static com.mongodb.client.model.Updates.pull;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import app.MongoDBConnection;
public class UserDeleteDaoImpl {
    public boolean deleteUserDB(String hotel_name, String id){
        MongoDBConnection mongodb = MongoDBConnection.getConnection();
        MongoCollection<Document> allListings = mongodb.getAllListings();
        System.out.println("hname: "+hotel_name+"\nuname: "+id);
        UpdateResult deleted = allListings.updateOne(eq("reviews.reviewer_id",id), pull("reviews", eq("reviewer_id", id)));
        boolean it_has = false;
        System.out.println(deleted.getMatchedCount());
        System.out.println(deleted.getModifiedCount());

        if(deleted.getModifiedCount()==1){
            it_has=true;
            System.out.println("Coming inside deleted");
        }
        return it_has;
    }
}