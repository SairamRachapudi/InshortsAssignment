package com.sairam.inshortsapp.baseframework;

import android.app.Application;
import android.support.multidex.MultiDex;
import com.manaschaudhari.android_mvvm.utils.BindingUtils;
import com.sairam.inshortsapp.utils.BindingAdapters;

/**
 * Created by Sairam Rachapudi on 10/09/17.
 * Application class
 */

public class InshortsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BindingUtils.setDefaultBinder(BindingAdapters.defaultBinder);
        MultiDex.install(getApplicationContext());
    }
}
