package com.example.vasskob.videoreview;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vasskob.videoreview.factories.MediaPresenterFactory;
import com.example.vasskob.videoreview.model.Media;
import com.example.vasskob.videoreview.presenter.MediaPresenter;
import com.example.vasskob.videoreview.utils.VideoThumbnailUtil;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.List;


public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoListViewHolder> {

    private MediaPresenter mMediaPresenter = null;
    private List<Media> mMediaItems = null;
    private LayoutInflater mLayoutInflater = null;
    private Context context;

    public VideoListAdapter(FragmentActivity context) {
        mLayoutInflater = LayoutInflater.from(context);
        mMediaPresenter = MediaPresenterFactory.getMediaPresenter(context, Constants.MEDIA_TYPE_VIDEO);
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
        holder.mThumbnail.setImageBitmap(VideoThumbnailUtil.getVideoThumbnail(mMediaItems.get(position).getPath(), 200, 200));

//        Picasso.with(context)
//                .load(getImageUri(context, VideoThumbnailUtil.getVideoThumbnail(mMediaItems.get(position).getPath(), 200, 200)))
//                .centerCrop()
//                .fit()
//                .placeholder(R.drawable.video_pic)
//                .into(holder.mThumbnail);

        //   System.out.println(" onBindVieHolder path = " + mMediaItems.get(position).getPath());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPresenter.onMediaItemClicked(mMediaItems.get(holder.getAdapterPosition()));
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

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}