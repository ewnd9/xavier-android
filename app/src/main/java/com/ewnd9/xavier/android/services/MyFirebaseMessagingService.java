package com.ewnd9.xavier.android.services;

import android.content.Intent;

import com.ewnd9.xavier.android.remote_media.RemoteControlService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by ewnd9 on 17.06.16.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "Xavier";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String id = remoteMessage.getData().get("id");
        String command = remoteMessage.getData().get("command");

        Intent intent = new Intent(RemoteControlService.INTENT_COMMAND);

        intent.putExtra(RemoteControlService.EXTRA_COMMAND, command);
        intent.putExtra(RemoteControlService.EXTRA_ID, id);

        getApplicationContext().sendBroadcast(intent);
    }
}
