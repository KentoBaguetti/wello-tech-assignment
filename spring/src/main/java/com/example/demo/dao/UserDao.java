package com.example.demo.dao;

import com.example.demo.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("inMemory")
public class UserDao implements UserDaoInterface {

    // class vars
    private int numberOfUsers = 0;

    // init the in-memory db
    private static List<User> DB = new ArrayList<User>();

    @Override
    public int insertUser(String username, User user) {

        // check if an email was given
        if (user.getEmail() == null) {
            return 0;
        }

        // check if the email is a valid address - simple check
        if (!user.getEmail().contains("@")) {
            return 3;
        }

        // if the user count on a page is > maxUsersPerPage, create a new page

        this.DB.add(user);

        return 1;

    }

    // add emails to a list and return it based on matching email keywords
    @Override
    public List<User> filterByEmail(String keyword) {

        List<User> res = new ArrayList<User>();

       for (int i = 0; i < this.DB.size(); i++) {
           User currUser = this.DB.get(i);
           if (currUser.getEmail().contains(keyword)) {
               res.add(currUser);
           }
       }

       return res;

    }

    // return a page from a map
    @Override
    public List<User> paginate(Integer offset, Integer limit) {

        int dbSize = this.DB.size();

        if (offset <= 0) {
            offset = 0;
        }

        if (offset >= dbSize) {
            return new ArrayList<User>();
        }

        if (limit <= 0) {
            return new ArrayList<User>();
        }

        int endIndex = offset + limit;

        if (endIndex > dbSize) {
            endIndex = dbSize;
        }

        return this.DB.subList(offset, endIndex);

    }

    @Override
    public List<User> getAllUsers() {
        return this.DB;
    }

    @Override
    public int getTotalNumberOfUsers() {
        return this.DB.size();
    }

}
