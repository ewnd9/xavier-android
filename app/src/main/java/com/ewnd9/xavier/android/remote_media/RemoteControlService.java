package com.ewnd9.xavier.android.remote_media;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.session.MediaSessionManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.ewnd9.xavier.android.services.MyFirebase;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by ewnd9 on 17.06.16.
 */
@TargetApi(21)
public class RemoteControlService extends NotificationListenerService {

    private static final String TAG = "RemoteControlService";

    public static final String GET_BINDER_ACTION = "GET_BINDER_ACTION";

    private IBinder mBinder = new RCBinder();
    private Context mContext;

    private Handler handler = new Handler();
    private RemoteControl rc;

    public static final String INTENT_COMMAND = "INTENT_COMMAND";
    public static final String EXTRA_COMMAND = "EXTRA_COMMAND";

    public static final String EXTRA_MESSAGE_ID = "EXTRA_MESSAGE_ID";

    public static final String EXTRA_NEXT_TRACK = "EXTRA_NEXT_TRACK";
    public static final String EXTRA_PREVIOUS_TRACK = "EXTRA_PREVIOUS_TRACK";
    public static final String EXTRA_PLAY_PAUSE = "EXTRA_PLAY_PAUSE";
    public static final String EXTRA_NOW_PLAYING = "EXTRA_NOW_PLAYING";

    private IntentFilter intentFilter = new IntentFilter(INTENT_COMMAND);
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (rc == null) {
                return;
            }

            String command = intent.getStringExtra(EXTRA_COMMAND);
            String messageId = intent.getStringExtra(EXTRA_MESSAGE_ID);

            if (EXTRA_NEXT_TRACK.equals(command)) {
                rc.playNextTrack();
            } else if (EXTRA_PREVIOUS_TRACK.equals(command)) {
                rc.playPreviousTrack();
            } else if (EXTRA_PLAY_PAUSE.equals(command)) {
                rc.playPauseTrack();
            } else if (EXTRA_NOW_PLAYING.equals(command)) {
                Map<String, String> args = new HashMap<>();

                args.put("now_playing", rc.getNowPlaying());
                args.put("messageId", messageId);

                MyFirebase.sendIdToServer(args);
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        Log.v(TAG, "onBind " + intent.getAction());

        if(intent.getAction().equals(GET_BINDER_ACTION)) {
            return mBinder;
        } else {
            return super.onBind(intent);
        }
    }

    @Override
    public void onNotificationPosted(StatusBarNotification notification) {
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification notification) {
    }

    @Override
    public void onCreate() {
        mContext = getApplicationContext();
        Log.v(TAG, "onCreate");

        MediaSessionManager manager = (MediaSessionManager) mContext.getSystemService(Context.MEDIA_SESSION_SERVICE);
        ComponentName cmp = new ComponentName(this, getClass());

        rc = new RemoteControl(manager, handler, cmp);
        registerReceiver(myReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        rc.unregisterAll();
        unregisterReceiver(myReceiver);
    }

    public class RCBinder extends Binder {
        public RemoteControlService getService() {
            return RemoteControlService.this;
        }
    }

}
