package com.sairam.inshortsapp.baseframework;

/**
 * Created by Sairam Rachapudi on 10/09/17.
 * Home UI interactor
 */

public interface HomeInteractor {
    void navigateToWebViewActivity(String projectUrl);
    void setProgressBarVisibility(boolean visible);
    void finishActivty();
    void showToast(String message);
}
