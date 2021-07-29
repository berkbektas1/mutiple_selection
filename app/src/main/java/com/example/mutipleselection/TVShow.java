package com.example.mutipleselection;

public class TVShow {
    String name, createdBy, story;

    Boolean isSelected;
    float rating;
    String imagePoster, imageBanner;

    public TVShow(String name, String createdBy, String story, Boolean isSelected, float rating, String imagePoster, String imageBanner) {
        this.name = name;
        this.createdBy = createdBy;
        this.story = story;
        this.isSelected = isSelected;
        this.rating = rating;
        this.imagePoster = imagePoster;
        this.imageBanner = imageBanner;
    }
}
