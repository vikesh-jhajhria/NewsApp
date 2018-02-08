package com.newsapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.widget.TextView;

import java.util.HashMap;


public class Utils {

    public static HashMap getDeviceSize(Activity activity) {
        HashMap hashMap = new HashMap();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        hashMap.put("Height", displaymetrics.heightPixels);
        hashMap.put("Width", displaymetrics.widthPixels);
        return hashMap;
    }

}
