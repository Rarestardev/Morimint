package com.rarestardev.morimint.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.util.Log;

import com.rarestardev.morimint.Activities.MainActivity;

public class RewardTimer {

    Context context;
    private CountDownTimer countDownTimer;

    //private long totalTime = 86400000;// 24 hour
    private long totalTime = 20000;// 24 hour
    private static final String SHARED_TIMER_NAME = "RewardTimer";
    private static final String SHARED_TIMER_KEY = "timeLeft";
    private static final String SHARED_TIMER_KEY_Turbo = "Turbo";

    public RewardTimer(Context context) {
        this.context = context;
    }

    public void StartTimer() {
        countDownTimer = new CountDownTimer(totalTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                totalTime = millisUntilFinished;

                int seconds = (int) (millisUntilFinished / 1000) % 60;

                Log.i("CountTimer" + ":",seconds + "");
            }
            @Override
            public void onFinish() {
                SharedPreferences preferences = context.getSharedPreferences(SHARED_TIMER_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(SHARED_TIMER_KEY_Turbo,2);
                editor.apply();
                ((MainActivity) context).getTurboCount();

                countDownTimer.start();
            }
        };
        countDownTimer.start();
    }

    public void OnStopActivity() {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_TIMER_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(SHARED_TIMER_KEY, totalTime);
        editor.apply();
        countDownTimer.cancel();
    }

    public void OnResumeActivity() {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_TIMER_NAME, Context.MODE_PRIVATE);
        long time = preferences.getLong(SHARED_TIMER_KEY, 0);
        if (time == 0) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(SHARED_TIMER_KEY_Turbo , 2);
            editor.apply();
            StartTimer();
        } else {
            long systemTime = System.currentTimeMillis();
            totalTime = time - systemTime;

            if (totalTime == 0){
                StartTimer();
            }
        }
    }
}
