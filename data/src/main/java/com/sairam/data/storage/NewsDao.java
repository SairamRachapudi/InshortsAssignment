package com.sairam.data.storage;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.sairam.data.model.NewsArticleModel;
import java.util.Collection;
import java.util.List;

/**
 * Created by Sairam Rachapudi on 10/09/17.
 * Data Access Object Class
 */

@Dao
public interface NewsDao {

    @Insert
    public void insertProjectsInfo(List<NewsArticleModel> projects);

    @Query("SELECT * FROM  newsarticlemodel")
    List<NewsArticleModel> getNewsArticlesList();

    @Query("SELECT * FROM newsarticlemodel WHERE title LIKE :searchWord")
    List<NewsArticleModel> searchNewsArticles(String searchWord);

    @Query("SELECT * FROM  newsarticlemodel LIMIT 10 OFFSET :index")
    List<NewsArticleModel> getNextTenResults(int index);

    @Query("SELECT * FROM  newsarticlemodel WHERE category LIKE :category LIMIT 10 OFFSET :index")
    List<NewsArticleModel> getCategoryBasedResults(String category,int index);
}

