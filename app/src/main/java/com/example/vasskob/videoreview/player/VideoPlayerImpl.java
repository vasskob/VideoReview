package com.example.vasskob.videoreview.player;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;

import com.example.vasskob.videoreview.model.data.Video;

import java.io.IOException;

public class VideoPlayerImpl implements VideoPlayer {

    private static final String TAG = VideoPlayerImpl.class.getSimpleName();

    private final TextureView mTextureView;
    private final MediaPlayer mMediaPlayer;

    public VideoPlayerImpl(TextureView textureView) {
        mMediaPlayer = new MediaPlayer();

        mTextureView = textureView;
        mTextureView.setOnClickListener(new View.OnClickListener() {
            boolean click;

            @Override
            public void onClick(View v) {
               try {
                   click = !click;
                   if (click) {
                       mMediaPlayer.pause();
                   } else {
                       mMediaPlayer.start();
                   }

               }catch (Exception e){
                   Log.d("Exception", "mTextureView.setOnClickListener exception");
               }

            }
        });
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
    public void playMedia(final Video video) throws IOException {
        mMediaPlayer.reset();
        mTextureView.getLayoutParams().height = mTextureView.getWidth();
        mMediaPlayer.setDataSource(video.getPath());

        Surface surface = new Surface(mTextureView.getSurfaceTexture());
        mMediaPlayer.setSurface(surface);
        mMediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);
        mMediaPlayer.prepare();
        mMediaPlayer.start();
    }
}
