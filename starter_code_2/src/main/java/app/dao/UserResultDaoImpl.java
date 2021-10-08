package app.dao;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import org.bson.Document;


import java.util.ArrayList;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import app.MongoDBConnection;
public class UserResultDaoImpl {
    public ArrayList<String> getSearchDB(String name){
        MongoDBConnection mongodb = MongoDBConnection.getConnection();
        MongoCursor<Document> cursor;
        MongoCollection<Document> allListings = mongodb.getAllListings();
        cursor = allListings.find(eq("name", name)).projection(fields(include("name", "summary", "review_scores", "reviews", "property_type", "amenities", "accommodates", "host.host_is_superhost", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
        ArrayList<String> result = new ArrayList<String>();
        try {
         while (cursor.hasNext()) {
            Document record = cursor.next();
            String rec_name = record.get("name").toString();
            Document rec_images = (Document) record.get("images");
            String rec_bedroom = record.get("bedrooms").toString();
            String rec_bed = record.get("beds").toString();
            String rec_price = record.get("price").toString();
            String rec_summary = record.get("summary").toString();
            Document rec_address = (Document) record.get("address");
            Document rec_review_score = (Document) record.get("review_scores");
            String rec_ptype = record.get("property_type").toString();
            String rec_amenities = record.get("amenities").toString();
            String rec_accomodates = record.get("accommodates").toString();
            Document rec_host = (Document) record.get("host");
            ArrayList<Document> rec_reviews = (ArrayList<Document>) record.get("reviews");
            String all_review_info = "";
            for (Document docs_review : rec_reviews) {
                String reviewer_name = (String) docs_review.get("reviewer_name");
                String reviewer_comment = (String) docs_review.get("comments");
                all_review_info=all_review_info + reviewer_name + "~~" + reviewer_comment + "~~";
            }
            String final_result = rec_name + "~~" + (rec_images.get("picture_url")) + "~~" + rec_bedroom + "~~" + rec_bed + "~~" + rec_price + "~~" + rec_summary + "~~" + (rec_address.get("street")) + "~~" + (rec_address.get("suburb")) + "~~" + (rec_address.get("government_area")) + "~~" + (rec_address.get("market")) + "~~" + (rec_address.get("country")) + "~~" + (rec_address.get("country_code") + "~~");
            final_result = final_result + (rec_review_score.get("review_scores_rating")) + "~~" + rec_ptype + "~~" + rec_amenities + "~~" + rec_accomodates + "~~" + (rec_host.get("host_is_superhost")) + "~~" + all_review_info;
            result.add(final_result);
         }

      }
      finally {
         cursor.close();
      }
        return result;
    }
}