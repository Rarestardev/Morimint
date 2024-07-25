package com.rarestardev.morimint.ApplicationSetup;

import android.content.Context;
import android.content.SharedPreferences;

public class CoinMintManager {
    private static final String COIN_MANAGER_PREF_NAME = "Coin Manager";
    private static final String COIN_KEY_PREF = "Coin";

    private long balance;
    int mintValue;
    private long newValue;

    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Context context;

    public CoinMintManager(Context context) {
        this.context = context;
        this.balance = 0;
        sharedPreferences = context.getSharedPreferences(COIN_MANAGER_PREF_NAME, Context.MODE_PRIVATE);
        getTotalCoin();
    }

    // Main Activity onResume getCoin on server data
    public void setBalance(long coin) {
        this.balance = coin;
        this.newValue = coin;
        editor = sharedPreferences.edit();
        editor.putLong(COIN_KEY_PREF, coin);
        editor.apply();
    }

    private void getTotalCoin() {
        sharedPreferences = context.getSharedPreferences(COIN_MANAGER_PREF_NAME, Context.MODE_PRIVATE);
        balance = sharedPreferences.getLong(COIN_KEY_PREF, 0);
    }

    public void IncreaseBalance(int click, boolean turboMode,int mint) {
        if (turboMode) {
            balance += (long) mint * click * 3 / click;
        } else {
            mintValue = mint * click / click;
            balance += mintValue;
            mintValue = 0;

        }
    }

    // send new value coin and update database
    public long SendNewValue(){
        return balance - newValue;
    }


    public long getBalance() {
        return balance;
    }

}
