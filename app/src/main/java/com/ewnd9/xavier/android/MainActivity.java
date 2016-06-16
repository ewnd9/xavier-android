package com.ewnd9.xavier.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.concurrent.atomic.AtomicInteger;


public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private static final String SENDER_ID = "223613726281";
    private RemoteMediaController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new RemoteMediaController(this);
        controller.onCreate();

//        FirebaseMessaging fm = FirebaseMessaging.getInstance();
//        AtomicInteger msgId = new AtomicInteger();
//
//        fm.send(new RemoteMessage.Builder(SENDER_ID + "@gcm.googleapis.com")
//                .setMessageId(Integer.toString(msgId.incrementAndGet()))
//                .addData("my_message", "Hello World")
//                .addData("my_action","SAY_HELLO")
//                .build());
    }

    @Override
    public void onStart() {
        super.onStart();
        controller.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        controller.onStop();
    }
}
