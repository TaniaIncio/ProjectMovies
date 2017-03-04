package com.tincio.popularmovies.domain.interactor;

import com.tincio.popularmovies.data.services.response.ResponseMovies;
/**
 * Created by tincio on 04/03/17.
 */

public interface ListMovieInteractor {

    void callListMovies(String option, int page);

    void getRequesListMovies(String option, int page) ;

    ResponseMovies checkFavoriteInList(ResponseMovies responseMovies);

    void  saveFavorite(Integer id, String posterPath, String title);

    void showFavorite();

}
