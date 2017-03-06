package com.example.vasskob.videoreview.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.vasskob.videoreview.R;
import com.example.vasskob.videoreview.presenter.VideoPresenter;
import com.example.vasskob.videoreview.view.adapters.VideoListAdapter;
import com.example.vasskob.videoreview.utils.MarginDecoration;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import butterknife.Bind;
import butterknife.ButterKnife;


public class VideoListFragment extends Fragment {

    private static final int COUNT_OF_COLUMN = 5;

    @Bind(R.id.rv_video_list)
    RecyclerView mRecyclerView;

    @Bind(R.id.video_view)
    TextureView mVideoView;

    @Bind(R.id.video_seek_bar)
    RangeSeekBar mVideoSeekBar;

    @Bind(R.id.video_duration_spinner)
    Spinner spinner;

    private VideoPresenter videoPresenter = new VideoPresenter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.video_fragment_layout, parent, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mRecyclerView.addItemDecoration(new MarginDecoration(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), COUNT_OF_COLUMN));

        mVideoView.setOpaque(false);
        mVideoView.getLayoutParams().height = mVideoView.getWidth();

        mVideoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoPresenter.onVideoViewClicked();
            }
        });

        VideoListAdapter mAdapter = new VideoListAdapter(getActivity(), mVideoView);
        mRecyclerView.setAdapter(mAdapter);

        String selectedDuration = spinner.getSelectedItem().toString();

        videoSpinnerChangeListener();

        videoSeekBarChangeListener();

    }

    private void videoSpinnerChangeListener() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                videoPresenter.onSpinnerSelected("All videos");}
                else {
                    videoPresenter.onSpinnerSelected("10 second video");
                }
                Log.d("TAG", " videoSpinnerChangeListener spinner position = " + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                videoPresenter.onSpinnerSelected("All videos");
            }
        });
    }

    private void videoSeekBarChangeListener() {

        mVideoSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                videoPresenter.onRangeSelected((int) minValue, (int) maxValue);
                Log.d("TAG", " onRangeSeekBarValuesChanged  minValue = " + minValue + " maxValue = " + maxValue);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        videoPresenter.stopVideo();
    }
}
