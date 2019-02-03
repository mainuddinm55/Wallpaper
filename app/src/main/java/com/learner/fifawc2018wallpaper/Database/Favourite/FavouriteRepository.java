package com.learner.fifawc2018wallpaper.Database.Favourite;

import com.learner.fifawc2018wallpaper.Database.Recent.IRecentDataSource;
import com.learner.fifawc2018wallpaper.Database.Recent.Recent;
import com.learner.fifawc2018wallpaper.Database.Recent.RecentRepository;

import java.util.List;

import io.reactivex.Flowable;

public class FavouriteRepository implements IFavouriteDataSource {

    private IFavouriteDataSource mLocalDataSource;
    private static FavouriteRepository INSTANCE;

    public  FavouriteRepository (IFavouriteDataSource mLocalDataSource){
        this.mLocalDataSource = mLocalDataSource;
    }

    public static FavouriteRepository getInstance(IFavouriteDataSource mLocalDataSource){
        if (INSTANCE==null){
            INSTANCE = new FavouriteRepository(mLocalDataSource);
        }
        return INSTANCE;
    }


    @Override
    public Flowable<List<Favourite>> getAllFavourite() {
        return mLocalDataSource.getAllFavourite();
    }

    @Override
    public void addFavourite(Favourite... favourites) {
        mLocalDataSource.addFavourite(favourites);
    }

    @Override
    public void removeFavourite(Favourite... favourites) {
        mLocalDataSource.removeFavourite(favourites);
    }

}
