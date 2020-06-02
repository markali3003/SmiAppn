package com.example.smiapp;

public class Rating {
    private String username ;
    private float rating ;

    public Rating(String username, float rating) {
        this.username = username;
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public float getRating() {
        return rating;
    }
}
