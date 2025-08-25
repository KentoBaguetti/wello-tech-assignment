package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class User {

    private String username;
    private String email;

    public User(@JsonProperty("username") String username, @JsonProperty("email") String email) {
    this.username = username;
    this.email = email;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

}
