package app.service;
import app.dao.UserUpdateDaoImpl;
public class UserUpdateServiceImpl {
    public boolean isReviewUpdated(String hotel_name, String user_name, String text){
        UserUpdateDaoImpl userUpdateDaoImpl = new UserUpdateDaoImpl();
        return userUpdateDaoImpl.isUpdateDB(hotel_name, user_name, text);
    }
}
