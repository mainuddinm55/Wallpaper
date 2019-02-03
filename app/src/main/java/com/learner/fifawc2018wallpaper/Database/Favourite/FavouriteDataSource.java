package com.learner.fifawc2018wallpaper.Database.Favourite;

import com.learner.fifawc2018wallpaper.Database.Recent.Recent;
import com.learner.fifawc2018wallpaper.Database.Recent.RecentDAO;
import com.learner.fifawc2018wallpaper.Database.Recent.RecentDataSource;

import java.util.List;

import io.reactivex.Flowable;

public class FavouriteDataSource implements IFavouriteDataSource{
    private FavouriteDAO favouriteDAO;
    private static FavouriteDataSource INSTANCE;

    public FavouriteDataSource(FavouriteDAO favouriteDAO) {
        this.favouriteDAO = favouriteDAO;
    }

    public static FavouriteDataSource getInstance(FavouriteDAO favouriteDAO){
        if (INSTANCE==null){
            INSTANCE = new FavouriteDataSource(favouriteDAO);
        }
        return INSTANCE;
    }

    @Override
    public Flowable<List<Favourite>> getAllFavourite() {
        return favouriteDAO.getAllFavourite();
    }

    @Override
    public void addFavourite(Favourite... favourites) {
        favouriteDAO.addFavourite(favourites);
    }

    @Override
    public void removeFavourite(Favourite... favourites) {
        favouriteDAO.removeFavourite(favourites);
    }

}
