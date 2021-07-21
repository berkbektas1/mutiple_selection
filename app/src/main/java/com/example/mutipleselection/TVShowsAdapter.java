package com.example.mutipleselection;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class TVShowsAdapter extends RecyclerView.Adapter<TVShowsAdapter.TvShowViewHolder>{

    private List<TVShow> tvShows;
    private TVShowsListener tvShowsListener;

    public TVShowsAdapter(List<TVShow> tvShows, TVShowsListener tvShowsListener) {
        this.tvShows = tvShows;
        this.tvShowsListener = tvShowsListener;
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TvShowViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder holder, int position) {
        holder.bindTVShow(tvShows.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShows.size();

    }

    public List<TVShow> getSelectedTvShow() {
        List<TVShow> selectedTvShows = new ArrayList<>();
        for (TVShow tvShow : tvShows){
            if (tvShow.isSelected){
                selectedTvShows.add(tvShow);
            }
        }
        return selectedTvShows;
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout layoutTVShow;
        View viewBackground;
        RoundedImageView imageTVShow;
        TextView textName, textCreatedBy, textStory;
        RatingBar ratingBar;
        ImageView imageSelected;

        public TvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutTVShow = itemView.findViewById(R.id.layoutContainer);
            viewBackground = itemView.findViewById(R.id.viewBackground);
            imageTVShow = itemView.findViewById(R.id.imageTVShow);
            textName = itemView.findViewById(R.id.textName);
            textCreatedBy = itemView.findViewById(R.id.textCreateBy);
            textStory = itemView.findViewById(R.id.textStory);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            imageSelected = itemView.findViewById(R.id.imageSelected);

        }
        void bindTVShow(final TVShow tvShow){
            imageTVShow.setImageResource(tvShow.image);
            textName.setText(tvShow.name);
            textCreatedBy.setText(tvShow.createdBy);
            textStory.setText(tvShow.story);
            ratingBar.setRating(tvShow.rating);
            if (tvShow.isSelected){
                viewBackground.setBackgroundResource(R.drawable.container_selected_background);
                imageSelected.setVisibility(View.VISIBLE);
            }else {
                viewBackground.setBackgroundResource(R.drawable.container_background);
                imageSelected.setVisibility(View.GONE);
            }

            layoutTVShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tvShow.isSelected) {
                        viewBackground.setBackgroundResource(R.drawable.container_background);
                        imageSelected.setVisibility(View.GONE);
                        tvShow.isSelected = false;
                        if (getSelectedTvShow().size() == 0) {
                            tvShowsListener.onTVShowAction(false);
                        }
                    }else {
                        viewBackground.setBackgroundResource(R.drawable.container_selected_background);
                        imageSelected.setVisibility(View.VISIBLE);
                        tvShow.isSelected = true;
                        tvShowsListener.onTVShowAction(true);
                    }
                }
            });

        }
    }
}
