package com.newsapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.newsapp.model.NewsModel;
import com.newsapp.utils.Config;
import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends BaseActivity {

    private int index;
    private NewsModel newsModel;
    private TextView title,date,description;
    private ImageView imageView;
    private YouTubePlayerView youTubeView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        toolbar = findViewById(R.id.toolbar);
        ((TextView)toolbar.findViewById(R.id.title)).setText(Config.toolbarTitle);
        toolbar.findViewById(R.id.img_back).setOnClickListener(this);

        index = getIntent().getIntExtra("INDEX",0);
        newsModel = Config.newsList.get(index);
        title = findViewById(R.id.txt_title);
        date = findViewById(R.id.txt_date);
        description = findViewById(R.id.txt_description);
        imageView = findViewById(R.id.img_news);
        youTubeView = findViewById(R.id.youtube_view);

        title.setText(new String(Base64.decode(newsModel.getNews_title(),Base64.DEFAULT)));
        description.setText(new String(Base64.decode(newsModel.getNews_description(),Base64.DEFAULT)));
        date.setText(newsModel.getDate_modified());

        Picasso.with(this).load(newsModel.getCover_image())
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView);

        youTubeView.initialize(Config.YOUTUBE_KEY, this);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            youTubeView.initialize(Config.YOUTUBE_KEY, this);
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {
            youTubePlayer.cueVideo("X0HRBR8Wu3k");
        }
    }
}
