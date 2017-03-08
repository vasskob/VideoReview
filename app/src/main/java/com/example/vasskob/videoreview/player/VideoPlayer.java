package com.example.vasskob.videoreview.player;

import com.example.vasskob.videoreview.model.data.Video;

import java.io.IOException;


public interface VideoPlayer {
    void playMedia(Video video) throws IOException;
    void stopMedia();
    void release();
}