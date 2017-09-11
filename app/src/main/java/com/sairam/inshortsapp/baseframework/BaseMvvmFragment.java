package com.sairam.inshortsapp.baseframework;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewStubProxy;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.manaschaudhari.android_mvvm.adapters.ViewModelBinder;
import com.manaschaudhari.android_mvvm.utils.BindingUtils;
import com.manaschaudhari.android_mvvm.utils.Preconditions;
import com.sairam.inshortsapp.R;
import com.sairam.inshortsapp.databinding.FragmentBaseBinding;

/**
 * Created by sairamrachapudi on 17/07/17.
 */

public abstract class BaseMvvmFragment extends Fragment {

    public abstract int getFragmentLayoutId();
    protected ViewDataBinding binding;
    private LifeCycleViewModel lifeCycleViewModel;
    @NonNull
    protected abstract ViewModel createViewModel();
    @Override
    public void onStart() {
        super.onStart();
        if(lifeCycleViewModel !=null){
            lifeCycleViewModel.onStart();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentBaseBinding fragmentBaseBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_base,container,false);

        ViewModel viewModel = createViewModel();
        if(isLifeCycleViewModel())
            lifeCycleViewModel = (LifeCycleViewModel) viewModel;

        ViewStubProxy viewStubProxy=fragmentBaseBinding.baseFragmentViewstub;
        ViewStub viewStub = viewStubProxy.getViewStub();
        viewStub.setLayoutResource(getFragmentLayoutId());
        viewStubProxy.setContainingBinding(fragmentBaseBinding);

        viewStubProxy.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub viewStub, View view) {
                binding = DataBindingUtil.bind(view);
                getDefaultBinder().bind(binding, viewModel);
            }
        });

        if (!viewStubProxy.isInflated()) {
            viewStubProxy.getViewStub().inflate();
        }
        return fragmentBaseBinding.getRoot();
    }


    protected boolean isLifeCycleViewModel(){
        return false;
    }

    @Override
    public void onStop() {
        super.onStop();
        if(lifeCycleViewModel !=null){
            lifeCycleViewModel.onStop();
        }
    }

    @Override
    public void onDestroy() {
        if(lifeCycleViewModel !=null){
            lifeCycleViewModel.onDestroy();
        }
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
}
