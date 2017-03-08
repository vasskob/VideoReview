package com.example.vasskob.videoreview.presenter;

import com.example.vasskob.videoreview.view.VideoView;

public interface BasePresenter {
    void onAttachView(VideoView videoView);
    void onDetachView();

}
