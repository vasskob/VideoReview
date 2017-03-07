package com.example.vasskob.videoreview.ui;

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
import com.example.vasskob.videoreview.factories.MediaPresenterFactory;
import com.example.vasskob.videoreview.globals.Constants;
import com.example.vasskob.videoreview.model.pojo.MediaItem;
import com.example.vasskob.videoreview.presenter.MediaPresenter;
import com.example.vasskob.videoreview.presenter.MediaPresenterImpl;
import com.example.vasskob.videoreview.ui.holder.UiHolder;
import com.example.vasskob.videoreview.utils.MarginDecoration;

import java.util.List;


public class VideoListFragment extends Fragment {

    private View rootView;
    private MediaPresenter mMediaPresenter;

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

        UiHolder uiHolder = new UiHolder(getActivity(), mVideoView);
        mMediaPresenter = MediaPresenterFactory.getMediaPresenter(Constants.MEDIA_TYPE_VIDEO);
        mMediaPresenter.onAttachView(uiHolder);

        final VideoListAdapter mAdapter = new VideoListAdapter(getActivity(), mMediaPresenter);
        mRecyclerView.setAdapter(mAdapter);

        mMediaPresenter.onLoadMediaItems(new MediaPresenterImpl.Callback() {
            @Override
            public void onItemsAvailable(List<MediaItem> items) {
                mAdapter.setMediaItems(items);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        mMediaPresenter.onDetachView();
    }
}
