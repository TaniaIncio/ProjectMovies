package com.tincio.popularmovies.presentation.view;

import com.tincio.popularmovies.data.services.response.ResponseTrailersMovie;
import com.tincio.popularmovies.data.services.response.ResultTrailer;


public interface MovieTrailerView extends MvpView {
    void showMovieTrailer(ResponseTrailersMovie detailMovie, String responseError);
    void showResultFavorite(String response);
}
