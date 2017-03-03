package com.example.vasskob.videoreview.factories;

import android.view.TextureView;

import com.example.vasskob.videoreview.Constants;
import com.example.vasskob.videoreview.player.MediaPlayer;
import com.example.vasskob.videoreview.player.VideoPlayer;


public class MediaPlayerFactory {

    public static MediaPlayer getMediaPlayer(int type, TextureView textureView) {
        switch (type) {
            case Constants.MEDIA_TYPE_VIDEO:
                return new VideoPlayer(textureView);

            default:
                return null;
        }
    }
}
