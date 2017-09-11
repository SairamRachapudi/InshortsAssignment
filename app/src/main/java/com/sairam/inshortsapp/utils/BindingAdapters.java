package com.sairam.inshortsapp.utils;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.solver.widgets.Rectangle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.manaschaudhari.android_mvvm.adapters.RecyclerViewAdapter;
import com.manaschaudhari.android_mvvm.adapters.ViewModelBinder;
import com.manaschaudhari.android_mvvm.adapters.ViewProvider;
import com.manaschaudhari.android_mvvm.utils.Preconditions;
import com.sairam.inshortsapp.BR;
import com.sairam.inshortsapp.R;
import com.sairam.inshortsapp.components.AmazeTextView;
import com.sairam.inshortsapp.components.MyRecyclerViewAdapter;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import rx.functions.Action0;

import static com.manaschaudhari.android_mvvm.utils.BindingUtils.bindAdapter;

/**
 * Created by Sairam Rachapudi on 10/09/17.
 * Custom Binding Adapters for Attributes
 */
public class BindingAdapters {

    @NonNull
    public static final ViewModelBinder defaultBinder = (viewDataBinding, viewModel) -> viewDataBinding.setVariable(BR.viewModel, viewModel);

    @BindingAdapter({"items", "view_provider"})
    public static void bindAdapterWithDefaultBinder(@NonNull RecyclerView recyclerView, @Nullable Observable<List<ViewModel>> items, @Nullable ViewProvider viewProvider) {
        MyRecyclerViewAdapter adapter = null;
        if (items != null && viewProvider != null) {
            Preconditions.checkNotNull(defaultBinder, "Default Binder");
            adapter = new MyRecyclerViewAdapter(items, viewProvider, defaultBinder);
        }
        bindAdapter(recyclerView, adapter);
    }

    @BindingConversion
    public static View.OnClickListener toOnClickListener(final Action0 listener) {
        if (listener != null) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.call();
                }
            };
        } else {
            return null;
        }
    }

    @BindingAdapter("background")
    public static void setBackground(LinearLayout view,String category){
        if(category!=null) {
            view.setBackgroundColor(Color.parseColor(getColorForCategory(category)));
        }
    }
    @BindingAdapter("text")
    public static void setBackground(AmazeTextView view, String category){
        if(category !=null)
        view.setText(getCategoryName(category));
    }

    private static String getCategoryName(String category) {
        switch (category){
            case "b" : return "Business";
            case "t" : return "Science & Technology";
            case "e" : return "Entertainmnet";
            case "m" : return "Health";
        }
        return "Business";
    }

    @BindingAdapter("cardbackground")
    public static void setBackground(View view, String category){
        if(category !=null)
        view.setBackground(view.getResources().getDrawable(getDrawableForCategory(category)));
    }

    private static int getDrawableForCategory(String category) {
        switch (category){
            case "b" : return R.drawable.b_border;
            case "t" : return R.drawable.t_border;
            case "e" : return R.drawable.e_border;
            case "m" : return R.drawable.m_border;
        }
        return 0;
    }

    private static String getColorForCategory(String category) {
        switch (category){
            case "b" : return "#841722";
            case "t" : return "#d2c01e";
            case "e" : return "#2aa45f";
            case "m" : return "#841722";
        }
        return "#FF4081";
    }

    @BindingConversion
    public static View.OnClickListener toOnClickListener(final Action listener) {
        if (listener != null) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        listener.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
        } else {
            return null;
        }
    }

}
