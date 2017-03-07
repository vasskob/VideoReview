package com.example.vasskob.videoreview.model.pojo;


public class MediaItem {
    private final int mId;
    private final String mPath;

    public MediaItem(int id, String path) {
        mId = id;
        mPath = path;
    }

    public int getId() {
        return mId;
    }

    public String getPath() {
        return mPath;
    }
}