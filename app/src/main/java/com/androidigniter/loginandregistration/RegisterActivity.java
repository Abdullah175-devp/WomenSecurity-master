package com.androidigniter.loginandregistration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidigniter.loginandregistration.controllers.RealmController;
import com.androidigniter.loginandregistration.models.Users;
import com.androidigniter.loginandregistration.utils.UtilsProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RegisterActivity extends AppCompatActivity {

    RealmController helper;
    private Realm realm;
    private static final String KEY_EMPTY = "";

    private EditText etUsername;
    private EditText etPassword;
    private EditText etEmail;
    private EditText etPhone;

    private String username;
    private String password;
    private String phone;
    private String email;

    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());
        setContentView(R.layout.activity_register);

        RealmConfiguration config=new RealmConfiguration.Builder().build();
        realm=Realm.getInstance(config);//RETRIEVE
        helper=new RealmController(realm);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);

        Button login = findViewById(R.id.btnRegisterLogin);
        Button register = findViewById(R.id.btnRegister);

        //Launch Login screen when Login Button is clicked
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        Log.d("sizeis", helper.retrieve().size()+"aaaa");
//        Log.d("sizeis", String.valueOf(helper.retrieve().get(0)));

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                username = etUsername.getText().toString().toLowerCase().trim();
                                                                                                                                                                                                        password = etPassword.getText().toString().trim();
                phone = etPhone.getText().toString().trim();
                email = etEmail.getText().toString().trim();
                if (validateInputs()) {
                    registerUser();
                }

            }
        });

    }

    private void loadDashboard() {
        Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(i);
        finish();
    }

    private void registerUser() {
        UtilsProvider.displayLoader(this);
        Users users = new Users();
        users.setId(helper.retrieve().size() + 1);
        users.setName(username);
        users.setEmail(email);
        users.setPassword(password);
        users.setPhone(phone);
        if (!helper.checkIfExists(phone, email)) {
            helper.save(users);
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Phone or email already exists", Toast.LENGTH_SHORT).show();
        }
        UtilsProvider.pDialog.dismiss();
    }

    private boolean validateInputs() {
        if (KEY_EMPTY.equals(username)) {
            etUsername.setError("username cannot be empty");
            etUsername.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(password)) {
            etPassword.setError("password cannot be empty");
            etPassword.requestFocus();
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

        return true;
    }
}
