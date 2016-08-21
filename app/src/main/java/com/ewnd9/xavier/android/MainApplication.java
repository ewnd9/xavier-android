package com.ewnd9.xavier.android;

import android.app.Application;

/**
 * Created by ewnd9 on 21.08.16.
 */
public class MainApplication extends Application {

    public static String gcmAppId;

    @Override
    public void onCreate() {
        super.onCreate();
        gcmAppId = getApplicationContext().getString(R.string.gcm_app_id);
    }
}
