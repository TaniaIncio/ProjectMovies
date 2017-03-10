package com.tincio.popularmovies.domain.callback;

import com.tincio.popularmovies.data.model.User;
import com.tincio.popularmovies.data.model.UserRealm;

/**
 * Created by Alberto on 08/03/2017.
 */

public interface UserMovieCallback {

    void onResponseSaveUser(String mensaje);
    void onResponseGetUserBD (UserRealm user, String... mensajes);
}
