package com.example.vasskob.videoreview.view.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.vasskob.videoreview.R;
import com.example.vasskob.videoreview.model.data.Video;
import com.example.vasskob.videoreview.presenter.MainPresenter;
import com.example.vasskob.videoreview.presenter.VideoPresenter;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoListViewHolder> {

    private VideoPresenter mPresenter = null;
    private LayoutInflater mLayoutInflater = null;
    private final Context context;
    private int mCurrentPosition = 100;
    private List<Video> videoList;
    private TextureView textureView;


    public VideoListAdapter(final FragmentActivity context, VideoPresenter videoPresenter, TextureView textureView, final com.example.vasskob.videoreview.view.View view) {
        mLayoutInflater = LayoutInflater.from(context);
        mPresenter = videoPresenter;

        view.startLoading(new MainPresenter.Callback() {
            @Override
            public void onItemsAvailable(List<Video> items) {
                if (items.size() == 0) {
                    view.showError(context.getResources().getString(R.string.list_is_empty));
                }
                videoList = items;
                notifyDataSetChanged();
            }
        });

        this.context = context;
        this.textureView = textureView;
    }


    @Override
    public VideoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.video_item_layout, parent, false);
        return new VideoListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VideoListViewHolder holder, final int position) {

        Glide.with(context)
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
                mPresenter.onMediaItemClicked(videoList.get(holder.getAdapterPosition()));
                mCurrentPosition = holder.getAdapterPosition();

                VideoListAdapter.this.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (videoList == null) return 0;
        return videoList.size();
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