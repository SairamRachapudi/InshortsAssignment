package com.sairam.inshortsapp.baseframework;

import io.reactivex.functions.Action;

/**
 * Created by sairamrachapudi on 17/07/17.
 */

public interface NavigatorFragment {

    void showAlertDialog(String alert, String s, Action aVoid);

    void showProgressDialog(String s);

    void dismissProgress();
}
