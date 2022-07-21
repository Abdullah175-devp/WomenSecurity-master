package com.androidigniter.loginandregistration;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidigniter.loginandregistration.adapters.KeywordsAdapter;
import com.androidigniter.loginandregistration.adapters.RelativesAdapter;
import com.androidigniter.loginandregistration.controllers.RealmController;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ViewKeywords extends AppCompatActivity {

    RealmController helper;
    private Realm realm;
    private static final String KEY_EMPTY = "";
    RecyclerView rvRelatives;
    KeywordsAdapter relativesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_relative);

        RealmConfiguration config = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(config);//RETRIEVE
        helper = new RealmController(realm);

        rvRelatives = (RecyclerView) findViewById(R.id.rvRelatives);
        rvRelatives.setLayoutManager(new LinearLayoutManager(this));
        relativesAdapter = new KeywordsAdapter(this, helper.retrieveKeywords(), helper);
        rvRelatives.setAdapter(relativesAdapter);

    }

}

