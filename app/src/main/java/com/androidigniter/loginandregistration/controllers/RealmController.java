package com.androidigniter.loginandregistration.controllers;

import com.androidigniter.loginandregistration.models.Keywords;
import com.androidigniter.loginandregistration.models.Relatives;
import com.androidigniter.loginandregistration.models.Users;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmController {

    Realm realm;

    public RealmController(Realm realm) {
        this.realm = realm;
    }

    //SAVE OR WRITE
    public void save(final Users spacecraft) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Users s = realm.copyToRealm(spacecraft);
            }
        });
    }

    public void saveRelative(final Relatives spacecraft) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Relatives s = realm.copyToRealm(spacecraft);
            }
        });
    }

    public void saveKeyword(final Keywords spacecraft) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Keywords s = realm.copyToRealm(spacecraft);
            }
        });
    }

    //RETRIEVE OR READ
    public ArrayList<Users> retrieve() {
        RealmResults<Users> spacecrafts = realm.where(Users.class).findAll();
        return new ArrayList<>(spacecrafts);
    }

    public ArrayList<Relatives> retrieveRelatives() {
        RealmResults<Relatives> spacecrafts = realm.where(Relatives.class).findAll();
        return new ArrayList<>(spacecrafts);
    }

    public ArrayList<Keywords> retrieveKeywords() {
        RealmResults<Keywords> spacecrafts = realm.where(Keywords.class).findAll();
        return new ArrayList<>(spacecrafts);
    }

    public ArrayList<Relatives> retrieveSpecificRelatives(String username) {
        RealmResults<Relatives> spacecrafts = realm.where(Relatives.class)
                .equalTo("username", username.toLowerCase())
                .findAll();
        return new ArrayList<>(spacecrafts);
    }

    //LOGIN
    public ArrayList<Users> login(String email, String password) {
        RealmResults<Users> spacecrafts = realm.where(Users.class)
                .equalTo("email", email)
                .equalTo("password", password)
                .findAll();
        return new ArrayList<>(spacecrafts);
    }

    //CHECKS
    public boolean checkIfExists(String phone, String email) {
        RealmQuery<Users> query = realm.where(Users.class)
                .equalTo("phone", phone)
                .equalTo("email", email);
        return query.count() == 0 ? false : true;
    }

    public boolean checkIfRelativeExists(String phone, String email) {
        RealmQuery<Relatives> query = realm.where(Relatives.class)
                .equalTo("phone", phone)
                .equalTo("email", email);
        return query.count() == 0 ? false : true;
    }

    public boolean checkIfKeywordExists(String keyword) {
        RealmQuery<Keywords> query = realm.where(Keywords.class)
                .equalTo("keyword", keyword);
        return query.count() == 0 ? false : true;
    }

    //DELETE
    public void deleteRelative(final String phone, final String email) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Relatives> result = realm.where(Relatives.class)
                        .equalTo("phone",phone)
                        .equalTo("email",email)
                        .findAll();
                result.deleteAllFromRealm();
            }
        });
    }

    public void deleteKeyword(final String keyword) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Keywords> result = realm.where(Keywords.class)
                        .equalTo("keyword",keyword)
                        .findAll();
                result.deleteAllFromRealm();
            }
        });
    }

}