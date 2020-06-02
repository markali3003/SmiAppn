package com.example.smiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.smiapp.Mail.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FavorableListeActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    Globalv globalv;
    ArrayList<Cour> cours;
    AdapterRecycleVwCour adapterRecycleVwCour;
    RecyclerView recyclerView;
    TextView txt_acceuill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorable_liste);
        requestQueue = Volley.newRequestQueue(this);
        globalv = (Globalv) getApplicationContext();
        txt_acceuill = findViewById(R.id.id_acceuil);
        cours = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view_liste_fav);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getListeFav();

    }

    private void getListeFav() {
        String url = Config.IP + "getListeFav.php?username=" + globalv.getUsername();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("result");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String title = INITCAP(jsonObject.getString("title"));
                        String link = jsonObject.getString("link");
                        String info = INITCAP(jsonObject.getString("info"));
                        String photolink = jsonObject.getString("photolink");
                        String type = jsonObject.getString("type");
                        String module_id = jsonObject.getString("moduleID");
                        cours.add(new Cour(id, title, link, info, photolink, type, module_id));
                    }
                    adapterRecycleVwCour = new AdapterRecycleVwCour(cours, FavorableListeActivity.this, globalv.getUsername());
                    recyclerView.setAdapter(adapterRecycleVwCour);
                    if (!cours.isEmpty()) {
                        txt_acceuill.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("error", error.getMessage());
            }
        });
        req.setShouldCache(false) ;
        requestQueue.add(req);
    }

    public String INITCAP(String input) {
        String output = input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
        return output;
    }


}
