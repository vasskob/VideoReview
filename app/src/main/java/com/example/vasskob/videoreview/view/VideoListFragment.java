package com.example.vasskob.videoreview.view;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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
import com.example.vasskob.videoreview.model.data.Video;
import com.example.vasskob.videoreview.presenter.MainPresenter;
import com.example.vasskob.videoreview.presenter.VideoPresenter;
import com.example.vasskob.videoreview.utils.MarginDecoration;
import com.example.vasskob.videoreview.view.adapters.VideoListAdapter;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class VideoListFragment extends Fragment implements com.example.vasskob.videoreview.view.View, LoaderManager.LoaderCallbacks<Cursor> {

    private static final int COUNT_OF_COLUMN = 5;
    private static final int VIDEO_LOADER_ID = 10;

    @Bind(R.id.rv_video_list)
    RecyclerView mRecyclerView;

    @Bind(R.id.video_view)
    TextureView mVideoView;

    @Bind(R.id.video_seek_bar)
    RangeSeekBar mVideoSeekBar;

    @Bind(R.id.video_duration_spinner)
    Spinner spinner;

    //private VideoPresenter videoPresenter = new VideoPresenter(getActivity());
    private VideoPresenter videoPresenter = new VideoPresenter();

    private VideoListAdapter mAdapter;
    private MainPresenter.Callback mCallback;

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

        mAdapter = new VideoListAdapter(getActivity(), mVideoView, this);
        mRecyclerView.setAdapter(mAdapter);
        videoPresenter.onAttachView(this);

//      String selectedDuration = spinner.getSelectedItem().toString();

        videoSpinnerChangeListener();
        videoSeekBarChangeListener();

    }

    private void videoSpinnerChangeListener() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    videoPresenter.onSpinnerSelected("All videos");
                } else {
                    videoPresenter.onSpinnerSelected("10 second video");
                }
                Log.d("TAG", " videoSpinnerChangeListener, spinner position = " + position);
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
    public void onStop() {
        super.onStop();
        if (videoPresenter != null) {
            videoPresenter.stopVideo();
            videoPresenter.onDetachView();
        }


    }

    @Override
    public void showData(List<Video> videoList) {
        mAdapter.setVideoList(videoList);
    }

    @Override
    public void showError(String error) {
        makeToast(error);
    }

    @Override
    public void showEmptyList() {
        makeToast(getResources().getString(R.string.list_is_empty));
    }

    @Override
    public void startLoading() {
        getActivity().getSupportLoaderManager().initLoader(VIDEO_LOADER_ID, null, this);
    }

    private void makeToast(String text) {
        Snackbar.make(mRecyclerView, text, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == VIDEO_LOADER_ID) {
            CursorLoader loader = new CursorLoader(getActivity());
            loader.setUri(MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            loader.setProjection(null);
            loader.setSelection(null);
            loader.setSelectionArgs(null);
            loader.setSortOrder(MediaStore.Video.Media.DATE_ADDED + " DESC");
            return loader;
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == VIDEO_LOADER_ID) {
            LinkedList<Video> mediaList = new LinkedList<>();

            if (data != null && data.getCount() > 0) {
                int idIndex = data.getColumnIndex(MediaStore.Video.Media._ID);
                int titleIndex = data.getColumnIndex(MediaStore.Video.Media.TITLE);
                int pathIndex = data.getColumnIndex(MediaStore.Video.Media.DATA);

                while (data.moveToNext()) {
                    long id = data.getLong(idIndex);
                    String title = data.getString(titleIndex);
                    String path = data.getString(pathIndex);

                    Video video = new Video(id, title, path);
                    mediaList.add(video);
                }
            }
            if (mCallback != null) {
                mCallback.onItemsAvailable(mediaList);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (loader.getId() == VIDEO_LOADER_ID) {
            if (mCallback != null) {
                mCallback.onItemsAvailable(new LinkedList<Video>());
            }
        }
    }
}
