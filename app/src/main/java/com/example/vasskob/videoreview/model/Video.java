package com.example.vasskob.videoreview.model;

import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.Button;


public class Video implements Media {
    private final long mId;
    private final String mTitle;
//  private final String mArtist;
    private final String mPath;
//    private final Bitmap mThumbnail;

    public Video(long id, String title, String path) {
//      String artist){
//                 Bitmap thumbnail) {
        mId = id;
        mTitle = title;
        //   mArtist = artist;
        mPath = path;
//        mThumbnail = thumbnail;
    }

    @Override
    public long getId() {
        return mId;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

//    @Override
//    public String getArtist() {
//        return mArtist;
//    }

    @Override
    public String getPath() {
        return mPath;
    }

//    @Override
//    public Bitmap getThumbnail() {
//        return mThumbnail;
//    }
}