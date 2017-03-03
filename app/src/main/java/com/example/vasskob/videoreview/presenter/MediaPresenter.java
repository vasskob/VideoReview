package com.example.vasskob.videoreview.presenter;

import com.example.vasskob.videoreview.model.Media;

import java.util.List;

public interface MediaPresenter {


    void getMediaItems(Callback callback);
    void onMediaItemClicked(Media media);

    interface Callback{
        void onItemsAvailable(List<Media> items);
    }
}