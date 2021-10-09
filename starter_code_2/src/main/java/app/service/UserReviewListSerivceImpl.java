package app.service;

import java.util.ArrayList;

import app.dao.UserReviewListDaoImpl;

public class UserReviewListSerivceImpl {
    public ArrayList<String> getReviewList(String rname){
        UserReviewListDaoImpl userReviewListDaoImpl = new UserReviewListDaoImpl();
        return userReviewListDaoImpl.getReviewList(rname);
    }
}
