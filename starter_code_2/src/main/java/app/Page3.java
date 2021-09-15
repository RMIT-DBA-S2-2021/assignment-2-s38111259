package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.javalin.http.Context;
import io.javalin.http.Handler;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin by writing the raw HTML into a Java
 * String object
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 * @author Halil Ali, 2021. email: halil.ali@rmit.edu.au
 */
public class Page3 implements Handler {

   // URL of this page relative to http://localhost:7000/
   public static final String URL = "/page3.html";

   // Name of the Thymeleaf HTML template page in the resources folder
   private static final String TEMPLATE = ("page3.html");

   @Override
   public void handle(Context context) throws Exception {
      String user = Util.getLoggedInUser(context);
      //display login form if user is not logged in
      if(user == null) {
         context.redirect("/");
      }
      else {
         // The model of data to provide to Thymeleaf.
         // In this example the model will be filled with:
         // - Title to give to the h1 tag
         // - Array list of all properties for the UL element
         Map<String, Object> model = new HashMap<String, Object>();

         // Add in title for the h1 tag to the model
         model.put("title", new String("Page 3: Apartments by Accomodation capacity (Thymeleaf)"));


         model.put("USER", user);
         // Add into the model the list of types to give to the dropdown
         ArrayList<String> accomodates = new ArrayList<String>();
         accomodates.add("1");
         accomodates.add("2");
         accomodates.add("3");
         accomodates.add("4");
         accomodates.add("5");
         accomodates.add("6");
         accomodates.add("7");
         accomodates.add("8");
         model.put("accomodates", accomodates);

         // Look up from JDBC
         MongoDBConnection mongodb = MongoDBConnection.getConnection();

         /*
          * Get the Form Data from the drop down list Need to be Careful!! If the form is
          * not filled in, then the form will return null!
          */
         String accomodates_drop = context.formParam("accomodates_drop");
         if (accomodates_drop == null) {
            // If NULL, nothing to show, therefore we make some "no results" HTML
            // Also store empty array list for completness
            model.put("title_drop", new String("No Results to show for dropbox"));
            ArrayList<String> properties = new ArrayList<String>();
            model.put("properties_drop", properties);
         } else {
            // If NOT NULL, then lookup the properties by accomodation capacity!
            model.put("title_drop", new String(accomodates_drop + " person Properties"));
            int capacity =Integer.parseInt(accomodates_drop);
            ArrayList<String> properties = mongodb.getApartmentNamesByAccomodates(capacity);
            model.put("properties_drop", properties);
         }

         String accomodates_textbox = context.formParam("accomodates_textbox");
         if (accomodates_textbox == null || accomodates_textbox == "") {
            // If NULL, nothing to show, therefore we make some "no results" HTML
            // Also store empty array list for completness
            model.put("title_text", new String("No Results to show for textbox"));
            ArrayList<String> properties = new ArrayList<String>();
            model.put("properties_text", properties);
         } else {
            // If NOT NULL, then lookup the properties by accomodation capacity!
            model.put("title_text", new String(accomodates_textbox + "  person properties"));
            int capacity =Integer.parseInt(accomodates_textbox);
            ArrayList<String> properties = mongodb.getApartmentNamesByAccomodates(capacity);
            model.put("properties_text", properties);
         }

         // DO NOT MODIFY THIS
         // Makes Javalin render the webpage using Thymeleaf
         context.render(TEMPLATE, model);
      }
   }
}