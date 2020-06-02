package com.example.smiapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.smiapp.Mail.Config;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdpterRecycleVwModel extends RecyclerView.Adapter<AdpterRecycleVwModel.ModuleViewHolder>  {

   Context context ;
   int resource ;
   ArrayList<Model> models ;
   Dialog dialog ;
   String username ;




    public AdpterRecycleVwModel(Context context, int resource, ArrayList<Model> models,String username) {
        this.context = context;
        this.resource = resource;
        this.models = models;
        this.username=username ;

    }

    @NonNull
    @Override
    public AdpterRecycleVwModel.ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(resource,parent,false) ;
        return new ModuleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdpterRecycleVwModel.ModuleViewHolder holder, int position) {
       final Model model = models.get(position) ;
              holder.txt_name.setText(model.getName());
              holder.txt_dureheure.setText(model.getDureEnHeure());
        Picasso.get().load(Config.IP+"image/imageModule/"+model.getImageModule()).into(holder.imageView);
        holder.bu_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,CoursActivity.class) ;
                intent.putExtra("id_module",model.getId()) ;
                context.startActivity(intent);
            }
        });
        holder.txt_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,CoursActivity.class) ;
                intent.putExtra("id_module",model.getId()) ;
                context.startActivity(intent);
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,CoursActivity.class) ;
                intent.putExtra("id_module",model.getId()) ;
                context.startActivity(intent);
            }
        });


        dialog= new Dialog(context) ;
        dialog.setContentView(R.layout.item_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

   holder.bu_noter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final EditText txt_note = dialog.findViewById(R.id.id_textView_dialog) ;
                Button buttoon_note = dialog.findViewById(R.id.button_note);

                txt_note.setText(holder.txt_noter.getText());
                buttoon_note.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendNote(txt_note.getText().toString(),model.getId(),holder.txt_noter,dialog,txt_note);

                    }
                });

               dialog.show();
            }
        });
        getNoote(model.getId(),holder.txt_noter);

    }


    public  void getNoote(String moduleID, final TextView view){
        RequestQueue requestQueue = Volley.newRequestQueue(context) ;
        final Globalv globalv = (Globalv) context.getApplicationContext();
        String url = Config.IP+ "getNote.php?username="+globalv.getUsername()+"&moduleID="+moduleID ;
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("notemoyen") ;
                    JSONObject jsonObject = jsonArray.getJSONObject(0) ;
                    String note = jsonObject.getString("note") ;
                    if(!note.isEmpty()){
                        view.setText(note);
                        view.setVisibility(View.VISIBLE);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("error",error.getMessage());
            }
        }) ;
        req.setShouldCache(false);
        requestQueue.add(req)    ;
    }
    @Override
    public int getItemCount() {
        return models.size();
    }



    public class ModuleViewHolder extends RecyclerView.ViewHolder{
   TextView txt_name,txt_noter,bu_noter,txt_dureheure;
        ImageView imageView ;
        Button bu_start ;

        public ModuleViewHolder(@NonNull View itemView) {
            super(itemView);
             txt_name =itemView.findViewById(R.id.id_textView_name_modele) ;
            txt_noter = itemView.findViewById(R.id.id_textview_note) ;
             bu_noter = itemView.findViewById(R.id.id_bu_noter) ;
             imageView = itemView.findViewById(R.id.id_imageView_imagemodule) ;
             bu_start = itemView.findViewById(R.id.id_button_startnow) ;
             txt_dureheure = itemView.findViewById(R.id.id_text_dureenheure) ;
        }
    }
    public void sendNote(final String note, String moduleID, final TextView txt_noter, final Dialog dialog,EditText txt_note) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String url = Config.IP + "setNote.php?note=" + note + "&username=" + username + "&moduleID=" + moduleID;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean result = response.getBoolean("result");
                    if (result) {
                        txt_noter.setText(note);
                        dialog.dismiss();

                    } else {
                        Toast.makeText(context, "error", Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error", error.getMessage());
            }
        });

        if(note.isEmpty()){
            txt_note.setError("enter note");
            txt_note.requestFocus() ;
        }else if(Integer.parseInt(note) > 21 ){
            txt_note.setError("enter note < 20");
            txt_note.requestFocus() ;
        }else{
            request.setShouldCache(false);
            requestQueue.add(request)  ;
        }



    }

}
