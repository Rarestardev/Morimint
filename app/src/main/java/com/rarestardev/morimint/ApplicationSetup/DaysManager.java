package com.rarestardev.morimint.ApplicationSetup;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

public class DaysManager {

    private long currentDay = 0;

    private Context context;

    public DaysManager(Context context) {
        this.context = context;
    }


    public void getCurrentDay(long day){
        currentDay = day;

        SharedPreferences preferences = context.getSharedPreferences("Days",Context.MODE_PRIVATE);

        long Day = preferences.getLong("Day",-1);

        if (Day > currentDay -1){
            //Toast.makeText(context, "Ok", Toast.LENGTH_SHORT).show();
        }

    }
}
