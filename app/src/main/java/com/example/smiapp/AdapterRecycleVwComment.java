package com.example.smiapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterRecycleVwComment extends RecyclerView.Adapter<AdapterRecycleVwComment.ItemViewHolder>{
    Context context ;
    ArrayList<Comment> comments ;

    public AdapterRecycleVwComment(Context context, ArrayList<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_comment,parent,false) ;
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Comment comment = comments.get(position) ;
              holder.txt_name.setText(comment.getUser_name());
              holder.txt_time.setText(comment.getTime_comment());
              holder.txt_comment.setText(comment.getComment());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name,txt_time,txt_comment ;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.id_textView_username) ;
            txt_time = itemView.findViewById(R.id.id_textView_time_comment) ;
            txt_comment = itemView.findViewById(R.id.id_textView_comment) ;

        }
    }
}
