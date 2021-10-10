package app;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import app.service.UserResultServiceImpl;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class Result_Display implements Handler {
   // URL of this page relative to http://localhost:7000/
   public static final String URL = "/result_display.html";
   public static final String TEMPLATE = "result_display.html";
   @Override
   public void handle(Context context) throws Exception {
      Map<String,ArrayList<String>> model = new HashMap<>();
      Cookie[] cookies = context.req.getCookies();
      Cookie cookie = cookies[0];
      UserResultServiceImpl userResultServiceImpl = new UserResultServiceImpl();
      ArrayList<String> display = userResultServiceImpl.getResultDetail(cookie.getValue());
      display.add(context.sessionAttribute("username_id")+"~~");
      display.add(context.sessionAttribute("lo"));
      System.out.println(display);
      System.out.println(context.sessionAttribute("username_id")+" I am the key");
      model.put("display", display);
      context.render(TEMPLATE,model);
   }
}
