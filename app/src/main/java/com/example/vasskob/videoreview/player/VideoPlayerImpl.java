package com.example.vasskob.videoreview.player;

import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;

import com.example.vasskob.videoreview.model.data.Video;

import java.io.IOException;

public class VideoPlayerImpl implements VideoPlayer, TextureView.SurfaceTextureListener {

    private static final String TAG = VideoPlayerImpl.class.getSimpleName();

    private TextureView mTextureView = null;
    private Video video = null;
    private MediaPlayer mMediaPlayer = null;
    private boolean mIsPlaying = false;


    public VideoPlayerImpl(TextureView textureView) {
        mTextureView = textureView;
    }

    @Override
    public void stopMedia() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }
    }

    @Override
    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
    }

    @Override
    public void playMedia(final Video video) {
        this.video = video;

        if (mIsPlaying && mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.reset();
        } else {
            mIsPlaying = true;
            if (mMediaPlayer == null) {
                mMediaPlayer = new android.media.MediaPlayer();
            }
            mTextureView.getLayoutParams().height = mTextureView.getWidth();
            mTextureView.setOnClickListener(new View.OnClickListener() {
                boolean click;

                @Override
                public void onClick(View v) {
                    click = !click;
                    if (click) {
                        mMediaPlayer.pause();
                    } else {
                        mMediaPlayer.start();
                    }
                }
            });

        }
        onSurfaceTextureAvailable(mTextureView.getSurfaceTexture(), mTextureView.getWidth(), mTextureView.getHeight());
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        try {
            mMediaPlayer.reset();
            Surface mSurface = new Surface(surface);
            mMediaPlayer.setDataSource(video.getPath());
            mMediaPlayer.setSurface(mSurface);
            mMediaPlayer.setVideoScalingMode(android.media.MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);
            mMediaPlayer.prepare();
            mMediaPlayer.start();

        } catch (IOException e) {
            Log.d(TAG, "IOException there is no file to play");
        }

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
