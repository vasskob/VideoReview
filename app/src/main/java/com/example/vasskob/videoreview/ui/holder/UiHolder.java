package com.example.vasskob.videoreview.ui.holder;


import android.support.v4.app.FragmentActivity;
import android.view.TextureView;

public class UiHolder {
    private final FragmentActivity mActivity;
    private final TextureView mTextureView;


    public UiHolder(FragmentActivity activity, TextureView textureView) {
        mActivity = activity;
        mTextureView = textureView;
    }

    public FragmentActivity getActivity() {
        return mActivity;
    }

    public TextureView getTextureView() {
        return mTextureView;
    }
}
