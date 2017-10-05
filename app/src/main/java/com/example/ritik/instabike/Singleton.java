package com.example.ritik.instabike;

/**
 * Created by Akash Chetty on 21-Mar-17.
 */

public class Singleton {

    private static Singleton instance;

    public static Singleton getInstance() {
        if (instance == null)
            instance = new Singleton();
        return instance;
    }

    private Singleton() {
    }

    private String User;

    public String getUser() {
        return User;
    }

    public void setUser(String User) {
        this.User = User;
    }

}