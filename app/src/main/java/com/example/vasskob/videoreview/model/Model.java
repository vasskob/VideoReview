package com.example.vasskob.videoreview.model;

import com.example.vasskob.videoreview.model.data.Video;

import java.util.List;

public interface Model {
    List<Video> getVideos();
    void addAll(List<Video> videos);
}
