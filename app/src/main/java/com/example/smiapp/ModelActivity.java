package com.example.smiapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.smiapp.Mail.Config;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModelActivity extends AppCompatActivity {
    private FrameLayout adContainerView;
    private AdView adView;

       RequestQueue requestQueue ;
       Globalv globalv ;

    //spinner
       Spinner spinner ;
       ArrayList<String> strings ;
    //listmodel
       ArrayList<Model> models ;
    RecyclerView recyclerView ;
    AdpterRecycleVwModel adpterRecycleVwModel ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);






        requestQueue = Volley.newRequestQueue(this) ;
        globalv = (Globalv) getApplicationContext();
        models = new ArrayList<>() ;
        recyclerView =findViewById(R.id.id_recyModule) ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //spinner
        spinner = findViewById(R.id.spinner) ;
        strings = new ArrayList<>() ;
        strings.add(0,"Chose  Semestre") ;
        strings.add(" S1 ") ;
        strings.add("S2") ;
        strings.add("S3") ;
        strings.add("S4") ;
        strings.add("S5") ;
        strings.add("S6") ;
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,strings);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).equals("Chose Semestre")){
           /// do nothing
                    Toast.makeText(ModelActivity.this, "Chose Semestre", Toast.LENGTH_SHORT).show();
                }else{
                   String semestre = adapterView.getItemAtPosition(i).toString() ;
                   globalv.setSemestre(semestre);
                   getModele();
                }





            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                
            }
        });


    }

    public void getModele(){
        models.clear();

        String url=Config.IP+"getmodule.php?semestre="+globalv.getSemestre() ;
        new TaskAdapter(ModelActivity.this).execute(5);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("module") ;
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id_module = jsonObject.getString("id") ;
                        String name = INITCAP(jsonObject.getString("name") )    ;
                        String dureEnHeure = jsonObject.getString("dure_en_heure") ;
                        String semestre = jsonObject.getString("semestre");
                        String imageModulee = jsonObject.getString("image") ;

                        models.add(new Model(id_module,name,dureEnHeure,semestre,imageModulee)) ;

                    }
                    adpterRecycleVwModel = new AdpterRecycleVwModel(ModelActivity.this,R.layout.item_modele,models,globalv.getUsername()) ;
                    recyclerView.setAdapter(adpterRecycleVwModel);

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

        requestQueue.add(request) ;
}

public  String INITCAP(String input){
    String output = input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    return   output ;
}



}
