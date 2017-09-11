package com.sairam.inshortsapp.viewmodel;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.sairam.data.model.NewsArticleModel;
import com.sairam.inshortsapp.baseframework.HomeInteractor;

/**
 * Created by Sairam Rachapudi on 10/09/17.
 * Kick Starter List Item View Model
 */

public class NewsArticleViewModel implements ViewModel {
    private final NewsArticleModel model;
    private final HomeInteractor navigator;

    public NewsArticleViewModel(NewsArticleModel model, HomeInteractor navigator) {
        this.model = model;
        this.navigator = navigator;
    }

    public NewsArticleModel getModel() {
        return model;
    }

    public void onItemClicked(){
        navigator.navigateToWebViewActivity(model.getUrl());
    }
}
