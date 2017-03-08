package com.example.vasskob.videoreview.model;

import com.example.vasskob.videoreview.model.data.Video;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
    private List<Video> videos;

    public ModelImpl() {
        this.videos = new ArrayList<>();
    }

    @Override
    public List<Video> getVideoList() {
        return videos;
    }

    @Override
    public void addVideo(Video video) {
        videos.add(video);
    }
}
