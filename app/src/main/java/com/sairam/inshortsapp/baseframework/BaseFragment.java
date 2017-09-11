package com.sairam.inshortsapp.baseframework;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;

import io.reactivex.functions.Action;

/**
 * Created by sairamrachapudi on 17/07/17.
 */

public abstract class BaseFragment extends BaseMvvmFragment {
    protected ProgressDialog progressDialog;

    @NonNull
    protected NavigatorFragment getNavigator() {
        return new NavigatorFragment() {
            @Override
            public void showAlertDialog(String title, String alertMsg, final Action onPositiveClick) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

                alertDialog.setTitle(title);
                // Setting Dialog Message
                alertDialog.setMessage(alertMsg);

                // Setting Positive "ok" Button
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        try {
                            onPositiveClick.run();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                alertDialog.setCancelable(false);
                alertDialog.show();
            }

            @Override
            public void showProgressDialog(String msg) {

                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(getActivity());
                }

                progressDialog.setMessage(msg);
                progressDialog.setCancelable(false);
                progressDialog.setIndeterminate(true);
                progressDialog.show();
            }

            @Override
            public void dismissProgress() {
                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();
            }

            private void navigate(Class<?> destination) {
                Intent intent = new Intent(getActivity(), destination);
                startActivity(intent);
            }
        };
    }
}
