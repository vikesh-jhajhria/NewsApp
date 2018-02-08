package com.newsapp.model;

/**
 * Created by Vikesh on 08-Feb-18.
 */

public class CategoryModel {
    private String pk_cat_id;
    private String cat_name;
    private String cat_url_key;
    private String status;
    private String date_added;

    public String getPk_cat_id() {
        return pk_cat_id;
    }

    public void setPk_cat_id(String pk_cat_id) {
        this.pk_cat_id = pk_cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_url_key() {
        return cat_url_key;
    }

    public void setCat_url_key(String cat_url_key) {
        this.cat_url_key = cat_url_key;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }
}
