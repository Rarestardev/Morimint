package com.rarestardev.morimint.Utilities;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.rarestardev.morimint.Constants.UserConstants;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setupInitialValue();
        scheduleDailyCheckWorker();
    }

    private void setupInitialValue() {
        SharedPreferences sharedPreferences = getSharedPreferences("Worker", MODE_PRIVATE);
        int value = sharedPreferences.getInt("turbo", 0);
        int jackpot = sharedPreferences.getInt("jackpot", 0);
        int jackpotAds = sharedPreferences.getInt("jackpotAds", 0);
        if (value == 0 && jackpot == 0 && jackpotAds == 0) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("turbo", UserConstants.TurboCountCharge);
            editor.putInt("jackpot", UserConstants.JackpotPlayed);
            editor.putInt("jackpotAds", UserConstants.JackpotPlayedAds);
            editor.apply();

            OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(DailyCheckWorker.class).build();
            WorkManager.getInstance(this).enqueue(oneTimeWorkRequest);
        }
    }

    private void scheduleDailyCheckWorker() {
        PeriodicWorkRequest dailyWorkRequest =
                new PeriodicWorkRequest.Builder(DailyCheckWorker.class, 1, TimeUnit.DAYS)
                        .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
                        .build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "dailyCheckWork",
                ExistingPeriodicWorkPolicy.KEEP,
                dailyWorkRequest);
    }

    private long calculateInitialDelay() {
        Calendar currentTime = Calendar.getInstance();
        Calendar midnightTime = Calendar.getInstance();
        midnightTime.set(Calendar.HOUR_OF_DAY, 0);
        midnightTime.set(Calendar.MINUTE, 0);
        midnightTime.set(Calendar.SECOND, 0);
        midnightTime.set(Calendar.MILLISECOND, 0);

        if (currentTime.after(midnightTime)) {
            midnightTime.add(Calendar.DAY_OF_MONTH, 1);
        }

        return midnightTime.getTimeInMillis() - currentTime.getTimeInMillis();
    }
}