package com.example.myproject;

public class User {
    private String id;
    private String name;
    private String phone;
    private String identity;

    public User(String id, String name, String phone, String identity) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.identity = identity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getIdentity() {
        return identity;
    }
}
