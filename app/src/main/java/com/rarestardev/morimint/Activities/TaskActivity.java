package com.rarestardev.morimint.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.rarestardev.morimint.Constants.UserConstants;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.ViewModel.ApplicationDataViewModel;
import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.adsbase.StartAppSDK;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TaskActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    AppCompatTextView tvNoData, tvWarning, warning_frame;
    ApplicationDataViewModel applicationDataViewModel;
    private static final String ADS_TAG = "StartApp";
    Handler handler = new Handler();
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isTabletMode()) {
            setContentView(R.layout.activity_task_tablet);
        } else {
            setContentView(R.layout.activity_task);
        }

        recyclerView = findViewById(R.id.recyclerView);
        tvNoData = findViewById(R.id.tvNoData);
        tvWarning = findViewById(R.id.tvWarning);
        warning_frame = findViewById(R.id.warning_frame);

        tvWarning.setText(readFromAssets());


        runnable = () -> {
            YoYo.with(Techniques.Flash).duration(500).playOn(warning_frame);

            handler.postDelayed(runnable, 1000);
        };

        handler.post(runnable);


        applicationDataViewModel = new ViewModelProvider(this).get(ApplicationDataViewModel.class);
        applicationDataViewModel.getTasks(TaskActivity.this, recyclerView, tvNoData);


        StartAppSDK.init(this, UserConstants.startAppId, true);
        StartAppSDK.setTestAdsEnabled(UserConstants.startAppIsTested);
        Banner startAppBanner = findViewById(R.id.startapp_banner);
        Log.d(ADS_TAG, startAppBanner + "");
        startAppBanner.loadAd();

    }


    private String readFromAssets() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream is = getAssets().open("warning_text.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append('\n');
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.post(runnable);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    private boolean isTabletMode() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;

        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);

        return diagonalInches >= 7.0; // Tablet 7 inches
    }
}