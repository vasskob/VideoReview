package com.example.vasskob.videoreview;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.vasskob.videoreview.factories.MediaPresenterFactory;
import com.example.vasskob.videoreview.model.Media;
import com.example.vasskob.videoreview.presenter.MediaPresenter;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;


public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoListViewHolder> {

    private MediaPresenter mMediaPresenter = null;
    private List<Media> mMediaItems = null;
    private LayoutInflater mLayoutInflater = null;
    private final Context context;
    private int mCurrentPosition=100;



    public VideoListAdapter(FragmentActivity context, TextureView view) {
        mLayoutInflater = LayoutInflater.from(context);
        mMediaPresenter = MediaPresenterFactory.getMediaPresenter(context, Constants.MEDIA_TYPE_VIDEO, view);
        mMediaPresenter.getMediaItems(new MediaPresenter.Callback() {
            @Override
            public void onItemsAvailable(List<Media> items) {
                mMediaItems = items;
                VideoListAdapter.this.notifyDataSetChanged();
            }
        });
        this.context = context;
    }

    @Override
    public VideoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.video_item_layout, parent, false);
        return new VideoListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final VideoListViewHolder holder, final int position) {

        Glide.with(context)
                .load(mMediaItems.get(position).getPath())
                .asBitmap()
                .placeholder(R.drawable.video_pic_small)
                .centerCrop()
                .override(200, 200)
                .into(holder.mThumbnail);

        holder.mThumbnail.setSelected(position == mCurrentPosition);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPresenter.onMediaItemClicked(mMediaItems.get(holder.getAdapterPosition()));
                mCurrentPosition = holder.getAdapterPosition();
                VideoListAdapter.this.notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mMediaItems == null) {
            return 0;
        }

        return mMediaItems.size();
    }


    static class VideoListViewHolder extends RecyclerView.ViewHolder {

//      private TextView mTitleView = null;

        private RoundedImageView mThumbnail = null;

        VideoListViewHolder(View view) {
            super(view);
            mThumbnail = (RoundedImageView) view.findViewById(R.id.video_thumbnail);
//          mTitleView = (TextView)view.findViewById(R.id.title);


        }
    }


}