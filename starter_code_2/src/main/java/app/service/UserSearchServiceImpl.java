package app.service;

import java.util.ArrayList;

import app.dao.UserSearchDaoImpl;

public class UserSearchServiceImpl {
    public ArrayList<String> getSearchDetail(String location, String market, String accomodates, String bedroom, String bed, String ptype, ArrayList<String> al, String price, String review, String host, String partial, String page){
        UserSearchDaoImpl userSearchDaoImpl = new UserSearchDaoImpl();
        ArrayList<String> result = userSearchDaoImpl.getSearchDB(location, market, accomodates, bedroom, bed, ptype, al, price, review, host, partial, page);
        return result;
    }
}
