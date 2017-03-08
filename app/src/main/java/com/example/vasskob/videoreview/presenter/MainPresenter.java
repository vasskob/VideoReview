package com.example.vasskob.videoreview.presenter;

import com.example.vasskob.videoreview.model.data.Video;

import java.util.List;

public interface MainPresenter extends  BasePresenter {

    void onVideoItemClicked(Video video);
    void onSpinnerSelected(int item);
    void onRangeSelected(int start,int end);
    void onVideoViewClicked();
    void addVideos(List<Video> videos);
    List<Video> getVideos();
    void clearVideos();
}