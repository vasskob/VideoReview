package com.example.vasskob.videoreview.utils;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

public class VideoThumbnailUtil {

    public static Bitmap getVideoThumbnail(String videoPath, int width, int height) {
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Images.Thumbnails.MINI_KIND);
        if (bitmap == null) {
            return null;
        }
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }


}
