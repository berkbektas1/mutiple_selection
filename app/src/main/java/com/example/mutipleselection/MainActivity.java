package com.example.mutipleselection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TVShowsListener {

    private Button buttonAddToWatchlist;

    private RequestQueue mRequestQueue;
    List<TVShow> tvShows;

    //Recyclerview
    RecyclerView tvShowsRecyclerView;
    TVShowsAdapter tvShowsAdapter;
    LinearLayoutManager mLinearLayoutManager;
    private ProgressBar loadingPB;

    //Pagination
    private int totalItemCount;
    private int firstVisibleItem;
    private int visibleItemCount;
    private int page = 1;
    private int previousTotal;
    private boolean load = true;
    boolean isLoading = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvShowsRecyclerView = findViewById(R.id.tvShowsRecyclerView);
        buttonAddToWatchlist = findViewById(R.id.buttonAddToWatchlist);
        mLinearLayoutManager=new LinearLayoutManager(this);
        tvShowsRecyclerView.setLayoutManager(mLinearLayoutManager);
        loadingPB = findViewById(R.id.idPBLoading);

        // local data
        tvShows = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);

        //parse and pagination
        parseJSON();
        pagination();


        //add selected item movies
        buttonAddToWatchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                //Following is the list of selected items in recycler view.
                List<TVShow> selectedTvShows = tvShowsAdapter.getSelectedTvShow();

                StringBuilder tvShowNames = new StringBuilder();
                for (int i = 0; i< selectedTvShows.size(); i++){
                    if (i == 0) {
                        tvShowNames.append(selectedTvShows.get(i).name);
                    }else {
                        tvShowNames.append("\n").append(selectedTvShows.get(i).name);
                    }
                }

                Toast.makeText(MainActivity.this, tvShowNames.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }



    @Override
    public void onTVShowAction(Boolean isSelected) {
        if (isSelected){
            buttonAddToWatchlist.setVisibility(View.VISIBLE);
        }else{
            buttonAddToWatchlist.setVisibility(View.GONE);
        }
    }

    private void parseJSON(){
        String JSON_URL = "https://api.themoviedb.org/3/movie/popular?api_key=881a3e97e259d362b9f73333adff2199&page="+page;
        //Search:
        //https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=Jack+Reacher
        //https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=Jack+Reacher
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, JSON_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //response
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject ability = jsonArray.getJSONObject(i);
                                //https://image.tmdb.org/t/p/w500 /niw2AKHz6XmwiRMLWaoyAOAti0G.jpg

                                String original_title = ability.getString("original_title");
                                String imagePoster = "https://image.tmdb.org/t/p/w500" + ability.getString("poster_path");
                                String imageBanner = "https://image.tmdb.org/t/p/w500" + ability.getString("backdrop_path");
                                String story = ability.getString("overview");
                                String release_date = ability.getString("release_date");
                                float rating = ability.getLong("vote_average");


                                //System.out.println(original_title);
                               //System.out.println(imagePoster);



                                tvShows.add(new TVShow(original_title,release_date,story,false,rating,imagePoster,imageBanner));
                                tvShowsAdapter = new TVShowsAdapter(tvShows, MainActivity.this);
                                tvShowsRecyclerView.setAdapter(tvShowsAdapter);
                                System.out.println(tvShows.size());

                            }

                            }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

    private void pagination() {
        tvShowsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                totalItemCount = linearLayoutManager.getItemCount();
                visibleItemCount = recyclerView.getChildCount();
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

                if (load){
                    if (totalItemCount > previousTotal){
                        previousTotal = totalItemCount;
                        page++;
                        load=false;
                    }
                }
                if (!load && (firstVisibleItem + visibleItemCount) >= totalItemCount){
                    getNext(page);
                    load = true;

                    System.out.println("Page:"+page+"firstVisibleItem:"+firstVisibleItem+"visibleItemCount"+visibleItemCount+"totalItemCount:"+totalItemCount);
                }


            }
        });

    }

    private void getNext(int page) {
        String JSON_URL = "https://api.themoviedb.org/3/movie/popular?api_key=881a3e97e259d362b9f73333adff2199&page="+page;
        System.out.println(JSON_URL);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, JSON_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //response
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject ability = jsonArray.getJSONObject(i);
                                //https://image.tmdb.org/t/p/w500 /niw2AKHz6XmwiRMLWaoyAOAti0G.jpg

                                String original_title = ability.getString("original_title");
                                String imagePoster = "https://image.tmdb.org/t/p/w500" + ability.getString("poster_path");
                                String imageBanner = "https://image.tmdb.org/t/p/w500" + ability.getString("backdrop_path");
                                String story = ability.getString("overview");
                                String release_date = ability.getString("release_date");
                                float rating = ability.getLong("vote_average");

                                //System.out.println(original_title);
                                //System.out.println(imagePoster);

                                tvShows.add(new TVShow(original_title,release_date,story,false,rating,imagePoster,imageBanner));
                                tvShowsAdapter = new TVShowsAdapter(tvShows, MainActivity.this);
                                tvShowsRecyclerView.setAdapter(tvShowsAdapter);


                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }


}
