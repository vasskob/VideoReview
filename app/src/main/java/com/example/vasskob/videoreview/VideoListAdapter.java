package com.example.vasskob.videoreview;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.vasskob.videoreview.model.pojo.MediaItem;
import com.example.vasskob.videoreview.presenter.MediaPresenter;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;


public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoListViewHolder> {

    private final Context mContext;
    MediaPresenter mMediaPresenter;
    private List<MediaItem> mItems = null;
    private LayoutInflater mLayoutInflater = null;
    private int mCurrentPosition = -1;

    public VideoListAdapter(FragmentActivity context, MediaPresenter mediaPresenter) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        mMediaPresenter = mediaPresenter;
    }

    @Override
    public VideoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.video_item_layout, parent, false);
        return new VideoListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final VideoListViewHolder holder, final int position) {

        Glide.with(mContext)
                .load(mItems.get(position).getPath())
                .asBitmap()
                .placeholder(R.drawable.video_pic_small)
                .centerCrop()
                .override(200, 200)
                .into(holder.mThumbnail);

        holder.mThumbnail.setSelected(position == mCurrentPosition);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentPosition = holder.getAdapterPosition();
                mMediaPresenter.onPlay(mItems.get(mCurrentPosition));
                notifyDataSetChanged();
            }
        });
    }

    public void setMediaItems(List<MediaItem> items) {
        mItems = items;
    }

    @Override
    public int getItemCount() {
        if (mItems == null) {
            return 0;
        }

        return mItems.size();
    }


    static class VideoListViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView mThumbnail = null;

        VideoListViewHolder(View view) {
            super(view);
            mThumbnail = (RoundedImageView) view.findViewById(R.id.video_thumbnail);
        }
    }
}