package com.tincio.popularmovies.domain.callback;

import com.tincio.popularmovies.data.model.UserRealm;

/**
 * Created by Alberto on 10/03/2017.
 */

public interface DetalleCallback {

    void onResponseGetuserDB(UserRealm user, String...mensajes);
    void onResponseCloseSession( String mensajes);
}
