package com.example.vasskob.videoreview.model;

import com.example.vasskob.videoreview.model.pojo.MediaItem;

import java.util.ArrayList;
import java.util.List;

public class MediaModelImpl implements MediaModel {

    private List<MediaItem> mItems;
    private MediaItem mCurrent;

    public MediaModelImpl() {
        mItems = new ArrayList<>();
        mCurrent = null; // no current yet
    }

    @Override
    public List<MediaItem> getAllItems() {
        return mItems;
    }

    @Override
    public void removeAllItems() {
        mItems.clear();

    }

    @Override
    public MediaItem getById(int id) {
        return mItems.get(id);
    }

    @Override
    public int findItemIdx(MediaItem item) {
        return mItems.indexOf(item);
    }

    @Override
    public void add(MediaItem item) {
        if (!mItems.contains(item))
            mItems.add(item);
    }

    @Override
    public MediaItem nextItem() {
        int pos = findItemIdx(mCurrent);
        if (--pos >= 0) {
            mCurrent = mItems.get(pos);
        }
        return mCurrent;
    }

    @Override
    public MediaItem prevItem() {
        int pos = findItemIdx(mCurrent);
        if (++pos <= mItems.size() - 1) {
            mCurrent = mItems.get(pos);
        }
        return mCurrent;
    }

    @Override
    public MediaItem getCurrentItem() {
        return mCurrent;
    }

    @Override
    public void setCurrent(MediaItem mCurrent) {
        this.mCurrent = mCurrent;
    }

    @Override
    public MediaItem firstItem() {
        return mItems.get(0);
    }
}
