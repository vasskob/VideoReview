package com.example.vasskob.videoreview.presenter;

import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.example.vasskob.videoreview.model.data.Video;

import java.io.IOException;
import java.util.LinkedList;

public class VideoPresenter implements Presenter, LoaderManager.LoaderCallbacks<Cursor> {

    private FragmentActivity mActivity = null;
    private Callback mCallback = null;

    private static final int VIDEO_LOADER_ID = 10;
    private MediaPlayer mediaPlayer;
    private boolean paused = false;
    private String selectedSpinner = "All videos";
    private com.example.vasskob.videoreview.view.View view;

    public VideoPresenter(com.example.vasskob.videoreview.view.View view) {
        this.view = view;
    }

    @Override
    public void getMediaItems(Callback callback) {
        mCallback = callback;
    //    view.showData(callback);
        // view.getSupportLoaderManager().initLoader(VIDEO_LOADER_ID, null, this);
    }

    @Override
    public void onMediaItemClicked(Video video) {
        playVideo(video);
    }

    private void setMediaPlayerCountDown(int timeInMs) {
        CountDownTimer mMediaPlayerCountDown = new CountDownTimer(timeInMs, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
            }
        };
        mMediaPlayerCountDown.start();
    }

    @Override
    public void onSpinnerSelected(String selectedSpinner) {
        this.selectedSpinner = selectedSpinner;
    }

    @Override
    public void onRangeSelected(int start, int end) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(start);
            mediaPlayer.start();
            setMediaPlayerCountDown((end - start) * mediaPlayer.getDuration() / 100);
        }
    }

    @Override
    public void onVideoViewClicked() {
        if (mediaPlayer != null) {
            if (!paused) {
                mediaPlayer.pause();
                paused = !paused;
            } else {
                mediaPlayer.release();
            }
        }
    }

    @Override
    public void stopVideo() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }

    private void playVideo(Video video) {

        if (selectedSpinner.equals("All videos")) {
            initMediaPlayer(video);
            mediaPlayer.start();
        } else {
            initMediaPlayer(video);
            if (mediaPlayer.getDuration() > 10 * 1000) {
                view.showError("This video is longer than 10s");
            } else {
                mediaPlayer.start();
            }
        }
    }

    private void initMediaPlayer(Video video) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        try {
            mediaPlayer.setDataSource(video.getPath());
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Loader
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
            LinkedList<Video> mediaList = new LinkedList<>();

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
                mCallback.onItemsAvailable(new LinkedList<Video>());
            }
        }
    }
}

