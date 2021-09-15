package app;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.UUID;

import javax.servlet.http.Cookie;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin by writing the raw HTML into a Java
 * String object
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class Index implements Handler {

   // URL of this page relative to http://localhost:7000/
   public static final String URL = "/";
   public static String usernames[] = {"halil", "bill"};
   public static String passwords[] = {"halilpass", "billpass"};

   @Override
   public void handle(Context context) throws Exception {
      // Create a simple HTML webpage in a String
      String html = "<html>\n";
      String user = "";

      // Add some Header information
      html = html + "<head>" + "<title>Homepage</title>\n";

      // Add some CSS (external file)
      html = html + "<link rel='stylesheet' type='text/css' href='common.css' />\n";

      // Add the body
      html = html + "<body>\n";

      // Add HTML for the logo.png image
      html = html + "<img src='logo.png' height='200px'/>\n";

      user = Util.getLoggedInUser(context);
      //display login form if user is not logged in
      if(user == null) {


         // get username and password (and hidden field to identify login button pressed)
         String username_textbox = context.formParam("username_textbox");
         String password_textbox = context.formParam("password_textbox");
         String login_hidden = context.formParam("login_hidden");

         if(username_textbox == null){
            username_textbox = "";
         }
         html = html + "<form action='/' method='post'>\n";
         html = html + "   <div class='form-group'>\n";
         html = html + "      <label for='username_textbox'>Username (Textbox)</label>\n";
         html = html + "      <input class='form-control' id='username_textbox' name='username_textbox' placeholder='username' value='" + username_textbox + "'>\n";
         html = html + "   </div>\n";
         html = html + "   <div class='form-group'>\n";
         html = html + "      <label for='password_textbox'>Username (Textbox)</label>\n";
         html = html + "      <input class='form-control' id='password_textbox' name='password_textbox' placeholder='password'>\n";
         html = html + "   </div>\n";
         html = html + "   <input type='hidden' id='login_hidden' name='login_hidden' value='true'>"; 
         html = html + "   <button type='submit' class='btn btn-primary'>Login</button>\n";
         html = html + "</form>\n";

         // if login button pressed proceess username and password fields
         if(login_hidden != null && login_hidden.equals("true")){
            if (username_textbox == null || username_textbox == "" || password_textbox == null || password_textbox == "") {
               // If username or password NULL/empty, prompt for authorisation details
               html = html + "Enter a username and password\n";
            } else {
               // If NOT NULL, then test password for match against array of users (in your code you will run a query)!
               if(checkPassword(context, username_textbox, password_textbox)) {
                  //matching password found, reload page
                  context.redirect("/");
               } else {
                  // no password match found 
                  html = html + "invalid username/password";
               }
            }
         }
      }
      else {
         // user is logged in - check if logout button pressed
         String logout_hidden = context.formParam("logout_hidden");

         if(logout_hidden != null && logout_hidden.equals("true")){
            // logout clicked
            logout(context);
         } else {
            // logout not clicked - show logout button
            html = html + "<div></div>USER: " + "<div>" + user + "</div>";
            html = html + "<form action='/' method='post'>\n";
            html = html + "   <input type='hidden' id='logout_hidden' name='logout_hidden' value='true'>"; 
            html = html + "   <button type='submit' class='btn btn-primary'>Logout</button>\n";
            html = html + "</form>\n";


            // show logged in page content
            // Add HTML for the list of pages
            html = html + "<h1>Homepage</h1>" + "<p>Links to sub-pages</p>" + "<ul>\n";

            // Link for each page
            html = html + "<li> <a href='page1.html'>Page 1</a> </li>\n";
            html = html + "<li> <a href='page2.html'>Page 2</a> </li>\n";
            html = html + "<li> <a href='page3.html'>Page 3</a> </li>\n";
            html = html + "<li> <a href='page4.html'>Page 4</a> </li>\n";
            html = html + "<li> <a href='page5.html'>Page 5</a> </li>\n";
            html = html + "<li> <a href='page6.html'>Page 6</a> </li>\n";

            // Finish the List HTML
            html = html + "</ul>\n";
         }
      }

      // Finish the HTML webpage
      html = html + "</body>" + "</html>\n";

      // DO NOT MODIFY THIS
      // Makes Javalin render the webpage
      context.html(html);
   }

   // check if username and password matches agains array of users (in your code, this needs to query the database)
   public boolean checkPassword(Context context, String username, String password){
      boolean passwordMatchFound = false;
      for(int i=0; i<usernames.length; i++){
         if(usernames[i].equalsIgnoreCase(username) && passwords[i].equals(password)){
            // match found - login the user
            login(context, username);
            passwordMatchFound = true;
         } 
      }
      return passwordMatchFound;
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
