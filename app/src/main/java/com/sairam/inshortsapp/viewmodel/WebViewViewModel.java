package com.sairam.inshortsapp.viewmodel;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.sairam.inshortsapp.baseframework.HomeInteractor;

/**
 * Created by Sairam Rachapudi on 10/09/17.
 */

public class WebViewViewModel implements ViewModel {


    private final HomeInteractor navigator;

    public WebViewViewModel(HomeInteractor navigator) {
        this.navigator = navigator;
    }

    public void close(){
        navigator.finishActivty();
    }
}
