package com.example.vasskob.videoreview.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import com.example.vasskob.videoreview.R;
import com.example.vasskob.videoreview.VideoListAdapter;
import com.example.vasskob.videoreview.utils.MarginDecoration;

import java.io.IOException;


public class VideoListFragment extends Fragment implements TextureView.SurfaceTextureListener {


    private static final String TAG = "TAG";
    private MediaPlayer mMediaPlayer;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.video_fragment_layout, parent, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        VideoListAdapter mAdapter = new VideoListAdapter(getActivity());
        recyclerView.addItemDecoration(new MarginDecoration(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));

        TextureView mVideoPreview = (TextureView) rootView.findViewById(R.id.video_view);
        mVideoPreview.setSurfaceTextureListener(this);
        mVideoPreview.setOpaque(false);

    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        try {

            SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            Surface mSurface = new Surface(surfaceTexture);
            AssetFileDescriptor afd = getContext().getAssets().openFd("welcome.mp4");
            mMediaPlayer = new MediaPlayer();

            if (preferences.getString("path", null) != null) {
                mMediaPlayer.setDataSource(preferences.getString("path", null));
            } else {
                mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            }
            System.out.println("path !!!!=" + preferences.getString("path", null));
            mMediaPlayer.setSurface(mSurface);
            mMediaPlayer.prepareAsync();

            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setLooping(true);
                    mediaPlayer.seekTo(0);
                    mediaPlayer.start();
                }
            });
        } catch (IOException e) {
            Log.d(TAG, "IOException there is no file to play");
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

}
