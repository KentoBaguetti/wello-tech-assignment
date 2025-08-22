package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class User {

    private final UUID id;
    private String email;

    public User(@JsonProperty("id") UUID id, @JsonProperty("email") String email) {
    this.id = id;
    this.email = email;
    }

    public UUID getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

}
