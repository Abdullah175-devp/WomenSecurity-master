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

import com.androidigniter.loginandregistration.controllers.RealmController;
import com.androidigniter.loginandregistration.models.Keywords;
import com.androidigniter.loginandregistration.models.Relatives;
import com.androidigniter.loginandregistration.utils.UtilsProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;

//import android.support.v7.app.AppCompatActivity;

public class AddKeywords extends AppCompatActivity {

    RealmController helper;
    private Realm realm;
    Button btnAdd,btnView;
    EditText etKeyword;

    private String keyword;
    private String KEY_EMPTY = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_keyword);

        RealmConfiguration config=new RealmConfiguration.Builder().build();
        realm=Realm.getInstance(config);//RETRIEVE
        helper=new RealmController(realm);

        etKeyword = (EditText) findViewById(R.id.etKeyword);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnView = (Button) findViewById(R.id.btnView);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword = etKeyword.getText().toString();
                if (validateInputs()) {
                    registerKeyword();
                }

            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (helper.retrieveKeywords().size()>0) {
                    Intent intent = new Intent(AddKeywords.this, ViewKeywords.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(AddKeywords.this, "No relatives registered yet", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void registerKeyword() {
        UtilsProvider.displayLoader(this);
        Keywords relatives = new Keywords();
        relatives.setId(helper.retrieveKeywords().size() + 1);
        relatives.setKeyword(keyword.toLowerCase());
        if (!helper.checkIfKeywordExists(keyword)) {
            helper.saveKeyword(relatives);
            etKeyword.setText("");
            Toast.makeText(this, "Keyword registered successful", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Keyword already exists", Toast.LENGTH_SHORT).show();
        }
        UtilsProvider.pDialog.dismiss();
    }

    private boolean validateInputs() {
        if (KEY_EMPTY.equals(keyword)) {
            etKeyword.setError("keyword cannot be empty");
            etKeyword.requestFocus();
            return false;
        }

        return true;
    }

}