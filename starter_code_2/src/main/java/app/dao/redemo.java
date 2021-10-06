package app.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.internal.operation.SyncOperations;

import static com.mongodb.client.model.Projections.*;
import org.bson.Document;
import java.util.ArrayList;
import static com.mongodb.client.model.Filters.*;
import app.MongoDBConnection;

public class redemo {
    public ArrayList<String> getSearchDB(String location, String accomodates, String bedroom, String bed, String ptype, String amenities, String price, String review, String host){
        MongoDBConnection mongodb = MongoDBConnection.getConnection();
        System.out.println(location);
        System.out.println(review);
        MongoCursor<Document> cursor;
        MongoCollection<Document> allListings = mongodb.getAllListings();

        String column[] = {"address.government_area","accommodates","bedrooms","beds","property_type","amenities","price","host.host_is_superhost","review_scores.review_scores_rating"};
        String values[] = {location,accomodates,bedroom,bed,ptype,amenities,price,host,review};
        ArrayList<String> save_column = new ArrayList<>();
        ArrayList<String> save_values = new ArrayList<>();
        
        // String str_values[] = {location,ptype,amenities,host,review};
        // String int_values[] = {accomodates,bedroom,bed,price};
        int not_nul=0;
        for(int i=0;i<values.length;i++){
            if(values[i]!=null && !values[i].isEmpty()){
                save_column.add(column[i]);
                save_values.add(values[i]);
                not_nul++;
            }
        }
        System.out.println("not null: "+not_nul);
        System.out.println("Column: "+save_column);
        System.out.println("Values: "+save_values);
        
        //input paramter is 1
        if(not_nul==1){
            //check for integer
            if(save_values.get(0) == accomodates || save_values.get(0) == bedroom || save_values.get(0) == bed || save_values.get(0) == price){
                //for price parameter
                if(save_values.get(0)==price){
                    cursor = allListings.find(lte(save_column.get(0), Integer.parseInt(save_values.get(0)))).projection(fields(include("name", "address"), exclude("_id"))).iterator();    
                }
                //for review parameter
                else if(save_values.get(0)==review){
                    cursor = allListings.find(gte(save_column.get(0), Integer.parseInt(save_values.get(0)))).projection(fields(include("name", "address"), exclude("_id"))).iterator();    
                }
                //for other integers dt
                else{
                    System.out.println("ITs an integer 0");
                    cursor = allListings.find(eq(save_column.get(0), Integer.parseInt(save_values.get(0)))).projection(fields(include("name", "address"), exclude("_id"))).iterator();    
                }
            }
            //for string
            else{
                cursor = allListings.find(eq(save_column.get(0), save_values.get(0))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
            }
        }
        //input paramter is 2
        else if(not_nul==2){
            ArrayList<Integer> save_int = new ArrayList<>();
            //checks for the amount of integer values and stores its index in a list
            for(int i=0;i<2;i++){
                if(save_values.get(i) == accomodates || save_values.get(i) == bedroom || save_values.get(i) == bed || save_values.get(i) == price || save_values.get(i) == review){
                    save_int.add(i);
                    System.out.println(i);
                }
            }
            
            //if size of list is 2 then it means it has 2 integer parameters
            if(save_int.size()==2){
                //if 1st one is price and 2nd one is review 
                if(save_values.get(0)==price && save_values.get(1)==review){
                    cursor = allListings.find(and(lte(save_column.get(0), Integer.parseInt(save_values.get(0))), gte(save_column.get(1), Integer.parseInt(save_values.get(1))))).projection(fields(include("name", "address"), exclude("_id"))).iterator();    
                }
                //if 1st one is review and 2nd one is price  
                else if(save_values.get(0)==review && save_values.get(1)==price){
                    cursor = allListings.find(and(gte(save_column.get(0), Integer.parseInt(save_values.get(0))), lte(save_column.get(1), Integer.parseInt(save_values.get(1))))).projection(fields(include("name", "address"), exclude("_id"))).iterator();    
                }
                //if its none of them
                else{
                    cursor = allListings.find(and(eq(save_column.get(0), Integer.parseInt(save_values.get(0))), eq(save_column.get(1), Integer.parseInt(save_values.get(1))))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                    System.out.println("Both Integers");
                }
            }
            //if only one of the parameter is Integer
            else if(save_int.size()==1){
                //if 0th index is Integer
                if(save_int.get(0)==0){
                    if(save_values.get(0)==price){
                        //1st Integer(price) 2nd String
                        cursor = allListings.find(and(lte(save_column.get(0), Integer.parseInt(save_values.get(0))), eq(save_column.get(1), save_values.get(1)))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                    }
                    else if(save_values.get(0)==review){
                        //1st Integer(review) 2nd String
                        cursor = allListings.find(and(gte(save_column.get(0), Integer.parseInt(save_values.get(0))), eq(save_column.get(1), save_values.get(1)))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                    }
                    else{
                        //1st Integer(accomodates,bed,bedroom) 2nd String
                        cursor = allListings.find(and(eq(save_column.get(0), Integer.parseInt(save_values.get(0))), eq(save_column.get(1), save_values.get(1)))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                        System.out.println("Column: "+save_column.get(0)+"\nValues: "+save_values.get(0));
                    }
                }
                //if 1st index is Integer
                else{
                    if(save_values.get(1)==price){
                        //1st String 2nd Integer(price)
                        cursor = allListings.find(and(eq(save_column.get(0), save_values.get(0)), lte(save_column.get(1), Integer.parseInt(save_values.get(1))))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                    }
                    else if(save_values.get(1)==review){
                        //1st Integer(review) 2nd String
                        cursor = allListings.find(and(eq(save_column.get(0), save_values.get(0)), gte(save_column.get(1), Integer.parseInt(save_values.get(1))))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                    }
                    else{
                        //1st String 2nd Integer(accomodates,bed,bedroom)
                        cursor = allListings.find(and(eq(save_column.get(0), save_values.get(0)), eq(save_column.get(1), Integer.parseInt(save_values.get(1))))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                        System.out.println("Column: "+save_column.get(0)+"\nValues: "+save_values.get(0)+"\nColumn1: "+save_column.get(1)+"\nValues1: "+save_values.get(1));
                        System.out.println("One integer");
                    }
                }
            }
            else{
                cursor = allListings.find(and(eq(save_column.get(0), save_values.get(0)), eq(save_column.get(1), save_values.get(1)))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                System.out.println("both strings");
            }  
        }
        else if(not_nul==3){
            ArrayList<Integer> save_int = new ArrayList<>();
            //checks for the amount of integer values and stores its index in a list
            for(int i=0;i<3;i++){
                if(save_values.get(i) == accomodates || save_values.get(i) == bedroom || save_values.get(i) == bed || save_values.get(i) == price || save_values.get(i) == review){
                    save_int.add(i);
                    System.out.println(i);
                }
            }
            //if 3 are integer
            if(save_int.size()==3){
                //if its like price, review, integer
                if(save_values.get(0)==price && save_values.get(1)==review){
                    cursor = allListings.find(and(lte(save_column.get(0), Integer.parseInt(save_values.get(0))), gte(save_column.get(1), Integer.parseInt(save_values.get(1))), eq(save_column.get(2), Integer.parseInt(save_values.get(2))))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                }
                //if its like review, price, integer
                else if(save_values.get(0)==review && save_values.get(1)==price){
                    cursor = allListings.find(and(gte(save_column.get(0), Integer.parseInt(save_values.get(0))), lte(save_column.get(1), Integer.parseInt(save_values.get(1))), eq(save_column.get(2), Integer.parseInt(save_values.get(2))))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                }
                //if its like integer, price, review,
                else if(save_values.get(1)==price && save_values.get(2)==review){
                    cursor = allListings.find(and(eq(save_column.get(0), Integer.parseInt(save_values.get(0))), lte(save_column.get(1), Integer.parseInt(save_values.get(1))), gte(save_column.get(2), Integer.parseInt(save_values.get(2))))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                }
                //if its like integer, review, price
                else if(save_values.get(1)==review && save_values.get(2)==price){
                    cursor = allListings.find(and(eq(save_column.get(0), Integer.parseInt(save_values.get(0))), gte(save_column.get(1), Integer.parseInt(save_values.get(1))), lte(save_column.get(2), Integer.parseInt(save_values.get(2))))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                }
                //if its like price, integer, review
                else if(save_values.get(0)==price && save_values.get(2)==review){
                    cursor = allListings.find(and(lte(save_column.get(0), Integer.parseInt(save_values.get(0))), eq(save_column.get(1), Integer.parseInt(save_values.get(1))), gte(save_column.get(2), Integer.parseInt(save_values.get(2))))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                }
                //if its like review, integer, price
                else if(save_values.get(0)==review && save_values.get(2)==price){
                    cursor = allListings.find(and(gte(save_column.get(0), Integer.parseInt(save_values.get(0))), eq(save_column.get(1), Integer.parseInt(save_values.get(1))), lte(save_column.get(2), Integer.parseInt(save_values.get(2))))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                }
                //int, int, int
                else{
                    cursor = allListings.find(and(eq(save_column.get(0), Integer.parseInt(save_values.get(0))), eq(save_column.get(1), Integer.parseInt(save_values.get(1))), eq(save_column.get(2), Integer.parseInt(save_values.get(2))))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                }
            }
            //if 2 are integer
            else if(save_int.size()==2){
                System.out.println("line 172");
                //if 0 and 1st indexes are integer (input paramteres we are talking about)
                if(save_int.get(0)==0 && save_int.get(1)==1){
                    //This means integer, integer, string
                    //price, int, string
                    if(save_values.get(0)==price && save_values.get(1)!=review){
                        cursor = allListings.find(and(lte(save_column.get(0), Integer.parseInt(save_values.get(0))), eq(save_column.get(1), Integer.parseInt(save_values.get(1))), eq(save_column.get(2), save_values.get(2)))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                    }
                    //review, int, string
                    else if(save_values.get(0)==review && save_values.get(1)!=price){
                        cursor = allListings.find(and(gte(save_column.get(0), Integer.parseInt(save_values.get(0))), eq(save_column.get(1), Integer.parseInt(save_values.get(1))), eq(save_column.get(2), save_values.get(2)))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                    }
                    //int, review, string
                    else if(save_values.get(0)!=price && save_values.get(1)==review){
                        cursor = allListings.find(and(eq(save_column.get(0), Integer.parseInt(save_values.get(0))), gte(save_column.get(1), Integer.parseInt(save_values.get(1))), eq(save_column.get(2), save_values.get(2)))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                    }
                    //int, price, review
                    else if(save_values.get(0)!=review && save_values.get(1)==price){
                        cursor = allListings.find(and(eq(save_column.get(0), Integer.parseInt(save_values.get(0))), lte(save_column.get(1), Integer.parseInt(save_values.get(1))), eq(save_column.get(2), save_values.get(2)))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                    }
                    //if its price, review, string
                    else if(save_values.get(0)==price && save_values.get(1)==review){
                        cursor = allListings.find(and(lte(save_column.get(0), Integer.parseInt(save_values.get(0))), gte(save_column.get(1), Integer.parseInt(save_values.get(1))), eq(save_column.get(2), save_values.get(2)))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                    }
                    //if its review, price, string
                    else if(save_values.get(0)==review && save_values.get(1)==price){
                        cursor = allListings.find(and(gte(save_column.get(0), Integer.parseInt(save_values.get(0))), lte(save_column.get(1), Integer.parseInt(save_values.get(1))), eq(save_column.get(2), save_values.get(2)))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                    }
                    //int, int, string
                    else{
                        cursor = allListings.find(and(eq(save_column.get(0), Integer.parseInt(save_values.get(0))), eq(save_column.get(1), Integer.parseInt(save_values.get(1))), eq(save_column.get(2), save_values.get(2)))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                    }
                }
                //if 0 and 2nd indexes are integer (input parameters)
                else if(save_int.get(0)==0 && save_int.get(1)==2){
                    //This means integer, string , integer
                    
                    //price, string, int
                    if(save_values.get(0)==price && save_values.get(2)!=review){
                        cursor = allListings.find(and(lte(save_column.get(0), Integer.parseInt(save_values.get(0))), eq(save_column.get(1), save_values.get(1)), eq(save_column.get(2), Integer.parseInt(save_values.get(2))))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                    }
                    //review, string, int
                    else if(save_values.get(0)==review && save_values.get(1)!=price){
                        cursor = allListings.find(and(gte(save_column.get(0), Integer.parseInt(save_values.get(0))), eq(save_column.get(1), save_values.get(1)), eq(save_column.get(2), Integer.parseInt(save_values.get(2))))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                    }
                    //int, string, price
                    else if(save_values.get(0)!=price && save_values.get(1)==review){
                        cursor = allListings.find(and(eq(save_column.get(0), Integer.parseInt(save_values.get(0))), eq(save_column.get(1), save_values.get(1)), lte(save_column.get(2), Integer.parseInt(save_values.get(2))))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                    }
                    //int, string, review
                    else if(save_values.get(0)!=review && save_values.get(1)==price){
                        cursor = allListings.find(and(eq(save_column.get(0), Integer.parseInt(save_values.get(0))), eq(save_column.get(1), save_values.get(1)), gte(save_column.get(2), Integer.parseInt(save_values.get(2))))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                    }
                    //if its price, string, review
                    else if(save_values.get(0)==price && save_values.get(2)==review){
                        cursor = allListings.find(and(lte(save_column.get(0), Integer.parseInt(save_values.get(0))), eq(save_column.get(1), save_values.get(1)), gte(save_column.get(2), Integer.parseInt(save_values.get(2))))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                    }
                    //if its review, string, price
                    else if(save_values.get(0)==review && save_values.get(2)==price){
                        cursor = allListings.find(and(gte(save_column.get(0), Integer.parseInt(save_values.get(0))), eq(save_column.get(1), save_values.get(1)), lte(save_column.get(2), Integer.parseInt(save_values.get(2))))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                    }
                    //int, string, int
                    else{
                        cursor = allListings.find(and(eq(save_column.get(0), Integer.parseInt(save_values.get(0))), eq(save_column.get(1), save_values.get(1)), eq(save_column.get(2), Integer.parseInt(save_values.get(2))))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                    }
                }
                //if 1 and 2 are integer
                else{
                    System.out.println("line 240");
                    //This means string, int, int
                    //string, price, int
                    if(save_values.get(1)==price && save_values.get(2)!=review){
                        cursor = allListings.find(and(eq(save_column.get(0), save_values.get(0)), lte(save_column.get(1), Integer.parseInt(save_values.get(1))), eq(save_column.get(2), Integer.parseInt(save_values.get(2))))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                        System.out.println("line 245");
                    }
                    //string, review, int
                    else if(save_values.get(1)==review && save_values.get(2)!=price){
                        cursor = allListings.find(and(eq(save_column.get(0), save_values.get(0)), gte(save_column.get(1), Integer.parseInt(save_values.get(1))), eq(save_column.get(2), Integer.parseInt(save_values.get(2))))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                        System.out.println("line 250");
                    }
                    //string, int, price
                    else if(save_values.get(1)!=review && save_values.get(2)==price){
                        cursor = allListings.find(and(eq(save_column.get(0), save_values.get(0)), eq(save_column.get(1), Integer.parseInt(save_values.get(1))), lte(save_column.get(2), Integer.parseInt(save_values.get(2))))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                        System.out.println("line 255");
                    }
                    //string, int, review
                    else if(save_values.get(1)!=price && save_values.get(2)==review){
                        cursor = allListings.find(and(eq(save_column.get(0), save_values.get(0)), eq(save_column.get(1), Integer.parseInt(save_values.get(1))), gte(save_column.get(2), Integer.parseInt(save_values.get(2))))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                        System.out.println("yes");
                        System.out.println("line 261");
                    }
                    //if its string, price, review
                    else if(save_values.get(1)==price && save_values.get(2)==review){
                        cursor = allListings.find(and(eq(save_column.get(0), save_values.get(0)), lte(save_column.get(1), Integer.parseInt(save_values.get(1))), gte(save_column.get(2), Integer.parseInt(save_values.get(2))))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                        System.out.println("line 266");
                    }
                    //if its string, review, price
                    else if(save_values.get(1)==review && save_values.get(2)==price){
                        cursor = allListings.find(and(eq(save_column.get(0), save_values.get(0)), gte(save_column.get(1), Integer.parseInt(save_values.get(1))), lte(save_column.get(2), Integer.parseInt(save_values.get(2))))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                        System.out.println("line 271");
                    }
                    //string, int, int
                    else{
                        cursor = allListings.find(and(eq(save_column.get(0), save_values.get(0)), eq(save_column.get(1), Integer.parseInt(save_values.get(1))), eq(save_column.get(2), Integer.parseInt(save_values.get(2))))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                        System.out.println("line 276");
                    }
                }
            }
            //if 1 parameter is integer
            else if(save_int.size()==1){
                //if 0 is integer
                if(save_int.get(0)==0){
                    //this means int, string, string
                    //if its price, string, string
                    if(save_values.get(0)==price){
                        cursor = allListings.find(and(lte(save_column.get(0), Integer.parseInt(save_values.get(0))), eq(save_column.get(1), save_values.get(1)), eq(save_column.get(2), save_values.get(2)))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                    }     
                    //if its review, string, string
                    else if(save_values.get(0)==review){
                        cursor = allListings.find(and(gte(save_column.get(0), Integer.parseInt(save_values.get(0))), eq(save_column.get(1), save_values.get(1)), eq(save_column.get(2), save_values.get(2)))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                    }
                    //int, string, string
                    else{
                        cursor = allListings.find(and(eq(save_column.get(0), Integer.parseInt(save_values.get(0))), eq(save_column.get(1), save_values.get(1)), eq(save_column.get(2), save_values.get(2)))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                    }
                }
                //if 1 is integer
                else if(save_int.get(0)==1){
                    //this means string, int, string
                    //if its string, price, string
                    if(save_values.get(1)==price){
                        cursor = allListings.find(and(eq(save_column.get(0), save_values.get(0)), lte(save_column.get(1), Integer.parseInt(save_values.get(1))), eq(save_column.get(2), save_values.get(2)))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                    }
                    //if its string, review, string
                    else if(save_values.get(1)==review){
                        cursor = allListings.find(and(eq(save_column.get(0), save_values.get(0)), gte(save_column.get(1), Integer.parseInt(save_values.get(1))), eq(save_column.get(2), save_values.get(2)))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                    }
                    //string, int, string
                    else{
                        cursor = allListings.find(and(eq(save_column.get(0), save_values.get(0)), eq(save_column.get(1), Integer.parseInt(save_values.get(1))), eq(save_column.get(2), save_values.get(2)))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                    }
                }
                //if 2nd is integer
                else{
                    //this means string, string, int
                    //if its string, string, price
                    if(save_values.get(2)==price){
                        cursor = allListings.find(and(eq(save_column.get(0), save_values.get(0)), eq(save_column.get(1), save_values.get(1)), lte(save_column.get(2), Integer.parseInt(save_values.get(2))))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                    }
                    //if its string, string, review
                    else if(save_values.get(2)==review){
                        cursor = allListings.find(and(eq(save_column.get(0), save_values.get(0)), eq(save_column.get(1), save_values.get(1)), gte(save_column.get(2), Integer.parseInt(save_values.get(2))))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                    }
                    //if its string, string, int
                    else{
                        cursor = allListings.find(and(eq(save_column.get(0), save_values.get(0)), eq(save_column.get(1), save_values.get(1)), eq(save_column.get(2), Integer.parseInt(save_values.get(2))))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
                    }
                }
            }
            //all of the 3 are Strings
            else{
                cursor = allListings.find(and(eq(save_column.get(0), save_values.get(0)), eq(save_column.get(1), save_values.get(1)), eq(save_column.get(2), save_values.get(2)))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
            }
        }
        // else if(not_nul==4){
        //     ArrayList<Integer> save_int = new ArrayList<>();
        //     //checks for the amount of integer values and stores its index in a list
        //     for(int i=0;i<3;i++){
        //         if(save_values.get(i) == accomodates || save_values.get(i) == bedroom || save_values.get(i) == bed || save_values.get(i) == price || save_values.get(i) == review){
        //             save_int.add(i);
        //             System.out.println(i);
        //         }
        //     }
        // }
        else{
            cursor = allListings.find(and(eq(save_column.get(0), save_values.get(0)), eq(save_column.get(1), save_values.get(1)), eq(save_column.get(2), save_values.get(2)), eq(save_column.get(3), save_values.get(3)), eq(save_column.get(4), save_values.get(4)), eq(save_column.get(5), save_values.get(5)), eq(save_column.get(6), save_values.get(6)))).projection(fields(include("name", "address"), exclude("_id"))).iterator();
            System.out.println("Last else statement");
        }
           

        ArrayList<String> result = new ArrayList<String>();
        try {
         while (cursor.hasNext()) {
            Document record = cursor.next();
            String name = record.get("name").toString()+"#";
            String address = record.get("address").toString();
            
            result.add(name);
         }
      }
      finally {
         cursor.close();
      }
        return result;
    }
    public static void criteriaNotnul(){

    }
}
