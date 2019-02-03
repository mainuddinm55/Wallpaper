package com.learner.fifawc2018wallpaper.Database.Favourite;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.learner.fifawc2018wallpaper.Database.Recent.Recent;

import java.util.List;

import io.reactivex.Flowable;

public interface IFavouriteDataSource {

    Flowable<List<Favourite>> getAllFavourite();

    void addFavourite(Favourite...favourites);

    void removeFavourite(Favourite...favourites);
}
