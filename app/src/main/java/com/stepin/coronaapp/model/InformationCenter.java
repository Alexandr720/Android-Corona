package com.stepin.coronaapp.model;

public class InformationCenter {

    private String title;
    private String description;
    private int image;

    public InformationCenter() {
    }

    public InformationCenter(String title, String description, int image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public InformationCenter(String title, int image) {
        this.title = title;
        this.description = "Lorem ipsum dolor sit amet, conseture";
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImage() {
        return image;
    }
}
