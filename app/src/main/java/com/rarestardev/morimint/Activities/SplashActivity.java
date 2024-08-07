package com.rarestardev.morimint.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import com.rarestardev.morimint.Constants.UserConstants;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.Utilities.InternetConnection;

import cn.pedant.SweetAlert.SweetAlertDialog;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DURATION = 2500;

    @SuppressLint({"SetTextI18n", "MissingPermission"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        InternetConnection connection = new InternetConnection();
        if (connection.isConnectedNetwork(this)) {
            new Handler().postDelayed(() -> {

                SharedPreferences preferences = getSharedPreferences(UserConstants.SHARED_PREF_USER, MODE_PRIVATE);
                String Token = preferences.getString(UserConstants.SHARED_KEY_TOKEN, "");

                Intent intent2;
                if (Token.isEmpty()) {
                    intent2 = new Intent(SplashActivity.this, SignUpActivity.class);
                } else {
                    intent2 = new Intent(SplashActivity.this, MainActivity.class);

                    Intent notificationIntent = new Intent(this, MainActivity.class);
                    notificationIntent.putExtra("from_notification", true);
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
                    Notification notification = new NotificationCompat.Builder(this, "CHANNEL_ID")
                            .setContentText("Content")
                            .setContentTitle("Title")
                            .setSmallIcon(R.drawable.morimint_app_icon)
                            .setContentIntent(pendingIntent)
                            .build();

                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
                    notificationManagerCompat.notify(0, notification);


                }
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);

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