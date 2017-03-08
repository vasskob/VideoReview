package com.example.vasskob.videoreview.player;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.MediaController;

import com.example.vasskob.videoreview.model.data.Video;

import java.io.IOException;

public class VideoPlayer implements MediaPlayer, TextureView.SurfaceTextureListener {

    private static final String TAG = VideoPlayer.class.getSimpleName();
    private Context mContext = null;
    private TextureView mTextureView = null;
    private Video video = null;
    private MediaController mVideoController = null;
    private android.media.MediaPlayer mMediaPlayer = null;
    private boolean mIsPlaying = false;
    private int length;
    private int videoDuration=0;

    public VideoPlayer(TextureView textureView) {
        mTextureView = textureView;

    }


    @Override
    public void stopMedia() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer = null;
        }
    }

    @Override
    public void playMedia(final Video video) {
        this.video = video;

        if (mIsPlaying) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.reset();
        } else {
            mIsPlaying = true;
            mMediaPlayer = new android.media.MediaPlayer();

            final MediaPlayerController mPlayerController = new MediaPlayerController(mMediaPlayer);
//            mVideoController = new MediaController() {
//                @Override
//                public void hide() {
//                    mVideoController.setVisibility(View.GONE);
//                }
//            };
            mTextureView.getLayoutParams().height = mTextureView.getWidth();
            mTextureView.setOnClickListener(new View.OnClickListener() {
                boolean click;

                @Override
                public void onClick(View v) {

                    click = !click;
                    if (click) {
                        mMediaPlayer.pause();
                        length = mMediaPlayer.getCurrentPosition();

                    } else {
                        mMediaPlayer.seekTo(length);
                        mMediaPlayer.start();

                    }
                }
            });

//            mVideoController.setAnchorView(mTextureView);
//            mVideoController.setMediaPlayer(mPlayerController);

        }
        onSurfaceTextureAvailable(mTextureView.getSurfaceTexture(), mTextureView.getWidth(), mTextureView.getHeight());
    }

    @Override
    public int getVideoDuration() {

        return videoDuration;
    }


//    private Uri getUriFromIndex(int index) {
//        return Uri.parse(video.getPath());
//
//    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        try {
            mMediaPlayer.reset();

            Surface mSurface = new Surface(surface);


            mMediaPlayer.setDataSource(video.getPath());
            mMediaPlayer.setSurface(mSurface);
            mMediaPlayer.setVideoScalingMode(android.media.MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);
            //mMediaPlayer.setOnVideoSizeChangedListener(this);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(new android.media.MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(android.media.MediaPlayer mediaPlayer) {

                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                    //  mVideoController.show();
                 //   videoDuration = mediaPlayer.getDuration();

                }
            });

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


    private class MediaPlayerController implements MediaController.MediaPlayerControl, android.media.MediaPlayer.OnBufferingUpdateListener {

        private android.media.MediaPlayer mMediaPlayer = null;
        private int mBufferPercent = 0;

        MediaPlayerController(android.media.MediaPlayer mediaPlayer) {
            mMediaPlayer = mediaPlayer;
            mMediaPlayer.setOnBufferingUpdateListener(this);
        }

        @Override
        public void start() {
            mMediaPlayer.start();
        }

        @Override
        public void pause() {
            mMediaPlayer.pause();
        }

        @Override
        public int getDuration() {
            return mMediaPlayer.getDuration();
        }

        @Override
        public int getCurrentPosition() {
            return mMediaPlayer.getCurrentPosition();
        }

        @Override
        public void seekTo(int pos) {
            mMediaPlayer.seekTo(pos);
        }

        @Override
        public boolean isPlaying() {
            return mMediaPlayer.isPlaying();
        }

        @Override
        public int getBufferPercentage() {
            return mBufferPercent;
        }

        @Override
        public boolean canPause() {
            return true;
        }

        @Override
        public boolean canSeekBackward() {
            return true;
        }

        @Override
        public boolean canSeekForward() {
            return true;
        }

        @Override
        public int getAudioSessionId() {
            return mMediaPlayer.getAudioSessionId();
        }

        @Override
        public void onBufferingUpdate(android.media.MediaPlayer mp, int percent) {
            mBufferPercent = percent;
        }
    }
}
