package com.ewnd9.xavier.android.remote_media;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.media.session.PlaybackState;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ewnd9 on 21.08.16.
 */
@TargetApi(21)
public class RemoteControl {

    private static final String TAG = "RemoteControl";

    private List<MediaController> mControllers = new ArrayList<>();

    private MediaSessionManager manager;
    private Handler handler;
    private ComponentName cmp;

    private String nowPlaying = "no data";

    public RemoteControl(MediaSessionManager manager, Handler handler, ComponentName cmp) {
        this.manager = manager;
        this.handler = handler;
        this.cmp = cmp;

        updateSessions(manager.getActiveSessions(cmp));
        manager.addOnActiveSessionsChangedListener(new MediaSessionManager.OnActiveSessionsChangedListener() {
            @Override
            public void onActiveSessionsChanged(List<MediaController> controllers) {
                updateSessions(controllers);
            }
        }, cmp, handler);
    }

    public void unregisterAll() {
        for (MediaController m : mControllers) {
            m.unregisterCallback(cb);
        }
    }

    public String getNowPlaying() {
        return nowPlaying;
    }

    public void playNextTrack() {
        for (MediaController m : mControllers) {
            sendKeyEvent(m, KeyEvent.KEYCODE_MEDIA_NEXT);
        }
    }

    public void playPreviousTrack() {
        for (MediaController m : mControllers) {
            sendKeyEvent(m, KeyEvent.KEYCODE_MEDIA_PREVIOUS);
        }
    }

    public void playPauseTrack() {
        for (MediaController m : mControllers) {
            sendKeyEvent(m, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE);
        }
    }

    private boolean sendKeyEvent(MediaController c, int keyCode) {
        KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, keyCode);
        boolean first = c.dispatchMediaButtonEvent(keyEvent);

        keyEvent = new KeyEvent(KeyEvent.ACTION_UP, keyCode);
        boolean second = c.dispatchMediaButtonEvent(keyEvent);

        return first && second;
    }

    private void updateSessions(List<MediaController> controllers) {
        for (MediaController c : controllers) {
            c.unregisterCallback(cb);
        }

        mControllers = new ArrayList<>(controllers);

        for (MediaController c : controllers) {
            c.registerCallback(cb);

            cb.onMetadataChanged(c.getMetadata());
            cb.onPlaybackStateChanged(c.getPlaybackState());
        }
    }

    private MediaController.Callback cb = new MediaController.Callback() {
        public void onPlaybackStateChanged(@NonNull PlaybackState state) {
            Log.v(TAG, "onPlaybackStateChanged " + (state.getState() == PlaybackState.STATE_PLAYING));

        }
        public void onMetadataChanged(@Nullable MediaMetadata metadata) {
            String artist = metadata.getString(MediaMetadata.METADATA_KEY_ARTIST);

            String title = metadata.getString(MediaMetadata.METADATA_KEY_TITLE);
            String album = metadata.getString(MediaMetadata.METADATA_KEY_ALBUM);

//            long duration = metadata.getLong(MediaMetadata.METADATA_KEY_DURATION);
//            Bitmap bitmap = metadata.getBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART);

            nowPlaying = artist + " - " + title + " (album: " + album + ")";
            Log.v(TAG, "onMetadataChanged " + nowPlaying);
        }
        public void onSessionDestroyed() {
            Log.v(TAG, "destroyed");
        }
    };

}
