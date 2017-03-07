package com.example.vasskob.videoreview.view;

import com.example.vasskob.videoreview.model.pojo.MediaItem;


public interface MyMediaPlayer {
    void stop();

    void release();

    void play(MediaItem item);
}