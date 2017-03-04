package com.tincio.popularmovies.presentation.view;

import android.content.Context;


public interface MvpView {

    Context getContext();
    void showLoading();
    void closeLoading();

}
