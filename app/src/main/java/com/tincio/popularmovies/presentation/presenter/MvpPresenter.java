package com.tincio.popularmovies.presentation.presenter;

public interface MvpPresenter<V> {
    public void setView(V view);
    public void detachView();
}
