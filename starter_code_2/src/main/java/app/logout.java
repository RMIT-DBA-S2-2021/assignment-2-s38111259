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
public class logout implements Handler {

   // URL of this page relative to http://localhost:7000/
   public static final String URL = "/logout.html";
   public static final String TEMPLATE = "home.html";

   @Override
   public void handle(Context context) throws Exception {
      logout(context);
      context.render(TEMPLATE);
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
}
