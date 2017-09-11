package com.sairam.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sairamrachapudi on 10/09/17.
 */

@Entity
public class NewsArticleModel {

    @PrimaryKey
    @SerializedName("ID")
    private int id;

    @SerializedName("TITLE")
    String title;

    @SerializedName("URL")
    String url;

    @SerializedName("PUBLISHER")
    String publisher;

    @SerializedName("CATEGORY")
    String category;

    @SerializedName("HOSTNAME")
    String hostname;

    @SerializedName("TIMESTAMP")
    long timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
