package app;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import app.service.UserReviewListSerivceImpl;
import io.javalin.http.Context;
import io.javalin.http.Handler;
public class Review_Display implements Handler {
   // URL of this page relative to http://localhost:7000/
   public static final String URL = "/reviews_display.html";
   public static final String TEMPLATE = "reviews_display.html";
   @Override
   public void handle(Context context) throws Exception {
      Map<String,ArrayList<String>> model = new HashMap<>();
      Cookie[] cookies = context.req.getCookies();
      UserReviewListSerivceImpl userReviewListSerivceImpl = new UserReviewListSerivceImpl();
      ArrayList<String> list_display = userReviewListSerivceImpl.getReviewList(cookies[0].getValue());
      model.put("list_display", list_display);
      context.render(TEMPLATE, model);
   }
}
