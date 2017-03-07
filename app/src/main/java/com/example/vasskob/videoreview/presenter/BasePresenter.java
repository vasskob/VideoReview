package com.example.vasskob.videoreview.presenter;

import com.example.vasskob.videoreview.view.View;

public interface BasePresenter {
    void onAttachView(View view);
    void onDetachView();

}
