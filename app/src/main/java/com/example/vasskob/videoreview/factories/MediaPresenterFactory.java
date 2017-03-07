package com.example.vasskob.videoreview.factories;

import com.example.vasskob.videoreview.globals.Constants;
import com.example.vasskob.videoreview.presenter.MediaPresenter;
import com.example.vasskob.videoreview.presenter.MediaPresenterImpl;


public class MediaPresenterFactory {

    public static MediaPresenter getMediaPresenter(int type) {
        switch (type) {

            case Constants.MEDIA_TYPE_VIDEO:
                return new MediaPresenterImpl();

            default:
                return null;

        }
    }
}