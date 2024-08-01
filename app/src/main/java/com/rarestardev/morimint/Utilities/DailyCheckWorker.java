package com.rarestardev.morimint.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class DailyCheckWorker extends Worker {

    private static final String TAG = "DailyCheckWorker";

    public DailyCheckWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "Worker is running");
        checkAndUpdateValue();
        Log.d(TAG, "Value checked and updated if necessary");
        return Result.success();
    }

    private void checkAndUpdateValue() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Worker", Context.MODE_PRIVATE);
        int value = sharedPreferences.getInt("value", 0);
        if (value != 2) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("value", 2);
            editor.apply();
        }
    }
}
