package com.learner.fifawc2018wallpaper;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learner.fifawc2018wallpaper.Adapter.WallpaperListAdapter;
import com.learner.fifawc2018wallpaper.Model.Common;
import com.learner.fifawc2018wallpaper.Model.Wallpaper;

import java.util.ArrayList;
import java.util.List;

public class WallpaperListActivity extends AppCompatActivity {

    private DatabaseReference wallpaperRef;
    private RecyclerView wallpaperListRecyclerView;
    private WallpaperListAdapter adapter;
    private List<Wallpaper> wallpaperList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(Common.STR_SELECTED_CATEGORY + " Wallpaper");
        setSupportActionBar(toolbar);
        wallpaperListRecyclerView = findViewById(R.id.wallpaper_list_recycler_view);

        wallpaperListRecyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        wallpaperListRecyclerView.setLayoutManager(gridLayoutManager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        wallpaperRef = FirebaseDatabase.getInstance().getReference().child(Common.STR_WALLPAPERS_REF);
        wallpaperRef.keepSynced(true);
        wallpaperRef.orderByChild("categoryId").equalTo(Common.STR_SELECTED_CATEGORY_ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                wallpaperList.clear();
                Common.CHILD_REF_KEY.clear();
                for (DataSnapshot postData : dataSnapshot.getChildren()) {
                    Wallpaper wallpaper = postData.getValue(Wallpaper.class);
                    String key = postData.getKey();
                    Common.CHILD_REF_KEY.add(key);
                    wallpaperList.add(wallpaper);
                }
                if (wallpaperList.size() > 0) {
                    adapter = new WallpaperListAdapter(WallpaperListActivity.this, wallpaperList);
                    wallpaperListRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
