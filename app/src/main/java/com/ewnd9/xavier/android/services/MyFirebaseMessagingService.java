package com.ewnd9.xavier.android.services;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by ewnd9 on 17.06.16.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "Xavier";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + (remoteMessage.getNotification() == null));
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getData().get("message"));
    }
}
