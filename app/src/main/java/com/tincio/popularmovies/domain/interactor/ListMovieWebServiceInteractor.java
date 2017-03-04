package com.tincio.popularmovies.domain.interactor;

import android.util.Log;

import com.tincio.popularmovies.R;
import com.tincio.popularmovies.data.model.MovieRealm;
import com.tincio.popularmovies.data.services.response.ResponseMovies;
import com.tincio.popularmovies.data.services.response.Result;
import com.tincio.popularmovies.domain.callback.ListMovieCallback;
import com.tincio.popularmovies.presentation.application.PopularMoviesApplication;
import com.tincio.popularmovies.presentation.util.Constants;


import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by tincio on 04/03/17.
 */

public class ListMovieWebServiceInteractor implements ListMovieInteractor{

    ListMovieCallback callback;
    public int TIMEOUT = 5000;
    PopularMoviesApplication application = PopularMoviesApplication.mApplication;

    public ListMovieWebServiceInteractor(ListMovieCallback callback) {
        this.callback = callback;
    }

    @Override
    public void callListMovies(String option, int page) {
        try{
            if(option.equals(application.getString(R.string.id_order_three))){
                this.showFavorite();
            }else{
                getRequesListMovies(option, page);
            }
        }catch(Exception e){
            throw e;
        }
    }

    @Override
    public void getRequesListMovies(String option, int page) {
        try{
            PopularMoviesApplication application = PopularMoviesApplication.mApplication;
            if (application != null) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);

                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                httpClient.addInterceptor(logging);
                Retrofit retrofit = new Retrofit.Builder()
                        .client(httpClient.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(Constants.serviceNames.BASE_MOVIES)
                        .build();

                MovieService service = retrofit.create(MovieService.class);
                Call<ResponseMovies> call = service.listMovies(option, Constants.KEY, page);
                call.enqueue(new Callback<ResponseMovies>() {
                    @Override
                    public void onResponse(Call<ResponseMovies> call, Response<ResponseMovies> response) {
                        callback.onResponse(checkFavoriteInList(response.body()));
                    }

                    @Override
                    public void onFailure(Call<ResponseMovies> call, Throwable t) {
                        Log.i("taggg ", t.getMessage());
                    }
                });
            }
        }catch(Exception e){
            throw e;
        }
    }

    @Override
    public ResponseMovies checkFavoriteInList(ResponseMovies responseMovies) {
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

    @Override
    public void saveFavorite(Integer id, String posterPath, String title) {
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
                movieRealm.setPosterPath(posterPath);
                movieRealm.setTitle(title);
                realm.copyToRealm(movieRealm);
            }
            realm.commitTransaction();
            callback.onResponseFavorite(application.getString(R.string.response_succesfull));
        }catch(Exception e){
            callback.onResponseFavorite(application.getString(R.string.response_error)+e.getMessage());
            //  throw e;
        }
    }

    @Override
    public void showFavorite() {
        try{
            Realm realm = application.getRealm();
            realm.beginTransaction();
            RealmResults<MovieRealm> movieSelection = realm.where(MovieRealm.class).findAll();
            realm.commitTransaction();
            ResponseMovies responseMovies = new ResponseMovies();
            List<Result> lista = new ArrayList<>();
            Result mResult;
            for (MovieRealm movie : movieSelection){
                mResult = new Result();
                mResult.setId(movie.getId());
                mResult.setPosterPath(movie.getPosterPath());
                mResult.setTitle(movie.getTitle());
                lista.add(mResult);
            }
            responseMovies.setResults(lista);
            callback.onResponse(checkFavoriteInList(responseMovies));


        }catch(Exception e){
            callback.onResponseFavorite(application.getString(R.string.response_error)+e.getMessage());
            //  throw e;
        }
    }

    /***Implementacion Retrofit*/
    public interface MovieService {
        //http://api.themoviedb.org/3/movie/popular?api_key=39335edf6af0e5ee10c4be3cded34eb1&page=1
        @GET("{option}")
        Call<ResponseMovies> listMovies(@Path("option") String option, @Query("api_key") String key, @Query("page") int page);
    }

    /**FIn Retrofit**/
}
