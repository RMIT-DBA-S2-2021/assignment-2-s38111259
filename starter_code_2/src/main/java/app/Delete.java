package app;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import app.service.UserDeleteServiceImpl;
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
public class Delete implements Handler {
   // URL of this page relative to http://localhost:7000/
   public static final String URL = "/delete.html";
   public static final String TEMPLATE = "reviews_display.html";
   @Override
   public void handle(Context context) throws Exception {
        System.out.println("Yep delete here it is");
        Map<String,String> model = new HashMap<>();
        Cookie[] cookies = context.req.getCookies();
        System.out.println(cookies.length+" cookies length");
        System.out.println(cookies[0].getName());
        int index=0;
        for(int i=0;i<cookies.length;i++){
            if(cookies[i].getName().equals("hot_name")){
                index=i;
            }
        }
        System.out.println(index+"ondexckn");
        String values = cookies[index].getValue();
        String Rows[] = values.split("~~,");
        System.out.println(Arrays.toString(Rows));
        System.out.println(Rows.length+"rwpfjeskln");

        UserDeleteServiceImpl userDeleteServiceImpl = new UserDeleteServiceImpl();
        if(userDeleteServiceImpl.isUserDeleted(Rows[0], Rows[2])){
            String del_msg = "Successfully Deleted";
            model.put("del_msg", del_msg);
            context.render(TEMPLATE,model);
        }
        else{
            String not_del = "Reviews not Deleted";
            model.put("not_del", not_del);
            context.render(TEMPLATE, model);
         }
    }
}
