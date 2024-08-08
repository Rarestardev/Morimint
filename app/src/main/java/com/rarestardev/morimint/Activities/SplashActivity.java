package com.rarestardev.morimint.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.rarestardev.morimint.Constants.UserConstants;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.Utilities.InternetConnection;

import cn.pedant.SweetAlert.SweetAlertDialog;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DURATION = 2500;
    private static final int NOTIFICATION_PERMISSION_CODE = 100;

    @SuppressLint({"SetTextI18n", "MissingPermission"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        InternetConnection connection = new InternetConnection();
        if (connection.isConnectedNetwork(this)) {

            checkPermissions();

        } else {
            SweetAlertDialog dialog = new SweetAlertDialog(SplashActivity.this, SweetAlertDialog.ERROR_TYPE);
            dialog.setCancelable(false);
            dialog.setTitle("No Internet!");
            dialog.setContentText("Please check the internet connection");
            dialog.setConfirmButton("Exit", sweetAlertDialog -> {
                finish();
                dialog.dismiss();
            });
            dialog.show();
        }

    }

    private void checkPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_CODE);
            }
        }else {
            CheckUserAndStartActivity();
            Log.d(UserConstants.APP_LOG_TAG,"Notification granted");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NOTIFICATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                CheckUserAndStartActivity();
                Log.d(UserConstants.APP_LOG_TAG,"Notification granted");
            }else {
                Log.d(UserConstants.APP_LOG_TAG,"Notification denied");
                checkPermissions();
            }
        }
    }


    private void CheckUserAndStartActivity(){
        new Handler().postDelayed(() -> {

            SharedPreferences preferences = getSharedPreferences(UserConstants.SHARED_PREF_USER, MODE_PRIVATE);
            String Token = preferences.getString(UserConstants.SHARED_KEY_TOKEN, "");

            Intent intent2;
            if (Token.isEmpty()) {
                intent2 = new Intent(SplashActivity.this, SignUpActivity.class);
            } else {
                intent2 = new Intent(SplashActivity.this, MainActivity.class);
            }
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent2);

        }, SPLASH_DURATION);
    }
}