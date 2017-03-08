package com.example.vasskob.videoreview.view;


import com.example.vasskob.videoreview.presenter.MainPresenter;

public interface View {

//    void showData(List<Video> videoList);

    void showError(String error);

    void showEmptyList();

    void startLoading(MainPresenter.Callback callback);

    void showInfo(String s);
}
