package com.example.smiapp;

public class Model {
    private String id  ;
    private  String name ;
    private String dureEnHeure ;
    private String semestre ;
    private String imageModule ;

    public Model(String id, String name, String dureEnHeure, String semestre, String imageModule) {
        this.id = id;
        this.name = name;
        this.dureEnHeure = dureEnHeure;
        this.semestre = semestre;
        this.imageModule = imageModule;
    }

    public Model(String id, String name, String dureEnHeure, String semestre) {
        this.id = id;
        this.name = name;
        this.dureEnHeure = dureEnHeure;
        this.semestre = semestre;
    }

    public Model(String name, String dureEnHeure, String semestre) {
        this.name = name;
        this.dureEnHeure = dureEnHeure;
        this.semestre = semestre;
    }



    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDureEnHeure() {
        return dureEnHeure;
    }

    public String getSemestre() {
        return semestre;
    }

    public String getImageModule() {
        return imageModule;
    }
}
