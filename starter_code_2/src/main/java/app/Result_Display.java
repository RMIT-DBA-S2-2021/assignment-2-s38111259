package app;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;

import app.service.UserResultServiceImpl;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin by writing the raw HTML into a Java
 * String object
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
import javax.servlet.http.Cookie;

public class Result_Display implements Handler {

   // URL of this page relative to http://localhost:7000/
   public static final String URL = "/result_display.html";
   public static final String TEMPLATE = "result_display.html";

   @Override
   public void handle(Context context) throws Exception {
      Map<String,ArrayList<String>> model = new HashMap<>();
      Cookie[] cookies = context.req.getCookies();
      Cookie cookie = cookies[0];
      System.out.println(cookie.getValue());
      UserResultServiceImpl userResultServiceImpl = new UserResultServiceImpl();
      ArrayList<String> display = userResultServiceImpl.getResultDetail(cookie.getValue());
      model.put("display", display);
      context.render(TEMPLATE,model);
   }
}
