package com.sairam.inshortsapp.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;
import com.manaschaudhari.android_mvvm.ViewModel;
import com.sairam.data.NewsService;
import com.sairam.data.model.retrofit.RetrofitClient;
import com.sairam.data.storage.AppDataBase;
import com.sairam.inshortsapp.R;
import com.sairam.inshortsapp.baseframework.BaseActivity;
import com.sairam.inshortsapp.databinding.ActivityHomeBinding;
import com.sairam.inshortsapp.events.CategorySelectedEvent;
import com.sairam.inshortsapp.fragments.FilterFragment;
import com.sairam.inshortsapp.viewmodel.HomeActivityViewModel;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.concurrent.TimeUnit;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import static android.view.View.GONE;

/**
 * Created by Sairam Rachapudi on 10/09/17.
 * Home Screen which loads list of News Articles
 */

public class HomeActivity extends BaseActivity {

    HomeActivityViewModel viewModel;
    private Disposable subscription;

    @Override
    public int getActivityLayoutId() {
        return R.layout.activity_home;
    }

    @NonNull
    @Override
    protected ViewModel createViewModel() {
        AppDataBase database = AppDataBase.getDatabase(this);
        viewModel = new HomeActivityViewModel(RetrofitClient.getAPIService(),getNavigator(),new NewsService(database));
        return viewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBinding().searchView.setQueryHint(getString(R.string.search_hint));
        getBinding().searchView.setIconifiedByDefault(false);
        getBinding().searchView.setFocusable(false);
        initGlobalSearchObservable(getBinding().searchView);
        enablePaging();
        EventBus.getDefault().register(this);
    }

    private void enablePaging() {
        getBinding().recyclerView.addOnScrollListener(scrollChangeListener);
    }

    public void initGlobalSearchObservable(SearchView searchView) {
        subscription = RxSearchView.queryTextChangeEvents(searchView)
                .debounce(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .skip(1)
                .subscribe(getGlobalSearchObserver(), throwable -> {
                    Log.d("TAG", "Error in searching");
                });
    }

    private Consumer<SearchViewQueryTextEvent> getGlobalSearchObserver() {
        return textViewTextChangeEvent -> {
            String searchWord = textViewTextChangeEvent.queryText().toString().trim();
            viewModel.searchNews(searchWord);
        };
    }

    private ActivityHomeBinding getBinding() {
        return (ActivityHomeBinding) binding;
    }

    public void onFabClicked(View v){
        getSupportFragmentManager().beginTransaction()
                .replace(getBinding().viewGroup.getId(),new FilterFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscription.dispose();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void getSelectedCategory(CategorySelectedEvent event){
        viewModel.setFilterCategory(event.getCategory());
        getBinding().cardView.setVisibility(View.VISIBLE);
        getSupportFragmentManager().popBackStack();
    }

    RecyclerView.OnScrollListener scrollChangeListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager layoutManager = ((LinearLayoutManager)recyclerView.getLayoutManager());
            int firstVisiblePos = layoutManager.findFirstVisibleItemPosition();
            int totalItemCount = layoutManager.getItemCount();
            int lastVisiblePos = layoutManager.findLastCompletelyVisibleItemPosition();

            if(lastVisiblePos == totalItemCount -1 && firstVisiblePos>0){
                viewModel.loadNextTenResults();
            }
        }
    };
}