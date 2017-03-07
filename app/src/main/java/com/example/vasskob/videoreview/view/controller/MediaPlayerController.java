package com.example.vasskob.videoreview.view.controller;

import android.media.MediaPlayer;
import android.widget.MediaController;

public class MediaPlayerController implements MediaController.MediaPlayerControl, MediaPlayer.OnBufferingUpdateListener {

    private MediaPlayer mMediaPlayer = null;
    private int mBufferPercent = 0;

    public MediaPlayerController(MediaPlayer mediaPlayer) {
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