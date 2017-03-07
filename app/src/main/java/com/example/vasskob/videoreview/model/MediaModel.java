package com.example.vasskob.videoreview.model;

import com.example.vasskob.videoreview.model.pojo.MediaItem;

import java.util.List;

public interface MediaModel {

    List<MediaItem> getAllItems();

    void removeAllItems();

    MediaItem getById(int id);

    int findItemIdx(MediaItem item);

    void add(MediaItem item);

    MediaItem nextItem();

    MediaItem prevItem();

    MediaItem getCurrentItem();

    void setCurrent(MediaItem current);

    MediaItem firstItem();
}