package com.ewnd9.xavier.android;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ewnd9.xavier.android.remote_media.OnRemoteUpdateListener;
import com.ewnd9.xavier.android.remote_media.RemoteControlService;

/**
 * Created by ewnd9 on 17.06.16.
 */
public class RemoteMediaController {
    private static final String TAG = "MainActivity";

    private TextView textView;
    private Button nextButton;

    protected RemoteControlService remoteControlService;
    protected boolean mBound = false; //flag indicating if service is bound to Activity

    private Activity activity;
    private Handler handler;

    public RemoteMediaController(Activity activity) {
        this.activity = activity;
    }

    public void onCreate() {
        handler = new Handler();
    }

    public void onStart() {
        Intent intent = new Intent(this.activity, RemoteControlService.class);
        intent.setAction(RemoteControlService.GET_BINDER_ACTION);

        this.activity.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        Log.v(TAG, "onStart");
    }

    public void onStop() {
        if(mBound) {
            remoteControlService.setRemoteControllerDisabled();
        }

        this.activity.unbindService(mConnection);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.v(TAG, "onServiceConnected start");

            RemoteControlService.RCBinder binder = (RemoteControlService.RCBinder) service;

            remoteControlService = binder.getService();
            remoteControlService.setRemoteControllerEnabled();
            remoteControlService.setClientUpdateListener(new OnRemoteUpdateListener());
            mBound = true;

            Log.v(TAG, "onServiceConnected end");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;

            Log.v(TAG, "onServiceDisconnected");
        }
    };

}
