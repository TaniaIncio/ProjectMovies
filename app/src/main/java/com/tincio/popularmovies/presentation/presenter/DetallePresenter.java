package com.tincio.popularmovies.presentation.presenter;

import com.tincio.popularmovies.data.model.UserRealm;
import com.tincio.popularmovies.domain.callback.DetalleCallback;
import com.tincio.popularmovies.domain.interactor.DetalleInteractor;
import com.tincio.popularmovies.domain.interactor.DetalleMovieInteractor;
import com.tincio.popularmovies.presentation.view.DetalleMoveView;

/**
 * Created by Alberto on 10/03/2017.
 */

public class DetallePresenter implements MvpPresenter<DetalleMoveView>,DetalleCallback {

    DetalleMoveView view;
    DetalleInteractor detalleInteractor;

    @Override
    public void onResponseGetuserDB(UserRealm user, String... mensajes) {
        view.ShowgetUserDB(user,(mensajes.length>0?mensajes[0]:""));
    }

    @Override
    public void onResponseCloseSession(String mensajes) {
           view.ShowResultClose(mensajes);
    }

    @Override
    public void setView(DetalleMoveView view) {
        this.view = view;
        detalleInteractor = new DetalleMovieInteractor(this);
    }

    public void getUserDB(){
        detalleInteractor.getUserDB();
    }

    public void closeSession(){
        detalleInteractor.closeSessionDB();
    }

    @Override
    public void detachView() {
               this.view = null;
    }
}
