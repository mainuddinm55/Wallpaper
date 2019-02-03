package com.learner.fifawc2018wallpaper.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.learner.fifawc2018wallpaper.Database.Favourite.Favourite;
import com.learner.fifawc2018wallpaper.Database.Favourite.FavouriteDAO;
import com.learner.fifawc2018wallpaper.Database.Recent.Recent;
import com.learner.fifawc2018wallpaper.Database.Recent.RecentDAO;

import static com.learner.fifawc2018wallpaper.Database.LocalDatabase.DATABASE_VERSION;

@Database(entities = {Recent.class,Favourite.class}, version =  DATABASE_VERSION)
public abstract class LocalDatabase extends RoomDatabase {

    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "FIFAWC2018Wallpaper";

    public abstract RecentDAO recentDAO();
    public abstract FavouriteDAO favouriteDAO();
    private static LocalDatabase INSTANCE;

    public static LocalDatabase getInstance (Context context){
        if (INSTANCE==null){
            INSTANCE = Room.databaseBuilder(context, LocalDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }



}
