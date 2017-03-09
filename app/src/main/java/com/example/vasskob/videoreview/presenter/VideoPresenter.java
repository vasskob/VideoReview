package com.example.vasskob.videoreview.presenter;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;

import com.example.vasskob.videoreview.model.Model;
import com.example.vasskob.videoreview.model.ModelImpl;
import com.example.vasskob.videoreview.model.data.Video;
import com.example.vasskob.videoreview.player.VideoPlayer;
import com.example.vasskob.videoreview.player.VideoPlayerImpl;
import com.example.vasskob.videoreview.view.VideoView;

import java.io.IOException;
import java.util.List;

public class VideoPresenter implements MainPresenter {

    private MediaPlayer mediaPlayer;
    private boolean paused = false;
    private int selectedSpinner = 100;
    private VideoView mVideoview;
    private VideoPlayer videoPlayer;
    private Model mModel;

    @Override
    public void onVideoItemClicked(Video video) {
        if (selectedSpinner == 1 && video.getDuration() > 10 * 1000) {
            mVideoview.showError("This video is longer than 10s");
        } else {
            try {
                videoPlayer.playMedia(video);
                mVideoview.showInfo(video.getTitle() + " is playing");
            } catch (IOException e) {
                mVideoview.showError("IOException there is no file to play");
            }
        }
    }

    @Override
    public void addVideos(List<Video> videos) {
        if (videos.isEmpty())
            mVideoview.showEmptyList();
        else {
            mModel.addAll(videos);
        }
    }

    @Override
    public List<Video> getVideos() {
        return mModel.getVideos();
    }

    @Override
    public void clearVideos() {
        mModel.clearAll();
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
    public void onAttachView(VideoView videoView) {
        mVideoview = videoView;
        videoPlayer = new VideoPlayerImpl(mVideoview.getTextureView()); // view
        mModel = new ModelImpl(); // model
    }

    @Override
    public void onDetachView() {
        videoPlayer.release();
        videoPlayer = null;
        mModel = null;
    }
}

