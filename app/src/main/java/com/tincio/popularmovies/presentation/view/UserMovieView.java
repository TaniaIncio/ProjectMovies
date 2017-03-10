package com.tincio.popularmovies.presentation.view;

import com.tincio.popularmovies.data.model.UserRealm;

/**
 * Created by Alberto on 09/03/2017.
 */

public interface UserMovieView extends  MvpView {
    void showUser(UserRealm user, String responseError);
    void saveUser(String response);

}
