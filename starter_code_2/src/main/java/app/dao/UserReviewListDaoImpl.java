package app.dao;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import org.bson.Document;
import java.util.ArrayList;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import app.MongoDBConnection;
public class UserReviewListDaoImpl {
    public ArrayList<String> getReviewList(String rname){
        MongoDBConnection mongodb = MongoDBConnection.getConnection();
        MongoCursor<Document> cursor;
        MongoCollection<Document> allListings = mongodb.getAllListings();
        cursor = allListings.find(eq("reviews.reviewer_name", rname)).projection(fields(include("name","reviews.$"), exclude("_id"))).iterator();
        ArrayList<String> result = new ArrayList<String>();
        try {
         while (cursor.hasNext()) {
            Document record = cursor.next();
            ArrayList<Document> rec_reviews = (ArrayList<Document>) record.get("reviews");
            String all_review_info = "";
            for (Document docs_review : rec_reviews) {
                String reviewer_name = (String) docs_review.get("reviewer_name");
                String reviewer_comment = (String) docs_review.get("comments");
                String reviewer_id = (String) docs_review.get("reviewer_id");
                all_review_info=all_review_info + reviewer_name + "~~" + reviewer_comment + "~~" + reviewer_id + "~~";
            }
            String hotel_name = record.get("name").toString();
            result.add(hotel_name+"~~"+all_review_info);
         }
      }
      finally {
         cursor.close();
      }
        return result;
    }
}