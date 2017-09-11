package com.sairam.inshortsapp.baseframework;

import android.content.Intent;
import android.support.annotation.NonNull;
import com.sairam.inshortsapp.R;
import com.sairam.inshortsapp.activities.WebViewActivity;
import com.sairam.inshortsapp.events.NewsInfoEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Sairam Rachapudi on 10/09/17.
 * Base Activity for generic Activity functions
 */

public abstract class BaseActivity extends BaseMvvmActivity {

    @NonNull
    protected HomeInteractor getNavigator(){
        return new HomeInteractor() {
            @Override
            public void navigateToWebViewActivity(String projectUrl) {
                navigate(WebViewActivity.class);
                EventBus.getDefault().postSticky(new NewsInfoEvent(projectUrl));
            }

            @Override
            public void setProgressBarVisibility(boolean visible) {
                setProgressBar(visible);
            }

            @Override
            public void finishActivty() {
                finish();
            }

            @Override
            public void showToast(String message) {
                showToast(message);
            }

            private void navigate(Class<?> destination) {
                Intent intent = new Intent(BaseActivity.this, destination);
                startActivity(intent);
                overridePendingTransition(R.anim.enter,R.anim.exit);
            }


        };
    }
}
