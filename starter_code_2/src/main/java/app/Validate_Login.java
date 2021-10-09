package app;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.HashMap;
import java.util.Map;
import app.service.UserCheckServiceImpl;
// import app.service.UserCheckServiceImpl;

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

   @Override
   public void handle(Context context) throws Exception {
    Map<String, Object> model = new HashMap<String, Object>();
      String username = context.formParam("username");
      String password = context.formParam("pass");
      
    if(username!=null && password!=null){
        if(checkPassword(context, username, password)){
            context.sessionAttribute("username_id", username);
            context.sessionAttribute("lo",", xyz");
            String msg = "Valid";
            model.put("valid_msg", msg);
            model.put("Username", context.sessionAttribute("username_id"));
            model.put("lo_button", context.sessionAttribute("lo"));
            // Cookie cookie = new Cookie("Username", username);
            // cookie.setPath("/result_display.html");
            // context.res.addCookie(cookie);
            // context.res.
            context.render(TEMPLATE,model);
            }
        else{
            System.out.println("invalid");
            String msg = "Invalid Username/Password";
            model.put("err_msg", msg);
            context.render("login.html",model);
            }
        }
    }

    // check if username and password matches agains array of users (in your code, this needs to query the database)
   public boolean checkPassword(Context context, String name, String id){
    UserCheckServiceImpl userCheckServiceImpl = new UserCheckServiceImpl();
    return userCheckServiceImpl.getCheckResult(name, id);
   }
}
