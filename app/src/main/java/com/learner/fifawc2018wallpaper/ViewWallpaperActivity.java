package com.learner.fifawc2018wallpaper;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learner.fifawc2018wallpaper.Database.Favourite.Favourite;
import com.learner.fifawc2018wallpaper.Database.Favourite.FavouriteDataSource;
import com.learner.fifawc2018wallpaper.Database.Favourite.FavouriteRepository;
import com.learner.fifawc2018wallpaper.Database.LocalDatabase;
import com.learner.fifawc2018wallpaper.Database.Recent.Recent;
import com.learner.fifawc2018wallpaper.Database.Recent.RecentDataSource;
import com.learner.fifawc2018wallpaper.Database.Recent.RecentRepository;
import com.learner.fifawc2018wallpaper.Model.Common;
import com.learner.fifawc2018wallpaper.Model.Wallpaper;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ViewWallpaperActivity extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout ;
    private FloatingActionButton setWallpaperButton;
    private LinearLayout linearLayout;
    private ImageView wallpaperImage;
    private Uri uri;
    private DatabaseReference wallpaperRef;

    CompositeDisposable compositeDisposable;
    RecentRepository recentRepository;
    FavouriteRepository favouriteRepository;
    Favourite favourite;
    List<Favourite> favouriteList = new ArrayList<>();

    Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(ViewWallpaperActivity.this);
            try {
                wallpaperManager.setBitmap(bitmap);
                Snackbar.make(linearLayout,"Wallpaper set",Snackbar.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };
    Target uriToBitmap = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            Common.SELECTED_WALLPAPER_BITMAP = bitmap;
            Log.d("Image Bitmap",bitmap.toString());
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            //Common.SELECTED_WALLPAPER_BITMAP = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_wallpaper);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("wallpaper");
        setSupportActionBar(toolbar);
        toolbar.getBackground().setAlpha(255);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        wallpaperRef = FirebaseDatabase.getInstance().getReference(Common.STR_WALLPAPERS_REF);
        wallpaperRef.keepSynced(true);
        compositeDisposable  = new CompositeDisposable();
        LocalDatabase database = LocalDatabase.getInstance(getApplicationContext());
        recentRepository = RecentRepository.getInstance(RecentDataSource.getInstance(database.recentDAO()));
        favouriteRepository = FavouriteRepository.getInstance(FavouriteDataSource.getInstance(database.favouriteDAO()));

        linearLayout = findViewById(R.id.root_layout);
        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapseAppBar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setTitle(Common.STR_SELECTED_CATEGORY);
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.black));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.white));
        wallpaperImage = findViewById(R.id.wallpaper_image);
        uri = Uri.parse(Common.SELECTED_WALLPAPER.getImageUrl());
        Log.e("Uri", "selected photo uri = "+uri+"" );

        loadFavourite();

        Glide.with(this).load(Common.SELECTED_WALLPAPER.getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder_image)
                .into(wallpaperImage);

        Picasso.get().load(Common.SELECTED_WALLPAPER.getImageUrl())
                .into(uriToBitmap);

        /*try {
            Common.SELECTED_WALLPAPER_BITMAP = Glide.with(this).load(Common.SELECTED_WALLPAPER.getImageUrl())
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(100,100)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
*/
        addToRecent();

        /*setWallpaperButton = findViewById(R.id.set_wallpaper_button);
        setWallpaperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), WallpaperCroperActivity.class));
                *//*CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setCropMenuCropButtonTitle("crop")
                        .start(ViewWallpaperActivity.this);*//*
                Picasso.get().load(Common.SELECTED_WALLPAPER.getImageUrl())
                        .into(target);

            }
        });*/

        increaseViewCount();

    }

    private void increaseViewCount() {
                wallpaperRef.child(Common.STR_SELECTED_WALLPAPER_KEY)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("viewCount")){
                            Wallpaper wallpaper = dataSnapshot.getValue(Wallpaper.class);
                            long count = wallpaper.getViewCount()+1;
                            Map<String, Object> update_view_count = new HashMap<>();
                            update_view_count.put("viewCount",count);
                            FirebaseDatabase.getInstance().getReference(Common.STR_WALLPAPERS_REF)
                                    .child(Common.STR_SELECTED_WALLPAPER_KEY)
                                    .updateChildren(update_view_count)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ViewWallpaperActivity.this, "Can't Update View Count", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }else {
                            Map<String, Object> update_view_count = new HashMap<>();
                            update_view_count.put("viewCount",Long.valueOf(1));
                            wallpaperRef.child(Common.STR_SELECTED_WALLPAPER_KEY)
                                    .updateChildren(update_view_count)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ViewWallpaperActivity.this, "Can't set Default View Count", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void addToRecent() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                Recent recent = new Recent(Common.SELECTED_WALLPAPER.getImageUrl(),
                        Common.SELECTED_WALLPAPER.getCategoryId(),
                        String.valueOf(System.currentTimeMillis()),
                        Common.STR_SELECTED_WALLPAPER_KEY);
                recentRepository.insertRecent(recent);
                e.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.d("Add Success","success");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("Error", throwable.getMessage() );
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        Picasso.get().cancelRequest(target);
        compositeDisposable.clear();
        super.onDestroy();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_set_wallpaper:

                startActivity(new Intent(this,WallpaperCroperActivity.class));
                //Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_favourite:
                if (favourite==null){
                    addToFavourite();
                    item.setIcon(R.drawable.ic_favorite_borderless_black_24dp);
                }else {
                    removeFromFavourite();
                    item.setIcon(R.drawable.ic_favorite_border_black_24dp);
                }

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void removeFromFavourite() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                Favourite favourite = new Favourite(Common.SELECTED_WALLPAPER.getCategoryId(),
                        Common.SELECTED_WALLPAPER.getImageUrl(),
                        Common.STR_SELECTED_WALLPAPER_KEY);
                favouriteRepository.removeFavourite(favourite);
                emitter.onComplete();
            }
        }).subscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

        compositeDisposable.add(disposable);
    }

    private void addToFavourite() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                Favourite favourite = new Favourite(Common.SELECTED_WALLPAPER.getCategoryId(),
                        Common.SELECTED_WALLPAPER.getImageUrl(),
                        Common.STR_SELECTED_WALLPAPER_KEY);
                favouriteRepository.addFavourite(favourite);
                e.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.d("Add Success","success");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("Error", throwable.getMessage() );
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
        compositeDisposable.add(disposable);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.action_favourite);
        checkFavouriteIsExits();
        if (favourite==null){
            menuItem.setIcon(R.drawable.ic_favorite_border_black_24dp);
        }else {
            menuItem.setIcon(R.drawable.ic_favorite_borderless_black_24dp);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void checkFavouriteIsExits() {
        if (favouriteList.size()>0){
            for (int i = 0; i < favouriteList.size(); i++){
                if (favouriteList.get(i).getImageUrl().equals(Common.SELECTED_WALLPAPER.getImageUrl())){
                    favourite=favouriteList.get(i);
                    return;
                }
            }
        }
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
    }

}
