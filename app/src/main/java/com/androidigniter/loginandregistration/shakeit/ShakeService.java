package com.androidigniter.loginandregistration.shakeit;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;

import com.androidigniter.loginandregistration.DashboardActivity;
import com.androidigniter.loginandregistration.R;
import com.androidigniter.loginandregistration.controllers.RealmController;

import java.util.ArrayList;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static com.androidigniter.loginandregistration.utils.UtilsProvider.sendSMS;

public class ShakeService extends Service implements LocationListener {

    public static boolean isGPSEnabled = false;
    public static boolean isNetworkEnabled = false;
    public static boolean canGetLocation = false;
    static Location location; // Location
    static double latitude; // Latitude
    static double longitude; // Longitude
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    protected LocationManager locationManager;
    DashboardActivity dashboardActivity= new DashboardActivity();


    RealmController helper;
    private Realm realm;

    int volumePrev = 0;
    public Intent speechRecognizerIntent;
    public static final Integer RecordAudioRequestCode = 1;
    private SpeechRecognizer speechRecognizer;
    private Context context = this;

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            startMyOwnForeground();
        } else {
            startForeground(1, new Notification());
        }

        RealmConfiguration config = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(config);//RETRIEVE
        helper = new RealmController(realm);

    }

    private BroadcastReceiver volBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if ("android.media.VOLUME_CHANGED_ACTION".equals(intent.getAction())) {

                int volume = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", 0);
  // --------------------commented  Code changed for test case---------------------------------------//
              //  recognizeSpeech();


                if (volumePrev < volume) {
                    Log.i("prsdbtn", "You have pressed volume up button");
                    //-------------Commented Code added------------------------//
                   dashboardActivity.smssendmethod();
                    for (int i=0; i<helper.retrieveRelatives().size(); i++){
                        sendSMS(helper.retrieveRelatives().get(i).getPhone(), " My current Location is - \nLat: " + latitude + "\nLong: " + longitude, ShakeService.this);
                    }
                } else {
                    Log.i("prsdbtn", "You have pressed volume down button");
                    for (int i=0; i<helper.retrieveRelatives().size(); i++){
                        sendSMS(helper.retrieveRelatives().get(i).getPhone(), " My current Location is - \nLat: " + latitude + "\nLong: " + longitude, ShakeService.this);
                    }

                }
                volumePrev = volume;
            }
        }
    };

    private void recognizeSpeech() {
        Log.d("speechresultis", "is disabled inside method");
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {
            }

            @Override
            public void onBeginningOfSpeech() {
            }

            @Override
            public void onRmsChanged(float v) {
            }

            @Override
            public void onBufferReceived(byte[] bytes) {
            }

            @Override
            public void onEndOfSpeech() {
            }

            @Override
            public void onError(int i) {
            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                assert data != null;
                if (data.size() > 0) {
                    if (helper.retrieveKeywords().size()>0){
                        for (int i=0; i<helper.retrieveKeywords().size(); i++){
                            if (helper.retrieveKeywords().get(i).getKeyword().toLowerCase().trim().startsWith(data.get(0).toLowerCase().trim())){
                                for (int a=0; a<helper.retrieveRelatives().size(); a++){
                                    sendSMS(helper.retrieveRelatives().get(a).getPhone(), "I am in danger and need your help. My current Location is - \nLat: " + latitude + "\nLong: " + longitude, context);
                                }
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {
            }

            @Override
            public void onEvent(int i, Bundle bundle) {
            }
        });
        speechRecognizer.startListening(speechRecognizerIntent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (ShakeManager.isSupported(context)) {
            // Start Accelerometer Listening
            ShakeManager.startListening(ShakeIt.shakeListener,
                    ShakeIt.threshold, ShakeIt.interval);
        }

        // Volume btns broadcast
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.media.VOLUME_CHANGED_ACTION");
        registerReceiver(volBroadcastReceiver, filter);

        getLocation();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onDestroy() {
        // Check device supported Accelerometer sensor or not
        if (ShakeManager.isListening()) {
            ShakeManager.stopListening();
        }
        speechRecognizer.destroy();
        unregisterReceiver(volBroadcastReceiver);
        super.onDestroy();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void startMyOwnForeground() {
        String NOTIFICATION_CHANNEL_ID = "example.permanence";
        String channelName = "Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setAutoCancel(true)
                .build();
        startForeground(2, notification);
    }




    public Location getLocation() {
        try {
            locationManager = (LocationManager) context
                    .getSystemService(LOCATION_SERVICE);

            // Getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // Getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // No network provider is enabled
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // If GPS enabled, get latitude/longitude using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    public static double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public static double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }
        return longitude;
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

}
