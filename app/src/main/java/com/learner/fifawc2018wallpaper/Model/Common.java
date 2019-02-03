package com.learner.fifawc2018wallpaper.Model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Common {
    public static final String STR_COUNTRY_CATEGORY_REF = "CountryInformation";
    public static final String STR_WALLPAPERS_REF = "Wallpapers";
    public static Bitmap SELECTED_WALLPAPER_BITMAP;
    public static String STR_SELECTED_CATEGORY;
    public static String STR_SELECTED_CATEGORY_ID;
    public static Wallpaper SELECTED_WALLPAPER = new Wallpaper();
    public static String STR_SELECTED_WALLPAPER_KEY;
    public static List<String> CHILD_REF_KEY = new ArrayList<>();
}
