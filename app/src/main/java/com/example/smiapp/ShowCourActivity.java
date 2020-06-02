package com.example.smiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.smiapp.Mail.Config;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowCourActivity extends YouTubeBaseActivity {
    RequestQueue requestQueue ;
    Globalv globalv ;

    YouTubePlayerView youTubePlayerView ;
    YouTubePlayer.OnInitializedListener mOnInitializedListener ;
      //VideoView videoView ;
     // ProgressBar progressBar ;
  //  WebView webView ;

      TextView textView_title ;
      String link ;
      String title ;
      String id_cour ;
    //step 2  like
    TextView txt_show_number_like ;
    Button bu_add_like ;
    //step 2  comment
    TextView txt_show_number_comment ;
    LinearLayout layout_addComment ;
    LinearLayout layout_showComment ;
    ArrayList<Comment> comments ;
    AdapterRecycleVwComment adapterRecycleVwComment ;
    RecyclerView recyclerView ;
    EditText txt_comment ;
    TextView txt_username_addcomment ;
    // Rating bar
    RatingBar ratingBar ;
    LinearLayout layout_rating ;
    TextView txt_rating_shownumber;
    Button bu_avis ;
    ArrayList<Rating> ratings ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cour);
        requestQueue = Volley.newRequestQueue(this);
        globalv = (Globalv) getApplicationContext() ;
        link = getIntent().getStringExtra("link");
        title = getIntent().getStringExtra("title") ;
        id_cour = getIntent().getStringExtra("id_cour") ;
        textView_title = findViewById(R.id.id_title_showcour) ;
        textView_title.setText(title);
        youTubePlayerView = findViewById(R.id.youtubeplayer) ;
        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(link);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        } ;
        youTubePlayerView.initialize(YoutubeVideoConfig.getApiKey(),mOnInitializedListener);
       /* webView = findViewById(R.id.webview) ;
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
        } );
        String url = " <iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/"+link+"\" frameborder=\"0\" allowfullscreen></iframe> " ;
        webView.loadData(url, "text/html" , "utf-8");
       // videoView = findViewById(R.id.id_videoview);
       // progressBar = findViewById(R.id.id_progressBar);
        //String u = "android.resource://com.example.smiapp/"+R.raw.quran  ;
        Uri uri = Uri.parse(link) ;
        progressBar = findViewById(R.id.id_progressBar) ;
        assert  progressBar!= null ;
        progressBar.bringToFront();
        MediaController mediaController = new MediaController(this) ;
        videoView.setVideoURI(uri);
        videoView.setMediaController(new MediaController(this));
        mediaController.setAnchorView(videoView);
        videoView.requestFocus() ;
         videoView.start();
      videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
          @Override
          public void onPrepared(MediaPlayer mediaPlayer) {
              progressBar.setVisibility(View.INVISIBLE);
          }
      });*/

     //step 2  like
       txt_show_number_like=findViewById(R.id.id_show_number_like);
       bu_add_like = findViewById(R.id.id_button_add_like);
       getNumberLike();
        //step 2  comment
       txt_show_number_comment = findViewById(R.id.id_show_number_comment) ;
       layout_addComment = findViewById(R.id.id_ly_addComment) ;
       layout_showComment = findViewById(R.id.id_ly_show_comment) ;
       txt_comment= findViewById(R.id.id_editText_comment) ;
       txt_username_addcomment = findViewById(R.id.id_textview_username_addcomment) ;
       comments = new ArrayList<>() ;
       recyclerView = findViewById(R.id.id_recycle_comment) ;
       recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));
       getNumberComment();
       show_Comment();
       // Rating bar
         ratingBar  = findViewById(R.id.ratingBar);
         layout_rating = findViewById(R.id.id_ly_rating) ;
         txt_rating_shownumber = findViewById(R.id.id_textview_rating_shownumber) ;
         bu_avis = findViewById(R.id.id_button_avis) ;
        ratings = new ArrayList<>();
         getNumberAvis();
         final TextView txt_rating = findViewById(R.id.textview_rating) ;
         ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
             @Override
             public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                 if(v==1){
                    txt_rating.setText("Décevant, je ne suis pas satisfait du tout");
                 }else if(v==2){
                     txt_rating.setText("Médiocre, plutôt déçu");
                 }else if(v==3){
                     txt_rating.setText("Moyen, peut mieux faire");
                 }else if(v==4){
                     txt_rating.setText("De bonne qualité, je suis satisfait");
                 }else if(v==5){
                     txt_rating.setText("Excellent, je suis très satisfait !");
                 }
             }
         });
         bu_avis.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 getNumberAvis();
                 layout_rating.setVisibility(View.VISIBLE);
                 layout_showComment.setVisibility(View.VISIBLE);
                 layout_addComment.setVisibility(View.GONE);
             }
         });
        new TaskAdapter(this).execute(5);




    }
    public void getNumberLike(){
        String url = Config.IP+ "show_mlike.php?movie_id="+id_cour ;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                StringBuilder text = new StringBuilder() ;
                try {
                    JSONArray jsonArray  = response.getJSONArray("mlikes") ;
                    txt_show_number_like.setText(""+jsonArray.length());
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i) ;
                        String name = jsonObject.getString("name") ;
                        text.append(name) ;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(text.toString().contains(globalv.getUsername())){
                    bu_add_like.setEnabled(false);
                    bu_add_like.setBackgroundResource(R.drawable.ic_like_a);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error", error.getMessage());
            }
        });

        request.setShouldCache(false);
requestQueue.add(request) ;

    }

    public void addLike(View view){
        String url = Config.IP+"add_like.php?movie_id="+id_cour+"&name_user="+globalv.getUsername();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean result = response.getBoolean("result");
                    if(result){
                        getNumberLike();
                        bu_add_like.setEnabled(false);
                        bu_add_like.setBackgroundResource(R.drawable.ic_like_a);

                    }else{

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
        request.setShouldCache(false);
        requestQueue.add(request) ;


    }
    public void getNumberComment(){
        String url = Config.IP+"show_coment.php?movie_id="+id_cour ;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray  = response.getJSONArray("mcomments") ;
                    txt_show_number_comment.setText(""+jsonArray.length());

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
        request.setShouldCache(false);
        requestQueue.add(request) ;
    }


    public void addComent(View view) {
        layout_addComment.setVisibility(View.VISIBLE);
        layout_showComment.setVisibility(View.VISIBLE);
        layout_rating.setVisibility(View.GONE);
        txt_username_addcomment.setText(globalv.getUsername());

    }
    public void sendComment(View view){
    String text = txt_comment.getText().toString().trim() ;
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response) ;
                    boolean result = jsonObject.getBoolean("result") ;
                    if(result){
                        getNumberComment();
                        show_Comment();
                        txt_comment.setText("");
                        layout_addComment.setVisibility(View.GONE);
                        layout_showComment.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
if(text.isEmpty()){
    txt_comment.requestFocus() ;
    txt_comment.setError("comment vide");
}else{

    Send_comment send_comment = new Send_comment(text,globalv.getUsername(),id_cour,listener) ;
    requestQueue.getCache().clear();
    requestQueue.add(send_comment) ;

}



    }

    public void show_Comment(){



        String url = Config.IP+"show_coment.php?movie_id="+id_cour ;
            comments.clear();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray  = response.getJSONArray("mcomments") ;

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i) ;
                        String username = jsonObject.getString("name") ;
                        String comment = jsonObject.getString("text") ;
                        String time_comment = jsonObject.getString("date_time") ;
                  comments.add(new Comment(username,comment,time_comment)) ;
                    }
                     adapterRecycleVwComment = new AdapterRecycleVwComment(ShowCourActivity.this,comments) ;
                    recyclerView.setAdapter(adapterRecycleVwComment);
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
        request.setShouldCache(false);
        requestQueue.add(request) ;
    }


    public void setRating(View view){

    String url = Config.IP+"setRating.php?movie_id="+id_cour+"&username="+globalv.getUsername()+"&rating="+(int)ratingBar.getRating();
    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            try {
                boolean result = response.getBoolean("result");
                if(result){
                  layout_rating.setVisibility(View.GONE);
                  layout_showComment.setVisibility(View.VISIBLE);
                  getNumberAvis();
                  bu_avis.setBackgroundResource(R.drawable.ic_avis_a);
                  Toast.makeText(ShowCourActivity.this,"..............",Toast.LENGTH_LONG).show();

                }else{

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
    request.setShouldCache(false);
    requestQueue.add(request) ;


}
    public void getNumberAvis(){

        String url = Config.IP+"getNumberRating.php?movie_id="+id_cour ;
        ratings.clear();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray  = response.getJSONArray("result") ;
                    txt_rating_shownumber.setText(""+jsonArray.length());
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i) ;
                        String name = jsonObject.getString("username") ;
                        float rating = (float) jsonObject.getInt("rating") ;
                        ratings.add(new Rating(name,rating))  ;
                    }

                    for(Rating r : ratings){
                        if(r.getUsername().equalsIgnoreCase(globalv.getUsername())){
                            ratingBar.setRating(r.getRating());
                            bu_avis.setBackgroundResource(R.drawable.ic_avis_a);

                            break;

                        }
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
        request.setShouldCache(false);
        requestQueue.add(request) ;



    }

    public void closepage(View view) {
        layout_rating.setVisibility(View.GONE);
        layout_showComment.setVisibility(View.VISIBLE);
        layout_addComment.setVisibility(View.GONE);
    }

    public void closeRating(View view) {
        layout_rating.setVisibility(View.GONE);
        layout_showComment.setVisibility(View.VISIBLE);
        layout_addComment.setVisibility(View.GONE);
    }
}
