package com.androidigniter.loginandregistration;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidigniter.loginandregistration.controllers.RealmController;
import com.androidigniter.loginandregistration.models.Users;
import com.androidigniter.loginandregistration.shakeit.AccelerometerManager;
import com.androidigniter.loginandregistration.shakeit.ShakeIt;
import com.androidigniter.loginandregistration.shakeit.ShakeListener;
import com.androidigniter.loginandregistration.shakeit.ShakeService;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static com.androidigniter.loginandregistration.utils.UtilsProvider.sendSMS;

public class DashboardActivity extends AppCompatActivity {

    double latitude = 0.0, longitude = 0.0;
    private SessionHandler session;
    RealmController helper;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();
        session = new SessionHandler(getApplicationContext());

        RealmConfiguration config = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(config);//RETRIEVE
        helper = new RealmController(realm);

        Users user = session.getUserDetails();
        TextView welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText("Welcome " + user.getName());

        Button logoutBtn = findViewById(R.id.btnLogout);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
                Intent i = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(i);
                finish();

            }
        });

        if (AccelerometerManager.isSupported(this)) {
            ShakeIt.initializeShakeService(this, 20, 600, new ShakeListener() {
                @Override
                public void onShake(float force) {
                    //shakeButtons();
                    Log.d("shakelstn in Dashboard", "Shake Class called 1");
                    //----------------- send sms code changed
      /*              if (canGetLocation()) {
                        latitude = ShakeService.getLatitude();
                        longitude = ShakeService.getLongitude();

                        Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                    } else {
                        showSettingsAlert();
                    }*/
                    //------------------------------------------------  Code Commented---------------------------//
                    //------------------- Code need to be unhide for any thing
                /*    for (int i=0; i<helper.retrieveRelatives().size(); i++){
                        sendSMS(helper.retrieveRelatives().get(i).getPhone(), "I am in danger and need your help. My current Location is - \nLat: " + latitude + "\nLong: " + longitude, DashboardActivity.this);
                    }*/
                }

                @Override
                public void onAccelerationChanged(float x, float y, float z) {
                    // Log.d("shakelstn", "called 2");
                }
            });
        } else {
            Log.d("shakelstn", "no");
        }
    }

    public void addRelative(View v) {
        Intent i = new Intent(getApplicationContext(), AddRelative.class);
        startActivity(i);
    }

    public void helplineNumbers(View v) {
        Intent i = new Intent(getApplicationContext(), helplineCall.class);
        startActivity(i);
    }

    public void HowTo(View v) {
        Intent i = new Intent(getApplicationContext(), HowToSwipe.class);
        startActivity(i);
    }

    public void triggers(View v) {
        Intent i = new Intent(getApplicationContext(), AddKeywords.class);
        startActivity(i);
    }

    public void developedBy(View v) {
        Intent i = new Intent(getApplicationContext(), DeveloperByActivity.class);
        startActivity(i);
    }

    public void LogOut(View v) {
        session.logoutUser();
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
        finish();
    }

    public boolean canGetLocation() {
        return ShakeService.canGetLocation;
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing the Settings button.
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // On pressing the cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    //---------------Commented Code added------------------------//
    public void smssendmethod() {
        Log.d("shakelstn in Dashboard", "called once test only");
        // send sms code changed

        //------------------------------------------------  Code Commented---------------------------//
        // Code need to be unhide for any thing


}

}
