package com.example.vasskob.videoreview.factories;

import android.support.v4.app.FragmentActivity;
import android.view.TextureView;

import com.example.vasskob.videoreview.Constants;
import com.example.vasskob.videoreview.presenter.MediaPresenter;
import com.example.vasskob.videoreview.presenter.VideoPresenter;


public class MediaPresenterFactory {

    public static MediaPresenter getMediaPresenter(FragmentActivity activity, int type, TextureView view) {
        switch (type) {

            case Constants.MEDIA_TYPE_VIDEO:
                return new VideoPresenter(activity, view);

            default:
                return null;

        }
    }
}