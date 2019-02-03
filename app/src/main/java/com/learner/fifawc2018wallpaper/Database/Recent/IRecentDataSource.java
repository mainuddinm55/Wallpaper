package com.learner.fifawc2018wallpaper.Database.Recent;

import java.util.List;

import io.reactivex.Flowable;

public interface IRecentDataSource {
    Flowable<List<Recent>> getAllRecent();
    void insertRecent (Recent... recents);
    void updateRecent (Recent... recents);
    void deleteRecent (Recent... recents);
    void deleteAllRecent();
}
