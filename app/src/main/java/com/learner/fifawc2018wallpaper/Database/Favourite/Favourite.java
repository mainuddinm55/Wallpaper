package com.learner.fifawc2018wallpaper.Database.Favourite;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Favourite {

    @ColumnInfo
    @NonNull
    private String categoryId;

    @ColumnInfo
    @NonNull
    @PrimaryKey
    private String imageUrl;

    @ColumnInfo
    private String key;

    public Favourite(@NonNull String categoryId, @NonNull String imageUrl, String key) {
        this.categoryId = categoryId;
        this.imageUrl = imageUrl;
        this.key = key;
    }

    @NonNull
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(@NonNull String categoryId) {
        this.categoryId = categoryId;
    }

    @NonNull
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@NonNull String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
