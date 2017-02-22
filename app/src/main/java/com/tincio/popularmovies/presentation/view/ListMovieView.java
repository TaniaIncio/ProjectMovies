package com.tincio.popularmovies.presentation.view;

import com.tincio.popularmovies.data.services.response.ResponseMovies;

import java.util.List;


public interface ListMovieView extends MvpView {
    void showListMovies(ResponseMovies responseMovies, String responseError);
    void showResultFavorite(String response);
}
