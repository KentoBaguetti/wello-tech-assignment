package com.example.demo.dao;

import com.example.demo.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UserDaoInterface {

    int insertUser(String username, User user);

    // allow for the creation of users without the need of an UUID explicitly from
    // the user
    default int insertUser(User user) {
        return this.insertUser("Guest", user);
    }

    List<User> filterByEmail(String keyword);

    List<User> paginate(Integer offset, Integer limit);

    List<User> getAllUsers();

    int getTotalNumberOfUsers();

}
