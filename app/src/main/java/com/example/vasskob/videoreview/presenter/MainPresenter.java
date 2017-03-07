package com.example.vasskob.videoreview.presenter;

import com.example.vasskob.videoreview.model.data.Video;

import java.util.List;

public interface MainPresenter extends  BasePresenter {


    void getMediaItems(Callback callback);
    void onMediaItemClicked(Video video);
    void onSpinnerSelected(String item);
    void onRangeSelected(int start,int end);
    void onVideoViewClicked();
    void stopVideo();

    interface Callback{
        void onItemsAvailable(List<Video> items);
    }
}