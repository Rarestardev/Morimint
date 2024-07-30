package com.rarestardev.morimint.Utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.widget.TextView;

public class CountdownTimer {

    private Context context;
    private CountDownTimer countDownTimer;

    private long totalTime = 86400000;// 24 hour
    private static final String SHARED_TIMER_NAME = "CountdownTimer";
    private static final String SHARED_TIMER_KEY = "timeLeft";

    public CountdownTimer(Context context) {
        this.context = context;
    }


    public void initTimer(){
        
    }


    private void StartTimer(){
        countDownTimer = new CountDownTimer(totalTime,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                totalTime = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                SetTurboValue();
                SetJackpotPlayed();
            }
        };
    }

    @SuppressLint("DefaultLocale")
    public void setTimeInTextView(TextView textView){
        int hours = (int) (totalTime / 1000) / 3600;
        int minutes = (int) ((totalTime / 1000) % 3600) / 60;
        int seconds = (int) (totalTime / 1000) % 60;
        String timeLeftFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        textView.setText(timeLeftFormatted);
    }

    private void SetJackpotPlayed() {

    }

    private void SetTurboValue() {

    }


    public void OnStopActivity(){
        SharedPreferences preferences = context.getSharedPreferences(SHARED_TIMER_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(SHARED_TIMER_KEY, totalTime);
        editor.apply();

        countDownTimer.cancel();
    }

    public void OnResumeActivity(){
        SharedPreferences preferences = context.getSharedPreferences(SHARED_TIMER_NAME,Context.MODE_PRIVATE);
        totalTime = preferences.getLong(SHARED_TIMER_KEY, totalTime);
        StartTimer();
    }
}
