package com.sairam.inshortsapp.viewmodel;

import android.databinding.ObservableField;
import android.text.TextUtils;
import android.util.Log;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.sairam.data.NewsService;
import com.sairam.data.model.NewsArticleModel;
import com.sairam.data.model.retrofit.HackerEarthApiService;
import com.sairam.inshortsapp.baseframework.HomeInteractor;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by Sairam Rachapudi on 10/09/17.
 * Home View Model class
 */

public class HomeActivityViewModel implements ViewModel {

    private final HomeInteractor navigator;
    private final NewsService newsService;
    private HackerEarthApiService apiService;
    private BehaviorSubject<List<NewsArticleModel>> responseList;
    public ObservableField<Boolean> emptyNote = new ObservableField<>();
    public  Observable<List<NewsArticleViewModel>> itemVms;
    private int currentIndex = 0;
    public ObservableField<Boolean> categorySelected = new ObservableField<Boolean>(false);
    public ObservableField<String> category = new ObservableField<>();
    public Action onCategoryClicked;
    private String categoryCode;

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public HomeActivityViewModel(HackerEarthApiService apiService, HomeInteractor navigator, NewsService newsService) {
        this.apiService = apiService;
        this.navigator = navigator;
        this.newsService = newsService;
        responseList = BehaviorSubject.create();
        itemVms = responseList.map(items ->{
            List<NewsArticleViewModel> list = new ArrayList<>();
            for(NewsArticleModel model:items){
                list.add(new NewsArticleViewModel(model,navigator));
            }
            return list;
        });
        onCategoryClicked= ()->{
          categorySelected.set(false);
          responseList.onNext(new ArrayList<>());
          loadList();
        };
        loadList();
    }

    public void loadList(){
        currentIndex =0;
        navigator.setProgressBarVisibility(true);
        newsService.getAllNewsArticles()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list->{
                    if(list !=null && list.size()>0){
                        responseList.onNext(list.subList(0,10));
                        currentIndex+=10;
                        navigator.setProgressBarVisibility(false);
                    }else{
                        loadListFromAPI();
                    }
                });
    }

    public void loadListFromAPI() {
        apiService.getListFromAPI()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<List<NewsArticleModel>>() {
                    @Override
                    public void accept(@NonNull List<NewsArticleModel> newsArticlesModels) throws Exception {
                        newsService.insertNewsArticles(newsArticlesModels,new Action(){
                            @Override
                            public void run() throws Exception {
                                navigator.setProgressBarVisibility(false);
                                responseList.onNext(newsArticlesModels.subList(0,10));
                                currentIndex+=10;
                            }
                        });
                    }
                },t->{
                        Log.d(this.getClass().getSimpleName(),"Error in Loading News Articles");
                        navigator.showToast("Error in Loading News Articles");
                        navigator.setProgressBarVisibility(false);
                     });
    }

    public void searchNews(String searchWord) {
        if(TextUtils.isEmpty(searchWord)){
            newsService.getAllNewsArticles()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(list ->{
                        responseList.onNext(new ArrayList<>());
                        responseList.onNext(list);
                        emptyNote.set(false);
                    });
            return;
        }

        newsService.getAllNewsArticlesWithSearch(searchWord)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(list->{
                        emptyNote.set(list==null || list.size() == 0);
                        if(list==null){
                            responseList.onNext(new ArrayList<>());
                        }else{
                            responseList.onNext(new ArrayList<>());
                            responseList.onNext(list);
                        }
                    });
    }

    public void loadNextTenResults() {
        if(categorySelected.get()){
            newsService.loadCategoryRelatedNews(getCategoryCode(),currentIndex)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(list->{
                        responseList.onNext(list);
                    });
            currentIndex +=10;
            return;
        }
        newsService.loadNextTenResults(currentIndex).observeOn(AndroidSchedulers.mainThread())
                .subscribe(list->{
            responseList.onNext(list);
        });
        currentIndex +=10;
    }

    public void setFilterCategory(String categoryName) {
        categorySelected.set(true);
        setCategoryCode(categoryName);
        category.set(getRealCategoryName(categoryName));
        currentIndex = 0;
        newsService.loadCategoryRelatedNews(categoryName,currentIndex)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    emptyNote.set(list.size() == 0);
                    responseList.onNext(new ArrayList<>());
                    responseList.onNext(list);
                });
        currentIndex+=10;
    }

    private String getRealCategoryName(String name) {
        switch (name){
            case "b" : return "Business";
            case "t" : return "Science and Technology";
            case "e" : return "Entertainment";
            case "m" : return "Health";
        }
        return null;
    }
}
