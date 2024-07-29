package com.rarestardev.morimint.ApplicationSetup;

import android.content.Context;
import android.content.SharedPreferences;

import com.rarestardev.morimint.Activities.MainActivity;
import com.rarestardev.morimint.Repository.CoinManagerRepository;

public class CoinMintManager {

    private long balance;
    int mintValue;

    Context context;

    public CoinMintManager(Context context) {
        this.context = context;
        this.balance = 0;
    }

    // Main Activity onResume getCoin on server data
    public void setBalance(long coin) {
        this.balance = coin;
    }

    public void IncreaseBalance(int click, boolean turboMode, int mint) {
        if (turboMode) {
            mintValue = mint * click * 3 / click;
        } else {
            mintValue = mint * click / click;
        }
        balance += mintValue;
        mintValue = 0;
        SavedTotalCoin();
    }

    public void SendNewValue(long coin) {
        SharedPreferences preferences = context.getSharedPreferences("Balance", Context.MODE_PRIVATE);
        long current_coin = preferences.getLong("Coin", 0);
        if (current_coin != 0) {
            if (current_coin != coin) {
                long total = current_coin - coin;
                balance += total;
                CoinManagerRepository coinManagerRepository = new CoinManagerRepository();
                coinManagerRepository.UpdateCoin(total, context);
                ((MainActivity) context).HandleResponseData();
            }
        }
    }

    // send new value coin and update database
    private void SavedTotalCoin() {
        SharedPreferences preferences = context.getSharedPreferences("Balance", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = preferences.edit();
        editor1.putLong("Coin", balance);
        editor1.apply();
    }

    public long getBalance() {
        return balance;
    }

}
