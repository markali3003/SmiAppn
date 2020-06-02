package com.example.smiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegistreActivity extends AppCompatActivity {
    RequestQueue requestQueue ;
    DataBS dataBS ;
    Globalv globalv ;
    String indice_checked ="0";
EditText txt_name,txt_email,txt_password1,txt_password2 ;
Button bu_signUp ;
CheckBox  checkBox_savedata,checkBox_showPassword ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre);
             requestQueue = Volley.newRequestQueue(this) ;
             dataBS = new DataBS(this);
             globalv = (Globalv) getApplicationContext() ;
        txt_name = findViewById(R.id.id_editText_name) ;
        txt_email = findViewById(R.id.id_editText_email) ;
        txt_password1 = findViewById(R.id.id_editText_password1) ;
        txt_password2 = findViewById(R.id.id_editText_password2) ;
        bu_signUp = findViewById(R.id.id_button_singUp) ;
        checkBox_savedata = findViewById(R.id.id_checkBox_savedata) ;
        checkBox_showPassword = findViewById(R.id.id_checkBox_show_password) ;
        checkBox_savedata.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    indice_checked="1" ;
                }else{
                    indice_checked="0" ;
                }
            }
        });
        checkBox_showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    txt_password2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    txt_password2.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

    }

    public void signUp(View view){
             final String name = txt_name.getText().toString().trim() ;
             final String email = txt_email.getText().toString().trim() ;
             final String password1 = txt_password1.getText().toString().trim() ;
              String password2 = txt_password2.getText().toString().trim();

        final Response.Listener<String> reponseLestener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response) ;
                    boolean result = jsonObject.getBoolean("success") ;
                    if(result){
                     Toast.makeText(RegistreActivity.this,"you are registre now",Toast.LENGTH_LONG).show();
                        dataBS.updateData_Registre(name,email,password1,indice_checked) ;
                        globalv.setUsername(name);
                        startActivity(new Intent(RegistreActivity.this,ModelActivity.class));
                        RegistreActivity.this.finish();

                    }else{
                        Toast.makeText(RegistreActivity.this,"error occured",Toast.LENGTH_LONG).show();
                        txt_name.setError("name pas valid");
                        txt_name.requestFocus() ;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };






        if (name.isEmpty()) {
            txt_name.setError("entrer votre nom");
            txt_name.requestFocus();
        } else if (email.isEmpty()) {
            txt_email.setError("enter votre email");
            txt_email.requestFocus();
        } else if (password1.isEmpty()) {
            txt_password1.setError("enter votre password");
            txt_password1.requestFocus();
        } else if (password2.isEmpty()) {
            txt_password2.setError("encore enter votre password");
            txt_password2.requestFocus();
        } else if (!password2.equalsIgnoreCase(password1)) {
            txt_password2.setError("enter le meme password");
            txt_password2.requestFocus();
        }else if (!(name.isEmpty() && email.isEmpty() && password1.isEmpty() && password2.isEmpty())) {
            SendData_registre sendDataLogin = new SendData_registre(name, email, password1, reponseLestener);
            requestQueue.getCache().clear();
            requestQueue.add(sendDataLogin);

        }

    }


    public void go_to_login(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }
}
