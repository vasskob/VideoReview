package com.example.vasskob.videoreview.presenter;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.TextureView;

import com.example.vasskob.videoreview.model.Model;
import com.example.vasskob.videoreview.model.ModelImpl;
import com.example.vasskob.videoreview.model.data.Video;
import com.example.vasskob.videoreview.player.VideoPlayer;
import com.example.vasskob.videoreview.view.View;

import java.util.List;

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
    private final Model modelodel;

    public VideoPresenter(TextureView textureView) {
        this.textureView = textureView;
        videoPlayer = new VideoPlayer(textureView);
        modelodel = new ModelImpl();
    }

    @Override
    public void onMediaItemClicked(Video video) {

        if (selectedSpinner == 1 && video.getDuration() > 10 * 1000) {
            view.showError("This video is longer than 10s");
        } else {
            videoPlayer.playMedia(video);
            view.showInfo(video.getTitle() + " is playing");
        }

    }

    @Override
    public void stopVideo() {
        videoPlayer.stopMedia();
    }

    @Override
    public void add(Video video) {
        modelodel.addVideo(video);
    }

    @Override
    public List<Video> getVideos() {
        return modelodel.getVideoList();
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

