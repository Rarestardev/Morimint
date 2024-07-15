package com.rarestardev.morimint.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.rarestardev.morimint.Model.Users;
import com.rarestardev.morimint.Repository.UserDataRepository;

public class UserDataViewModel extends ViewModel {

    UserDataRepository userDataRepository;

    public UserDataViewModel(){
        userDataRepository = new UserDataRepository();
    }


    public LiveData<Users> getUserData(Context context){
        return userDataRepository.UserData(context);
    }




    public void SignUp(Context context, String username, String email, String password){
        userDataRepository.SendUserDataSignUp(context,username, email, password);
    }

    public void LoginUserAccount(Context context , String username,String password){
        userDataRepository.LoginData(context, username, password);
    }

}
