package com.gnardini.lolmatchups.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.gnardini.lolmatchups.LolMatchupsApp;

public class DimenUtils {

    public static int convertDpToPixel(float dp) {
        Resources resources = LolMatchupsApp.getContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int) px;
    }

    public static float convertPixelsToDp(float px){
        Resources resources = LolMatchupsApp.getContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

}
