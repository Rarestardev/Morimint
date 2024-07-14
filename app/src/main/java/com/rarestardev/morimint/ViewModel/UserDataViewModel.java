package com.rarestardev.morimint.ViewModel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.rarestardev.morimint.Repository.UserDataRepository;

public class UserDataViewModel extends ViewModel {

    UserDataRepository userDataRepository;

    public UserDataViewModel(){
        userDataRepository = new UserDataRepository();
    }

    public void GetDataSignUpUsers(Context context,String username, String email, String password){
        userDataRepository.SendUserDataSignUp(context,username, email, password);
    }

    public void LoginUserAccount(Context context , String username,String password){
        userDataRepository.LoginData(context, username, password);
    }

}
