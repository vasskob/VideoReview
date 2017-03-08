package com.example.vasskob.videoreview.presenter;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.TextureView;

import com.example.vasskob.videoreview.model.data.Video;
import com.example.vasskob.videoreview.player.VideoPlayer;
import com.example.vasskob.videoreview.view.View;

public class VideoPresenter implements MainPresenter {

    private final TextureView textureView;
    private FragmentActivity mActivity = null;
    private Callback mCallback = null;

    private static final int VIDEO_LOADER_ID = 10;
    private MediaPlayer mediaPlayer;
    private boolean paused = false;
    private int selectedSpinner = 100;
    private com.example.vasskob.videoreview.view.View view;
    private int videoDuration;
    VideoPlayer videoPlayer;

    public VideoPresenter(TextureView textureView) {
        this.textureView = textureView;
        videoPlayer = new VideoPlayer(textureView);
    }

    @Override
    public void onMediaItemClicked(Video video) {
        initMediaPlayer(video);
        if (selectedSpinner == 1 && videoDuration > 10 * 1000) {
            videoPlayer.stopMedia();
            view.showError("This video is longer than 10s");
        }
        Log.d("Log", "playVideo mediaPlayer duration = " + videoDuration + " spinner = " + selectedSpinner);
    }

    private void initMediaPlayer(final Video video) {
//        try {
//            stopVideo();
//            mediaPlayer = new MediaPlayer();
////          mp.setDisplay(((SurfaceView) findViewById(R.id.surfaceView)).getHolder());
////          mediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
//
//            mediaPlayer.setDataSource(video.getPath());
//            mediaPlayer.prepareAsync();
//            mediaPlayer.setLooping(false);
//            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    videoDuration = mp.getDuration();
//                    mp.start();
//                }
//            });
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        videoPlayer.playMedia(video);
       // videoDuration = videoPlayer.getVideoDuration();
    }

    @Override
    public void stopVideo() {
//        if (mediaPlayer != null) {
//            mediaPlayer.stop();
//            mediaPlayer = null;
//        }
        videoPlayer.stopMedia();
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
    public void onSpinnerSelected(int selectedSpinner) {
        this.selectedSpinner = selectedSpinner;
        Log.d("Log", "Spinner HAS BEEN CHANGED TO = " + this.selectedSpinner);
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
    public void onAttachView(View view) {
        this.view = view;
    }

    @Override
    public void onDetachView() {
        view = null;
    }


}

