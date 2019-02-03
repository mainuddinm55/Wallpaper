package com.learner.fifawc2018wallpaper.Model;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DownloadWallpaper extends AsyncTask<String,Void,List<Wallpaper>>{

    private List<Wallpaper> wallpaperList = new ArrayList<>();
    private DatabaseReference wallpaperRef;

    @Override
    protected List<Wallpaper> doInBackground(String... strings) {
        wallpaperRef = FirebaseDatabase.getInstance().getReference().child(Common.STR_WALLPAPERS_REF);
        wallpaperRef.orderByChild("categoryId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postData : dataSnapshot.getChildren()){
                    Wallpaper wallpaper = postData.getValue(Wallpaper.class);
                    wallpaperList.add(wallpaper);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("download error", databaseError.getMessage());
            }
        });
        return wallpaperList;
    }

    @Override
    protected void onPostExecute(List<Wallpaper> wallpapers) {
        super.onPostExecute(wallpapers);
    }
}
