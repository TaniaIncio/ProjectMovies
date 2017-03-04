package com.tincio.popularmovies.presentation.presenter;

import com.tincio.popularmovies.data.services.response.ResponseMovies;
import com.tincio.popularmovies.domain.callback.ListMovieCallback;
import com.tincio.popularmovies.domain.interactor.ListMovieInteractor;
import com.tincio.popularmovies.domain.interactor.ListMovieWebServiceInteractor;
import com.tincio.popularmovies.presentation.view.ListMovieView;


public class ListMoviePresenter implements MvpPresenter<ListMovieView>, ListMovieCallback {

    ListMovieView view;
    ListMovieInteractor movieInteractor;

    @Override
    public void setView(ListMovieView view) {
        this.view = view;
        movieInteractor = new ListMovieWebServiceInteractor(this);
    }

    @Override
    public void detachView() {
        view = null;
    }

    public void callListMovie(String option, int page){
        try{
            if(page == 1)
                view.showLoading();
            movieInteractor.callListMovies(option, page);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(ResponseMovies responseMovies, String... mensajes) {
        view.closeLoading();
        view.showListMovies(responseMovies, (mensajes.length>0?mensajes[0]:""));

    }

    public void saveFavoriteMovie(Integer id, String posterPath, String title){
        movieInteractor.saveFavorite(id, posterPath, title );
    }



    @Override
    public void onResponseFavorite(String mensajes) {
        view.showResultFavorite(mensajes);
    }


}
