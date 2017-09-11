package com.sairam.inshortsapp.baseframework;

import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewStubProxy;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import com.manaschaudhari.android_mvvm.ViewModel;
import com.manaschaudhari.android_mvvm.adapters.ViewModelBinder;
import com.manaschaudhari.android_mvvm.utils.BindingUtils;
import com.manaschaudhari.android_mvvm.utils.Preconditions;
import com.sairam.inshortsapp.R;
import com.sairam.inshortsapp.databinding.ActivityBaseAppCompatBinding;

/**
 * Created by Sairam Rachapudi on 10/09/17.
 * Base MVVM Activity common to all
 */

public abstract class BaseMvvmActivity extends AppCompatActivity {

    private ActivityBaseAppCompatBinding baseAppCompatBinding;

    public abstract int getActivityLayoutId();
    protected ViewDataBinding binding;

    @NonNull
    protected abstract ViewModel createViewModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        baseAppCompatBinding = DataBindingUtil.setContentView(this, R.layout.activity_base_app_compat);

        ViewModel viewModel = createViewModel();

        ViewStubProxy viewStubProxy = baseAppCompatBinding.baseAppcompatViewstub;

        ViewStub viewStub = viewStubProxy.getViewStub();
        viewStub.setLayoutResource(getActivityLayoutId());
        viewStubProxy.setContainingBinding(baseAppCompatBinding);

        viewStubProxy.setOnInflateListener((viewStub1, view) -> {
            binding = DataBindingUtil.bind(view);
            getDefaultBinder().bind(binding, viewModel);
        });

        if (!viewStubProxy.isInflated()) {
            viewStubProxy.getViewStub().inflate();
        }
    }

    @Override
    protected void onDestroy() {
        getDefaultBinder().bind(binding, null);
        binding.executePendingBindings();
        super.onDestroy();
    }

    @NonNull
    private ViewModelBinder getDefaultBinder() {
        ViewModelBinder defaultBinder = BindingUtils.getDefaultBinder();
        Preconditions.checkNotNull(defaultBinder, "Default Binder");
        return defaultBinder;
    }


    protected void setProgressBar(boolean visible){
        baseAppCompatBinding.progressBar.setVisibility(visible? View.VISIBLE:View.GONE);
    }
}
