// package app.dao;

// import static com.mongodb.client.model.Filters.*;
// import static com.mongodb.client.model.Projections.*;
// import org.bson.Document;
// import org.bson.conversions.Bson;

// import java.util.ArrayList;

// import com.mongodb.client.MongoCollection;
// import com.mongodb.client.MongoCursor;

// import app.MongoDBConnection;
// public class UserCheckDaoImpl {
//     public boolean getCheckDB(String name){
//         MongoDBConnection mongodb = MongoDBConnection.getConnection();
//         MongoCursor<Document> cursor;
//         MongoCollection<Document> allListings = mongodb.getAllListings();

//         cursor = allListings.find(eq("reviews", elemMatch("fieldName"))).projection(fields(include("name", "summary", "review_scores", "reviews", "property_type", "amenities", "accommodates", "host.host_is_superhost", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
//         ArrayList<String> result = new ArrayList<String>();
//         try {
//          while (cursor.hasNext()) {
//             Document record = cursor.next();
//             String rec_name = record.get("name").toString();
//             result.add(final_result);
//          }
//       }
//       finally {
//          cursor.close();
//       }
//         return false;
//     }
// }