package com.androidigniter.loginandregistration.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Keywords extends RealmObject {

    @PrimaryKey
    private int id;
    private String keyword;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}