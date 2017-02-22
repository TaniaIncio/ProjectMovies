package com.tincio.popularmovies.domain.interactor;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.tincio.popularmovies.R;
import com.tincio.popularmovies.data.model.MovieRealm;
import com.tincio.popularmovies.data.services.response.ResponseMovies;
import com.tincio.popularmovies.data.services.response.Result;
import com.tincio.popularmovies.domain.callback.ListMovieCallback;
import com.tincio.popularmovies.presentation.application.PopularMoviesApplication;
import com.tincio.popularmovies.presentation.util.Constants;

import org.json.JSONObject;

import java.util.List;

import io.realm.Realm;


public class ListMovieInteractor {

    ListMovieCallback callback;
    public int TIMEOUT = 5000;
    PopularMoviesApplication application = PopularMoviesApplication.mApplication;
    public ListMovieInteractor(ListMovieCallback callback){
        this.callback = callback;
    }

    public void callListMovies(String option){
        try{
            getRequesListMovies(Constants.serviceNames.GET_LIST_MOVIES(option));
        }catch(Exception e){
            throw e;
        }
    }

    void getRequesListMovies(String url) {
        try{
            PopularMoviesApplication application = PopularMoviesApplication.mApplication;
            if (application != null) {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                        url,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Gson gson = new Gson();
                                ResponseMovies responseMovies = gson.fromJson(response.toString(), ResponseMovies.class);
                                callback.onResponse(checkFavoriteInList(responseMovies));
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

     ResponseMovies checkFavoriteInList(ResponseMovies responseMovies){

        Realm realm = application.getRealm();
        List<Result> lista = responseMovies.getResults();
        int indice = 0;
        try{
            for(Result result: lista){
                MovieRealm movie = realm.where(MovieRealm.class).equalTo("id",result.getId()).findFirst();
                if(realm.where(MovieRealm.class).equalTo("id",result.getId()).findFirst()!=null){
                    result.setFavorito(movie.getFavorite());
                }else
                    result.setFavorito(false);
                lista.set(indice, result);
                indice = indice+1;
            }
          responseMovies.setResults(lista);
        }catch(Exception e){
            e.printStackTrace();
        }
         return responseMovies;
    }

    //for favorite

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
