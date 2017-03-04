package com.tincio.popularmovies.presentation.util;

import android.net.Uri;

import java.io.BufferedReader;

public class Constants {

    public static String KEY = "39335edf6af0e5ee10c4be3cded34eb1";
    public static String param = "api_key";
    public static class serviceNames{
        public static String BASE_MOVIES = "http://api.themoviedb.org/3/movie/";
        public static String GET_IMAGE_MOVIES = "http://image.tmdb.org/t/p/w780";

        public static String prueba2 = "sasasa";
        public static String prueba= "xxxxxx";
        public static String GET_TRAILERS(Integer id){
            StringBuilder builderBase = new StringBuilder();
            builderBase.append(BASE_MOVIES);
            builderBase.append(id);
            builderBase.append("/videos?");
            Uri builUri = Uri.parse(builderBase.toString()).buildUpon()
                    .appendQueryParameter(param,KEY).build();
            return builUri.toString();
        }

        public static String GET_LIST_MOVIES(String option){
            StringBuilder builderBase = new StringBuilder();
            builderBase.append(BASE_MOVIES);
            builderBase.append(option);
            builderBase.append("?");
            Uri builUri = Uri.parse(builderBase.toString()).buildUpon()
                    .appendQueryParameter(param,KEY).build();
            return builUri.toString();
        }
    }
}