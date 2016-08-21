package com.ewnd9.xavier.android.services;

import com.ewnd9.xavier.android.MainApplication;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ewnd9 on 21.08.16.
 */
public class MyFirebase {

    public static void sendIdToServer(Map<String, String> map) {
        FirebaseMessaging fm = FirebaseMessaging.getInstance();
        AtomicInteger msgId = new AtomicInteger();

        fm.send(new RemoteMessage.Builder(MainApplication.gcmAppId + "@gcm.googleapis.com")
                .setMessageId(Integer.toString(msgId.incrementAndGet()))
                .setData(map)
                .build());
    }
}
