package com.example.vasskob.videoreview.player;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;

import com.example.vasskob.videoreview.model.data.Video;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.io.IOException;

public class VideoPlayerImpl implements VideoPlayer {

    private final TextureView mTextureView;
    private final MediaPlayer mMediaPlayer;
    private final RangeSeekBar mRangeSeekBar;

    public VideoPlayerImpl(TextureView textureView, RangeSeekBar rangeSeekBar) {
        mMediaPlayer = new MediaPlayer();
        mRangeSeekBar = rangeSeekBar;
        mTextureView = textureView;
        mTextureView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {

                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.pause();
                    } else {
                        mMediaPlayer.start();
                    }

                } catch (Exception e) {
                    Log.d("Exception", "mTextureView.setOnClickListener exception");
                }

            }
        });
    }

    @Override
    public void playMedia(final Video video) throws IOException {

        mRangeSeekBar.setRangeValues(0, video.getDuration() / 1000);
        mMediaPlayer.reset();
        mTextureView.getLayoutParams().height = mTextureView.getWidth();
        mMediaPlayer.setDataSource(video.getPath());
        Surface surface = new Surface(mTextureView.getSurfaceTexture());
        mMediaPlayer.setSurface(surface);
        mMediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);
        mMediaPlayer.prepare();
        mMediaPlayer.start();
    }

    @Override
    public void playMediaInRange(int begin, int end) {
        if (mMediaPlayer != null) {
            mMediaPlayer.seekTo(begin * 1000); // value must be in milliseconds
            mMediaPlayer.start();
            setMediaPlayerCountDown((end - begin) * 1000);
        }
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

    private void setMediaPlayerCountDown(int timeInMs) {
        CountDownTimer mMediaPlayerCountDown = new CountDownTimer(timeInMs, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                }
            }
        };
        mMediaPlayerCountDown.start();
    }
}
