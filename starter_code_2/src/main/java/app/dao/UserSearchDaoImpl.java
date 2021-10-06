package app.dao;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import org.bson.Document;


import java.util.ArrayList;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import app.MongoDBConnection;
public class UserSearchDaoImpl {
    public ArrayList<String> getSearchDB(String location, String market, String accomodates, String bedroom, String bed, String ptype, ArrayList<String> al, String price, String review, String host, String partial){
        MongoDBConnection mongodb = MongoDBConnection.getConnection();
        System.out.println(location);
        System.out.println(review);
        MongoCursor<Document> cursor;
        MongoCollection<Document> allListings = mongodb.getAllListings();

        //String column[] = {"address.country","accommodates","bedrooms","beds","property_type","amenities","price","host.host_is_superhost","review_scores.review_scores_rating"};
        String values[] = {location,market,accomodates,bedroom,bed,ptype,price,host,partial};
        int pagesize = 10;
        int n = 1;
        int not_nul=0;
        Long total_records=0l;
        for(int i=0;i<values.length;i++){
            if(values[i]!=null && !values[i].isEmpty()){
                not_nul++;
            }
        }
        if(al!=null && !al.isEmpty()){
            not_nul++;
        }
        if(Integer.parseInt(review)!=0){
            not_nul++;
        }
        System.out.println("not null: "+not_nul);




        // For single Search
        if(not_nul==1){
            //Location
            if(location!=null && !location.isEmpty()){
                cursor = allListings.find(eq("address.country", location)).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("This is Location");
            }
            //market
            else if(market!=null && !market.isEmpty()){
                cursor = allListings.find(eq("address.market", market)).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("This is market");
            }
            //Accommodates
            else if(accomodates!=null && !accomodates.isEmpty()){
                cursor = allListings.find(eq("accommodates", Integer.parseInt(accomodates))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("This is Accommodates");
            
            }
            //Bedrooms
            else if(bedroom!=null && !bedroom.isEmpty()){
                cursor = allListings.find(eq("bedrooms", Integer.parseInt(bedroom))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("This is Bedrooms");
            
            }
            //Beds
            else if(bedroom!=null && !bedroom.isEmpty()){
                cursor = allListings.find(eq("beds", Integer.parseInt(bed))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("This is Beds");
            
            }
            //ptype
            else if(ptype!=null && !ptype.isEmpty()){
                cursor = allListings.find(eq("property_type", ptype)).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("This is ptype");
            
            }
            //amenities
            else if(al!=null && !al.isEmpty()){
                cursor = allListings.find(in("amenities", al)).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("This is amenities");
            }
            //price
            else if(price!=null && !price.isEmpty()){
                cursor = allListings.find(lte("price", Integer.parseInt(price))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("This is price");
            } 
            //host
            else if(host!=null && !host.isEmpty()){
                if(host.equals("super")){
                    System.out.println("true");
                    cursor = allListings.find(eq("host.host_is_superhost", true)).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    System.out.println("false");
                    cursor = allListings.find(eq("host.host_is_superhost", false)).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("This is host");
            } 
            //review
            else if(review!=null && !review.isEmpty()){
                cursor = allListings.find(gte("review_scores.review_scores_rating", Integer.parseInt(review))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("This is review");
            } 
            //partial 
            //Fix this
            else{
                cursor = allListings.find(gte("review_scores.review_scores_rating", Integer.parseInt(review))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("This is partial");
            }
        }

        //For pair search
        else if(not_nul==2){
            //Location and market
            if(location!=null && !location.isEmpty() && market!=null && !market.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("address.market", market))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("Location, market");
            }
            //Location and guests
            else if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("Location, guests");
            }
            //Location and bedroom
            else if(location!=null && !location.isEmpty() && bedroom!=null && !bedroom.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("bedrooms", Integer.parseInt(bedroom)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("Location, bedroom");
            }
            //Location and bed
            else if(location!=null && !location.isEmpty() && bed!=null && !bed.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("beds", Integer.parseInt(bed)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("Location, bed");
            }
            //Location and ptype
            else if(location!=null && !location.isEmpty() && ptype!=null && !ptype.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("property_type", ptype))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("Location, ptype");
            }
            //Location and amenities
            else if(location!=null && !location.isEmpty() && al!=null && !al.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), in("amenities", al))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("Location, amenities");
            }
            //Location and price
            else if(location!=null && !location.isEmpty() && price!=null && !price.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), lte("price", Integer.parseInt(price)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("Location, price");
            }
            //Location and host
            else if(location!=null && !location.isEmpty() && host!=null && !host.isEmpty()){
                if(host=="Super-Host"){
                    cursor = allListings.find(and(eq("address.country", location), eq("host.host_is_superhost", true))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    cursor = allListings.find(and(eq("address.country", location), eq("host.host_is_superhost", false))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("location, host");
            }
            //Location and review
            else if(location!=null && !location.isEmpty() && review!=null && !review.isEmpty()){
                total_records = allListings.countDocuments(and(eq("address.country", location), gte("review_scores.review_scores_rating", Integer.parseInt(review))));
                cursor = allListings.find(and(eq("address.country", location), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("location, review");
            }
            //location , partial
            //Fix this
            else if(location!=null && !location.isEmpty() && partial!=null && !partial.isEmpty()){
                total_records = allListings.countDocuments(and(eq("address.country", location), gte("review_scores.review_scores_rating", Integer.parseInt(review))));
                cursor = allListings.find(and(eq("address.country", location), eq("", review))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("location, partial");
            
                
            }
            //Changes again
            //market, accomodates
            else if(market!=null && !market.isEmpty() && accomodates!=null && !accomodates.isEmpty()){
                total_records = allListings.countDocuments(and(eq("address.market", market), eq("accommodates", Integer.parseInt(accomodates))));
                cursor = allListings.find(and(eq("address.market", market), eq("accommodates", Integer.parseInt(accomodates)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("market, accomodates");
            }
            //market, beds
            else if(market!=null && !market.isEmpty() && bed!=null && !bed.isEmpty()){
                total_records = allListings.countDocuments(and(eq("address.market", market), eq("beds", Integer.parseInt(bed))));
                cursor = allListings.find(and(eq("address.market", market), eq("beds", Integer.parseInt(bed)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("market, beds");
            }
            //market, ptype
            else if(market!=null && !market.isEmpty() && ptype!=null && !ptype.isEmpty()){
                total_records = allListings.countDocuments(and(eq("address.market", market), eq("property_type", ptype)));
                cursor = allListings.find(and(eq("address.market", market), eq("property_type", ptype))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("market, ptype");
            }
            //market, amenities
            else if(market!=null && !market.isEmpty() && al!=null && !al.isEmpty()){
                total_records = allListings.countDocuments(and(eq("address.market", market), in("amenities", al)));
                cursor = allListings.find(and(eq("address.market", market), in("amenities", al))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("market, amenities");
            }
            //market, price
            else if(market!=null && !market.isEmpty() && price!=null && !price.isEmpty()){
                total_records = allListings.countDocuments(and(eq("address.market", market), eq("price", Integer.parseInt(price))));
                cursor = allListings.find(and(eq("address.market", market), eq("price", Integer.parseInt(price)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("market, price");
            }
            //market, host
            else if(market!=null && !market.isEmpty() && host!=null && !host.isEmpty()){
                if(host=="Super-Host"){
                    total_records = allListings.countDocuments(and(eq("address.market", market), eq("host.host_is_superhost", true)));
                    cursor = allListings.find(and(eq("address.market", market), eq("host.host_is_superhost", true))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    total_records = allListings.countDocuments(and(eq("address.market", market), eq("host.host_is_superhost", false)));
                    cursor = allListings.find(and(eq("address.market", market), eq("host.host_is_superhost", false))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("market, host");
            }
            //market, review
            else if(market!=null && !market.isEmpty() && review!=null && !review.isEmpty()){
                total_records = allListings.countDocuments(and(eq("address.market", market), gte("review_scores.review_scores_rating", Integer.parseInt(review))));
                cursor = allListings.find(and(eq("address.market", market), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("market, review");
            }
            //market, partial
            //Fix this
            else if(market!=null && !market.isEmpty() && partial!=null && !partial.isEmpty()){
                total_records = allListings.countDocuments(and(eq("address.market", market), eq("", partial)));
                cursor = allListings.find(and(eq("address.market", market), eq("", partial))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("market, partial");
            }
            //Changes again
            //accomodates, bedroom
            else if(accomodates!=null && !accomodates.isEmpty() && bedroom!=null && !bedroom.isEmpty()){
                cursor = allListings.find(and(eq("accommodates", Integer.parseInt(accomodates)), eq("bedrooms", Integer.parseInt(bedroom)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("market, bedroom");

            }
            //accomodates, bed
            else if(accomodates!=null && !accomodates.isEmpty() && bed!=null && !bed.isEmpty()){
                cursor = allListings.find(and(eq("accommodates", Integer.parseInt(accomodates)),eq("beds", Integer.parseInt(bed)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("market, beds");

            }//accomodates, ptype
            else if(accomodates!=null && !accomodates.isEmpty() && ptype!=null && !ptype.isEmpty()){
                cursor = allListings.find(and(eq("accommodates", Integer.parseInt(accomodates)),eq("property_type", ptype))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("market, beds");
            }
            //accomodates, amenities
            else if(accomodates!=null && !accomodates.isEmpty() && al!=null && !al.isEmpty()){
                cursor = allListings.find(and(eq("accommodates", Integer.parseInt(accomodates)), in("amenities", al))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("market, beds");

            }
            //accomodates, price
            else if(accomodates!=null && !accomodates.isEmpty() && price!=null && !price.isEmpty()){
                cursor = allListings.find(and(eq("accommodates", Integer.parseInt(accomodates)), lte("price", Integer.parseInt(price)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("market, beds");

            }
            //accomodates, host
            else if(accomodates!=null && !accomodates.isEmpty() && host!=null && !host.isEmpty()){
                if(host=="Super-Host"){
                    cursor = allListings.find(and(eq("accommodates", Integer.parseInt(accomodates)), eq("host.host_is_superhost", true))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    cursor = allListings.find(and(eq("accommodates", Integer.parseInt(accomodates)), eq("host.host_is_superhost", false))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("market, beds");
            }
            //accomodates, review
            else if(accomodates!=null && !accomodates.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("accommodates", Integer.parseInt(accomodates)), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("market, beds");
            }
            //accomodation, partial
            //Fix this
            else if(accomodates!=null && !accomodates.isEmpty() && partial!=null && !partial.isEmpty()){
                total_records = allListings.countDocuments(and(eq("accommodates", Integer.parseInt(accomodates)), eq("", partial)));
                cursor = allListings.find(and(eq("accommodates", Integer.parseInt(accomodates)), eq("", partial))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println(total_records);
                System.out.println("market, beds");
            }
            //Changes again
            //bedroom, bed
            else if(bedroom!=null && !bedroom.isEmpty() && bed!=null && !bed.isEmpty()){
                total_records = allListings.countDocuments(and(eq("bedrooms", Integer.parseInt(bedroom)), eq("beds", Integer.parseInt(bed))));
                cursor = allListings.find(and(eq("bedrooms", Integer.parseInt(bedroom)), eq("beds", Integer.parseInt(bed)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("market, beds");
            }
            //bedroom,ptype
            else if(bedroom!=null && !bedroom.isEmpty() && ptype!=null && !ptype.isEmpty()){
                total_records = allListings.countDocuments(and(eq("bedrooms", Integer.parseInt(bedroom)), eq("property_type", ptype)));
                cursor = allListings.find(and(eq("bedrooms", Integer.parseInt(bedroom)), eq("property_type", ptype))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("market, beds");
            }
            //bedroom, ameninities
            else if(bedroom!=null && !bedroom.isEmpty() && al!=null && !al.isEmpty()){
                total_records = allListings.countDocuments(and(eq("bedrooms", Integer.parseInt(bedroom)), in("amenities", al)));
                cursor = allListings.find(and(eq("bedrooms", Integer.parseInt(bedroom)), in("amenities", al))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For bedroom, amenities");
                System.out.println(total_records);
            }
            //bedroom, price
            else if(bedroom!=null && !bedroom.isEmpty() && price!=null && !price.isEmpty()){
                total_records = allListings.countDocuments(and(eq("bedrooms", Integer.parseInt(bedroom)), lte("price", Integer.parseInt(price))));
                cursor = allListings.find(and(eq("bedrooms", Integer.parseInt(bedroom)), lte("price", Integer.parseInt(price)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For bedroom, price");
                System.out.println(total_records);
            }
            //bedroom, host
            else if(bedroom!=null && !bedroom.isEmpty() && host!=null && !host.isEmpty()){
                if(host=="Super-Host"){
                    total_records = allListings.countDocuments(and(eq("bedrooms", Integer.parseInt(bedroom)),eq("host.host_is_superhost", true)));
                    cursor = allListings.find(and(eq("bedrooms", Integer.parseInt(bedroom)), eq("host.host_is_superhost", true))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    total_records = allListings.countDocuments(and(eq("bedrooms", Integer.parseInt(bedroom)),eq("host.host_is_superhost", false)));
                    cursor = allListings.find(and(eq("bedrooms", Integer.parseInt(bedroom)), eq("host.host_is_superhost", false))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For bedroom, host");
                System.out.println(total_records);
            }
            //bedroom, review
            else if(bedroom!=null && !bedroom.isEmpty() && review!=null && !review.isEmpty()){
                total_records = allListings.countDocuments(and(eq("bedrooms", Integer.parseInt(bedroom)), gte("review_scores.review_scores_rating", Integer.parseInt(review))));
                cursor = allListings.find(and(eq("bedrooms", Integer.parseInt(bedroom)), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For bedroom, review");
                System.out.println(total_records);
            }
            //bedroom, partial
            //Fix this
            else if(bedroom!=null && !bedroom.isEmpty() && partial!=null && !partial.isEmpty()){
                total_records = allListings.countDocuments(and(eq("bedrooms", bedroom), eq("", partial)));
                cursor = allListings.find(and(eq("bedrooms", Integer.parseInt(bedroom)), eq("", partial))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For bedroom, partial");
                System.out.println(total_records);
            }
            //Changes again
            //beds, ptype
            else if(bed!=null && !bed.isEmpty() && ptype!=null && !ptype.isEmpty()){
                total_records = allListings.countDocuments(and(eq("beds", Integer.parseInt(bed)), eq("property_type", ptype)));
                cursor = allListings.find(and(eq("beds", Integer.parseInt(bed)), eq("property_type", ptype))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For beds, ptype");
                System.out.println(total_records);
            }
            //beds, amenities
            else if(bed!=null && !bed.isEmpty() && al!=null && !al.isEmpty()){
                total_records = allListings.countDocuments(and(eq("beds", Integer.parseInt(bed)), in("amenities", al)));
                cursor = allListings.find(and(eq("beds", Integer.parseInt(bed)), in("amenities", al))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For beds, amenities");
                System.out.println(total_records);
            }
            //beds, price
            else if(bed!=null && !bed.isEmpty() && price!=null && !price.isEmpty()){
                total_records = allListings.countDocuments(and(eq("beds", Integer.parseInt(bed)), lte("price", Integer.parseInt(price))));
                cursor = allListings.find(and(eq("beds", Integer.parseInt(bed)), lte("price", Integer.parseInt(price)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For beds, price");
                System.out.println(total_records);
            }
            //beds, host
            else if(bed!=null && !bed.isEmpty() && host!=null && !host.isEmpty()){
                if(host=="Super-Host"){
                    total_records = allListings.countDocuments(and(eq("beds", Integer.parseInt(bed)), eq("host.host_is_superhost", true)));
                    cursor = allListings.find(and(eq("beds", Integer.parseInt(bed)), eq("host.host_is_superhost", true))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    total_records = allListings.countDocuments(and(eq("beds", Integer.parseInt(bed)), eq("host.host_is_superhost", false)));
                    cursor = allListings.find(and(eq("beds", Integer.parseInt(bed)), eq("host.host_is_superhost", false))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For beds, host");
                System.out.println(total_records);
            }
            //beds, review
            else if(bed!=null && !bed.isEmpty() && review!=null && !review.isEmpty()){
                total_records = allListings.countDocuments(and(eq("beds", Integer.parseInt(bed)), gte("review_scores.review_scores_rating", Integer.parseInt(review))));
                cursor = allListings.find(and(eq("beds", Integer.parseInt(bed)), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For beds, review");
                System.out.println(total_records);
            }
            //beds, partial
            //Fix this
            else if(bed!=null && !bed.isEmpty() && partial!=null && !partial.isEmpty()){
                total_records = allListings.countDocuments(and(eq("beds", Integer.parseInt(bed)), eq("", partial)));
                cursor = allListings.find(and(eq("beds", Integer.parseInt(bed)), eq("", partial))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For beds, partial");
                System.out.println(total_records);
            }
            //Changes again
            //ptype, amenities
            else if(ptype!=null && !ptype.isEmpty() && al!=null && !al.isEmpty()){
                total_records = allListings.countDocuments(and(eq("property_type", ptype), in("amenities", al)));
                cursor = allListings.find(and(eq("property_type", ptype), in("amenities", al))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For property_type, al");
                System.out.println(total_records);
            }
            //ptype, price
            else if(ptype!=null && !ptype.isEmpty() && price!=null && !price.isEmpty()){
                total_records = allListings.countDocuments(and(eq("property_type", ptype), lte("price", Integer.parseInt(price))));
                cursor = allListings.find(and(eq("property_type", ptype), lte("price", Integer.parseInt(price)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For property_type, price");
                System.out.println(total_records);
            }
            //ptype, host
            else if(ptype!=null && !ptype.isEmpty() && price!=null && !price.isEmpty()){
                if(host=="Super-Host"){
                    total_records = allListings.countDocuments(and(eq("property_type", ptype), eq("host.host_is_superhost", true)));
                    cursor = allListings.find(and(eq("property_type", ptype), eq("host.host_is_superhost", true))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    total_records = allListings.countDocuments(and(eq("property_type", ptype), eq("host.host_is_superhost", false)));
                    cursor = allListings.find(and(eq("property_type", ptype), eq("host.host_is_superhost", false))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For property_type, host");
                System.out.println(total_records);
            }
            //ptype, review
            else if(ptype!=null && !ptype.isEmpty() && review!=null && !review.isEmpty()){
                total_records = allListings.countDocuments(and(eq("property_type", ptype), gte("review_scores.review_scores_rating", Integer.parseInt(review))));
                cursor = allListings.find(and(eq("property_type", ptype), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For property_type, review");
                System.out.println(total_records);
            }
            //ptype, partial
            //Fix this
            else if(ptype!=null && !ptype.isEmpty() && partial!=null && !partial.isEmpty()){
                total_records = allListings.countDocuments(and(eq("property_type", ptype), eq("", partial)));
                cursor = allListings.find(and(eq("property_type", ptype), eq("", partial))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For property_type, partial");
                System.out.println(total_records);
            }
            //Changes again
            //amenities, price
            else if(al!=null && !al.isEmpty() && price!=null && !price.isEmpty()){
                total_records = allListings.countDocuments(and(in("amenities", al), lte("price", Integer.parseInt(price))));
                cursor = allListings.find(and(in("amenities", al), lte("price", Integer.parseInt(price)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For amenities, price");
                System.out.println(total_records);
            }
            //amenities, host
            else if(al!=null && !al.isEmpty() && host!=null && !host.isEmpty()){
                if(host=="Super-Host"){
                    total_records = allListings.countDocuments(and(in("amenities", al), eq("host.host_is_superhost", true)));
                    cursor = allListings.find(and(in("amenities", al), eq("host.host_is_superhost", true))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    total_records = allListings.countDocuments(and(in("amenities", al), eq("host.host_is_superhost", false)));
                    cursor = allListings.find(and(in("amenities", al), eq("host.host_is_superhost", false))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For amenities, host");
                System.out.println(total_records);
            }
            //amenities, review
            else if(al!=null && !al.isEmpty() && review!=null && !review.isEmpty()){
                total_records = allListings.countDocuments(and(in("amenities", al), gte("review_scores.review_scores_rating", Integer.parseInt(review))));
                cursor = allListings.find(and(in("amenities", al), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For amenities, review");
                System.out.println(total_records);
            }
            //amenities, partial
            //Fix this
            else if(al!=null && !al.isEmpty() && partial!=null && !partial.isEmpty()){
                total_records = allListings.countDocuments(and(in("amenities", al), eq("", partial)));
                cursor = allListings.find(and(in("amenities", al), eq("", partial))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For amenities, partial");
                System.out.println(total_records);
            }
            //Changes again
            //price, host
            else if(price!=null && !price.isEmpty() && host!=null && !host.isEmpty()){
                if(host=="Super-Host"){
                    total_records = allListings.countDocuments(and(lte("price", Integer.parseInt(price)), eq("host.host_is_superhost", true)));
                    cursor = allListings.find(and(lte("price", Integer.parseInt(price)), eq("host.host_is_superhost", true))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    total_records = allListings.countDocuments(and(lte("price", Integer.parseInt(price)), eq("host.host_is_superhost", false)));
                    cursor = allListings.find(and(lte("price", Integer.parseInt(price)), eq("host.host_is_superhost", false))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For price, host");
                System.out.println(total_records);
            }
            //price, review
            else if(price!=null && !price.isEmpty() && review!=null && !review.isEmpty()){
                total_records = allListings.countDocuments(and(lte("price", Integer.parseInt(price)), gte("review_scores.review_scores_rating", Integer.parseInt(review))));
                cursor = allListings.find(and(lte("price", Integer.parseInt(price)), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For price, review");
                System.out.println(total_records);
            }
            //price, partial
            // Fix this
            else if(price!=null && !price.isEmpty() && partial!=null && !partial.isEmpty()){
                total_records = allListings.countDocuments(and(lte("price", Integer.parseInt(price)), eq("", partial)));
                cursor = allListings.find(and(lte("price", Integer.parseInt(price)), eq("", partial))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For price, partial");
                System.out.println(total_records);
            }
            //Changes again
            //host, review
            else if(host!=null && !host.isEmpty() && review!=null && !review.isEmpty()){
                if(host=="Super-Host"){
                    total_records = allListings.countDocuments(and(eq("host.host_is_superhost", true), gte("review_scores.review_scores_rating", Integer.parseInt(review))));
                    cursor = allListings.find(and(eq("host.host_is_superhost", true), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    total_records = allListings.countDocuments(and(eq("host.host_is_superhost", false), gte("review_scores.review_scores_rating", Integer.parseInt(review))));
                    cursor = allListings.find(and(eq("host.host_is_superhost", false), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For host, review");
                System.out.println(total_records);
            }
            //host, partial
            //Fix this
            else if(host!=null && !host.isEmpty() && partial!=null && !partial.isEmpty()){
                if(host=="Super-Host"){
                    total_records = allListings.countDocuments(and(eq("host.host_is_superhost", true),eq("", partial)));
                    cursor = allListings.find(and(eq("host.host_is_superhost", true), eq("", partial))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    total_records = allListings.countDocuments(and(eq("host.host_is_superhost", false), eq("", partial)));
                    cursor = allListings.find(and(eq("host.host_is_superhost", false), eq("", partial))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For host, partial");
                System.out.println(total_records);
            }
            //Changes again last time
            //review, partial
            //Fix this
            else{
                total_records = allListings.countDocuments(and(gte("review_scores.review_scores_rating", Integer.parseInt(review)),eq("", partial)));
                cursor = allListings.find(and(gte("review_scores.review_scores_rating", Integer.parseInt(review)),eq("", partial))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For review, partial");
                System.out.println(total_records);
            }
        }
    



        //For Three search
        else if(not_nul==3){

            //For location, market, review
            if(location!=null && !location.isEmpty() && market!=null && !market.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("market", Integer.parseInt(market)), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location,market, review");
            }
            // For location,accomodation, review
            else if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location,accomodation, review");
            }
            // For location,bedroom, review
            else if(location!=null && !location.isEmpty() && bedroom!=null && !bedroom.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("bedrooms", Integer.parseInt(bedroom)), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location,bedroom, reviews");
            }
            // For location,bed, review
            else if(location!=null && !location.isEmpty() && bed!=null && !bed.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("beds", Integer.parseInt(bed)), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location,beds, review");
            }
            // For location,ptype, review
            else if(location!=null && !location.isEmpty() && ptype!=null && !ptype.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("property_type", ptype), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location,ptype, review");
            }
            // For location,amenities, review
            else if(location!=null && !location.isEmpty() && al!=null && !al.isEmpty() && review!=null && !review.isEmpty()){
                //Fix this
                cursor = allListings.find(and(eq("address.country", location), in("amenities", al), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location,amenities, review");
            }
            // For location,price, review
            else if(location!=null && !location.isEmpty() && price!=null && !price.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), lte("price", Integer.parseInt(price)), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location,price, review");
            }
            //For location, host review
            else if(location!=null && !location.isEmpty() && host!=null && !host.isEmpty() && review!=null && !review.isEmpty()){
                if(host.equals("super")){
                    System.out.println("true");
                    cursor = allListings.find(and(eq("address.country", location), eq("host.host_is_superhost", true), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    System.out.println("false");
                    cursor = allListings.find(and(eq("address.country", location), eq("host.host_is_superhost", false), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("line 83");
                System.out.println("For location,host, review");
            }
            //For location, partial, review
            //Fix this
            else{
                cursor = allListings.find(and(eq("address.country", location), eq("", partial), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, partial, review");
            }
        }






        // For 4 Search Criteria
        else if(not_nul==4){
            //Location and Reviews are mandatory
            //for location, market, people, review
            if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty() && market!=null && !market.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("address.market", market), eq("accommodates", Integer.parseInt(accomodates)),  gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, market, people, review");
            }
            //for location, market, bedroom, review
            else if(location!=null && !location.isEmpty() && market!=null && !market.isEmpty() && bedroom!=null && !bedroom.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("address.market", market), eq("bedrooms", Integer.parseInt(bedroom)),  gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, market, bedroom, review");
            }
            //for location, market, bed, review
            else if(location!=null && !location.isEmpty() && market!=null && !market.isEmpty() && bed!=null && !bed.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("address.market", market), eq("beds", Integer.parseInt(bed)),  gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, market, beds, review");
            }
            //for location, market, ptype, review
            else if(location!=null && !location.isEmpty() && market!=null && !market.isEmpty() && ptype!=null && !ptype.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("address.market", market), eq("property_type", ptype),  gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, market, ptype, review"); 
            }
            //for location, market, amenities, review
            else if(location!=null && !location.isEmpty() && market!=null && !market.isEmpty() && al!=null && !al.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("address.market", market), in("amenities", al),  gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, market, amenities, review"); 
            }
            //for location, market, price, review
            else if(location!=null && !location.isEmpty() && market!=null && !market.isEmpty() && price!=null && !price.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("address.market", market), lte("price", Integer.parseInt(price)),  gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, market, price, review"); 
            }
            //changes again
            //for location, market, host, review
            else if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty() && host!=null && !host.isEmpty() && review!=null && !review.isEmpty()){
                if(host=="super"){
                    cursor = allListings.find(and(eq("address.country", location), eq("address.market", market), eq("host.host_is_superhost", true),  gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();

                }
                else{
                    cursor = allListings.find(and(eq("address.country", location), eq("address.market", market), eq("host.host_is_superhost", false),  gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For location, market, host, review"); 
            }
            //for location, market, partial, review
            //Fix this
            else if(location!=null && !location.isEmpty() && market!=null && !market.isEmpty() && partial!=null && !partial.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location),eq("address.market", market), lte("", partial),  gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, market, partial, review"); 
            }
            //for location, people, bedroom, review
            else if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty() && bedroom!=null && !bedroom.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), eq("bedrooms", Integer.parseInt(bedroom)),  gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, people, bedrooms, review");
            }
            //for location, people, beds, review
            else if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty() && bed!=null && !bed.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), eq("beds", Integer.parseInt(bed)),  gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, people, beds, review");
            }
            //for location, people, ptype, review
            else if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty() && ptype!=null && !ptype.isEmpty() && review!=null && !review.isEmpty()){
                System.out.println("1");
                cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), eq("property_type", ptype),  gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, people, ptype, review"); 
            }
            //for location, people, amenities, review
            else if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty() && al!=null && !al.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), in("amenities", al),  gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, people, amenities, review"); 
            }
            //for location, people, price, review
            else if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty() && price!=null && !price.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), lte("price", Integer.parseInt(price)),  gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, people, price, review"); 
            }
            //changes again
            //for location, people, host, review
            else if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty() && host!=null && !host.isEmpty() && review!=null && !review.isEmpty()){
                if(host=="super"){
                    cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), eq("host.host_is_superhost", true),  gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();

                }
                else{
                    cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), eq("host.host_is_superhost", false),  gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For location, people, host, review"); 
            }
            //for location, people, partial, review
            //Fix this
            else if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty() && partial!=null && !partial.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), lte("", partial),  gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, people, partial, review"); 
            }
            //Changes again
            //for location, bedroom, bed, review
            else if(location!=null && !location.isEmpty() && bedroom!=null && !bedroom.isEmpty() && bed!=null && !bed.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("bedrooms", Integer.parseInt(bedroom)), eq("beds", Integer.parseInt(bed)),  gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, bedroom, bed, review"); 
            }
            //for location, bedroom, ptype, review
            else if(location!=null && !location.isEmpty() && bedroom!=null && !bedroom.isEmpty() && ptype!=null && !ptype.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("bedroom", Integer.parseInt(bedroom)), eq("property_type", ptype),  gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, bedroom, ptype, review"); 
            }
            //For location, bedroom, amenities, review
            else if(location!=null && !location.isEmpty() && bedroom!=null && !bedroom.isEmpty() && al!=null && !al.isEmpty() && review!=null && !review.isEmpty()){
                //Fix this
                cursor = allListings.find(and(eq("address.country", location), eq("bedroom", Integer.parseInt(bedroom)), in("amenities", al),  gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, bedroom, amenities, review"); 
            }
            //For location, bedroom, price, review
            else if(location!=null && !location.isEmpty() && bedroom!=null && !bedroom.isEmpty() && price!=null && !price.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("bedroom", Integer.parseInt(bedroom)), lte("price", Integer.parseInt(price)),  gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, bedroom, amenities, price"); 
            }
            //for location, bedroom, host, review
            else if(location!=null && !location.isEmpty() && bedroom!=null && !bedroom.isEmpty() && host!=null && !host.isEmpty() && review!=null && !review.isEmpty()){
                if(host=="super"){
                    cursor = allListings.find(and(eq("address.country", location), eq("bedroom", Integer.parseInt(bedroom)), eq("host.host_is_superhost", true),  gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();

                }
                else{
                    cursor = allListings.find(and(eq("address.country", location), eq("bedroom", Integer.parseInt(bedroom)), eq("host.host_is_superhost", false),  gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For location, bedroom, host, review"); 
            }
            //Fix this
            //for location, bedroom, partial, review
            else if(location!=null && !location.isEmpty() && bedroom!=null && !bedroom.isEmpty() && partial!=null && !partial.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("bedrooms", Integer.parseInt(bedroom)), eq("", partial),  gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, people, partial, review"); 
            }
            //Changes again
            //for location, bed, ptype, review
            else if(location!=null && !location.isEmpty() && bed!=null && !bed.isEmpty() && ptype!=null && !ptype.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("beds", Integer.parseInt(bed)), eq("property_type", ptype),  gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, bedroom, ptype, price"); 
            }
            //for location,bed, amenities, review
            else if(location!=null && !location.isEmpty() && bed!=null && !bed.isEmpty() && al!=null && !al.isEmpty() && review!=null && !review.isEmpty()){
                //Fix this
                cursor = allListings.find(and(eq("address.country", location), eq("beds", Integer.parseInt(bed)), in("amenities", al),  gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, bed, amenities, price"); 
            }
            //for location, bed, price, review
            else if(location!=null && !location.isEmpty() && bed!=null && !bed.isEmpty() && price!=null && !price.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("beds", Integer.parseInt(bed)), lte("price", Integer.parseInt(price)), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, bed, price, review"); 
            }
            //Changes again
            //for location, bed, host, review
            else if(location!=null && !location.isEmpty() && bed!=null && !bed.isEmpty() && price!=null && !price.isEmpty() && review!=null && !review.isEmpty()){
                if(host=="super"){
                    cursor = allListings.find(and(eq("address.country", location), eq("beds", Integer.parseInt(bed)), eq("host.host_is_superhost", true), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    cursor = allListings.find(and(eq("address.country", location), eq("beds", Integer.parseInt(bed)), eq("host.host_is_superhost", false), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For location, bed, host, review"); 
            }
            //Fix this
            //for location, bed, partial, review
            else if(location!=null && !location.isEmpty() && bed!=null && !bed.isEmpty() && partial!=null && !partial.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("beds", Integer.parseInt(bed)), eq("", partial),  gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, bed, partial, review"); 
            }
            //big change
            //for location, ptype, amenities, review
            else if(location!=null && !location.isEmpty() && ptype!=null && !ptype.isEmpty() && al!=null && !al.isEmpty() && review!=null && !review.isEmpty()){
                //Fix this
                cursor = allListings.find(and(eq("address.country", location), eq("property_type", ptype), in("amenities", al), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, ptype, amenities, review"); 
            }
            //for location, ptype, price, review
            else if(location!=null && !location.isEmpty() && ptype!=null && !ptype.isEmpty() && price!=null && !price.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("property_type", ptype), lte("price", Integer.parseInt(price)), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, ptype, price, review"); 
            }
            //for location, ptype, host, review
            else if(location!=null && !location.isEmpty() && ptype!=null && !ptype.isEmpty() && host!=null && !host.isEmpty() && review!=null && !review.isEmpty()){
                if(host=="super"){
                    cursor = allListings.find(and(eq("address.country", location), eq("ptype", ptype), eq("host.host_is_superhost", true), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    cursor = allListings.find(and(eq("address.country", location), eq("ptype", ptype), eq("host.host_is_superhost", false), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For location, ptype, host, review"); 
            }
            //Fix this
            //for location, ptype, partial, review
            else if(location!=null && !location.isEmpty() && ptype!=null && !ptype.isEmpty() && partial!=null && !partial.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("property_type", ptype), eq("", partial),  gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, ptype, partial, review"); 
            }
            //Changes again
            //for location, amenities, price, review
            else if(location!=null && !location.isEmpty() && al!=null && !al.isEmpty() && price!=null && !price.isEmpty() && review!=null && !review.isEmpty()){
                //Fix this
                cursor = allListings.find(and(eq("address.country", location), in("amenities", al), lte("price", Integer.parseInt(price)), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, amenities, price, review"); 
            }
            //for location, amenities, host, review
            else if(location!=null && !location.isEmpty() && al!=null && !al.isEmpty() && price!=null && !price.isEmpty() && review!=null && !review.isEmpty()){
                //Fix this
                if(host=="super"){
                    cursor = allListings.find(and(eq("address.country", location), in("amenities", al), eq("host.host_is_superhost", true), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    cursor = allListings.find(and(eq("address.country", location), in("amenities", al), eq("host.host_is_superhost", false), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For location, amenities, host, review"); 
            }
            //Fix this
            //for location, amenities, partial, review
            else if(location!=null && !location.isEmpty() && al!=null && !al.isEmpty() && partial!=null && !partial.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), in("amenities", al), eq("", partial),  gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, amenities, partial, review"); 
            }
            //Changes again
            //Fix this
            //for location, price, partial, review
            else if(location!=null && !location.isEmpty() && price!=null && !price.isEmpty() && partial!=null && !partial.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("price", Integer.parseInt(price)), eq("", partial),  gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, price, partial, review"); 
            }
            //for location, price, host, review
            else{
                if(host=="super"){
                    cursor = allListings.find(and(eq("address.country", location), lte("price", Integer.parseInt(price)), eq("host.host_is_superhost", true), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    cursor = allListings.find(and(eq("address.country", location), lte("price", Integer.parseInt(price)), eq("host.host_is_superhost", false), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For location, price, host, review"); 
            }
        }
        



            

        // For 5 Search Criteria
        else if(not_nul==5){
            //for location, market, accomodates, bedrooms, review
            if(location!=null && !location.isEmpty() && market!=null && !market.isEmpty() && accomodates!=null && !accomodates.isEmpty() && bedroom!=null && !bedroom.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq(eq("address.market", market)), eq("accommodates", Integer.parseInt(accomodates)), eq("bedrooms", Integer.parseInt(bedroom)),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, market, accomodates, bedrooms, review");
            }
            //for location, market, accomodates, beds, review
            else if(location!=null && !location.isEmpty() && market!=null && !market.isEmpty() && accomodates!=null && !accomodates.isEmpty() && bed!=null && !bed.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq(eq("address.market", market)), eq("accommodates", Integer.parseInt(accomodates)), eq("beds", Integer.parseInt(bed)),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, market, accomodates, beds, review");
            }
            //for location, market, accomodates, ptype, review
            else if(location!=null && !location.isEmpty() && market!=null && !market.isEmpty() && accomodates!=null && !accomodates.isEmpty() && ptype!=null && !ptype.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq(eq("address.market", market)), eq("accommodates", Integer.parseInt(accomodates)), eq("property_type", ptype),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, market, accomodates, ptype, review");
            }
            //for location, market, accomodates, amenities, review
            else if(location!=null && !location.isEmpty() && market!=null && !market.isEmpty() && accomodates!=null && !accomodates.isEmpty() && al!=null && !al.isEmpty() && review!=null && !review.isEmpty()){
                //Fix this
                cursor = allListings.find(and(eq("address.country", location), eq(eq("address.market", market)), eq("accommodates", Integer.parseInt(accomodates)),  in("amenities", al),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, market, accomodates, amenities, review");
            }
            //for location, market, accomodates, price, review
            else if(location!=null && !location.isEmpty() && market!=null && !market.isEmpty() && accomodates!=null && !accomodates.isEmpty() && price!=null && !price.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq(eq("address.market", market)), eq("accommodates", Integer.parseInt(accomodates)),  lte("price", Integer.parseInt(price)),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, market, accomodates, price, review");
            }
            //for location, market, accomodates, host, review
            else if(location!=null && !location.isEmpty() && market!=null && !market.isEmpty() && accomodates!=null && !accomodates.isEmpty() && host!=null && !host.isEmpty() && review!=null && !review.isEmpty()){
                //Fix this
                if(host=="super"){
                    cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), eq("address.market", market), eq("host.host_is_superhost", true),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), eq("address.market", market), eq("host.host_is_superhost", false),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For location, market, accomodates, host, review");
            }
            //Fix this
            //for location, market, accomodates, partial, review
            else if(location!=null && !location.isEmpty() && market!=null && !market.isEmpty() && accomodates!=null && !accomodates.isEmpty() && partial!=null && !partial.isEmpty() && review!=null && !review.isEmpty()){
                //Fix this
                cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), eq("address.market", market), eq("", partial),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, market, accomodates, partial, review");
            }
            //changes 
            //for location, market, beds, ptype, review
            else if(location!=null && !location.isEmpty() && market!=null && !market.isEmpty() && bed!=null && !bed.isEmpty() && ptype!=null && !ptype.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("address.market", market),  eq("beds", Integer.parseInt(bed)),  eq("property_type", ptype),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, market, beds, ptype, review");
            }
            //for location, market, beds, amenities, review
            else if(location!=null && !location.isEmpty() && market!=null && !market.isEmpty() && bed!=null && !bed.isEmpty() && al!=null && !al.isEmpty() && review!=null && !review.isEmpty()){
                //fix this
                cursor = allListings.find(and(eq("address.country", location), eq("address.market", market),  eq("beds", Integer.parseInt(bed)),  in("amenities", al),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, market, beds, amenities, review");
            }
            //for location, market, beds, price, review
            else if(location!=null && !location.isEmpty() && market!=null && !market.isEmpty() && bed!=null && !bed.isEmpty() && price!=null && !price.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("address.market", market),  eq("beds", Integer.parseInt(bed)),  lte("price", Integer.parseInt(price)),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, market, beds, price, review");
            }
            //for location, market, beds, host, review
            else if(location!=null && !location.isEmpty() && market!=null && !market.isEmpty() && bed!=null && !bed.isEmpty() && host!=null && !host.isEmpty() && review!=null && !review.isEmpty()){
                if(host=="super"){
                    cursor = allListings.find(and(eq("address.country", location), eq("address.market", market),  eq("beds", Integer.parseInt(bed)),  eq("host.host_is_superhost", true),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    cursor = allListings.find(and(eq("address.country", location), eq("address.market", market),  eq("beds", Integer.parseInt(bed)),  eq("host.host_is_superhost", false),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For location, market, beds, host, review");
            }
            //Fix this
            //for location, market, beds, partial, review
            else if(location!=null && !location.isEmpty() && market!=null && !market.isEmpty() && bed!=null && !bed.isEmpty() && partial!=null && !partial.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("address.market", market),  eq("beds", Integer.parseInt(bed)),  eq("partial", partial),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, market, beds, partial, review");
            }
            //changes again
            //for location, market, ptype, amenities, review
            else if(location!=null && !location.isEmpty() && market!=null && !market.isEmpty() && ptype!=null && !ptype.isEmpty() && al!=null && !al.isEmpty() && review!=null && !review.isEmpty()){
                //fix this
                cursor = allListings.find(and(eq("address.country", location), eq("address.market", market),  eq("property_type", ptype),  in("amenities", al),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, market, ptype, amenities, review"); 
            }
            //for location, market, ptype, price, review
            else if(location!=null && !location.isEmpty() && market!=null && !market.isEmpty() && ptype!=null && !ptype.isEmpty() && price!=null && !price.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("address.market", market),  eq("propert_type", ptype),  lte("price", Integer.parseInt(price)),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, market, ptype, price, review"); 
            }
            //for location, market, ptype, host, review
            else if(location!=null && !location.isEmpty() && market!=null && !market.isEmpty() && ptype!=null && !ptype.isEmpty() && host!=null && !host.isEmpty() && review!=null && !review.isEmpty()){
                if(host=="super"){
                    cursor = allListings.find(and(eq("address.country", location), eq("address.market", market),  eq("propert_type", ptype),  eq("host.host_is_superhost", true),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    cursor = allListings.find(and(eq("address.country", location), eq("address.market", market),  eq("propert_type", ptype),  eq("host.host_is_superhost", false),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For location, market, ptype, host, review"); 
            }
            //Fix this
            //for location, market, ptype, partial, review
            else if(location!=null && !location.isEmpty() && market!=null && !market.isEmpty() && ptype!=null && !ptype.isEmpty() && partial!=null && !partial.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("address.market", market),  eq("propert_type", ptype),  eq("partial", partial),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, market, ptype, partial, review"); 
            }
            //changes again
            //but we have to fix this first
            //for location, market, amenities, price, review
            else if(location!=null && !location.isEmpty() && market!=null && !market.isEmpty() && al!=null && !al.isEmpty() && price!=null && !price.isEmpty() && review!=null && !review.isEmpty()){
                //fix this
                cursor = allListings.find(and(eq("address.country", location), eq("address.market", market),  in("amenities", al),  lte("price", Integer.parseInt(price)),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, market, amenities, price, review"); 
            }
            //for location, market, amenities, host, review
            else if(location!=null && !location.isEmpty() && market!=null && !market.isEmpty() && al!=null && !al.isEmpty() && price!=null && !price.isEmpty() && review!=null && !review.isEmpty()){
                //fix this
                if(host=="super"){
                    cursor = allListings.find(and(eq("address.country", location), eq("address.market", market),  in("amenities", al),  eq("host.host_is_superhost", true),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    cursor = allListings.find(and(eq("address.country", location), eq("address.market", market),  in("amenities", al),  eq("host.host_is_superhost", false),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For location, market, amenities, host, review"); 
            }
            //Fix this
            //for location, market, amenities, partial, review
            else if(location!=null && !location.isEmpty() && market!=null && !market.isEmpty() && al!=null && !al.isEmpty() && partial!=null && !partial.isEmpty() && review!=null && !review.isEmpty()){
                //fix this
                cursor = allListings.find(and(eq("address.country", location), eq("address.market", market),  in("amenities", al),  eq("partial", partial),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, market, amenities, partial, review"); 
            }
            //changes again
            //for location, market, price, host, review
            else if(location!=null && !location.isEmpty() && market!=null && !market.isEmpty() && price!=null && !price.isEmpty() && host!=null && !host.isEmpty() && review!=null && !review.isEmpty()){
                if(host=="super"){
                    cursor = allListings.find(and(eq("address.country", location), eq("address.market", market),  lte("price", Integer.parseInt(price)),  eq("host.host_is_superhost", true),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    cursor = allListings.find(and(eq("address.country", location), eq("address.market", market),  lte("price", Integer.parseInt(price)),  eq("host.host_is_superhost", false),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For location, market, price, host"); 
            }
            //Fix this
            //for location, market, price, partial, review
            else if(location!=null && !location.isEmpty() && market!=null && !market.isEmpty() && price!=null && !price.isEmpty() && partial!=null && !partial.isEmpty() && review!=null && !review.isEmpty()){
                //fix this
                cursor = allListings.find(and(eq("address.country", location), eq("address.market", market),  lte("price", Integer.parseInt(price)),  eq("partial", partial),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, market, price, partial, review"); 
            }
            //Big Change
            //Changes again
            //for location, people, bedroom, beds, review
            else if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty() && bedroom!=null && !bedroom.isEmpty() && bed!=null && !bed.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), eq("bedrooms", Integer.parseInt(bedroom)), eq("beds", Integer.parseInt(bed)),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, people, bedrooms, beds, review");
            }
            //for location, people, bedroom, ptype, review
            else if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty() && bedroom!=null && !bedroom.isEmpty() && ptype!=null && !ptype.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), eq("bedrooms", Integer.parseInt(bedroom)), eq("property_type", ptype),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, people, bedrooms, ptype, review");
            }
            //for location, people, bedroom, amenities, review
            else if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty() && bedroom!=null && !bedroom.isEmpty() && al!=null && !al.isEmpty() && review!=null && !review.isEmpty()){
                //Fix this
                cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), eq("bedrooms", Integer.parseInt(bedroom)), in("amenities", al),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, people, bedrooms, amenities, review");
            }
            //for location, people, bedroom, price, review
            else if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty() && bedroom!=null && !bedroom.isEmpty() && price!=null && !price.isEmpty() && review!=null && !review.isEmpty()){
                //Fix this
                cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), eq("bedrooms", Integer.parseInt(bedroom)), lte("price", Integer.parseInt(price)),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, people, bedrooms, price, review");
            }
            //for location, people, bedroom, host, review
            else if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty() && bedroom!=null && !bedroom.isEmpty() && host!=null && !host.isEmpty() && review!=null && !review.isEmpty()){
                //Fix this
                if(host=="super"){
                    cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), eq("bedrooms", Integer.parseInt(bedroom)), eq("host.host_is_superhost", true),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), eq("bedrooms", Integer.parseInt(bedroom)), eq("host.host_is_superhost", false),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For location, people, bedrooms, host, review");
            }
            //Fix this
            //for location, people, bedroom, partial, review
            else if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty() && bedroom!=null && !bedroom.isEmpty() && partial!=null && !partial.isEmpty() && review!=null && !review.isEmpty()){
                //Fix this
                cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), eq("bedrooms", Integer.parseInt(bedroom)), eq("", partial),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, people, bedrooms, price, review");
            }
            
            //changes 
            //for location, people, beds, ptype, review
            else if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty() && bed!=null && !bed.isEmpty() && ptype!=null && !ptype.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), eq("beds", Integer.parseInt(bed)),  eq("property_type", ptype),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, people, beds, ptype, review");
            }
            //for location, people, beds, amenities, review
            else if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty() && bed!=null && !bed.isEmpty() && al!=null && !al.isEmpty() && review!=null && !review.isEmpty()){
                //fix this
                cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), eq("beds", Integer.parseInt(bed)),  in("amenities", al),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, people, beds, amenities, review");
            }
            //for location, people, beds, price, review
            else if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty() && bed!=null && !bed.isEmpty() && price!=null && !price.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), eq("beds", Integer.parseInt(bed)),  lte("price", Integer.parseInt(price)),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, people, beds, price, review");
            }
            //for location, people, beds, host, review
            else if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty() && bed!=null && !bed.isEmpty() && host!=null && !host.isEmpty() && review!=null && !review.isEmpty()){
                if(host=="super"){
                    cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), eq("beds", Integer.parseInt(bed)),  eq("host.host_is_superhost", true),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), eq("beds", Integer.parseInt(bed)),  eq("host.host_is_superhost", false),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For location, people, beds, host, review");
            }
            //Fix this
            //for location, people, beds, partial, review
            else if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty() && bed!=null && !bed.isEmpty() && partial!=null && !partial.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), eq("beds", Integer.parseInt(bed)),  eq("partial", partial),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, people, beds, partial, review");
            }
            //changes again
            //for location, people, ptype, amenities, review
            else if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty() && ptype!=null && !ptype.isEmpty() && al!=null && !al.isEmpty() && review!=null && !review.isEmpty()){
                //fix this
                cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), eq("property_type", ptype),  in("amenities", al),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, people, ptype, amenities, review"); 
            }
            //for location, people, ptype, price, review
            else if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty() && ptype!=null && !ptype.isEmpty() && price!=null && !price.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), eq("propert_type", ptype),  lte("price", Integer.parseInt(price)),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, people, ptype, price, review"); 
            }
            //for location, people, ptype, host, review
            else if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty() && ptype!=null && !ptype.isEmpty() && host!=null && !host.isEmpty() && review!=null && !review.isEmpty()){
                if(host=="super"){
                    cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), eq("propert_type", ptype),  eq("host.host_is_superhost", true),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), eq("propert_type", ptype),  eq("host.host_is_superhost", false),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For location, people, ptype, host, review"); 
            }
            //Fix this
            //for location, people, ptype, partial, review
            else if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty() && ptype!=null && !ptype.isEmpty() && partial!=null && !partial.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), eq("propert_type", ptype),  eq("partial", partial),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, people, ptype, partial, review"); 
            }
            //changes again
            //but we have to fix this first
            //for location, people, amenities, price, review
            else if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty() && al!=null && !al.isEmpty() && price!=null && !price.isEmpty() && review!=null && !review.isEmpty()){
                //fix this
                cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), in("amenities", al),  lte("price", Integer.parseInt(price)),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, people, amenities, price, review"); 
            }
            //for location, people, amenities, host, review
            else if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty() && al!=null && !al.isEmpty() && price!=null && !price.isEmpty() && review!=null && !review.isEmpty()){
                //fix this
                if(host=="super"){
                    cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), in("amenities", al),  eq("host.host_is_superhost", true),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), in("amenities", al),  eq("host.host_is_superhost", false),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For location, people, amenities, host, review"); 
            }
            //Fix this
            //for location, people, amenities, partial, review
            else if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty() && al!=null && !al.isEmpty() && partial!=null && !partial.isEmpty() && review!=null && !review.isEmpty()){
                //fix this
                cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), in("amenities", al),  eq("partial", partial),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, people, amenities, partial, review"); 
            }
            //changes again
            //for location, people, price, host, review
            else if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty() && price!=null && !price.isEmpty() && host!=null && !host.isEmpty() && review!=null && !review.isEmpty()){
                if(host=="super"){
                    cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), lte("price", Integer.parseInt(price)),  eq("host.host_is_superhost", true),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), lte("price", Integer.parseInt(price)),  eq("host.host_is_superhost", false),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For location, people, price, host"); 
            }
            //Fix this
            //for location, people, price, partial, review
            else if(location!=null && !location.isEmpty() && accomodates!=null && !accomodates.isEmpty() && price!=null && !price.isEmpty() && partial!=null && !partial.isEmpty() && review!=null && !review.isEmpty()){
                //fix this
                cursor = allListings.find(and(eq("address.country", location), eq("accommodates", Integer.parseInt(accomodates)), lte("price", Integer.parseInt(price)),  eq("partial", partial),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, people, price, partial, review"); 
            }
            //Big change
            //for location, bedroom, bed, ptype, review
            else if(location!=null && !location.isEmpty() && bedroom!=null && !bedroom.isEmpty() && bed!=null && !bed.isEmpty() && ptype!=null && !ptype.isEmpty()&& review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("bedroom", Integer.parseInt(bedroom)), eq("bed", Integer.parseInt(bed)),  eq("property_type", ptype),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, bedroom, bed, ptype, review"); 
            }
            //for location, bedroom, bed, amenities, review
            else if(location!=null && !location.isEmpty() && bedroom!=null && !bedroom.isEmpty() && bed!=null && !bed.isEmpty() && al!=null && !al.isEmpty()&& review!=null && !review.isEmpty()){
                //Fix this
                cursor = allListings.find(and(eq("address.country", location), eq("bedroom", Integer.parseInt(bedroom)), eq("bed", Integer.parseInt(bed)),  in("amenities", al),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, bedroom, bed, amenities, review"); 
            }
            //for location, bedroom, bed, price, review
            else if(location!=null && !location.isEmpty() && bedroom!=null && !bedroom.isEmpty() && bed!=null && !bed.isEmpty() && price!=null && !price.isEmpty()&& review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("bedroom", Integer.parseInt(bedroom)), eq("bed", Integer.parseInt(bed)),  lte("price", Integer.parseInt(price)),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, bedroom, bed, price, review"); 
            }
            //for location, bedroom, bed, host, review
            else if(location!=null && !location.isEmpty() && bedroom!=null && !bedroom.isEmpty() && bed!=null && !bed.isEmpty() && host!=null && !host.isEmpty()&& review!=null && !review.isEmpty()){
                if(host=="super"){
                    cursor = allListings.find(and(eq("address.country", location), eq("bedrooms", Integer.parseInt(bedroom)), eq("beds", Integer.parseInt(bed)),  eq("host.host_is_superhost", true),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    cursor = allListings.find(and(eq("address.country", location), eq("bedrooms", Integer.parseInt(bedroom)), eq("beds", Integer.parseInt(bed)),  eq("host.host_is_superhost", false),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();

                }
                System.out.println("For location, bedroom, bed, host, review"); 
            }
            //Fix this
            //for location, bedroom, bed, partial, review
            else if(location!=null && !location.isEmpty() && bedroom!=null && !bedroom.isEmpty() && bed!=null && !bed.isEmpty() && partial!=null && !partial.isEmpty()&& review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("bedroom", Integer.parseInt(bedroom)), eq("bed", Integer.parseInt(bed)),  eq("partial", partial),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, bedroom, bed, partial, review"); 
            }
            //changes again
            //for location, bedroom, ptype, amenities, review
            else if(location!=null && !location.isEmpty() && bedroom!=null && !bedroom.isEmpty() && ptype!=null && !ptype.isEmpty() && al!=null && !al.isEmpty() && review!=null && !review.isEmpty()){
                //Fix this
                cursor = allListings.find(and(eq("address.country", location), eq("bedroom", Integer.parseInt(bedroom)), eq("property_type", ptype),  in("amenities", al),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, bedroom, ptype, amenities, review"); 
            }
            //for location, bedroom, ptype, price, review
            else if(location!=null && !location.isEmpty() && bedroom!=null && !bedroom.isEmpty() && ptype!=null && !ptype.isEmpty() && price!=null && !price.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("bedroom", Integer.parseInt(bedroom)), eq("property_type", ptype),  lte("price", Integer.parseInt(price)),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, bedroom, ptype, price, review"); 
            }
            //for location, bedroom, ptype, host, review
            else if(location!=null && !location.isEmpty() && bedroom!=null && !bedroom.isEmpty() && ptype!=null && !ptype.isEmpty() && host!=null && !host.isEmpty() && review!=null && !review.isEmpty()){
                if(host=="super"){
                    cursor = allListings.find(and(eq("address.country", location), eq("bedroom", Integer.parseInt(bedroom)), eq("property_type", ptype),  eq("host.host_is_superhost", true),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    cursor = allListings.find(and(eq("address.country", location), eq("bedroom", Integer.parseInt(bedroom)), eq("property_type", ptype),  eq("host.host_is_superhost", false),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For location, bedroom, ptype, host, review"); 
            }
            //Fix this
            //for location, bedroom, ptype, partial, review
            else if(location!=null && !location.isEmpty() && bedroom!=null && !bedroom.isEmpty() && ptype!=null && !ptype.isEmpty() && partial!=null && !partial.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("bedroom", Integer.parseInt(bedroom)), eq("property_type", ptype),  eq("partial", partial),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, bedroom, ptype, partial, review"); 
            }
            //Changes again
            //For location, bedroom, amenities, price, review
            else if(location!=null && !location.isEmpty() && bedroom!=null && !bedroom.isEmpty() && al!=null && !al.isEmpty() && price!=null && !price.isEmpty() && review!=null && !review.isEmpty()){
                //Fix this
                cursor = allListings.find(and(eq("address.country", location), eq("bedroom", Integer.parseInt(bedroom)), in("amenities", al),  lte("price", Integer.parseInt(price)),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, bedroom, amenities, price, review"); 
            }
            //For location, bedroom, amenities, host, review
            else if(location!=null && !location.isEmpty() && bedroom!=null && !bedroom.isEmpty() && al!=null && !al.isEmpty() && host!=null && !host.isEmpty() && review!=null && !review.isEmpty()){
                //Fix this
                if(host=="super"){
                    cursor = allListings.find(and(eq("address.country", location), eq("bedroom", Integer.parseInt(bedroom)), in("amenities", al),  eq("host.host_is_superhost", true),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();

                }
                else{
                    cursor = allListings.find(and(eq("address.country", location), eq("bedroom", Integer.parseInt(bedroom)), in("amenities", al),  eq("host.host_is_superhost", false),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For location, bedroom, amenities, host, review"); 
            }
            //Fix this
            //For location, bedroom, amenities, partial, review
            else if(location!=null && !location.isEmpty() && bedroom!=null && !bedroom.isEmpty() && al!=null && !al.isEmpty() && partial!=null && !partial.isEmpty() && review!=null && !review.isEmpty()){
                //Fix this
                cursor = allListings.find(and(eq("address.country", location), eq("bedroom", Integer.parseInt(bedroom)), in("amenities", al),  eq("partial", partial),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, bedroom, amenities, partial, review"); 
            }
            //Changes again
            //For location, bedroom, price, host, review
            else if(location!=null && !location.isEmpty() && bedroom!=null && !bedroom.isEmpty() && price!=null && !price.isEmpty() && host!=null && !host.isEmpty() && review!=null && !review.isEmpty()){
                if(host=="super"){
                    cursor = allListings.find(and(eq("address.country", location), eq("bedroom", Integer.parseInt(bedroom)), lte("price", Integer.parseInt(price)),  eq("host.host_is_superhost", true),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();

                }
                else{
                    cursor = allListings.find(and(eq("address.country", location), eq("bedroom", Integer.parseInt(bedroom)), lte("price", Integer.parseInt(price)),  eq("host.host_is_superhost", false),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For location, bedroom, price, host, review"); 
            }
            //Fix this
            //For location, bedroom, price, partial, review
            else if(location!=null && !location.isEmpty() && bedroom!=null && !bedroom.isEmpty() && price!=null && !price.isEmpty() && partial!=null && !partial.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("bedroom", Integer.parseInt(bedroom)), lte("price", Integer.parseInt(price)),  eq("partial", partial),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, bedroom, price, partial, review"); 
            }
            //Big Change
            //for location, bed, ptype, amenities, review
            else if(location!=null && !location.isEmpty() && bed!=null && !bed.isEmpty() && ptype!=null && !ptype.isEmpty() && al!=null && !al.isEmpty() && review!=null && !review.isEmpty()){
                //Fix this
                cursor = allListings.find(and(eq("address.country", location), eq("beds", Integer.parseInt(bed)), eq("property_type", ptype),  in("amenities", al),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, bed, ptype, amenities, review"); 
            }
            //for location, bed, ptype, price, review
            else if(location!=null && !location.isEmpty() && bed!=null && !bed.isEmpty() && ptype!=null && !ptype.isEmpty() && price!=null && !price.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("beds", Integer.parseInt(bed)), eq("property_type", ptype),  lte("price", Integer.parseInt(price)),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, bed, ptype, price, review"); 
            }
            //for location, bed, ptype, host, review
            else if(location!=null && !location.isEmpty() && bed!=null && !bed.isEmpty() && ptype!=null && !ptype.isEmpty() && host!=null && !host.isEmpty() && review!=null && !review.isEmpty()){
                if(host=="super"){
                    cursor = allListings.find(and(eq("address.country", location), eq("beds", Integer.parseInt(bed)), eq("property_type", ptype), eq("host.host_is_superhost", true), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    cursor = allListings.find(and(eq("address.country", location), eq("beds", Integer.parseInt(bed)), eq("property_type", ptype), eq("host.host_is_superhost", false), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For location, bed, ptype, host, review"); 
            }
            //Fix this
            //for location, bed, ptype, partial, review
            else if(location!=null && !location.isEmpty() && bed!=null && !bed.isEmpty() && ptype!=null && !ptype.isEmpty() && partial!=null && !partial.isEmpty() && review!=null && !review.isEmpty()){
                cursor = allListings.find(and(eq("address.country", location), eq("beds", Integer.parseInt(bed)), eq("property_type", ptype),  eq("partial", partial),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, bed, ptype, partial, review"); 
            }
            //Changes again
            //for location,bed, amenities, price, review
            else if(location!=null && !location.isEmpty() && bed!=null && !bed.isEmpty() && al!=null && !al.isEmpty() && price!=null && !price.isEmpty() && review!=null && !review.isEmpty()){
                //Fix this
                cursor = allListings.find(and(eq("address.country", location), eq("beds", Integer.parseInt(bed)), in("amenities", al),  lte("price", Integer.parseInt(price)),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, bed, amenities, price, review"); 
            }
            //for location,bed, amenities, host, review
            else if(location!=null && !location.isEmpty() && bed!=null && !bed.isEmpty() && al!=null && !al.isEmpty() && price!=null && !price.isEmpty() && review!=null && !review.isEmpty()){
                //Fix this
                if(host=="super"){
                    cursor = allListings.find(and(eq("address.country", location), eq("beds", Integer.parseInt(bed)), in("amenities", al),  eq("host.host_is_superhost", true),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    cursor = allListings.find(and(eq("address.country", location), eq("beds", Integer.parseInt(bed)), in("amenities", al),  eq("host.host_is_superhost", false),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For location, bed, amenities, host, review"); 
            }
            //Fix this
            //for location,bed, amenities, partial, review
            else if(location!=null && !location.isEmpty() && bed!=null && !bed.isEmpty() && al!=null && !al.isEmpty() && partial!=null && !partial.isEmpty() && review!=null && !review.isEmpty()){
                //Fix this
                cursor = allListings.find(and(eq("address.country", location), eq("beds", Integer.parseInt(bed)), in("amenities", al),  eq("partial", partial),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, bed, amenities, partial, review"); 
            }
            //Changes again
            //for location, bed, price, host, review
            else if(location!=null && !location.isEmpty() && bed!=null && !bed.isEmpty() && price!=null && !price.isEmpty() && host!=null && !host.isEmpty() && review!=null && !review.isEmpty()){
                if(host=="super"){
                    cursor = allListings.find(and(eq("address.country", location), eq("beds", Integer.parseInt(bed)), lte("price", Integer.parseInt(price)), eq("host.host_is_superhost", true),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    cursor = allListings.find(and(eq("address.country", location), eq("beds", Integer.parseInt(bed)), lte("price", Integer.parseInt(price)), eq("host.host_is_superhost", false),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For location, bed, price, host, review"); 
            }
            //Fix this
            //for location,bed, price, partial, review
            else if(location!=null && !location.isEmpty() && bed!=null && !bed.isEmpty() && price!=null && !price.isEmpty() && partial!=null && !partial.isEmpty() && review!=null && !review.isEmpty()){
                //Fix this
                cursor = allListings.find(and(eq("address.country", location), eq("beds", Integer.parseInt(bed)), lte("price", Integer.parseInt(price)),  eq("partial", partial),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, bed, price, partial, review"); 
            }
            //Changes again
            //for location,ptype, amenities, price, review
            else if(location!=null && !location.isEmpty() && ptype!=null && !ptype.isEmpty() && al!=null && !al.isEmpty() && price!=null && !price.isEmpty() && review!=null && !review.isEmpty()){
                //Fix this
                cursor = allListings.find(and(eq("address.country", location), eq("beds", Integer.parseInt(bed)), in("amenities", al),  lte("price", Integer.parseInt(price)),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, ptype, amenities, price, review"); 
            }
            //for location,ptype, amenities, host, review
            else if(location!=null && !location.isEmpty() && ptype!=null && !ptype.isEmpty() && al!=null && !al.isEmpty() && price!=null && !price.isEmpty() && review!=null && !review.isEmpty()){
                //Fix this
                if(host=="super"){
                    cursor = allListings.find(and(eq("address.country", location), eq("beds", Integer.parseInt(bed)), in("amenities", al),  eq("host.host_is_superhost", true),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    cursor = allListings.find(and(eq("address.country", location), eq("beds", Integer.parseInt(bed)), in("amenities", al),  eq("host.host_is_superhost", false),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For location, ptype, amenities, host, review"); 
            }
            //Fix this
            //for location,ptype, amenities, partial, review
            else if(location!=null && !location.isEmpty() && ptype!=null && !ptype.isEmpty() && al!=null && !al.isEmpty() && partial!=null && !partial.isEmpty() && review!=null && !review.isEmpty()){
                //Fix this
                cursor = allListings.find(and(eq("address.country", location), eq("beds", Integer.parseInt(bed)), in("amenities", al),  eq("partial", partial),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, ptype, amenities, partial, review"); 
            }
            //Changes again
            //for location, ptype, price, host, review
            else if(location!=null && !location.isEmpty() && ptype!=null && !ptype.isEmpty() && price!=null && !price.isEmpty() && host!=null && !host.isEmpty() && review!=null && !review.isEmpty()){
                if(host=="super"){
                    cursor = allListings.find(and(eq("address.country", location), eq("ptype", Integer.parseInt(ptype)), lte("price", Integer.parseInt(price)), eq("host.host_is_superhost", true),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    cursor = allListings.find(and(eq("address.country", location), eq("ptype", Integer.parseInt(ptype)), lte("price", Integer.parseInt(price)), eq("host.host_is_superhost", false),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For location, bed, price, host, review"); 
            }
            //Fix this
            //for location,ptype, price, partial, review
            else if(location!=null && !location.isEmpty() && ptype!=null && !ptype.isEmpty() && price!=null && !price.isEmpty() && partial!=null && !partial.isEmpty() && review!=null && !review.isEmpty()){
                //Fix this
                cursor = allListings.find(and(eq("address.country", location), eq("beds", Integer.parseInt(bed)), lte("price", Integer.parseInt(price)),  eq("partial", partial),gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                System.out.println("For location, ptype, price, partial, review"); 
            }
            // Changes again
            //for location, amenities, price, host, review
            else{
                //Fix this
                if(host=="super"){
                    cursor = allListings.find(and(eq("address.country", location), in("amenities", al), lte("price", Integer.parseInt(price)), eq("host.host_is_superhost", true), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                else{
                    cursor = allListings.find(and(eq("address.country", location), in("amenities", al), lte("price", Integer.parseInt(price)), eq("host.host_is_superhost", false), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
                }
                System.out.println("For location, amenities, price, host, review"); 
            }
           
        }
        
        else{
            System.out.println("line 632");
            if(host=="super"){
                cursor = allListings.find(and(eq("address.country", location), eq("accommodates", accomodates), eq("bedrooms", Integer.parseInt(bedroom)), eq("beds", bed), eq("property_type", ptype), in("amenities", al),  lte("price", Integer.parseInt(price)), eq("host.host_is_superhost", true), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
            }
            else{
                cursor = allListings.find(and(eq("address.country", location), eq("accommodates", accomodates), lte("bedrooms", Integer.parseInt(bedroom)), eq("beds", bed), eq("property_type", ptype), in("amenities", al), lte("price", Integer.parseInt(price)), eq("host.host_is_superhost", false), gte("review_scores.review_scores_rating", Integer.parseInt(review)))).skip(pagesize*(n-1)).limit(pagesize).projection(fields(include("name", "address","images","bedrooms","beds","price"), exclude("_id"))).iterator();
            }
            System.out.println("ALl parameters");
        }
        
        Long tab = total_records/10;
        ArrayList<String> result = new ArrayList<String>();
        try {
         while (cursor.hasNext()) {
            Document record = cursor.next();
            String name = record.get("name").toString();
            Document images = (Document) record.get("images");
            String rec_bedroom = record.get("bedrooms").toString();
            String rec_bed = record.get("beds").toString();
            String rec_price = record.get("price").toString();
            String final_result = name + "~~" + (images.get("picture_url")) + "~~" + rec_bedroom + "~~" + rec_bed + "~~" + rec_price + "~~" + tab + "~~";
            System.out.println(final_result);
            result.add(final_result);
         }

      }
      finally {
         cursor.close();
      }
        return result;
    }
}
