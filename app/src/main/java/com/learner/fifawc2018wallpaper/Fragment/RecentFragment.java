package com.learner.fifawc2018wallpaper.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learner.fifawc2018wallpaper.Adapter.RecentWallpaperAdapter;
import com.learner.fifawc2018wallpaper.Database.LocalDatabase;
import com.learner.fifawc2018wallpaper.Database.Recent.Recent;
import com.learner.fifawc2018wallpaper.Database.Recent.RecentDataSource;
import com.learner.fifawc2018wallpaper.Database.Recent.RecentRepository;
import com.learner.fifawc2018wallpaper.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class RecentFragment extends Fragment {

    private RecyclerView recentWallpaperRecyclerView;
    private RecentWallpaperAdapter  adapter;
    private List<Recent> recentList = new ArrayList<>();

    Context context;
    CompositeDisposable compositeDisposable;
    RecentRepository recentRepository;

    private static RecentFragment INSTANCE = null;

    public static RecentFragment getInstance(Context context){
        if (INSTANCE ==null){
            return new RecentFragment(context);
        }else {
            return INSTANCE;
        }
    }


    @SuppressLint("ValidFragment")
    public RecentFragment(Context context) {
        // Required empty public constructor
        compositeDisposable  = new CompositeDisposable();
        LocalDatabase database = LocalDatabase.getInstance(context);
        recentRepository = RecentRepository.getInstance(RecentDataSource.getInstance(database.recentDAO()));

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_recent, container, false);
        recentWallpaperRecyclerView =  view.findViewById(R.id.recent_wallpaper_rv);
        recentWallpaperRecyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(container.getContext(), 2);
        recentWallpaperRecyclerView.setLayoutManager(gridLayoutManager);
        adapter = new RecentWallpaperAdapter(getContext(), recentList);
        recentWallpaperRecyclerView.setAdapter(adapter);
        loadRecent();
        return view;
    }

    private void loadRecent() {
        Disposable disposable = recentRepository.getAllRecent()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Recent>>() {
                    @Override
                    public void accept(List<Recent> recents) throws Exception {
                        onGetAllRecent(recents);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        compositeDisposable.add(disposable);

    }

    private void onGetAllRecent(List<Recent> recents) {
        recentList.clear();
        recentList.addAll(recents);
        adapter.notifyDataSetChanged();
        Log.d("size of recent ",recentList.size()+"");
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
