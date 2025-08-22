package com.example.demo.service;

import com.example.demo.dao.UserDao;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final UserDao userDao;

    @Autowired
    public UserService(@Qualifier("inMemory") UserDao userDao) {
        this.userDao = userDao;
    }

    public int addUser(User user) {
        return this.userDao.insertUser(user);
    }

    public List<String> filterByEmail(String keyword) {
        return userDao.filterByEmail(keyword);
    }

    public List<String> filterByPage(Integer pageNumber) {
        return userDao.getPage(pageNumber);
    }

    public Map<Integer, List<User>> allUsers() {
        return userDao.getAllUsers();
    }

    public boolean checkInRange(Integer index) {
        return userDao.inRange(index);
    }

}
