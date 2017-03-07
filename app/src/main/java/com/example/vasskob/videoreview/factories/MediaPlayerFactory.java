package com.example.vasskob.videoreview.factories;

import com.example.vasskob.videoreview.globals.Constants;
import com.example.vasskob.videoreview.ui.holder.UiHolder;
import com.example.vasskob.videoreview.view.MyMediaPlayerImpl;


public class MediaPlayerFactory {

    public static MyMediaPlayerImpl getMediaPlayer(int type, UiHolder uiHolder) {
        switch (type) {
            case Constants.MEDIA_TYPE_VIDEO:
                return new MyMediaPlayerImpl(uiHolder);

            default:
                return null;
        }
    }
}
