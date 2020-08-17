package com.example.bakingapp.utilities;

import android.content.Context;
import android.util.DisplayMetrics;

public class DisplayUtils {
    public static float getSmallestWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float screenWidth = dm.widthPixels / dm.density;
        float screenHeight = dm.heightPixels / dm.density;
        return Math.min(screenWidth, screenHeight);
    }

    public static boolean isTablet(Context context){
        return getSmallestWidth(context) >= 600;
    }
}
