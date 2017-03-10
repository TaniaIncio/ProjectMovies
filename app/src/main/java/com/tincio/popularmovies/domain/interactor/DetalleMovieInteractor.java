package com.tincio.popularmovies.domain.interactor;

import com.tincio.popularmovies.R;
import com.tincio.popularmovies.data.model.UserRealm;
import com.tincio.popularmovies.domain.callback.DetalleCallback;
import com.tincio.popularmovies.presentation.application.PopularMoviesApplication;

import io.realm.Realm;

/**
 * Created by Alberto on 10/03/2017.
 */

public class DetalleMovieInteractor implements DetalleInteractor {

    DetalleCallback callback;
    PopularMoviesApplication application = PopularMoviesApplication.mApplication;
    Realm realm;

    public DetalleMovieInteractor(DetalleCallback callback) {
        this.callback = callback;
        this.realm = application.getRealm();
    }

    @Override
    public void getUserDB() {
        try{
            realm.beginTransaction();

            UserRealm userRealm = realm.where(UserRealm.class).findFirst();
            realm.commitTransaction();
            if (userRealm == null){
                callback.onResponseGetuserDB(null,application.getString(R.string.user_empty));
            }else {
                callback.onResponseGetuserDB(userRealm,application.getString(R.string.user_exits));
            }


        }catch (Exception e){
            callback.onResponseGetuserDB(null,application.getString(R.string.error_save_user_db));


        }finally {


        }

    }

    @Override
    public void closeSessionDB() {
        try {
            realm.beginTransaction();
            realm.deleteAll();
            realm.commitTransaction();
            callback.onResponseCloseSession(application.getString(R.string.response_succesfull));
        }catch (Exception e){

            callback.onResponseCloseSession(application.getString(R.string.error_session_close));
        }finally {

        }

    }
}
