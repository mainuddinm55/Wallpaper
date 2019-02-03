package com.learner.fifawc2018wallpaper.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.learner.fifawc2018wallpaper.Fragment.CategoryFragment;
import com.learner.fifawc2018wallpaper.Fragment.TrendingFragment;
import com.learner.fifawc2018wallpaper.Fragment.RecentFragment;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return CategoryFragment.getInstance();
        }else if (position==1){
            return RecentFragment.getInstance(context);
        }else if (position==2){
            return TrendingFragment.getInstance(context);
        }else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Category";
            case 1:
                return "Recents";
            case 2:
                return "Trending";
        }
        return "";
    }

}
