package com.rarestardev.morimint.ApplicationSetup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class EnergyManager {

    private int level_text = 0;
    private int maxEnergyWithLevelFromServer = 0;
    private boolean clicked;

    private static final int[] LEVEL_ENERGY = {1000, 2000, 3000, 4000, 5000, 6000,
            7000, 8000, 9000, 10000, 11000, 12000, 14000, 16000, 18000};

    private static final String PREF_ENERGY_MANGER = "Energy Manager";
    private static final String PREF_ENERGY_MAX = "MaxEnergy";
    private static final String PREF_ENERGY = "Energy";
    private static final String PREF_LEVEL = "LEVEL";
    private static final String PREF_KEY_CURRENT_TIME = "LastTime";

    private int maxEnergy = 0;
    private static final int minEnergy = 0;
    private int valueEnergy;

    private final Context context;
    private final TextView tvLevel;
    private final TextView tvEnergy;
    AppCompatImageView icon_energy;

    private final Handler handler = new Handler();
    private Runnable runnable;

    public EnergyManager(Context context, TextView tvLevel, TextView tvEnergy, AppCompatImageView icon_energy) {
        this.tvLevel = tvLevel;
        this.icon_energy = icon_energy;
        this.tvEnergy = tvEnergy;
        this.context = context;
    }

    @SuppressLint("SetTextI18n")
    public void getLevelFromServer(int level) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_ENERGY_MANGER, Context.MODE_PRIVATE);
        int local_level = preferences.getInt(PREF_LEVEL, 0);
        SetEnergyWithLevelFromServer(level);
        if (local_level == level) {
            maxEnergy = preferences.getInt(PREF_ENERGY_MAX, 1000);
            valueEnergy = maxEnergy;

            int new_energy = preferences.getInt("current" + PREF_ENERGY, 0);
            long current_time = preferences.getLong(PREF_KEY_CURRENT_TIME, 0);
            long time = System.currentTimeMillis();

            if (new_energy == 0 && new_energy == maxEnergy) {
                valueEnergy = maxEnergy;
            } else {
                valueEnergy = new_energy;
            }

            final int step = 3;

            if (current_time != -1) {
                long total_sec = (time - current_time) / 1000;
                int added_energy = (int) (total_sec * step);

                valueEnergy = new_energy + added_energy;

                if (valueEnergy > maxEnergy) {
                    valueEnergy = maxEnergy;
                }
                if (total_sec >= 3600) {
                    valueEnergy = maxEnergy;
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("current" + PREF_ENERGY, valueEnergy);
                    editor.putLong(PREF_KEY_CURRENT_TIME, 0);
                    editor.apply();
                }


                tvEnergy.setText(valueEnergy + " / " + maxEnergy);
            }
        }
    }

    private void SetEnergyWithLevelFromServer(int level) {
        switch (level) {
            case 0:
            case 1:
                level_text = 1;
                maxEnergyWithLevelFromServer = LEVEL_ENERGY[0];
                break;
            case 2:
                level_text = 2;
                maxEnergyWithLevelFromServer = LEVEL_ENERGY[1];
                break;
            case 3:
                level_text = 3;
                maxEnergyWithLevelFromServer = LEVEL_ENERGY[2];
                break;
            case 4:
                level_text = 4;
                maxEnergyWithLevelFromServer = LEVEL_ENERGY[3];
                break;
            case 5:
                level_text = 5;
                maxEnergyWithLevelFromServer = LEVEL_ENERGY[4];
                break;
            case 6:
                level_text = 6;
                maxEnergyWithLevelFromServer = LEVEL_ENERGY[5];
                break;
            case 7:
                level_text = 7;
                maxEnergyWithLevelFromServer = LEVEL_ENERGY[6];
                break;
            case 8:
                level_text = 8;
                maxEnergyWithLevelFromServer = LEVEL_ENERGY[7];
                break;
            case 9:
                level_text = 9;
                maxEnergyWithLevelFromServer = LEVEL_ENERGY[8];
                break;
            case 10:
                level_text = 10;
                maxEnergyWithLevelFromServer = LEVEL_ENERGY[9];
                break;
            case 11:
                level_text = 11;
                maxEnergyWithLevelFromServer = LEVEL_ENERGY[10];
                break;
            case 12:
                level_text = 12;
                maxEnergyWithLevelFromServer = LEVEL_ENERGY[11];
                break;
            case 13:
                level_text = 13;
                maxEnergyWithLevelFromServer = LEVEL_ENERGY[12];
                break;
            case 14:
                level_text = 14;
                maxEnergyWithLevelFromServer = LEVEL_ENERGY[13];
                break;
            case 15:
                level_text = 15;
                maxEnergyWithLevelFromServer = LEVEL_ENERGY[14];
                break;

        }
        tvLevel.setText(String.valueOf(level_text));
        valueEnergy = maxEnergyWithLevelFromServer;
        maxEnergy = maxEnergyWithLevelFromServer;
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_ENERGY_MANGER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(PREF_ENERGY, maxEnergyWithLevelFromServer);
        editor.putInt(PREF_ENERGY_MAX, maxEnergyWithLevelFromServer);
        editor.putInt(PREF_LEVEL, level_text);
        editor.apply();
    }


    @SuppressLint("SetTextI18n")
    public void ReduceEnergy(int click) {
        if (valueEnergy >= minEnergy) {
            valueEnergy -= click;
            tvEnergy.setText(valueEnergy + " / " + maxEnergy);
            if (valueEnergy <= 0) {
                valueEnergy = 0;
                tvEnergy.setText(valueEnergy + " / " + maxEnergy);
            }
        }
    }

    public boolean mintStop(int mint) {
        return valueEnergy < mint;
    }

    public void clicked(boolean isClick) {
        clicked = isClick;
    }

    public void IncreasedEnergy() {
        final int step_charge = 3;
        final long IncreaseEnergyTime = 1000;
        runnable = new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                if (!clicked) {
                    if (valueEnergy < maxEnergy) {
                        valueEnergy += step_charge;
                        YoYo.with(Techniques.Flash).duration(IncreaseEnergyTime).playOn(icon_energy);
                        if (valueEnergy > maxEnergy) {
                            valueEnergy = maxEnergy;
                        }
                    }
                    tvEnergy.setText(valueEnergy + " / " + maxEnergy);
                }

                handler.postDelayed(this, IncreaseEnergyTime);
            }
        };
        handler.post(runnable);
    }

    public void getTimeFromSystemsOnStopMethod() {
        long current_time = System.currentTimeMillis();
        SharedPreferences preferences = context.getSharedPreferences(PREF_ENERGY_MANGER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("current" + PREF_ENERGY, valueEnergy);
        editor.putLong(PREF_KEY_CURRENT_TIME, current_time);
        editor.apply();
    }

    public void onDestroyApp() {
        handler.removeCallbacks(runnable);
    }
}
