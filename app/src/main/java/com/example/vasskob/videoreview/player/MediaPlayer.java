package com.example.vasskob.videoreview.player;

import android.content.Context;

import com.example.vasskob.videoreview.model.Media;

import java.util.List;


public interface MediaPlayer {
    void playMedia(Context context, List<Media>media);
}