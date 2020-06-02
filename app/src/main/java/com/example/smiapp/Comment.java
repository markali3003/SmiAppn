package com.example.smiapp;

public class Comment {
    private  String user_name ;
    private  String comment ;
    private String time_comment ;

    public Comment(String user_name, String text, String time_comment) {
        this.user_name = user_name;
        this.comment = text;
        this.time_comment = time_comment;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getComment() {
        return comment;
    }

    public String getTime_comment() {
        return time_comment;
    }
}
