package com.example.vasskob.videoreview.view;


import android.view.TextureView;

import com.example.vasskob.videoreview.model.data.Video;

import java.util.List;

public interface VideoView {

    void showData(List<Video> videoList);

    void showError(String error);

    void showEmptyList();
    
    void showInfo(String s);

    TextureView getTextureView();
}
