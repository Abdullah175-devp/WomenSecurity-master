package com.androidigniter.loginandregistration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.androidigniter.loginandregistration.utils.UtilsProvider;

public class PermissionsDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions_detail);
    }

    public void grantAll(View view){
        UtilsProvider.requestPermissions(PermissionsDetail.this);
    }

    public static void launchMe(Context context){
        Intent intent = new Intent(context, PermissionsDetail.class);
        context.startActivity(intent);
        ((Activity)context).finish();
    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        UtilsProvider.requestPermissions(PermissionsDetail.this);
    }

}