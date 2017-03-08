package com.example.vasskob.videoreview.player;

import com.example.vasskob.videoreview.model.data.Video;


public interface MediaPlayer {
    void playMedia(Video video);
    int getVideoDuration();
    void stopMedia();

}