package app.service;

import app.dao.UserCheckDaoImpl;

public class UserCheckServiceImpl {
    public boolean getCheckResult(String name, String id){
        UserCheckDaoImpl userCheckDaoImpl =new UserCheckDaoImpl();
        return userCheckDaoImpl.getCheckDB(name, id);
    }
}
