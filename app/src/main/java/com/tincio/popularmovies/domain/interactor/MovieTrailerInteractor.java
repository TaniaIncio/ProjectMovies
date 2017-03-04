package com.tincio.popularmovies.domain.interactor;
/**
 * Created by tincio on 04/03/17.
 */

public interface MovieTrailerInteractor {


    void getMovieTrailers(Integer id);

    void getRequesListMovies(Integer id);

    //For favorite
    void saveFavorite(Integer id);


}
