package com.newspaper.newspaper.service;

public class UserService {

    public String createUser() {
        return "User created successfully";
    }

    public String getUserDetails(String username) {
        return "User details for " + username;
    }

    public String updateUser(String username) {
        return "User " + username + " updated successfully";
    }

    public String deleteUser(String username) {
        return "User " + username + " deleted successfully";
    }

}
