package com.example.vasskob.videoreview.model;

import com.example.vasskob.videoreview.model.data.Video;

import java.util.List;

public class ModelImpl implements Model {
    private List<Video> mVideos;

    public ModelImpl() {
        mVideos = null;
    }

    public List<Video> getVideos() {
        return mVideos;
    }

    @Override
    public void addAll(List<Video> videos) {
        mVideos = videos;
    }
}
