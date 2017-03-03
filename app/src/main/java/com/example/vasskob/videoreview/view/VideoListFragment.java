package com.example.vasskob.videoreview.view;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import com.example.vasskob.videoreview.R;
import com.example.vasskob.videoreview.VideoListAdapter;
import com.example.vasskob.videoreview.utils.MarginDecoration;


public class VideoListFragment extends Fragment {

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
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_video_list);
        mRecyclerView.addItemDecoration(new MarginDecoration(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));

        TextureView mVideoView = (TextureView) rootView.findViewById(R.id.video_view);
        mVideoView.setOpaque(false);
//       mVideoView.setSurfaceTextureListener(this);
        VideoListAdapter mAdapter = new VideoListAdapter(getActivity(), mVideoView);
        mRecyclerView.setAdapter(mAdapter);

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }


        // TODO: 03.03.17  presenter onDestroy mediaPlayer .release(); mediaPlayer=null;
    }

}
