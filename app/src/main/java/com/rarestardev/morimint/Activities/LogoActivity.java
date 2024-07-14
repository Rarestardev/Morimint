package com.rarestardev.morimint.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.rarestardev.morimint.Constants.UserConstants;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.Utilities.InternetConnection;

public class LogoActivity extends AppCompatActivity {
    private static final int SPLASH_DURATION = 2500;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        InternetConnection connection = new InternetConnection();
        if (connection.isConnectedNetwork(this)){
            new Handler().postDelayed(() -> {

                SharedPreferences preferences = getSharedPreferences(UserConstants.SHARED_PREF_USER,MODE_PRIVATE);
                String Token = preferences.getString(UserConstants.SHARED_KEY_TOKEN,"");

                Intent intent;
                if (Token.isEmpty()){
                    intent = new Intent(LogoActivity.this, SignUpActivity.class);
                }else {
                    intent = new Intent(LogoActivity.this, MainActivity.class);
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            }, SPLASH_DURATION);
        }else {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.alert_dialog_no_internet);
            dialog.show();

            AppCompatButton close_dialog = dialog.findViewById(R.id.close_dialog);
            close_dialog.setOnClickListener(v -> {
                dialog.dismiss();
                finish();
            });
        }

    }
}