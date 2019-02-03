package com.learner.fifawc2018wallpaper;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.learner.fifawc2018wallpaper.Adapter.FavouriteListAdapter;
import com.learner.fifawc2018wallpaper.Adapter.RecentWallpaperAdapter;
import com.learner.fifawc2018wallpaper.Database.Favourite.Favourite;
import com.learner.fifawc2018wallpaper.Database.Favourite.FavouriteDataSource;
import com.learner.fifawc2018wallpaper.Database.Favourite.FavouriteRepository;
import com.learner.fifawc2018wallpaper.Database.LocalDatabase;
import com.learner.fifawc2018wallpaper.Database.Recent.RecentDataSource;
import com.learner.fifawc2018wallpaper.Database.Recent.RecentRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FavouriteActivity extends AppCompatActivity {

    private RecyclerView favouriteWallpaperRecyclerView;
    CompositeDisposable compositeDisposable;
    FavouriteRepository favouriteRepository;
    List<Favourite> favouriteList = new ArrayList<>();
    FavouriteListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        compositeDisposable  = new CompositeDisposable();
        LocalDatabase database = LocalDatabase.getInstance(getApplicationContext());
        favouriteRepository = FavouriteRepository.getInstance(FavouriteDataSource.getInstance(database.favouriteDAO()));

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Your Favourites");
        setSupportActionBar(toolbar);
        if (toolbar!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        favouriteWallpaperRecyclerView =  findViewById(R.id.favourite_wallpaper_rv);
        favouriteWallpaperRecyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        favouriteWallpaperRecyclerView.setLayoutManager(gridLayoutManager);
        loadFavourite();
        adapter = new FavouriteListAdapter(getApplicationContext(), favouriteList);
        favouriteWallpaperRecyclerView.setAdapter(adapter);
    }

    private void loadFavourite() {
        Disposable disposable = favouriteRepository.getAllFavourite()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Favourite>>() {
                    @Override
                    public void accept(List<Favourite> favourites) throws Exception {
                        loadAllFavourite(favourites);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        compositeDisposable.add(disposable);

    }

    private void loadAllFavourite(List<Favourite> favourites) {
        favouriteList.clear();
        favouriteList.addAll(favourites);
        Log.d("FavouriteList",favouriteList.size()+"");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
