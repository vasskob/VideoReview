package com.example.vasskob.videoreview.presenter;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;

import com.example.vasskob.videoreview.model.data.Video;
import com.example.vasskob.videoreview.view.View;

import java.io.IOException;

public class VideoPresenter implements MainPresenter {

    private FragmentActivity mActivity = null;
    private Callback mCallback = null;

    private static final int VIDEO_LOADER_ID = 10;
    private MediaPlayer mediaPlayer;
    private boolean paused = false;
    private String selectedSpinner = "All videos";
    private com.example.vasskob.videoreview.view.View view;

//    public VideoPresenter(FragmentActivity fragmentActivity) {
//        this.mActivity = fragmentActivity;
//    }


    @Override
    public void getMediaItems(Callback callback) {
        mCallback = callback;
      //  mActivity.getSupportLoaderManager().initLoader(VIDEO_LOADER_ID, null, this);
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

//          mp.setDisplay(((SurfaceView) findViewById(R.id.surfaceView)).getHolder());
//          mediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);

            mediaPlayer.setDataSource(video.getPath());
            mediaPlayer.prepareAsync();
            mediaPlayer.setLooping(true);
            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
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

