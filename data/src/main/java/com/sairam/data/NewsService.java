package com.sairam.data;

import android.os.AsyncTask;
import android.util.Log;

import com.sairam.data.model.NewsArticleModel;
import com.sairam.data.storage.AppDataBase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import rx.Subscriber;

import static android.content.ContentValues.TAG;

/**
 * Created by sairamrachapudi on 10/09/17.
 */

public class NewsService {
    AppDataBase appDataBase;

    public NewsService(AppDataBase appDataBase) {
        this.appDataBase = appDataBase;
    }

    public Observable<List<NewsArticleModel>> getAllNewsArticles() {
        return makeObservable(getNewsListFromDB()).subscribeOn(Schedulers.computation());
    }

    private Callable<List<NewsArticleModel>> getNewsListFromDB(){
        return new Callable<List<NewsArticleModel>>(){

            @Override
            public List<NewsArticleModel> call() throws Exception {
                return appDataBase.getNewsDao().getNewsArticlesList();
            }
        };
    }

    public void insertNewsArticles(final List<NewsArticleModel> newsList,Action action) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                appDataBase.getNewsDao().insertProjectsInfo(newsList);
            }
        }).subscribe(action);
    }

    public Observable<List<NewsArticleModel>> getAllNewsArticlesWithSearch(String searchWord) {
        return makeObservable(getNewsWithSearchWordFromDB(searchWord)).subscribeOn(Schedulers.computation());
    }

    private Callable<List<NewsArticleModel>> getNewsWithSearchWordFromDB(final String searchWord) {
        return new Callable<List<NewsArticleModel>>() {
            @Override
            public List<NewsArticleModel> call() throws Exception {
               return appDataBase.getNewsDao().searchNewsArticles("%"+searchWord+"%");
            }
        };
    }

    private <T> Observable<T> makeObservable(final Callable<T> func) {
        return Observable.create(
                new ObservableOnSubscribe<T>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<T> e) throws Exception {
                        try {
                            e.onNext(func.call());
                        } catch (Exception ex) {
                            Log.e(TAG, "Error reading from the database", ex);
                        }
                    }
                });
    }

    public Observable<List<NewsArticleModel>> loadNextTenResults(final int index) {
        return makeObservable(getNextTenResultsFromDB(index));
    }

    public Callable<List<NewsArticleModel>> getNextTenResultsFromDB(final int index) {
        return new Callable<List<NewsArticleModel>>() {
            @Override
            public List<NewsArticleModel> call() throws Exception {
                return appDataBase.getNewsDao().getNextTenResults(index);
            }
        };
    }

    public Observable<List<NewsArticleModel>> loadCategoryRelatedNews(String category,int index) {
        return makeObservable(getCategoryBasedResults(category,index));
    }

    private Callable<List<NewsArticleModel>> getCategoryBasedResults(final String category,final int index) {
        return new Callable<List<NewsArticleModel>>() {
            @Override
            public List<NewsArticleModel> call() throws Exception {
                return appDataBase.getNewsDao().getCategoryBasedResults(category,index);
            }
        };
    }
}
