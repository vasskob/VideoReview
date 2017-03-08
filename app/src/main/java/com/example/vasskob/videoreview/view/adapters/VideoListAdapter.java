package com.example.vasskob.videoreview.view.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.vasskob.videoreview.R;
import com.example.vasskob.videoreview.model.data.Video;
import com.example.vasskob.videoreview.presenter.VideoPresenter;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoListViewHolder> {

    private final VideoPresenter mPresenter;
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private int mCurrentPosition = -1;
    private List<Video> videoList;

    public VideoListAdapter(final FragmentActivity context, VideoPresenter videoPresenter) {
        mLayoutInflater = LayoutInflater.from(context);
        mPresenter = videoPresenter;
        mContext = context;
    }

    @Override
    public VideoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.video_item_layout, parent, false);
        return new VideoListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VideoListViewHolder holder, final int position) {

        Glide.with(mContext)
                .load(videoList.get(position).getPath())
                .asBitmap()
                .placeholder(R.drawable.video_pic_small)
                .centerCrop()
                .override(200, 200)
                .into(holder.mThumbnail);

        holder.mThumbnail.setSelected(position == mCurrentPosition);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onVideoItemClicked(videoList.get(holder.getAdapterPosition()));
                mCurrentPosition = holder.getAdapterPosition();

                VideoListAdapter.this.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (videoList != null)
            return videoList.size();
        return 0;
    }

    public void setRepoList(List<Video> list) {
        this.videoList = list;
        notifyDataSetChanged();
    }

    static class VideoListViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.video_thumbnail)
        RoundedImageView mThumbnail;

        VideoListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}