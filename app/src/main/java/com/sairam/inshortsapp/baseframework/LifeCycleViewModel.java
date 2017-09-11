package com.sairam.inshortsapp.baseframework;

import com.manaschaudhari.android_mvvm.ViewModel;

/**
 * Created by sairamrachapudi on 17/07/17.
 */

public interface LifeCycleViewModel extends ViewModel {
    void onStop();
    void onStart();
    void onDestroy();
}
