package com.tincio.popularmovies.domain.interactor;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tincio.popularmovies.R;
import com.tincio.popularmovies.data.model.MovieRealm;
import com.tincio.popularmovies.data.services.response.ResponseMovies;
import com.tincio.popularmovies.data.services.response.ResponseTrailersMovie;
import com.tincio.popularmovies.data.services.response.Result;
import com.tincio.popularmovies.domain.callback.MovieTrailerCallback;
import com.tincio.popularmovies.presentation.application.PopularMoviesApplication;
import com.tincio.popularmovies.presentation.util.Constants;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MovieTrailerInteractor {

    MovieTrailerCallback callback;
    public int TIMEOUT = 5000;
    PopularMoviesApplication application = PopularMoviesApplication.mApplication;
    public MovieTrailerInteractor(MovieTrailerCallback callback){
        this.callback = callback;
    }

    public void getMovieTrailers(Integer id){
        try{
            getRequesListMovies(Constants.serviceNames.GET_TRAILERS(id));
        }catch(Exception e){
            throw e;
        }
    }

    void getRequesListMovies(String url) {
        try{

            if (application != null) {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                        url,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Gson gson = new Gson();
                                ResponseTrailersMovie responseMovies = gson.fromJson(response.toString(), ResponseTrailersMovie.class);
                                callback.onResponse(responseMovies);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                callback.onResponse(null, error.getMessage());
                            }
                        });
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                        TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                application.getRequestQueue().add(jsonObjectRequest);
            }
        }catch(Exception e){
            throw e;
        }
    }

    //For favorite
    public void saveFavorite(Integer id){
        try{
            Realm realm = application.getRealm();
            realm.beginTransaction();
            MovieRealm movieSelection = realm.where(MovieRealm.class).equalTo("id",id).findFirst();
            if(movieSelection!=null){
                movieSelection.setFavorite(!(movieSelection.getFavorite()==null?false:movieSelection.getFavorite()));
            }else{
                MovieRealm movieRealm = new MovieRealm();
                movieRealm.setFavorite(true);
                movieRealm.setId(id);
                realm.copyToRealm(movieRealm);
            }
            realm.commitTransaction();
            callback.onResponseFavorite(application.getString(R.string.response_succesfull));
        }catch(Exception e){
            callback.onResponseFavorite(application.getString(R.string.response_error)+e.getMessage());
            //  throw e;
        }
    }


}
