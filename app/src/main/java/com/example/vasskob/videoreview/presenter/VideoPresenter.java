package com.example.vasskob.videoreview.presenter;

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

    private static final int SEC_TO_MILLISECOND = 1000;
    private static final String VIDEO_LENGTH_WARN = "This video is longer than 10s";
    private static final String IS_PLAYING = " is playing";
    private static final String IO_EXCEPTION = "IOException there is no file to play";
    private int selectedSpinner = 100;
    private VideoView mVideoView;
    private VideoPlayer videoPlayer;
    private Model mModel;

    @Override
    public void onVideoItemClicked(Video video) {
        if (selectedSpinner == 1 && video.getDuration() > 10 * SEC_TO_MILLISECOND) {
            mVideoView.showError(VIDEO_LENGTH_WARN);
        } else {
            try {
                mVideoView.getRangeSeekBar().resetSelectedValues();

                if (videoPlayer != null) {
                    videoPlayer.playMedia(video);
                } else {
                    videoPlayer = new VideoPlayerImpl(mVideoView.getTextureView(), mVideoView.getRangeSeekBar(), mVideoView.getFrameLayout());
                    videoPlayer.playMedia(video);
                }
                mVideoView.showInfo(video.getTitle() + IS_PLAYING);
            } catch (IOException e) {
                mVideoView.showError(IO_EXCEPTION);
            }
        }
    }

    @Override
    public void addVideos(List<Video> videos) {
        if (videos.isEmpty())
            mVideoView.showEmptyList();
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
        if (mModel != null) {
            mModel.clearAll();
        }
    }

    @Override
    public void onSpinnerSelected(int selectedSpinner) {
        this.selectedSpinner = selectedSpinner;
        Log.d("Log", "Spinner HAS BEEN CHANGED TO = " + this.selectedSpinner);
    }

    @Override
    public void onRangeSelected(int start, int end) {
        if (videoPlayer != null) {
            videoPlayer.playMediaInRange(start, end);
        }
    }

    @Override
    public void onAttachView(VideoView videoView) {
        mVideoView = videoView;
        videoPlayer = new VideoPlayerImpl(mVideoView.getTextureView(), mVideoView.getRangeSeekBar(), mVideoView.getFrameLayout()); // view
        mModel = new ModelImpl();
    }

    @Override
    public void onDetachView() {
        videoPlayer.release();
        videoPlayer = null;
        mModel = null;
    }
}

