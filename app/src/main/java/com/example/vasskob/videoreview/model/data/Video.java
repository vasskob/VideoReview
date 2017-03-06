package com.example.vasskob.videoreview.model.data;


public class Video {
    private final long mId;
    private final String mTitle;
    private final String mPath;


    public Video(long id, String title, String path) {

        mId = id;
        mTitle = title;
        mPath = path;

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


}