package app;
import io.javalin.http.Context;
import io.javalin.http.Handler;
public class page_result implements Handler {
    public static final String URL = "/page_result.html";
   public static final String TEMPLATE = "page_result.html";

   @Override
   public void handle(Context context) throws Exception {
      context.render(TEMPLATE);
    }
}
