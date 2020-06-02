package com.example.smiapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class TaskAdapter extends AsyncTask<Integer,String,String> {
ProgressDialog progressDialog ;
Context context ;
Thread thread ;

    public TaskAdapter(Context context) {
        this.context = context;
    }



    @Override
    protected String doInBackground(Integer... integers) {
        int time = integers[0]*1000 ;
        try {
            thread.sleep(time);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context,"","Please wait...",false,false);
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //Dismissing the progress dialog
        progressDialog.dismiss();
        //Showing a success message

    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Toast.makeText(context,values[0],Toast.LENGTH_LONG).show();

    }
}
