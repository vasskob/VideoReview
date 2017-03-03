package com.example.vasskob.videoreview.player;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.net.Uri;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.MediaController;

import com.example.vasskob.videoreview.model.Media;

import java.io.IOException;
import java.util.List;

public class VideoPlayer implements MediaPlayer, TextureView.SurfaceTextureListener {

    private static final String TAG = VideoPlayer.class.getSimpleName();
    private Context mContext = null;
    private TextureView mTextureView = null;
    private List<Media> mMediaList = null;
    private MediaController mVideoController = null;
    private android.media.MediaPlayer mMediaPlayer = null;
    private boolean mIsPlaying = false;

    public VideoPlayer(TextureView textureView) {
        mTextureView = textureView;
    }

    @Override
    public void stopMedia() {
        if (mMediaPlayer!=null){
            mMediaPlayer.stop();
            mMediaPlayer=null;
        }
    }

    @Override
    public void playMedia(Context context, final List<Media> mediaList) {
//        adjustAspectRatio(mediaList.get(,150);
        mContext = context;
        mMediaList = mediaList;

        if (mIsPlaying) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.reset();
        } else {
            mIsPlaying = true;
            mMediaPlayer = new android.media.MediaPlayer();

            MediaPlayerController mPlayerController = new MediaPlayerController(mMediaPlayer);
            mVideoController = new MediaController(mContext) {
                @Override
                public void hide() {
                    mVideoController.setVisibility(View.GONE);
                }
            };
            mTextureView.getLayoutParams().height = mTextureView.getWidth();
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
        onSurfaceTextureAvailable(mTextureView.getSurfaceTexture(), mTextureView.getWidth(), mTextureView.getHeight());
    }

    private Uri getUriFromIndex(int index) {
        return Uri.parse(mMediaList.get(index).getPath());

    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        try {
            mMediaPlayer.reset();

            Surface mSurface = new Surface(surface);

            int mIndexToBePlayed = 0;
            mMediaPlayer.setDataSource(mContext, getUriFromIndex(mIndexToBePlayed));
            mMediaPlayer.setSurface(mSurface);
            //mMediaPlayer.setOnVideoSizeChangedListener(this);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(new android.media.MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(android.media.MediaPlayer mediaPlayer) {

                    mediaPlayer.setLooping(true);
                    mediaPlayer.seekTo(0);
                    mediaPlayer.setVideoScalingMode(android.media.MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                    mediaPlayer.start();
                    mVideoController.show();

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
