package com.ewnd9.xavier.android.remote_media;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.RemoteControlClient;
import android.media.RemoteController;
import android.util.Log;

import com.ewnd9.xavier.android.R;

/**
 * Created by ewnd9 on 17.06.16.
 */
public class OnRemoteUpdateListener implements RemoteController.OnClientUpdateListener {

    private static final String TAG = "OnRemoteUpdateListener";

    private boolean mScrubbingSupported = false; //is scrubbing supported?

    private boolean isScrubbingSupported(int flags) {
        //if "flags" bitmask contains certain bits it means that srcubbing is supported
        return (flags & RemoteControlClient.FLAG_KEY_MEDIA_POSITION_UPDATE) != 0;
    }

    @Override
    public void onClientTransportControlUpdate(int transportControlFlags) {
        mScrubbingSupported = isScrubbingSupported(transportControlFlags);

        Log.v(TAG, "onClientTransportControlUpdate " + mScrubbingSupported);

        if(mScrubbingSupported) {
            //if scrubbing is supported, we shoul let user use scrubbing SeekBar
//                mScrubBar.setEnabled(true);
            //start SeekBar updater
//                mHandler.post(mUpdateSeekBar);
        } else {
            //disabling SeekBar and SeekBar updater
//                mScrubBar.setEnabled(false);
//                mHandler.removeCallbacks(mUpdateSeekBar);
        }
    }

    @Override
    public void onClientPlaybackStateUpdate(int state, long stateChangeTimeMs, long currentPosMs, float speed) {
        switch(state) {
            case RemoteControlClient.PLAYSTATE_PLAYING:
                //if music started playing, we should start updating our SeekBar position
                //also, update the play/pause icon
//                    if(mScrubbingSupported) mHandler.post(mUpdateSeekBar);
//                    mIsPlaying = true;
//                    mPlayPauseButton.setImageResource(android.R.drawable.ic_media_pause);
                break;
            default:
                //if music isn't playing, we should stop updating of SeekBar
//                    mHandler.removeCallbacks(mUpdateSeekBar);
//                    mIsPlaying = false;
//                    mPlayPauseButton.setImageResource(android.R.drawable.ic_media_play);
                break;
        }

//            mScrubBar.setProgress((int) (currentPosMs * mScrubBar.getMax() / mSongDuration));
    }

    @Override
    public void onClientPlaybackStateUpdate(int state) {
        switch(state) {
            case RemoteControlClient.PLAYSTATE_PLAYING:
//                    if(mScrubbingSupported) mHandler.post(mUpdateSeekBar);
//                    mIsPlaying = true;
//                    mPlayPauseButton.setImageResource(android.R.drawable.ic_media_pause);
                break;
            default:
//                    mHandler.removeCallbacks(mUpdateSeekBar);
//                    mIsPlaying = false;
//                    mPlayPauseButton.setImageResource(android.R.drawable.ic_media_play);
                break;
        }
    }

    @Override
    public void onClientMetadataUpdate(RemoteController.MetadataEditor editor) {
        String artist = editor.getString(MediaMetadataRetriever.METADATA_KEY_ARTIST,
                editor.getString(MediaMetadataRetriever.METADATA_KEY_ALBUMARTIST, "undefined")
        );

        String title = editor.getString(MediaMetadataRetriever.METADATA_KEY_TITLE, "undefined");
        String album = editor.getString(MediaMetadataRetriever.METADATA_KEY_ALBUM, "undefined");

        long duration = editor.getLong(MediaMetadataRetriever.METADATA_KEY_DURATION, 1);
        Bitmap bitmap = editor.getBitmap(RemoteControlClient.MetadataEditor.BITMAP_KEY_ARTWORK, null);

        Log.v(TAG, "onClientMetadataUpdate " + artist + " " + title + " " + album);
    }

    @Override
    public void onClientChange(boolean clearing) {

    }
}
