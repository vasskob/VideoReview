package com.example.vasskob.videoreview.view.fragments;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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


    private TextureView mVideoPreview;
    private MediaPlayer mMediaPlayer;

    private RecyclerView recyclerView;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.video_fragment_layout, parent, false);
//        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.rv_layout, parent, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        VideoListAdapter mAdapter = new VideoListAdapter(getActivity());
        recyclerView.addItemDecoration(new MarginDecoration(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));
        mVideoPreview = (TextureView) rootView.findViewById(R.id.video_view);
        mVideoPreview.setSurfaceTextureListener(this);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Surface s = new Surface(surface);
        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource("http://daily3gp.com/vids/747.3gp");
            mMediaPlayer.setSurface(s);
            mMediaPlayer.prepare();
//            mMediaPlayer.setOnBufferingUpdateListener(getActivity());
//            mMediaPlayer.setOnCompletionListener(getContext());
//            mMediaPlayer.setOnPreparedListener(this);
//            mMediaPlayer.setOnVideoSizeChangedListener(this);
//            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            mMediaPlayer.start();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
}
