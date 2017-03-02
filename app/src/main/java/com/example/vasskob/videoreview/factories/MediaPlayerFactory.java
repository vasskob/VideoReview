package com.example.vasskob.videoreview.factories;

import android.view.View;

import com.example.vasskob.videoreview.Constants;
import com.example.vasskob.videoreview.player.MediaPlayer;
import com.example.vasskob.videoreview.player.VideoPlayer;


public class MediaPlayerFactory {

    public static MediaPlayer getMediaPlayer(int type, View view) {
        switch (type) {
            case Constants.MEDIA_TYPE_VIDEO:
                return new VideoPlayer(view);

            default:
                return null;
        }
    }
}
