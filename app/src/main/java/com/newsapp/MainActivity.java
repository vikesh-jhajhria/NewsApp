package com.newsapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.newsapp.adapter.NewsAdapter;
import com.newsapp.model.CategoryModel;
import com.newsapp.model.NewsModel;
import com.newsapp.utils.Config;
import com.newsapp.utils.HTTPUrlConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.newsapp.utils.Config.categoryList;
import static com.newsapp.utils.Config.newsList;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private TextView breakingNews, emptyView;

    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.toolbar);


        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        new GetCategories().execute();
        breakingNews = findViewById(R.id.txt_breaking_news);
        emptyView = findViewById(R.id.empty_view);
        breakingNews.setSelected(true);
        recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        newsAdapter = new NewsAdapter(this, newsList);
        recyclerView.setAdapter(newsAdapter);
    }

    private void addNavigationMenuItems() {
        for (CategoryModel item : categoryList) {
            navigationView.getMenu().add(R.id.menu_group, Menu.NONE, 1, item.getCat_name()).setCheckable(true);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        /*if (item.getTitle().equals("Home")) {

        } else {*/
        for (CategoryModel category : categoryList) {
            if (item.getTitle().equals(category.getCat_name())) {
                newsList.clear();
                Config.toolbarTitle = category.getCat_name();
                toolbar.setTitle(category.getCat_name());
                new GetNews().execute(category.getPk_cat_id());
                break;
            }
        }
        //}
        return true;
    }

    class GetCategories extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgessDialog();
        }

        @Override
        protected String doInBackground(String... params) {

            return HTTPUrlConnection.getInstance().loadGet("http://royalsecurity.co.in/gnews_api/admin/api/get_category");
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dismissProgressDialog();
            try {
                JSONArray array = new JSONArray(result);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = (JSONObject) array.get(i);
                    CategoryModel categoryModel = new CategoryModel();
                    categoryModel.setCat_name(obj.getString("cat_name"));
                    categoryModel.setPk_cat_id(obj.getString("pk_cat_id"));
                    categoryModel.setCat_url_key(obj.getString("cat_url_key"));
                    categoryModel.setStatus(obj.getString("status"));
                    categoryModel.setDate_added(obj.getString("date_added"));

                    categoryList.add(categoryModel);
                }
                addNavigationMenuItems();
                if (categoryList.size() > 0) {
                    navigationView.getMenu().findItem(0).setChecked(true);
                    Config.toolbarTitle = categoryList.get(0).getCat_name();
                    toolbar.setTitle(categoryList.get(0).getCat_name());
                    new GetNews().execute(categoryList.get(0).getPk_cat_id());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    class GetNews extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgessDialog();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("category_id", params[0]);
            return HTTPUrlConnection.getInstance()
                    .loadPost("http://royalsecurity.co.in/gnews_api/admin/api/get_news",
                            map);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dismissProgressDialog();
            try {
                JSONArray array = new JSONArray(result);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = (JSONObject) array.get(i);
                    NewsModel newsModel = new NewsModel();
                    newsModel.setPk_news_id(obj.getString("pk_news_id"));
                    newsModel.setNews_title(obj.getString("news_title"));
                    newsModel.setNews_category(obj.getString("news_category"));
                    newsModel.setStatus(obj.getString("status"));
                    newsModel.setDate_added(obj.getString("date_added"));
                    newsModel.setCover_image(obj.getString("cover_image"));
                    newsModel.setVideo_url(obj.getString("video_url"));
                    newsModel.setNews_description(obj.getString("news_description"));
                    newsModel.setDate_modified(obj.getString("date_modified"));

                    newsList.add(newsModel);
                }
                if (newsList.size() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);
                }
                newsAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}
