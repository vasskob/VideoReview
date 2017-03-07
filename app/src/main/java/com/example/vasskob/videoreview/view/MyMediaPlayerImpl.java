package com.example.vasskob.videoreview.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.MediaController;

import com.example.vasskob.videoreview.model.pojo.MediaItem;
import com.example.vasskob.videoreview.ui.holder.UiHolder;
import com.example.vasskob.videoreview.view.controller.MediaPlayerController;

import java.io.IOException;

public class MyMediaPlayerImpl implements MyMediaPlayer {

    private static final String TAG = MyMediaPlayerImpl.class.getSimpleName();
    private final MediaController mVideoController;
    private final Context mContext;
    private final TextureView mTextureView;
    private MediaPlayer mMediaPlayer;
    private MediaPlayerController mPlayerController;

    public MyMediaPlayerImpl(UiHolder uiHolder) {
        mTextureView = uiHolder.getTextureView();
        mContext = uiHolder.getActivity();

        mMediaPlayer = new android.media.MediaPlayer();
        mPlayerController = new MediaPlayerController(mMediaPlayer);
        mVideoController = new MediaController(mContext) {
            @Override
            public void hide() {
                mVideoController.setVisibility(View.GONE);
            }
        };
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {

                mediaPlayer.setLooping(true);
                mediaPlayer.seekTo(0);
                mediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                mediaPlayer.start();
                mVideoController.show();
            }
        });

        mTextureView.setOnClickListener(new View.OnClickListener() {
            boolean click;

            @Override
            public void onClick(View v) {

                click = !click;
                if (click) {
                    mVideoController.setVisibility(View.VISIBLE);
                } else {
                    mVideoController.setVisibility(View.GONE);
                }
            }
        });

        mVideoController.setAnchorView(mTextureView);
        mVideoController.setMediaPlayer(mPlayerController);
    }

    @Override
    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer = null;
        }
    }

    @Override
    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public void play(MediaItem item) {
        mTextureView.getLayoutParams().height = mTextureView.getWidth();
        try {
            mMediaPlayer.reset();
            Surface mSurface = new Surface(mTextureView.getSurfaceTexture());
            mMediaPlayer.setDataSource(item.getPath());
            mMediaPlayer.setSurface(mSurface);
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            Log.d(TAG, "IOException there is no file to onPlay");
        }
    }
}
