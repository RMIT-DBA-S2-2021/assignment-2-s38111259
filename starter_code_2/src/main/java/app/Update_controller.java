package app;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

import app.service.UserUpdateServiceImpl;
import io.javalin.http.Context;
import io.javalin.http.Handler;
public class Update_controller implements Handler {
   // URL of this page relative to http://localhost:7000/
   public static final String URL = "/update_controller.html";
   public static final String TEMPLATE = "update_display.html";
   @Override
   public void handle(Context context) throws Exception {
      String str = context.formParam("comment");
      System.out.println(str+" bkjdrfgnk");
      Map<String,String> model = new HashMap<>();
      Cookie[] cookies = context.req.getCookies();
      System.out.println(cookies.length+" cookies length");
      System.out.println(cookies[1].getValue());
      String values = cookies[1].getValue();
      String Rows[] = values.split("~~,");

      UserUpdateServiceImpl userUpdateServiceImpl = new UserUpdateServiceImpl();
      if(str!=null && !str.isEmpty()){
         if(userUpdateServiceImpl.isReviewUpdated(Rows[0], Rows[1], str)){
            String u_message = "Successfully Updated";
            model.put("u_message", u_message);
            context.render("update_display.html",model);
         }
         else{
            String w_message = "Reviews not Updated";
            model.put("w_message", w_message);
            context.render("update_display.html", model);
         }
      }
      else{
         System.out.println("Else is working");
         context.render(TEMPLATE);
      }
   }
}
