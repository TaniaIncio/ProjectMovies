package com.tincio.popularmovies.domain.interactor;

import com.tincio.popularmovies.data.model.User;
import com.tincio.popularmovies.data.model.UserRealm;

/**
 * Created by Alberto on 08/03/2017.
 */

public interface UserInteractor {

    public void getUser();
    public void saveUser(UserRealm user);
}
