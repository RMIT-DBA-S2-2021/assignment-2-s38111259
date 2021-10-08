package app;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
public class page_result implements Handler {
    public static final String URL = "/page_result.html";
   public static final String TEMPLATE = "page_result.html";

   @Override
   public void handle(Context context) throws Exception {
      context.render(TEMPLATE);
    }
}
