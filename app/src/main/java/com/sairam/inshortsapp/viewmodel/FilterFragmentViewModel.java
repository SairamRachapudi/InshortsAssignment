package com.sairam.inshortsapp.viewmodel;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.sairam.inshortsapp.events.CategorySelectedEvent;
import org.greenrobot.eventbus.EventBus;
import io.reactivex.functions.Action;

/**
 * Created by sairamrachapudi on 10/09/17.
 */

public class FilterFragmentViewModel implements ViewModel {

    public Action businessClicked,scienceClicked,entrmntClicked,healthClicked,onbgClicked;

    public FilterFragmentViewModel() {
        businessClicked = ()->{
            EventBus.getDefault().post(new CategorySelectedEvent("b"));
        };
        scienceClicked = ()->{
            EventBus.getDefault().post(new CategorySelectedEvent("t"));
        };
        entrmntClicked = ()->{
            EventBus.getDefault().post(new CategorySelectedEvent("e"));
        };
        healthClicked = ()->{
            EventBus.getDefault().post(new CategorySelectedEvent("m"));
        };
        onbgClicked = ()->{
            // Not Needed
        };
    }
}
