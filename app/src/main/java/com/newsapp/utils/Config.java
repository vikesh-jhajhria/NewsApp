package com.newsapp.utils;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.FragmentManager;

import com.newsapp.model.CategoryModel;
import com.newsapp.model.NewsModel;

import java.util.ArrayList;

/**
 * Created by Vikesh on 02/14/2017.
 */

public class Config {

    public static ArrayList<CategoryModel> categoryList = new ArrayList<>();
    public static ArrayList<NewsModel> newsList = new ArrayList<>();
    public static String toolbarTitle = "";

    public final static int SPLASH_TIME = 2000;

    public final static String YOUTUBE_KEY = "AIzaSyCbTifD4vSnJTEpJSfVdlC1tr03iWcAMOE";

}
