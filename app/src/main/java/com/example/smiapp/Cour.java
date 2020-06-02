package com.example.smiapp;

public class Cour {
    private  String id ;
    private String title ;
    private String link ;
    private String info ;
    private String photolink ;
    private String type ;
    private String module_id ;


    public Cour(String id, String title, String link, String info, String photolink, String type, String module_id) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.info = info;
        this.photolink = photolink;
        this.type = type;
        this.module_id = module_id;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getInfo() {
        return info;
    }

    public String getPhotolink() {
        return photolink;
    }

    public String getType() {
        return type;
    }

    public String getModule_id() {
        return module_id;
    }
}
