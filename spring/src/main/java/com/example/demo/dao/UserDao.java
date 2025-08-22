package com.example.demo.dao;

import com.example.demo.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("inMemory")
public class UserDao implements UserDaoInterface {

    private final int maxUsersPerPage = 10;
    private Integer numberOfPages = 1;
    private static Map<Integer, List<User>> DB = new HashMap<Integer, List<User>>(Map.of(
            1 , new ArrayList<User>()
    ));

    @Override
    public int insertUser(UUID id, User user) {

        if (user.getEmail() == null) {
            return 0;
        }

        if (this.containsEmail(user.getEmail())) {
            return 2;
        }

        if (!user.getEmail().contains("@")) {
            return 3;
        }

        if (DB.get(numberOfPages).size() >= maxUsersPerPage) {
            numberOfPages++;
            DB.put(numberOfPages, new ArrayList<User>());
        }

        List<User> page = DB.get(numberOfPages);
        page.add(new User(id, user.getEmail()));

        return 1;

    }

    @Override
    public List<String> filterByEmail(String keyword) {

        List<String> res = new ArrayList<String>();

        for (int i = 1; i <= numberOfPages; i++ ) {

            for (User user : DB.get(i)) {
                if (user.getEmail().contains(keyword)) {
                    res.add(user.getEmail());
                }
            }

        }

        return res;

    }

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

    private boolean containsEmail(String email) {

        for (int i = 1; i <= numberOfPages; i++ ) {

            for (User user : DB.get(i)) {
                if (user.getEmail().equals(email)) {
                    return true;
                }
            }

        }

        return false;

    }

    public boolean inRange(Integer index) {
        return index > 0 && index <= numberOfPages;
    }

}
