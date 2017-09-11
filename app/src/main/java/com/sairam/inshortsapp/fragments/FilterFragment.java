package com.sairam.inshortsapp.fragments;

import android.support.annotation.NonNull;
import com.manaschaudhari.android_mvvm.ViewModel;
import com.sairam.inshortsapp.R;
import com.sairam.inshortsapp.baseframework.BaseFragment;
import com.sairam.inshortsapp.viewmodel.FilterFragmentViewModel;

/**
 * Created by sairamrachapudi on 10/09/17.
 */

public class FilterFragment extends BaseFragment {
    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_filter_view;
    }

    @NonNull
    @Override
    protected ViewModel createViewModel() {
        return new FilterFragmentViewModel();
    }
}
