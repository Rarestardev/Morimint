package com.rarestardev.morimint.Repository;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.rarestardev.morimint.Activities.MainActivity;
import com.rarestardev.morimint.Api.ApiClient;
import com.rarestardev.morimint.Api.ApiService;
import com.rarestardev.morimint.Constants.UserConstants;
import com.rarestardev.morimint.Model.Users;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoinManagerRepository {

    ApiService apiService;

    public CoinManagerRepository() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public void UpdateCoin(long coin, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(UserConstants.SHARED_PREF_USER, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(UserConstants.SHARED_KEY_TOKEN, "");
        RequestBody coinRequest = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(coin));
        MultipartBody.Part coinPart = MultipartBody.Part.createFormData("coin", null, coinRequest);
        Call<Users> call = apiService.putCoinData(coinPart, token);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(@NonNull Call<Users> call, @NonNull Response<Users> response) {
                if (response.isSuccessful()) {
                    Log.d("UpdateCoin", "Success");
                } else {
                    try {
                        assert response.errorBody() != null;
                        String errorBodyString = response.errorBody().string();
                        Log.e("UpdateCoin", "Error: " + errorBodyString);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("UpdateCoin", "Error parsing error body");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Users> call, @NonNull Throwable t) {
                Log.e("UpdateCoin", "ERROR", t);
            }
        });
    }


    public void UpdateLevel(int level, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(UserConstants.SHARED_PREF_USER, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(UserConstants.SHARED_KEY_TOKEN, "");

        RequestBody levelRequest = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(level));
        MultipartBody.Part levelPart = MultipartBody.Part.createFormData("level", null, levelRequest);

        SweetAlertDialog dialog = new SweetAlertDialog(context,SweetAlertDialog.SUCCESS_TYPE);
        SweetAlertDialog alertDialog = new SweetAlertDialog(context,SweetAlertDialog.PROGRESS_TYPE);
        dialog.setTitle("Good Job!");
        dialog.setContentText("Great! , Your level has been upgraded.");
        dialog.setCancelable(false);
        dialog.setConfirmButton("Thanks", sweetAlertDialog -> {
            alertDialog.setTitle("Update");
            alertDialog.setCancelable(false);
            alertDialog.show();
            if (context instanceof MainActivity) {
                ((MainActivity) context).refreshActivity();
            } else {
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
            sweetAlertDialog.dismiss();
        }).show();

        Call<Users> call = apiService.putCoinData(levelPart, token);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(@NonNull Call<Users> call, @NonNull Response<Users> response) {
                if (response.isSuccessful()) {
                    Log.d("UpdateLevel", "Success");
                    alertDialog.dismiss();

                } else {
                    try {
                        assert response.errorBody() != null;
                        String errorBodyString = response.errorBody().string();
                        Log.e("UpdateLevel", "Error: " + errorBodyString);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("UpdateLevel", "Error parsing error body");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Users> call, @NonNull Throwable t) {
                Log.e("UpdateLevel", "ERROR", t);
            }
        });
    }
}
