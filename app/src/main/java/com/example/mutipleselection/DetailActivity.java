package com.example.mutipleselection;

import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";
    private TextView textMovieName, textStory,textRating;
    private ImageView imageMovie, imageBackButton;
    RatingBar ratingBar;
    private Button detailAddWatchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String movieName = intent.getStringExtra("movieName");
        String movieStory = intent.getStringExtra("story");
        float rating = intent.getFloatExtra("rating", 0); //default value eklenmeli
        String imageBanner = intent.getStringExtra("banner");


        textMovieName = findViewById(R.id.textMovieNameDetail);
        textStory = findViewById(R.id.textDetail);
        ratingBar = findViewById(R.id.ratingBar);
        textRating = findViewById(R.id.textRating);
        imageMovie = findViewById(R.id.detailImage);
        detailAddWatchList = findViewById(R.id.addWatchList);
        imageBackButton = findViewById(R.id.backButton);


        textMovieName.setText(movieName);
        ratingBar.setRating(rating);
        textRating.setText(String.valueOf(rating));
        textStory.setText(movieStory);
        Picasso.get().load(imageBanner).into(imageMovie);

        imageBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }


}
