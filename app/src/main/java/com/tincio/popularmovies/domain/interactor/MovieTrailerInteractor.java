package com.tincio.popularmovies.domain.interactor;

public interface MovieTrailerInteractor {


    void getMovieTrailers(Integer id);

    void getRequesListMovies(Integer id);

    //For favorite
    void saveFavorite(Integer id, String posterPath, String title);


}
