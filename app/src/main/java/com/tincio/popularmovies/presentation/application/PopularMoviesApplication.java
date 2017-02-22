package com.tincio.popularmovies.presentation.application;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class PopularMoviesApplication extends Application {


    RequestQueue requestQueue;
    public static PopularMoviesApplication mApplication;
    Realm realm;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mApplication = this;
    }

    public RequestQueue getRequestQueue(){
        try{
            if(requestQueue == null){
                requestQueue = Volley.newRequestQueue(getApplicationContext());
            }
        }catch(Exception e){
            throw e;
        }
        return requestQueue;
    }

    public void cancelPendingRequest(Object tag){
        try{
            if(requestQueue !=null){
                requestQueue.cancelAll(tag);
            }
        }catch(Exception e){
            throw e;
        }
    }

    public Realm getRealm(){
        try{
            if(realm ==null){
                RealmConfiguration realmConfig = new RealmConfiguration.Builder(mApplication).build();
                Realm.setDefaultConfiguration(realmConfig);
                realm = Realm.getDefaultInstance();
            }

        }catch(Exception e){
            throw e;
        }
        return realm;
    }

}
