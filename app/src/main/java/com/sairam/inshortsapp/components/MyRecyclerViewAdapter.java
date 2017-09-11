package com.sairam.inshortsapp.components;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.manaschaudhari.android_mvvm.adapters.RecyclerViewAdapter;
import com.manaschaudhari.android_mvvm.adapters.ViewModelBinder;
import com.manaschaudhari.android_mvvm.adapters.ViewProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by sairamrachapudi on 10/09/17.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.DataBindingViewHolder> {
    private
    @NonNull
    List<ViewModel> latestViewModels = new ArrayList<>();
    private final
    @NonNull
    ViewProvider viewProvider;
    private final
    @NonNull
    ViewModelBinder binder;
    private final
    @NonNull
    HashMap<RecyclerView.AdapterDataObserver, Disposable> subscriptions = new HashMap<>();

    public MyRecyclerViewAdapter(@NonNull Observable<List<ViewModel>> viewModels,
                                 @NonNull ViewProvider viewProvider,
                                 @NonNull ViewModelBinder viewModelBinder) {
        this.viewProvider = viewProvider;
        this.binder = viewModelBinder;
        viewModels
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    if(list.size() == 0){
                        latestViewModels.clear();
                        notifyDataSetChanged();
                        return;
                    }
                    latestViewModels.addAll(list);
                    notifyItemRangeInserted(latestViewModels.size()-list.size(),list.size());
                }, t -> {
                    latestViewModels.clear();
                    notifyDataSetChanged();
                });
    }

    @Override
    public int getItemViewType(int position) {
        return viewProvider.getView(latestViewModels.get(position));
    }

    @Override
    public MyRecyclerViewAdapter.DataBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        return new MyRecyclerViewAdapter.DataBindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewAdapter.DataBindingViewHolder holder, int position) {
        binder.bind(holder.viewBinding, latestViewModels.get(position));
        holder.viewBinding.executePendingBindings();
    }

    @Override
    public void onViewRecycled(MyRecyclerViewAdapter.DataBindingViewHolder holder) {
        binder.bind(holder.viewBinding, null);
        holder.viewBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return latestViewModels.size();
    }

    public static class DataBindingViewHolder extends RecyclerView.ViewHolder {
        @NonNull
        final ViewDataBinding viewBinding;

        public DataBindingViewHolder(@NonNull ViewDataBinding viewBinding) {
            super(viewBinding.getRoot());
            this.viewBinding = viewBinding;
        }
    }
}
