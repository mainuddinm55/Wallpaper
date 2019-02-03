package com.learner.fifawc2018wallpaper.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learner.fifawc2018wallpaper.Adapter.TrendingWallpaperAdapter;
import com.learner.fifawc2018wallpaper.Model.Common;
import com.learner.fifawc2018wallpaper.Model.Wallpaper;
import com.learner.fifawc2018wallpaper.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class TrendingFragment extends Fragment {

    private RecyclerView trendingRecyclerView;
    private Context context;
    private List<Wallpaper> wallpaperList = new ArrayList<>();
    private TrendingWallpaperAdapter adapter;

    private static TrendingFragment INSTANCE = null;

    public static TrendingFragment getInstance(Context context){
        if (INSTANCE ==null){
            return new TrendingFragment(context);
        }else {
            return INSTANCE;
        }
    }

    @SuppressLint("ValidFragment")
    public TrendingFragment(final Context context) {
        this.context = context;
        // Required empty public constructor
        final DatabaseReference wallpaperRef = FirebaseDatabase.getInstance().getReference().child(Common.STR_WALLPAPERS_REF);
        wallpaperRef.keepSynced(true);
        wallpaperRef.orderByChild("viewCount").limitToLast(10)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        wallpaperList.clear();
                        Common.CHILD_REF_KEY.clear();
                        for (DataSnapshot postData : dataSnapshot.getChildren()){
                            Wallpaper wallpaper = postData.getValue(Wallpaper.class);
                            String key = postData.getKey();
                            Common.CHILD_REF_KEY.add(key);
                            Log.d("TrendingClickKey", key);
                            wallpaperList.add(wallpaper);
                        }
                        if (wallpaperList.size()>0){
                            Collections.reverse(wallpaperList);
                            adapter = new TrendingWallpaperAdapter(context, wallpaperList);
                            trendingRecyclerView.setAdapter(adapter);
                        }
                        Log.d("WallpaperListSize",wallpaperList.size()+"");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("Error", databaseError.getMessage());
                    }
                });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_treanding, container, false);
        trendingRecyclerView = view.findViewById(R.id.trending_wallpaper_rv);
        trendingRecyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        //gridLayoutManager.setStackFromEnd(true);
        //gridLayoutManager.setReverseLayout(true);
        trendingRecyclerView.setLayoutManager(gridLayoutManager);
        return view;
    }

}
