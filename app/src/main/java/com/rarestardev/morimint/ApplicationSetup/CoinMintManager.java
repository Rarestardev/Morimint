package com.rarestardev.morimint.UsersManagement;

import android.content.Context;
import android.content.SharedPreferences;

public class CoinMintManager {
    private static final String COIN_MANAGER_PREF_NAME = "Coin Manager";
    private static final String COIN_KEY_PREF = "Coin";

    private long balance;

    private int Mint;
    private int mintValue;
    private long newValue;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private Context context;

    public CoinMintManager(Context context, int Mint) {
        this.context = context;
        this.Mint = Mint;
        this.balance = 0;
        sharedPreferences = context.getSharedPreferences(COIN_MANAGER_PREF_NAME, Context.MODE_PRIVATE);
        getTotalCoin();
    }

    // on Start App pref is clear for new value
    public void OnStartClearData() {
        sharedPreferences = context.getSharedPreferences(COIN_MANAGER_PREF_NAME, Context.MODE_PRIVATE);
        long coin = sharedPreferences.getLong(COIN_KEY_PREF, 0);

        if (coin != 0){
            editor.clear();
        }
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

    public void IncreaseBalance(int click, boolean turboMode) {
        if (turboMode) {
            balance += (long) Mint * click * 3 / click;
        } else {
            mintValue = Mint * click / click;
            balance += mintValue;
            mintValue = 0;
        }
    }

    public long SendNewValue(){
        return balance - newValue;
    }

    // send new value coin and update database
    public long getBalance() {
        return balance;
    }

}
