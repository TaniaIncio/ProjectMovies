package com.tincio.popularmovies.presentation.application;

import android.app.Application;


import io.realm.Realm;
import io.realm.RealmConfiguration;

public class PopularMoviesApplication extends Application {

    public static PopularMoviesApplication mApplication;
    Realm realm;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mApplication = this;
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
