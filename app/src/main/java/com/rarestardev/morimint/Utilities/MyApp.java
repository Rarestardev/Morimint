package com.rarestardev.morimint.Utilities;

import android.app.Application;
import android.util.Log;

import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MyApp extends Application {

    private static final String TAG = "DailyCheckWorker";
    @Override
    public void onCreate() {
        super.onCreate();
        scheduleDailyCheckWorker();
    }

    private void scheduleDailyCheckWorker() {
        WorkRequest dailyWorkRequest =
                new PeriodicWorkRequest.Builder(DailyCheckWorker.class, 1, TimeUnit.DAYS)
                        .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
                        .build();

        Log.d(TAG, "Worker is running");
        WorkManager.getInstance(this).enqueue(dailyWorkRequest);
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