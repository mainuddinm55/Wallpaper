package com.learner.fifawc2018wallpaper.Database.Recent;

import java.util.List;

import io.reactivex.Flowable;

public class RecentRepository implements IRecentDataSource {

    private IRecentDataSource mLocalDataSource;
    private static RecentRepository INSTANCE;

    public  RecentRepository (IRecentDataSource mLocalDataSource){
        this.mLocalDataSource = mLocalDataSource;
    }

    public static RecentRepository getInstance(IRecentDataSource mLocalDataSource){
        if (INSTANCE==null){
            INSTANCE = new RecentRepository(mLocalDataSource);
        }
        return INSTANCE;
    }

    @Override
    public Flowable<List<Recent>> getAllRecent() {
        return mLocalDataSource.getAllRecent();
    }

    @Override
    public void insertRecent(Recent... recents) {
        mLocalDataSource.insertRecent(recents);
    }

    @Override
    public void updateRecent(Recent... recents) {
        mLocalDataSource.updateRecent(recents);
    }

    @Override
    public void deleteRecent(Recent... recents) {
        mLocalDataSource.deleteRecent(recents);
    }

    @Override
    public void deleteAllRecent() {
        mLocalDataSource.deleteAllRecent();
    }
}
