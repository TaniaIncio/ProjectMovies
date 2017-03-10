package com.tincio.popularmovies.domain.interactor;

import com.tincio.popularmovies.R;
import com.tincio.popularmovies.data.model.MovieRealm;
import com.tincio.popularmovies.data.model.User;
import com.tincio.popularmovies.data.model.UserRealm;
import com.tincio.popularmovies.domain.callback.UserMovieCallback;
import com.tincio.popularmovies.presentation.application.PopularMoviesApplication;

import io.realm.Realm;

/**
 * Created by Alberto on 08/03/2017.
 */

public class userMovieInteractor implements UserInteractor {
    PopularMoviesApplication application = PopularMoviesApplication.mApplication;
    UserMovieCallback callback;
    Realm realm;

    public userMovieInteractor(UserMovieCallback callback) {
        this.callback = callback;
        realm = application.getRealm();
    }

    @Override
    public void getUser() {

        try{
             realm.beginTransaction();
            UserRealm userRealm = realm.where(UserRealm.class).findFirst();
            realm.commitTransaction();
             if (userRealm == null){
                 callback.onResponseGetUserBD(null,application.getString(R.string.user_empty));
             }else {
                    callback.onResponseGetUserBD(userRealm,application.getString(R.string.user_exits));
             }


        }catch (Exception e){
               callback.onResponseGetUserBD(null,application.getString(R.string.error_save_user_db));


        }finally {


        }
    }

    @Override
    public void saveUser(UserRealm user) {
            try {
                realm.beginTransaction();
                UserRealm usuario = realm.where(UserRealm.class).equalTo("id",user.getId()).findFirst();
                if (usuario!=null){
                    callback.onResponseSaveUser(application.getString(R.string.user_exits));
                }else{
                    UserRealm userRealm = new UserRealm();
                    userRealm.setBirthday(user.getBirthday());
                    userRealm.setGenere(user.getGenere());
                    userRealm.setId(user.getId());
                    userRealm.setName(user.getName());
                    userRealm.setEmail(user.getEmail());
                    realm.copyToRealm(userRealm);
                    realm.commitTransaction();
                    callback.onResponseSaveUser(application.getString(R.string.response_succesfull));

                }


            }catch (Exception e){
                 callback.onResponseSaveUser(application.getString(R.string.fail_save)+e.getMessage());
            }finally {

            }
    }

}
