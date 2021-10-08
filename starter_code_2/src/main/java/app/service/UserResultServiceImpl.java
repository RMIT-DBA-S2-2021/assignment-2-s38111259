package app.service;

import java.util.ArrayList;

import app.dao.UserResultDaoImpl;

public class UserResultServiceImpl {
    public ArrayList<String> getResultDetail(String name){
        UserResultDaoImpl userResultDaoImpl = new UserResultDaoImpl();
        ArrayList<String> result = userResultDaoImpl.getSearchDB(name);
        return result;
    }
}
