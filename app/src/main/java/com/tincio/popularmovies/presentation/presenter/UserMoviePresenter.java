package com.tincio.popularmovies.presentation.presenter;

import com.tincio.popularmovies.data.model.User;
import com.tincio.popularmovies.data.model.UserRealm;
import com.tincio.popularmovies.domain.callback.UserMovieCallback;
import com.tincio.popularmovies.domain.interactor.UserInteractor;
import com.tincio.popularmovies.domain.interactor.userMovieInteractor;
import com.tincio.popularmovies.presentation.view.UserMovieView;

/**
 * Created by Alberto on 09/03/2017.
 */


// MvpPresenter<ListMovieView>, ListMovieCallback
public class UserMoviePresenter implements  MvpPresenter<UserMovieView>,UserMovieCallback{

    UserMovieView view;
    UserInteractor userInteractor;


    //mensaje en la view
    @Override
    public void onResponseSaveUser(String mensaje) {
        //view.closeLoading();
        view.saveUser(mensaje);
    }

    @Override
    public void onResponseGetUserBD(UserRealm user, String... mensajes) {
          view.showUser(user, (mensajes.length>0?mensajes[0]:""));
    }


    @Override
    public void setView(UserMovieView view) {
      this.view = view;
        userInteractor =  new userMovieInteractor(this);
    }


    @Override
    public void detachView() {
         this.view = null;
    }

    public void saveUser(UserRealm user){
       // view.showLoading();
        userInteractor.saveUser(user);
    }

    public  void getUser(){
        userInteractor.getUser();
    }



}
