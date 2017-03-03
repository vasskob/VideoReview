package com.example.vasskob.videoreview.presenter;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.TextureView;
import android.view.View;

import com.example.vasskob.videoreview.Constants;
import com.example.vasskob.videoreview.factories.MediaPlayerFactory;
import com.example.vasskob.videoreview.model.Media;
import com.example.vasskob.videoreview.model.Video;

import java.util.LinkedList;

public class VideoPresenter implements MediaPresenter, LoaderManager.LoaderCallbacks<Cursor> {

    private FragmentActivity mActivity = null;
    private Callback mCallback = null;
    private static com.example.vasskob.videoreview.player.MediaPlayer mVideoPlayer = null;
    private final TextureView textureView;

    private static final int VIDEO_LOADER_ID = 10;

    public VideoPresenter(FragmentActivity activity, TextureView view) {
        mActivity = activity;
        mVideoPlayer = MediaPlayerFactory.getMediaPlayer(Constants.MEDIA_TYPE_VIDEO, view);
        textureView = view;
    }

    @Override
    public void getMediaItems(Callback callback) {
        mCallback = callback;
        mActivity.getSupportLoaderManager().initLoader(VIDEO_LOADER_ID, null, this);
    }


    @Override
    public void onMediaItemClicked(Media media) {

        LinkedList<Media> video = new LinkedList<>();
        video.add(media);
        mVideoPlayer.playMedia(mActivity, video);
        textureView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }

    public static void onBackPressed() {
        mVideoPlayer.stopMedia();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == VIDEO_LOADER_ID) {
            CursorLoader loader = new CursorLoader(mActivity);
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
            LinkedList<Media> mediaList = new LinkedList<>();

            if (data != null && data.getCount() > 0) {
                int idIndex = data.getColumnIndex(MediaStore.Video.Media._ID);
                int titleIndex = data.getColumnIndex(MediaStore.Video.Media.TITLE);

                int pathIndex = data.getColumnIndex(MediaStore.Video.Media.DATA);

                while (data.moveToNext()) {
                    long id = data.getLong(idIndex);
                    String title = data.getString(titleIndex);
                    String path = data.getString(pathIndex);

                    Video video = new Video(id, title, path);
                    mediaList.add(video);
                }
            }
            if (mCallback != null) {
                mCallback.onItemsAvailable(mediaList);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (loader.getId() == VIDEO_LOADER_ID) {
            if (mCallback != null) {
                mCallback.onItemsAvailable(new LinkedList<Media>());
            }
        }
    }
}

