package com.example.vasskob.videoreview.model;


public class Video implements Media {
    private final long mId;
    private final String mTitle;
    private final String mPath;


    public Video(long id, String title, String path) {

        mId = id;
        mTitle = title;
        mPath = path;

    }

    @Override
    public long getId() {
        return mId;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }


    @Override
    public String getPath() {
        return mPath;
    }


}