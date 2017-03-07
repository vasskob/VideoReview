package com.example.vasskob.videoreview.presenter;

import com.example.vasskob.videoreview.model.pojo.MediaItem;
import com.example.vasskob.videoreview.ui.holder.UiHolder;

import java.util.List;

/**
 * Created by vchepeli on 08/03/2017.
 */

public interface MediaPresenter {
    void onLoadMediaItems(Callback callback);

    void onBackPressed();

    void onPlay(MediaItem item);

    void onPlay();

    void onPlayNext();

    void onPlayPrev();

    void onAttachView(UiHolder uiHolder);

    void onDetachView();

    interface Callback {
        void onItemsAvailable(List<MediaItem> items);
    }
}
