package com.example.smiapp;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.smiapp.Mail.Config;

import java.util.HashMap;
import java.util.Map;


class SendData_login extends StringRequest {
    private static final String SEND_DATA_URL = Config.IP+"lognIn.php";
    private Map<String, String> mapData;

    public SendData_login(String name, String passwd, Response.Listener<String> reponseLestener) {
        super(Method.POST, SEND_DATA_URL, reponseLestener, null);
        mapData = new HashMap<>() ;
        mapData.put("name",name);
        mapData.put("password",passwd);



    }
    @Override
    public  Map<String,String>  getParams(){
        return mapData ;
    }
}