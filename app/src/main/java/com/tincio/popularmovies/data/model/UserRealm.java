package com.tincio.popularmovies.data.model;

import io.realm.RealmObject;

/**
 * Created by Alberto on 08/03/2017.
 */

public class UserRealm extends RealmObject {

    private String name;
    private String birthday;
    private String id;
    private String genere;
    private  String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }
}
