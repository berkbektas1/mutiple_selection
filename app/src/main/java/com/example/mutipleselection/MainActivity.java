package com.example.mutipleselection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    List<TVShow> tvShows;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView tvShowsRecyclerView = findViewById(R.id.tvShowsRecyclerView);
        buttonAddToWatchlist = findViewById(R.id.buttonAddToWatchlist);

        // local data
        tvShows = new ArrayList<>();

        TVShow arog = new TVShow();
        arog.image = R.drawable.arog;
        arog.name = "Arog";
        arog.rating = 5f;
        arog.createdBy = "Cem Yılmaz";
        arog.story = "Halı satıcısı Arif, kahramanlıklarıyla G.O.R.A. gezegeninde yalnızca dost edinmekle kalmaz, bol bol düşman da edinir. ";
        tvShows.add(arog);

        TVShow joker = new TVShow();
        joker.image = R.drawable.joker;
        joker.name = "Joker";
        joker.rating = 4f;
        joker.createdBy = "Todd Phillips";
        joker.story = "Joker, başarısız bir komedyen olan Arthur Fleck'in hayatına odaklanıyor. Toplum tarafından dışlanan bir adam olan Arthur, hayatta yapayalnızdır.";
        tvShows.add(joker);

        TVShow thedescent = new TVShow();
        thedescent.image = R.drawable.thedescent;
        thedescent.name = "The Descent";
        thedescent.rating = 4f;
        thedescent.createdBy = "Neil Marshall";
        thedescent.story = "Kocası, kızı ve arkadaşları ile birlikte tatile çıkan genç ve hoş bir kadın, trajik bir kazaya şahit olurlar.";
        tvShows.add(thedescent);

        //





        final TVShowsAdapter tvShowsAdapter = new TVShowsAdapter(tvShows, this);
        tvShowsRecyclerView.setAdapter(tvShowsAdapter);
        
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



}
