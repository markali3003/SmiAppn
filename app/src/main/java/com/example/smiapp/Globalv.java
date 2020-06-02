package com.example.smiapp;

import android.app.Application;

public class Globalv extends Application {
    private String username ;
    private String semestre ;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }


}
