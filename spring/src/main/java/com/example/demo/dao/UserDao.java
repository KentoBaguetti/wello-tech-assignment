package com.example.demo.dao;

import com.example.demo.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("inMemory")
public class UserDao implements UserDaoInterface {

    // class vars
    private final int maxUsersPerPage = 10;
    private Integer numberOfPages = 1;

    // init the in-memory db
    private static Map<Integer, List<User>> DB = new HashMap<Integer, List<User>>(Map.of(
            1, new ArrayList<User>()));

    @Override
    public int insertUser(UUID id, User user) {

        // check if an email was given
        if (user.getEmail() == null) {
            return 0;
        }

        // check if the email alr exists
        if (this.containsEmail(user.getEmail())) {
            return 2;
        }

        // check if the email is a valid address - simple check
        if (!user.getEmail().contains("@")) {
            return 3;
        }

        // if the user count on a page is > maxUsersPerPage, create a new page
        if (DB.get(numberOfPages).size() >= maxUsersPerPage) {
            numberOfPages++;
            DB.put(numberOfPages, new ArrayList<User>());
        }

        List<User> page = DB.get(numberOfPages);
        page.add(new User(id, user.getEmail()));

        return 1;

    }

    // add emails to a list and return it based on matching email keywords
    @Override
    public List<String> filterByEmail(String keyword) {

        List<String> res = new ArrayList<String>();

        for (int i = 1; i <= numberOfPages; i++) {

            for (User user : DB.get(i)) {
                if (user.getEmail().contains(keyword)) {
                    res.add(user.getEmail());
                }
            }

        }

        return res;

    }

    // return a page from a map
    @Override
    public List<String> getPage(Integer index) {

        List<String> res = new ArrayList<String>();

        for (User user : DB.get(index)) {
            res.add(user.getEmail());
        }

        return res;

    }

    @Override
    public Map<Integer, List<User>> getAllUsers() {
        return this.DB;
    }

    // helper method
    private boolean containsEmail(String email) {

        for (int i = 1; i <= numberOfPages; i++) {

            for (User user : DB.get(i)) {
                if (user.getEmail().equals(email)) {
                    return true;
                }
            }

        }

        return false;

    }

    // helper method
    public boolean inRange(Integer index) {
        return index > 0 && index <= numberOfPages;
    }

}
