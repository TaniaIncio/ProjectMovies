package com.tincio.popularmovies.presentation.view;

import com.tincio.popularmovies.data.model.UserRealm;

/**
 * Created by Alberto on 10/03/2017.
 */

public interface DetalleMoveView extends MvpView {
    void ShowgetUserDB(UserRealm user, String responseError);
    void ShowResultClose(String message);
}
