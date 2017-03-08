package com.example.vasskob.videoreview.player;

import com.example.vasskob.videoreview.model.data.Video;


public interface VideoPlayer {
    void playMedia(Video video);
    void stopMedia();
    void release();
}