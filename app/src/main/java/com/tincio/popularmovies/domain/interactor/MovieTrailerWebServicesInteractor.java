package com.tincio.popularmovies.domain.interactor;

import com.tincio.popularmovies.R;
import com.tincio.popularmovies.data.model.MovieRealm;
import com.tincio.popularmovies.data.services.response.ResponseMovies;
import com.tincio.popularmovies.data.services.response.ResponseTrailersMovie;
import com.tincio.popularmovies.domain.callback.MovieTrailerCallback;
import com.tincio.popularmovies.presentation.application.PopularMoviesApplication;
import com.tincio.popularmovies.presentation.util.Constants;

import org.json.JSONObject;

import io.realm.Realm;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by tincio on 04/03/17.
 */

public class MovieTrailerWebServicesInteractor implements MovieTrailerInteractor{

    MovieTrailerCallback callback;
    public int TIMEOUT = 5000;
    PopularMoviesApplication application = PopularMoviesApplication.mApplication;

    public MovieTrailerWebServicesInteractor(MovieTrailerCallback callback){
        this.callback = callback;
    }

    @Override
    public void getMovieTrailers(Integer id) {
        try{
            getRequesListMovies(id);
        }catch(Exception e){
            throw e;
        }
    }

    @Override
    public void getRequesListMovies(Integer idMovie) {
        try{

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

                TrailerMovieService service = retrofit.create(TrailerMovieService.class);
                Call<ResponseTrailersMovie> call = service.listTrailerMovies(idMovie,Constants.KEY);
                call.enqueue(new Callback<ResponseTrailersMovie>() {
                    @Override
                    public void onResponse(Call<ResponseTrailersMovie> call, retrofit2.Response<ResponseTrailersMovie> response) {
                        callback.onResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<ResponseTrailersMovie> call, Throwable t) {
                        callback.onResponse(null,t.getMessage());
                    }
                });
            }
        }catch(Exception e){
            callback.onResponse(null,e.getMessage());
            //throw e;
        }
    }

    @Override
    public void saveFavorite(Integer id, String posterPath, String title){
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

    public interface TrailerMovieService {
        @GET("{idmovie}/videos")
        Call<ResponseTrailersMovie> listTrailerMovies(@Path("idmovie") Integer idMovie, @Query("api_key") String key);
    }

}
