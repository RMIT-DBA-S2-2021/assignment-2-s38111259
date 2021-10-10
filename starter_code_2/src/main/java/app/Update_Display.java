package app;
import io.javalin.http.Context;
import io.javalin.http.Handler;
public class Update_Display implements Handler {
   // URL of this page relative to http://localhost:7000/
   public static final String URL = "/update_display.html";
   public static final String TEMPLATE = "update_display.html";
   @Override
   public void handle(Context context) throws Exception {
      context.render(TEMPLATE);
   }
}
