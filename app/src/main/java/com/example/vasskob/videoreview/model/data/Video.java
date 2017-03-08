package com.example.vasskob.videoreview.model.data;


public class Video {
    private final long mId;
    private final String mTitle;
    private final String mPath;
    private final int mDuration;


    public Video(long id, String title, String path, int duration) {

        mId = id;
        mTitle = title;
        mPath = path;
        mDuration = duration;

    }


    public long getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPath() {
        return mPath;
    }


    public int getDuration() {
        return mDuration;
    }
}