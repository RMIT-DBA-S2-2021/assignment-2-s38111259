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
public class Home_Caller implements Handler {

   // URL of this page relative to http://localhost:7000/
   public static final String URL = "/";
   public static final String TEMPLATE = "home.html";
   public static final String LOGIN = "login.html";
   public static String usernames[] = {"halil", "bill"};
   public static String passwords[] = {"halilpass", "billpass"};

   @Override
   public void handle(Context context) throws Exception {
      Map<String, Object> model = new HashMap<String, Object>();
      String sign = "";
      model.put("sign", sign);
      context.render(TEMPLATE,model);
    }
}
