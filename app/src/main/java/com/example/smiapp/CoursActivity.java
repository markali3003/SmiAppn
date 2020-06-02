package com.example.smiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
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
import java.util.List;

public class CoursActivity extends AppCompatActivity {
    RequestQueue requestQueue ;
    Globalv globalv ;
     String id_module ;
     List<Cour> cours ;
    RecyclerView recyclerView ;
    AdapterRecycleVwCour adapterRecycleVwCour ;
    TextView txt_number ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cours);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        requestQueue = Volley.newRequestQueue(this) ;
        globalv = (Globalv) getApplicationContext();
        new TaskAdapter(CoursActivity.this).execute(5);
        id_module = getIntent().getStringExtra("id_module") ;
        cours = new ArrayList<>() ;
        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
       // recyclerView.setLayoutManager(new LinearLayoutManager(this));
       // recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this) ;
        recyclerView.setLayoutManager(layoutManager);
        txt_number = findViewById(R.id.id_number_cour) ;

            //new TaskAdapter(this).execute(5);
        getCour();

    }


    public void getCour(){
        String url = Config.IP+"getCourByIdModule.php?module_id="+id_module ;
                  cours.clear();
         JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray =  response.getJSONArray("all_movies") ;
                    txt_number.setText("total number cour is :"+jsonArray.length());
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i) ;
                          String id = jsonObject.getString("id");
                         String title = INITCAP(jsonObject.getString("title")) ;
                         String link = jsonObject.getString("link");
                         String info = INITCAP( jsonObject.getString("info"))    ;
                         String photolink = jsonObject.getString("photolink");
                         String type = jsonObject.getString("type");
                         String module_id = jsonObject.getString("moduleID") ;
                         cours.add(new Cour(id,title,link,info,photolink,type,module_id)) ;
                    }

                    adapterRecycleVwCour = new AdapterRecycleVwCour(cours,CoursActivity.this,globalv.getUsername()) ;
                    recyclerView.setAdapter(adapterRecycleVwCour);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error",error.getMessage());
            }
        });

        requestQueue.add(request) ;

    }
    public  String INITCAP(String input){
        String output = input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
        return   output ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterRecycleVwCour.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId() ;
        switch (id){
            case R.id.action_fav :
                startActivity(new Intent(this,FavorableListeActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
