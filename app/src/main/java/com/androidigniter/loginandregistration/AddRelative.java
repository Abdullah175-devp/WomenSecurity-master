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
import com.androidigniter.loginandregistration.models.Relatives;
import com.androidigniter.loginandregistration.models.Users;
import com.androidigniter.loginandregistration.utils.UtilsProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;

//import android.support.v7.app.AppCompatActivity;

public class AddRelative extends AppCompatActivity {

    String phone_regex = "^((\\+92)|(0092))-{0,1}\\d{3}-{0,1}\\d{7}$|^\\d{11}$|^\\d{4}-\\d{7}$";
    RealmController helper;
    private Realm realm;
    private static final String KEY_EMPTY = "";
    private static final int REQUEST_CALL = 1;
    Button btnAdd,btnView;
    EditText etName, etEmail, etPhone;

    private String username;
    private String email;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_relative);

        RealmConfiguration config=new RealmConfiguration.Builder().build();
        realm=Realm.getInstance(config);//RETRIEVE
        helper=new RealmController(realm);

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPhone = (EditText) findViewById(R.id.etPhone);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnView = (Button) findViewById(R.id.btnView);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = etName.getText().toString();
                email = etEmail.getText().toString();
                phone = etPhone.getText().toString();
                if (validateInputs()) {
                    registerRelative();
                }

            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (helper.retrieveRelatives().size()>0) {
                    Intent intent = new Intent(AddRelative.this, ViewRelative.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(AddRelative.this, "No relatives registered yet", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void registerRelative() {
        UtilsProvider.displayLoader(this);
        Relatives relatives = new Relatives();
        relatives.setId(helper.retrieveRelatives().size() + 1);
        relatives.setName(username.toLowerCase());
        relatives.setEmail(email);
        relatives.setPhone(phone);
        if (!helper.checkIfRelativeExists(phone, email)) {
            helper.saveRelative(relatives);
            etName.setText("");
            etEmail.setText("");
            etPhone.setText("");
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Phone or email already exists", Toast.LENGTH_SHORT).show();
        }
        UtilsProvider.pDialog.dismiss();
    }

    private boolean validateInputs() {
        if (KEY_EMPTY.equals(username)) {
            etName.setError("username cannot be empty");
            etName.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(email)) {
            etEmail.setError("email cannot be empty");
            etEmail.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(phone)) {
            etPhone.setError("phone cannot be empty");
            etPhone.requestFocus();
            return false;
        }
        if (!phone.matches(phone_regex)) {
            etPhone.setError("invalid phone number");
            etPhone.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        int action, keycode;

        action = event.getAction();
        keycode = event.getKeyCode();

        switch (keycode)
        {
            case KeyEvent.KEYCODE_VOLUME_UP:
                {
                if(KeyEvent.ACTION_UP == action){

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"));
                    if (ActivityCompat.checkSelfPermission(AddRelative.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(AddRelative.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);

                    } else {
                        callIntent.setData(Uri.parse("tel:"));
                        startActivity(callIntent);
                    }
                    startActivity(callIntent);
                }
            }
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if(KeyEvent.ACTION_DOWN == action){
                    //count = 0;
                    //String S2 = String.valueOf(count);
                    //Log.d("downButton", S2);
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"));
                    if (ActivityCompat.checkSelfPermission(AddRelative.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(AddRelative.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                        System.out.println(AddRelative.this );

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

