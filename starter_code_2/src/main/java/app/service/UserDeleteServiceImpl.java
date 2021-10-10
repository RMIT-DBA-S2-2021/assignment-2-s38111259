package app.service;
import app.dao.UserDeleteDaoImpl;
public class UserDeleteServiceImpl {
    public boolean isUserDeleted(String hotel_name, String user_name){
        UserDeleteDaoImpl UserDeleteDaoImpl = new UserDeleteDaoImpl();
        return UserDeleteDaoImpl.deleteUserDB(hotel_name, user_name);
    }
}
