package app.service;
import java.util.ArrayList;
import app.dao.UserResultDaoImpl;
public class UserResultServiceImpl {
    public ArrayList<String> getResultDetail(String id){
        UserResultDaoImpl userResultDaoImpl = new UserResultDaoImpl();
        ArrayList<String> result = userResultDaoImpl.getSearchDB(id);
        return result;
    }
}
