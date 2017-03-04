package com.tincio.popularmovies.domain.interactor;
/*
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.tincio.popularmovies.R;
import com.tincio.popularmovies.data.model.MovieRealm;*/
import com.tincio.popularmovies.data.services.response.ResponseMovies;
import com.tincio.popularmovies.data.services.response.Result;
import com.tincio.popularmovies.domain.callback.ListMovieCallback;
import com.tincio.popularmovies.presentation.application.PopularMoviesApplication;
import com.tincio.popularmovies.presentation.util.Constants;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public interface ListMovieInteractor {



    public void callListMovies(String option, int page);

    void getRequesListMovies(String option, int page) ;

     ResponseMovies checkFavoriteInList(ResponseMovies responseMovies);

    //for favorite

    public void  saveFavorite(Integer id, String posterPath, String title);

    public void showFavorite();

}
