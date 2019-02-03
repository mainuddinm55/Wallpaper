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
import com.learner.fifawc2018wallpaper.Model.Common;
import com.learner.fifawc2018wallpaper.Model.Wallpaper;
import com.learner.fifawc2018wallpaper.R;
import com.learner.fifawc2018wallpaper.ViewWallpaperActivity;

import java.util.ArrayList;
import java.util.List;

public class TrendingWallpaperAdapter extends RecyclerView.Adapter<WallpaperListAdapter.WallpaperHolder> {

    private Context context;
    private List<Wallpaper> wallpaperList = new ArrayList<>();

    public TrendingWallpaperAdapter(Context context, List<Wallpaper> wallpaperList) {
        this.context = context;
        this.wallpaperList = wallpaperList;
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
                .load(wallpaperList.get(position).getImageUrl())
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
                wallpaper.setCategoryId(wallpaperList.get(position).getCategoryId());
                wallpaper.setImageUrl(wallpaperList.get(position).getImageUrl());
                Common.SELECTED_WALLPAPER = wallpaper;
                Common.STR_SELECTED_WALLPAPER_KEY =  Common.CHILD_REF_KEY.get(position);
//                Log.d("Recent Child Ref Key", Common.STR_SELECTED_WALLPAPER_KEY);
                context.startActivity(new Intent(context, ViewWallpaperActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return wallpaperList.size();
    }
}
