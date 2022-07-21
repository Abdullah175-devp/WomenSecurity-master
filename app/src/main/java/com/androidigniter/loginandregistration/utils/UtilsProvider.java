package com.androidigniter.loginandregistration.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.gsm.SmsManager;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.androidigniter.loginandregistration.DashboardActivity;
import com.androidigniter.loginandregistration.PermissionsDetail;
import com.androidigniter.loginandregistration.RegisterActivity;

public class UtilsProvider {

    public static ProgressDialog pDialog;
    private static int Count=0;


    public static void displayLoader(Context context) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public static void sendSMS(String phoneNo, String msg, Context context) {

        try {
            if(Count<=10) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNo, null, msg, null, null);
                Toast.makeText(context, "Phone# " + phoneNo + " Msg=" + msg, Toast.LENGTH_SHORT).show();
            Count++;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void requestPermissions(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                ((Activity)context).requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.SEND_SMS
                }, 0);
            }else{
                Intent intent = new Intent(context, DashboardActivity.class);
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        }else {
            Intent intent = new Intent(context, DashboardActivity.class);
            context.startActivity(intent);
            ((Activity)context).finish();
        }
    }

    public static void checkPermissions(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                PermissionsDetail.launchMe(context);
            }else{
                Intent intent = new Intent(context, DashboardActivity.class);
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        }else {
            Intent intent = new Intent(context, DashboardActivity.class);
            context.startActivity(intent);
            ((Activity)context).finish();
        }
    }

}
