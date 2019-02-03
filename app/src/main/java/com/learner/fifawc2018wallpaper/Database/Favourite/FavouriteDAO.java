package com.learner.fifawc2018wallpaper.Database.Favourite;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.learner.fifawc2018wallpaper.Database.Recent.Recent;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface FavouriteDAO {

    @Query("SELECT * FROM favourite")
    Flowable<List<Favourite>> getAllFavourite();

    @Insert
    void addFavourite(Favourite...favourites);

    @Delete
    void removeFavourite(Favourite...favourites);

}
