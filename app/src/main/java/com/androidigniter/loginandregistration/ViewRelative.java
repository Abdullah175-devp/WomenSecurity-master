package com.androidigniter.loginandregistration;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidigniter.loginandregistration.adapters.RelativesAdapter;
import com.androidigniter.loginandregistration.controllers.RealmController;
import com.androidigniter.loginandregistration.models.Relatives;
import com.androidigniter.loginandregistration.utils.UtilsProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ViewRelative extends AppCompatActivity {

    RealmController helper;
    private Realm realm;
    private static final String KEY_EMPTY = "";
    private static final int REQUEST_CALL = 1;
    RecyclerView rvRelatives;
    RelativesAdapter relativesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_relative);

        RealmConfiguration config = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(config);//RETRIEVE
        helper = new RealmController(realm);

        rvRelatives = (RecyclerView) findViewById(R.id.rvRelatives);
        rvRelatives.setLayoutManager(new LinearLayoutManager(this));
        relativesAdapter = new RelativesAdapter(this, helper.retrieveRelatives(), helper);
        rvRelatives.setAdapter(relativesAdapter);

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        int action, keycode;

        action = event.getAction();
        keycode = event.getKeyCode();

        switch (keycode) {
            case KeyEvent.KEYCODE_VOLUME_UP: {
                if (KeyEvent.ACTION_UP == action) {

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"));
                    if (ActivityCompat.checkSelfPermission(ViewRelative.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ViewRelative.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);

                    } else {
                        callIntent.setData(Uri.parse("tel:"));
                        startActivity(callIntent);
                    }
                    startActivity(callIntent);
                }
            }
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (KeyEvent.ACTION_DOWN == action) {
                    //count = 0;
                    //String S2 = String.valueOf(count);
                    //Log.d("downButton", S2);
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                   callIntent.setData(Uri.parse("tel:3362888146"));


                    System.out.println("callintentttt" + callIntent);
                    if (ActivityCompat.checkSelfPermission(ViewRelative.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ViewRelative.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);

                    } else {
                        callIntent.setData(Uri.parse("tel:"));
                        startActivity(callIntent);
                    }
                    startActivity(callIntent);
                }
        }
        return super.dispatchKeyEvent(event);
    }
}

