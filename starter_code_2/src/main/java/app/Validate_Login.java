package app;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.HashMap;
import java.util.Map;
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
public class Validate_Login implements Handler {

   // URL of this page relative to http://localhost:7000/
   public static final String URL = "/validate_login.html";
   public static final String TEMPLATE = "home.html";
   public static String usernames[] = {"halil", "bill"};
   public static String passwords[] = {"halilpass", "billpass"};

   @Override
   public void handle(Context context) throws Exception {
    Map<String, Object> model = new HashMap<String, Object>();
      String username = context.formParam("username");
      String password = context.formParam("pass");
      
    if(username!=null && password!=null){
        if(checkPassword(context, username, password)){
            String msg = "Valid";
            model.put("valid_msg", msg);
            model.put("Username", username);
            context.render(TEMPLATE,model);
            }
        else{
            String msg = "Invalid Username/Password";
            model.put("err_msg", msg);
            context.render("login.html",model);
            }
        }
        context.render(TEMPLATE);
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


    

 // login the user by creating a random user id and associating in their cookie and session 
    public void login(Context context, String username){
        String id = UUID.randomUUID().toString();
//      context.sessionAttribute("id", id);
        context.sessionAttribute(id, username);
        context.cookie("id", id);
 }
}
