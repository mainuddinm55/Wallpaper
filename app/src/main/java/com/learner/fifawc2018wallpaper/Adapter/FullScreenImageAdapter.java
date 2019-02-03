package com.learner.fifawc2018wallpaper.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.learner.fifawc2018wallpaper.R;

public class FullScreenImageAdapter extends PagerAdapter {

    private Activity activity;
    private Context context;
    int position;

    public FullScreenImageAdapter(Context context){
        this.context = context;
        activity = (Activity) context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        this.position = position;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.full_screen_image_item, container,false);
        ImageView fullScreenImageView = view.findViewById(R.id.galleryImageView);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        Log.e("height", "height: "+height );
        int width = displayMetrics.widthPixels;
        Log.e("width", "width: "+width );
        fullScreenImageView.setMinimumHeight(height);
        fullScreenImageView.setMinimumWidth(width);


        container.addView(fullScreenImageView);
        return fullScreenImageView;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View)object);
    }

    public int getPosition(){
        return position;
    }
}
