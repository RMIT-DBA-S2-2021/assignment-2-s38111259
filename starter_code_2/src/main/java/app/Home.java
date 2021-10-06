package app;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.sound.midi.Receiver;

import app.service.UserSearchServiceImpl;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin by writing the raw HTML into a Java
 * String object
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class Home implements Handler {

   // URL of this page relative to http://localhost:7000/
   public static final String URL = "/home.html";
   public static final String TEMPLATE = "home.html";
   public static final String LOGIN = "login.html";


   @Override
   public void handle(Context context) throws Exception {
      Map<String,ArrayList<String>> model = new HashMap<>();
      String location = context.formParam("location");
      String market = context.formParam("market");
      String accomodates = context.formParam("people");
      String bedroom = context.formParam("bedroom");
      String bed = context.formParam("beds");
      String ptype = context.formParam("ptype");
      String TV = context.formParam("TV");
      String DM = context.formParam("Doorman");
      String Wifi = context.formParam("Wifi");
      String Kitchen = context.formParam("Kitchen");
      String parking = context.formParam("parking");
      String Smoking = context.formParam("smoking");
      String Pets = context.formParam("pets");
      String Heating = context.formParam("Heating");
      String Washer = context.formParam("Washer");
      String First = context.formParam("aid");
      String aid = context.formParam("Fire");
      String Essentials = context.formParam("Essentials");
      String dryer = context.formParam("dryer");
      String Iron = context.formParam("Iron");
      String water = context.formParam("water");
      String pillows = context.formParam("pillows");
      String Coffee_maker = context.formParam("Coffee");
      String Refrigerator = context.formParam("Refrigerator");
      String Dishwasher = context.formParam("Dishwasher");
      String Dishes = context.formParam("Dishes");
      String Oven = context.formParam("Oven");
      String Stove = context.formParam("Stove");
      String Cleaning = context.formParam("Cleaning");
      String Waterfront = context.formParam("Waterfront");
      String price = context.formParam("price");
      String review = context.formParam("range");
      String host = context.formParam("host");
      String amenities[] = {TV, DM, Wifi, Kitchen, parking, Smoking, Pets, Heating, Washer, First, aid, Essentials, dryer, Iron, water, pillows, Coffee_maker, Refrigerator, Dishwasher, Dishes, Oven, Stove, Cleaning, Waterfront};
      ArrayList<String> al = new ArrayList<>();
      for(int i=0;i<amenities.length;i++){
         if(amenities[i]!=null && !amenities[i].isEmpty()){
            al.add(amenities[i]);
         }
      }
      
      String partial = context.formParam("partial");
      System.out.println(al.size());
      System.out.println(al);
      int not_null=0;
      String values[] = {location,market,accomodates,bedroom,bed,ptype,price,host,review,partial};
      for(int i=0;i<values.length;i++){
         if(values[i]!=null && !values[i].isEmpty()){
            not_null++;
        }
      }
      if(al.size()!=0){
         not_null++;
      }
      
      if(not_null!=0){
         UserSearchServiceImpl userSearchServiceImpl = new UserSearchServiceImpl();
         ArrayList<String> result = userSearchServiceImpl.getSearchDetail(location, market, accomodates, bedroom, bed, ptype, al, price, review, host, partial);
         model.put("Result", result);  
         context.render("page_result.html",model);
         
      }
      else{
         // DO NOT MODIFY THIS
         // Makes Javalin render the webpage
         context.render(TEMPLATE);
      }
   }

   // logout the current user by removing their cookie and session id details
   public void logout(Context context) {
      String id = context.cookie("id");
      context.sessionAttribute(id);
      context.removeCookie("id");
      context.sessionAttribute(id, null);
      // reload the page
      context.redirect("/");
   }

   // login the user by creating a random user id and associating in their cookie and session 
   public void login(Context context, String username){
      String id = UUID.randomUUID().toString();
//      context.sessionAttribute("id", id);
      context.sessionAttribute(id, username);
      context.cookie("id", id);
   }
}
