package app;
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
 */
public class Logout implements Handler {

   // URL of this page relative to http://localhost:7000/
   public static final String URL = "/logout.html";
   public static final String TEMPLATE = "home.html";
   @Override
   public void handle(Context context) throws Exception {
      context.sessionAttribute("username_id", "");
      System.out.println(context.sessionAttribute("username_id")+" i am the logout");
      context.redirect("/");
    }
}
