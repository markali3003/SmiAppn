package com.example.smiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.smiapp.Mail.Config;
import com.example.smiapp.Mail.SendMail;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RequestQueue requestQueue ;
    Globalv globalv;
    DataBS dataBS ;
    EditText txt_name,txt_password;
    Button bu_login ;
    CheckBox checkBox_save_data ;
    TextView textView_recuperation,textView_registre ;
    LinearLayout layout_recuper ;
    LinearLayout layout_login ;
    EditText txt_email_recuperer ;
    Button bu_send_info ;
    String indice_checked="0" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        requestQueue = Volley.newRequestQueue(this) ;
         globalv = (Globalv) getApplicationContext();
         dataBS = new DataBS(this);
        txt_name = findViewById(R.id.id_edittext_name_login) ;
        txt_password = findViewById(R.id.id_editText_password_login);
        bu_login = findViewById(R.id.id_button_login) ;
        checkBox_save_data = findViewById(R.id.id_checkBox_login) ;
        textView_recuperation = findViewById(R.id.id_textview_recuperer);
        textView_registre = findViewById(R.id.id_textview_registre);
        layout_recuper = findViewById(R.id.id_ly_recuperation);
        layout_login = findViewById(R.id.id_ly_login) ;
        txt_email_recuperer = findViewById(R.id.id_editText_email_recuperer) ;
        bu_send_info = findViewById(R.id.id_button_send_info);

           if(dataBS.getIndicedata() != null ){
               if(dataBS.getIndicedata().equals("1")){
                   txt_name.setText(dataBS.getName());
                   txt_password.setText(dataBS.getPassword());
                   checkBox_save_data.setChecked(true);
                   indice_checked="1" ;


               }else{
                   checkBox_save_data.setChecked(false);
                   indice_checked="0" ;
               }


           }




        checkBox_save_data.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                 indice_checked="1" ;
                }else{
                 indice_checked="0" ;
                }
            }
        });

    }




    public void login(View view){
        new TaskAdapter(MainActivity.this).execute(5);
         final String name = txt_name.getText().toString().trim() ;
         final String password = txt_password.getText().toString().trim();
        Response.Listener<String> req = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response) ;
                    boolean result = jsonObject.getBoolean("success");
                    if(result){

                        dataBS.updateData_Login(name,password,indice_checked);
                        globalv.setUsername(name);
                        startActivity(new Intent(MainActivity.this,ModelActivity.class));

                    }else{
                        Toast.makeText(MainActivity.this,"name or password pas valid",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        if(name.isEmpty()){
            txt_name.setError("name vide");
            txt_name.requestFocus() ;
        }else if(password.isEmpty()){
            txt_password.setError("name vide");
            txt_password.requestFocus() ;
        }else if(name.isEmpty() && password.isEmpty()){
            Toast.makeText(MainActivity.this,"colon vide",Toast.LENGTH_LONG).show();
        }else if(!(name.isEmpty() && password.isEmpty())) {
           SendData_login sendDataLgin = new SendData_login(name,password,req);
           requestQueue.getCache().clear();
            requestQueue.add(sendDataLgin) ;

        }else{
            Toast.makeText(MainActivity.this,"error ocured",Toast.LENGTH_LONG).show();
        }

    }

    public void registre(View view){
        startActivity(new Intent(MainActivity.this,RegistreActivity.class));
    }
    public void recuperer(View view){
        layout_login.setVisibility(View.GONE);
        layout_recuper.setVisibility(View.VISIBLE);



    }

    public void SendInfo(View view) {
        final String email = txt_email_recuperer.getText().toString().trim() ;
        String url = Config.IP+"forget_password.php?email="+email ;
        if(email.isEmpty()){
            txt_email_recuperer.setError("colon vide");
            txt_email_recuperer.requestFocus() ;
        }else{
             JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        boolean result = response.getBoolean("result") ;
                        if(result){

                        }else{
                            Toast.makeText(MainActivity.this,"Adresse introuvable ",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        JSONArray jsonArray = response.getJSONArray("info") ;
                        JSONObject jsonObject = jsonArray.getJSONObject(0) ;
                        String name = jsonObject.getString("name") ;
                        String password = jsonObject.getString("password");
                        SendMail sendMail = new SendMail(MainActivity.this,email,"","hello I'm Admin :\n your name is: "+name+"\nyour password is : "+password);
                        sendMail.execute() ;
                        layout_login.setVisibility(View.VISIBLE);
                        layout_recuper.setVisibility(View.GONE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());
                }
            });
             request.setShouldCache(false) ;
             requestQueue.add(request) ;
        }




    }

    public void close(View view) {
        layout_recuper.setVisibility(View.GONE);
        layout_login.setVisibility(View.VISIBLE);

    }



}
