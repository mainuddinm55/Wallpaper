package com.learner.fifawc2018wallpaper.Database.Recent;

import java.util.List;

import io.reactivex.Flowable;

public class RecentDataSource implements IRecentDataSource {

    private RecentDAO recentDAO;
    private static RecentDataSource INSTANCE;

    public RecentDataSource(RecentDAO recentDAO) {
        this.recentDAO = recentDAO;
    }

    public static RecentDataSource getInstance(RecentDAO recentDAO){
        if (INSTANCE==null){
            INSTANCE = new RecentDataSource(recentDAO);
        }
        return INSTANCE;
    }

    @Override
    public Flowable<List<Recent>> getAllRecent() {
        return recentDAO.getAllRecent();
    }

    @Override
    public void insertRecent(Recent... recents) {
        recentDAO.insertRecent(recents);
    }

    @Override
    public void updateRecent(Recent... recents) {
        recentDAO.updateRecent(recents);
    }

    @Override
    public void deleteRecent(Recent... recents) {
        recentDAO.deleteRecent(recents);
    }

    @Override
    public void deleteAllRecent() {
        recentDAO.deleteAllRecent();
    }
}
