package com.rarestardev.morimint.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.rarestardev.morimint.Constants.UserConstants;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.Utilities.InternetConnection;

import cn.pedant.SweetAlert.SweetAlertDialog;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DURATION = 2500;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        // دریافت FCM Token
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("FFM", "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // دریافت Token
                    String token = task.getResult();
                    Log.d("FFM", "FCM Token: " + token);

                    // ارسال Token به سرور (در صورت نیاز)
                });

        InternetConnection connection = new InternetConnection();
        if (connection.isConnectedNetwork(this)) {
            new Handler().postDelayed(() -> {

                SharedPreferences preferences = getSharedPreferences(UserConstants.SHARED_PREF_USER, MODE_PRIVATE);
                String Token = preferences.getString(UserConstants.SHARED_KEY_TOKEN, "");

                Intent intent;
                if (Token.isEmpty()) {
                    intent = new Intent(SplashActivity.this, SignUpActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            }, SPLASH_DURATION);
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
}