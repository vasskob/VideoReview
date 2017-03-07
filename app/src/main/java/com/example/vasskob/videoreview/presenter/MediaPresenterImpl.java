package com.example.vasskob.videoreview.presenter;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;

import com.example.vasskob.videoreview.factories.MediaPlayerFactory;
import com.example.vasskob.videoreview.globals.Constants;
import com.example.vasskob.videoreview.model.MediaModel;
import com.example.vasskob.videoreview.model.MediaModelImpl;
import com.example.vasskob.videoreview.model.pojo.MediaItem;
import com.example.vasskob.videoreview.ui.holder.UiHolder;
import com.example.vasskob.videoreview.view.MyMediaPlayer;

public class MediaPresenterImpl implements LoaderManager.LoaderCallbacks<Cursor>, MediaPresenter {

    private static final int VIDEO_LOADER_ID = 10;
    private static MyMediaPlayer mVideoView; // view
    private UiHolder mUiHolder; // holder for ui elements
    private Callback mCallback = null;
    private MediaModel mVideoModel; // model

    @Override
    public void onLoadMediaItems(Callback callback) {
        mCallback = callback;
        mUiHolder.getActivity().getSupportLoaderManager().initLoader(VIDEO_LOADER_ID, null, this);
    }

    @Override
    public void onAttachView(UiHolder uiHolder) {
        mUiHolder = uiHolder;
        mVideoView = MediaPlayerFactory.getMediaPlayer(Constants.MEDIA_TYPE_VIDEO, mUiHolder);
        mVideoModel = new MediaModelImpl();
    }

    @Override
    public void onDetachView() {
        mVideoView.release();
        mVideoModel.removeAllItems();
    }

    @Override
    public void onBackPressed() {
        mVideoView.stop();
    }

    @Override
    public void onPlay(MediaItem item) {
        mVideoModel.setCurrent(item);
        mVideoView.play(item);
        mUiHolder.getTextureView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }

    @Override
    public void onPlay() {
        MediaItem item = mVideoModel.getCurrentItem();
        onPlay(item);
    }

    @Override
    public void onPlayNext() {
        MediaItem item = mVideoModel.nextItem();
        onPlay(item);
    }

    @Override
    public void onPlayPrev() {
        MediaItem item = mVideoModel.prevItem();
        onPlay(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == VIDEO_LOADER_ID) {
            CursorLoader loader = new CursorLoader(mUiHolder.getActivity());
            loader.setUri(MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            loader.setProjection(null);
            loader.setSelection(null);
            loader.setSelectionArgs(null);
            loader.setSortOrder(MediaStore.Video.Media.DATE_ADDED + " DESC");
            return loader;
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == VIDEO_LOADER_ID) {
            if (data != null && data.getCount() > 0) {
                int idIndex = data.getColumnIndex(MediaStore.Video.Media._ID);
                int pathIndex = data.getColumnIndex(MediaStore.Video.Media.DATA);

                while (data.moveToNext()) {
                    int id = data.getInt(idIndex);
                    String path = data.getString(pathIndex);

                    MediaItem video = new MediaItem(id, path);
                    mVideoModel.add(video);
                }
                mVideoModel.setCurrent(mVideoModel.firstItem());
            }
            if (mCallback != null) {
                mCallback.onItemsAvailable(mVideoModel.getAllItems());
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (loader.getId() == VIDEO_LOADER_ID) {
            if (mCallback != null) {
                mVideoModel.removeAllItems();
                mCallback.onItemsAvailable(mVideoModel.getAllItems());
            }
        }
    }

}

