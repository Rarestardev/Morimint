package com.rarestardev.morimint.Repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rarestardev.morimint.Activities.MainActivity;
import com.rarestardev.morimint.Activities.SignInActivity;
import com.rarestardev.morimint.Api.ApiClient;
import com.rarestardev.morimint.Api.ApiService;
import com.rarestardev.morimint.Api.ApiResponse;
import com.rarestardev.morimint.Constants.UserConstants;
import com.rarestardev.morimint.Model.Users;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDataRepository {

    ApiService apiService;

    public UserDataRepository(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }



    public void UserData(Context context , TextView tvUsername){
        SharedPreferences sharedPreferences = context.getSharedPreferences(UserConstants.SHARED_PREF_USER,Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(UserConstants.SHARED_KEY_TOKEN,"");

        Call<List<Users>> call = apiService.UserData("Token " + token);
        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(@NonNull Call<List<Users>> call, @NonNull Response<List<Users>> response) {
                if (response.isSuccessful() && response.body() != null){
                    List<Users> users = response.body();
                    String username = users.get(0).getUsername();
                    tvUsername.setText(username);
                    Log.e("UserData",username);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Users>> call, @NonNull Throwable t) {
                Log.e("UserData","Error",t);
            }
        });
    }




    public void SendUserDataSignUp(Context context,String username, String email, String password){
        RequestBody Username = RequestBody.create(MediaType.parse("text/plain"),username);
        RequestBody Email = RequestBody.create(MediaType.parse("text/plain"),email);
        RequestBody Password = RequestBody.create(MediaType.parse("text/plain"),password);

        Call<ApiResponse> call = apiService.Sign_up(Username, Email, Password);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    ApiResponse apiResponse = response.body();
                    String status = apiResponse.getStatus();
                    String message = apiResponse.getMessage();
                    Log.d("Sign UP",status + "\n" + message);

                    if (status.equals("success")){
                        Toast.makeText(context, "Registered account success.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, SignInActivity.class);
                        context.startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                Log.e("Signup","Failed" + t.getMessage());
            }
        });
    }

    public void LoginData(Context context ,String username,String password){
        RequestBody Username = RequestBody.create(MediaType.parse("text/plain"),username);
        RequestBody Password = RequestBody.create(MediaType.parse("text/plain"),password);

        Call<ApiResponse> call = apiService.Login(Username,Password);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    ApiResponse apiResponse = response.body();
                    String token = apiResponse.getToken();

                    SharedPreferences preferences = context.getSharedPreferences(UserConstants.SHARED_PREF_USER,Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(UserConstants.SHARED_KEY_USERNAME,username);
                    editor.putString(UserConstants.SHARED_KEY_TOKEN,token);
                    editor.apply();

                    Intent intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }else {
                    Toast.makeText(context, "Wrong username or password!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                Log.e("Login","Failed" + t.getMessage());
            }
        });
    }
}
