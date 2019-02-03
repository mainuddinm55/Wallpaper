package com.learner.fifawc2018wallpaper.Database.Recent;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(tableName = "recent", primaryKeys = {"imageUrl","categoryId"})
public class Recent {
    @ColumnInfo
    @NonNull
    private String imageUrl;

    @ColumnInfo
    @NonNull
    private String categoryId;

    @ColumnInfo
    private String saveTime;

    @ColumnInfo
    private String key;

    public Recent(@NonNull String imageUrl, @NonNull String categoryId, String saveTime, String key) {
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
        this.saveTime = saveTime;
        this.key = key;
    }

    @NonNull
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@NonNull String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NonNull
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(@NonNull String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(String saveTime) {
        this.saveTime = saveTime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
