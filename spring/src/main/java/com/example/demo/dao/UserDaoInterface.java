package com.example.demo.dao;

import com.example.demo.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UserDaoInterface {

    int insertUser(UUID id, User user);

    default int insertUser(User user) {
        UUID id = UUID.randomUUID();
        return this.insertUser(id, user);
    }

    List<String> filterByEmail(String keyword);

    List<String> getPage(Integer index);

    Map<Integer, List<User>> getAllUsers();

    boolean inRange(Integer index);

    private boolean containsEmail(String email) {
        return false;
    };

}
