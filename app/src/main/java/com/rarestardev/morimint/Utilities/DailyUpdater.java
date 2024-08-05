package com.rarestardev.morimint.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.DateFormat;

import com.rarestardev.morimint.Constants.UserConstants;

import java.util.Date;

public class DailyUpdater {

    private static final String PREFS_NAME = "DailyUpdater";
    private static final String LAST_UPDATE = "last_update";
    SharedPreferences sharedPreferences;

    public DailyUpdater(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void updateValueIfNeeded() {
        String lastUpdate = sharedPreferences.getString(LAST_UPDATE, null);
        String today = getCurrentDate();

        if (lastUpdate == null || !lastUpdate.equals(today)) {
            updateValue();
            saveCurrentDate(today);
        }
    }

    private void updateValue() {
        int value = sharedPreferences.getInt("turbo", 0);
        int valueJackpot = sharedPreferences.getInt("jackpot", 0);
        int valueJackpotAds = sharedPreferences.getInt("jackpotAds", 0);
        int moreReward = sharedPreferences.getInt("reward", 0);
        if (value != UserConstants.TurboCountCharge && valueJackpot != UserConstants.JackpotPlayed &&
                valueJackpotAds != UserConstants.JackpotPlayedAds && moreReward != 3) {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("turbo", UserConstants.TurboCountCharge);
            editor.putInt("jackpot", UserConstants.JackpotPlayed);
            editor.putInt("jackpotAds", UserConstants.JackpotPlayedAds);
            editor.putInt("reward", 3);
            editor.apply();
        }
    }

    private void saveCurrentDate(String date) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LAST_UPDATE, date);
        editor.apply();
    }

    private String getCurrentDate() {
        return DateFormat.format("yyyyMMdd", new Date()).toString();
    }
}
