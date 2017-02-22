package com.tincio.popularmovies.domain.callback;

import com.tincio.popularmovies.data.services.response.ResponseMovies;

public interface ListMovieCallback {

    void onResponse(ResponseMovies responseMovies, String...mensajes);
    void onResponseFavorite( String mensajes);
}
