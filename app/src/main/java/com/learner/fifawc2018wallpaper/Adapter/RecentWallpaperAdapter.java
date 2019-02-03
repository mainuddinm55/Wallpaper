package com.learner.fifawc2018wallpaper.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.learner.fifawc2018wallpaper.Database.Recent.Recent;
import com.learner.fifawc2018wallpaper.Model.Common;
import com.learner.fifawc2018wallpaper.Model.Wallpaper;
import com.learner.fifawc2018wallpaper.R;
import com.learner.fifawc2018wallpaper.ViewWallpaperActivity;

import java.util.ArrayList;
import java.util.List;

public class RecentWallpaperAdapter extends RecyclerView.Adapter<WallpaperListAdapter.WallpaperHolder>{

    private Context context;
    private List<Recent> recents = new ArrayList<>();

    public RecentWallpaperAdapter(Context context, List<Recent> recents) {
        this.context = context;
        this.recents = recents;
    }

    @NonNull
    @Override
    public WallpaperListAdapter.WallpaperHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wallpaper_list_item,parent,false);
        return new WallpaperListAdapter.WallpaperHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperListAdapter.WallpaperHolder holder, final int position) {
        Glide.with(context)
                .load(recents.get(position).getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.wallpaperHolderImageView);
        /*Picasso.get().load(wallpaperList.get(position).getImageUrl())
                .fit()
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.wallpaperHolderImageView);*/
        holder.wallpaperHolderImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Wallpaper wallpaper = new Wallpaper();
                wallpaper.setCategoryId(recents.get(position).getCategoryId());
                wallpaper.setImageUrl(recents.get(position).getImageUrl());
                Common.SELECTED_WALLPAPER = wallpaper;
                Common.STR_SELECTED_WALLPAPER_KEY = recents.get(position).getKey();
                Log.d("Recent Child Ref Key", Common.STR_SELECTED_WALLPAPER_KEY);
                context.startActivity(new Intent(context, ViewWallpaperActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return recents.size();
    }
}
