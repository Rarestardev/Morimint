package com.rarestardev.morimint.ViewModel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.rarestardev.morimint.Repository.CoinManagerRepository;

public class CoinManagerViewModel extends ViewModel {

    CoinManagerRepository coinManagerRepository;

    public CoinManagerViewModel(){
        coinManagerRepository = new CoinManagerRepository();
    }


    public void UpdateCoin(long coin, Context context){
        coinManagerRepository.UpdateCoin(coin,context);
    }

}
