package com.newsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsapp.NewsDetailActivity;
import com.newsapp.R;
import com.newsapp.model.NewsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Vikesh on 11/9/2016.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    Context context;
    ArrayList<NewsModel> list;

    public NewsAdapter(Context context, ArrayList<NewsModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.name.setText(new String(Base64.decode(list.get(position).getNews_title(),Base64.DEFAULT)));
        holder.date.setText(list.get(position).getDate_modified());
        Log.v("NEWS", list.get(position).getCover_image());

        Picasso.with(context).load(list.get(position).getCover_image())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.image);
        final NewsModel newsModel = list.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("INDEX", position);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, date;
        ImageView image;


        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.txt_title);
            date = (TextView) itemView.findViewById(R.id.txt_date);
            image = (ImageView) itemView.findViewById(R.id.img_news);
        }
    }
}
