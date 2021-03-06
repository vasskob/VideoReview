package com.example.vasskob.videoreview.player;

import android.graphics.Matrix;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;

import com.example.vasskob.videoreview.model.data.Video;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.io.IOException;

public class VideoPlayerImpl implements VideoPlayer {

    private final TextureView mTextureView;
    private final MediaPlayer mMediaPlayer;
    private final RangeSeekBar mRangeSeekBar;
    private final FrameLayout mFrameLayout;
    float videoHeight;
    float videoWidth;
    int videoOrientation;
    private static final String TAG = VideoPlayerImpl.class.getName();

    public VideoPlayerImpl(TextureView textureView, RangeSeekBar rangeSeekBar, FrameLayout frameLayout) {
        mMediaPlayer = new MediaPlayer();
        mRangeSeekBar = rangeSeekBar;
        mTextureView = textureView;
        mFrameLayout = frameLayout;
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
        videoSize(video);
        System.out.println("video width = " + videoWidth + " height = " + videoHeight);
        mRangeSeekBar.setRangeValues(0, video.getDuration() / 1000);
        mMediaPlayer.reset();

        updateTextureViewSize(mFrameLayout.getWidth(), mFrameLayout.getHeight());
        // updateTextureViewSize(1080, 608);
        // updateTextureViewSize(1080, 1080);


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

    private void videoSize(Video video) throws IOException {
        MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
        metaRetriever.setDataSource(video.getPath());
        videoHeight = Float.parseFloat(metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
        videoWidth = Float.parseFloat(metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
        videoOrientation = Integer.parseInt(metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION));
    }

    private void updateTextureViewSize(int viewWidth, int viewHeight) {
        float scaleX = 1.0f;
        float scaleY = 1.0f;

        if (videoOrientation == 0) {
            scaleY = videoHeight / videoWidth;
        } else {
            scaleX = videoHeight / videoWidth;
        }

        // Calculate pivot points, in our case crop from center
        int pivotPointX = viewWidth / 2;
        int pivotPointY = viewHeight / 2;

        Matrix matrix = new Matrix();
        matrix.setScale(scaleX, scaleY, pivotPointX, pivotPointY);

        mTextureView.setTransform(matrix);
        mTextureView.setLayoutParams(new FrameLayout.LayoutParams(viewWidth, viewHeight));
    }
}
