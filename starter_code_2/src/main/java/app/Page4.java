package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

/**
 * Temporary HTML as an example page.
 * 
 * Based on the Project Workshop code examples.
 * This page currently:
 *  - Provides a link back to the index page
 *  - Displays the list of movies from the Movies Database using the JDBCConnection
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 * @author Halil Ali, 2021. email: halil.ali@rmit.edu.au
 */
public class Page4 implements Handler {

   // URL of this page relative to http://localhost:7000/
   public static final String URL = "/page4.html";

   @Override
   public void handle(Context context) throws Exception {
      // Create a simple HTML webpage in a String
      String user = Util.getLoggedInUser(context);
      //display login form if user is not logged in
      if(user == null) {
         context.redirect("/");
      }
      else {
         String html = "<html>\n";

         // Add some Header information
         html = html + "<head>" + "<title>Page 4: All Apartments</title>\n";

         // Add some CSS (external file)
         html = html + "<link rel='stylesheet' type='text/css' href='common.css' />\n";

         // Add the body
         html = html + "<body>\n";
         html = html + "USER: " + "<div>" + user + "</div>";
         
         // Add HTML for link back to the homepage
         html = html + "<h1>Page 4: All apartments</h1>\n";
         html = html + "<p>Return to Homepage: \n";
         html = html + "<a href='/'>Link to Homepage</a>\n";
         html = html + "</p>\n";

         // Look up some information from JDBC
         // First we need to use your JDBCConnection class
         MongoDBConnection mongodb = MongoDBConnection.getConnection();

         // Next we will ask this *class* for the movies
         ArrayList<String> apartments = mongodb.getAllApartmentNames();
         // Add HTML for the movies list
         html = html + "<h1>Apartments</h1>" + "<ul>\n";

         // Finally we can print out all of the movies
         for (String apartment : apartments) {
            html = html + "<li>" + apartment + "</li>\n";
         }

         // Finish the List HTML
         html = html + "</ul>\n";

         // Finish the HTML webpage
         html = html + "</body>" + "</html>\n";

         // DO NOT MODIFY THIS
         // Makes Javalin render the webpage
      
         context.html(html);
      }
   }
}
