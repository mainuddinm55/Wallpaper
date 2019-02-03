package com.learner.fifawc2018wallpaper.Database.Recent;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface RecentDAO {

    @Query("SELECT * FROM recent ORDER BY saveTime DESC LIMIT 10")
    Flowable<List<Recent>> getAllRecent();

    @Insert
    void insertRecent (Recent... recents);

    @Update
    void updateRecent (Recent... recents);

    @Delete
    void deleteRecent (Recent... recents);

    @Query("DELETE FROM recent")
    void deleteAllRecent();
}
