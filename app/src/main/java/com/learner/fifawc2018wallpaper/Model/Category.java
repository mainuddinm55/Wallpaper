package com.learner.fifawc2018wallpaper.Model;

public class Category {
    private String countryName;
    private String imageUrl;
    private String categoryId;

    public Category() {
    }

    public Category(String countryName, String imageUrl,String categoryId) {
        this.countryName = countryName;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
