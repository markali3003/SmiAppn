package com.example.smiapp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AdapterRecycleVwCour extends RecyclerView.Adapter<AdapterRecycleVwCour.ItemViewHolder> implements Filterable {

    List<Cour> cours;
    List<Cour> exampleListFull;
    Context context;
    String username;

    public AdapterRecycleVwCour(List<Cour> cours, Context context,String username) {
        this.cours = cours;
        exampleListFull = new ArrayList<>(cours) ;
        this.context = context;
        this.username =username ;


    }

    @NonNull
    @Override
    public AdapterRecycleVwCour.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cour, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position) {
        final Cour cour = cours.get(position);
        holder.txt_title.setText(cour.getTitle());
        holder.txt_info.setText(cour.getInfo());
        holder.txt_semestre.setText(cour.getType());
        Picasso.get().load(Config.IP + "image/" + cour.getPhotolink()).into(holder.imageView);
        holder.txt_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowCourActivity.class);
                intent.putExtra("id_cour", cour.getId());
                intent.putExtra("title", cour.getTitle());
                intent.putExtra("info", cour.getInfo());
                intent.putExtra("type", cour.getType());
                intent.putExtra("link", cour.getLink());
                intent.putExtra("moodule_id", cour.getModule_id());
                context.startActivity(intent);
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowCourActivity.class);
                intent.putExtra("id_cour", cour.getId());
                intent.putExtra("title", cour.getTitle());
                intent.putExtra("info", cour.getInfo());
                intent.putExtra("type", cour.getType());
                intent.putExtra("link", cour.getLink());
                intent.putExtra("moodule_id", cour.getModule_id());
                context.startActivity(intent);
            }
        });
        showIconFavorable(cour.getId(), holder);
        holder.bu_fav1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TaskAdapter(context).execute(2);
                addToFavorable(cour.getId(), cour.getModule_id(), holder);
            }
        });

    }

    private void showIconFavorable(String cour_id, final ItemViewHolder holder) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = Config.IP + "getFavorable.php?username=" + username + "&cour_id=" + cour_id;
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean result = response.getBoolean("result");
                    if (!result) {
                        holder.bu_fav1.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
                    } else {
                        holder.bu_fav1.setBackgroundResource(R.drawable.ic_favorite_vert_24dp);

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
        req.setShouldCache(false) ;
        requestQueue.add(req);
    }

   public void addToFavorable(String cour_id, String module_id, final ItemViewHolder holder) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
       String url = Config.IP + "setFavorable.php?username=" + username + "&cour_id=" + cour_id + "&module_id=" + module_id;
        final JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String result = response.getString("result");
                    if (result.equalsIgnoreCase("insert")) {
                        Toast.makeText(context, "insert !", Toast.LENGTH_LONG).show();
                        holder.bu_fav1.setBackgroundResource(R.drawable.ic_favorite_vert_24dp);
                    } else if (result.equalsIgnoreCase("updateto1")) {
                        Toast.makeText(context, "update !", Toast.LENGTH_LONG).show();
                        holder.bu_fav1.setBackgroundResource(R.drawable.ic_favorite_vert_24dp);
                    } else if (result.equalsIgnoreCase("updateto0")) {
                        Toast.makeText(context, "update !", Toast.LENGTH_LONG).show();

                        holder.bu_fav1.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
                    } else {
                        Toast.makeText(context, "error !", Toast.LENGTH_LONG).show();
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
        req.setShouldCache(false) ;
        requestQueue.add(req);
    }



    public  class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView txt_title,txt_info ,txt_semestre,bu_fav1;
        ImageView imageView ;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.id_textview_title) ;
            txt_info = itemView.findViewById(R.id.id_textView_info) ;
            txt_semestre = itemView.findViewById(R.id.id_textView_semestre) ;
            bu_fav1 = itemView.findViewById(R.id.id_bu_favorable1);
            imageView = itemView.findViewById(R.id.id_imageView) ;
        }
    }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Cour> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Cour item : exampleListFull) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            cours.clear();
            cours.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };





    @Override
    public int getItemCount() {
        return cours.size();
    }

}



