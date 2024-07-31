package com.rarestardev.morimint.Repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.rarestardev.morimint.Activities.MainActivity;
import com.rarestardev.morimint.Api.ApiClient;
import com.rarestardev.morimint.Api.ApiService;
import com.rarestardev.morimint.ApplicationSetup.CoinMintManager;
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

        final SweetAlertDialog dialog = new SweetAlertDialog(context,SweetAlertDialog.SUCCESS_TYPE);
        dialog.setTitle("Good Job!");
        dialog.setContentText("Your Level is upgraded");
        dialog.setCancelable(false);
        dialog.setConfirmButton("Thanks", sweetAlertDialog -> {
            Call<Users> call = apiService.putCoinData(levelPart, token);
            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(@NonNull Call<Users> call, @NonNull Response<Users> response) {
                    if (response.isSuccessful()) {
                        Log.d(UserConstants.APP_LOG_TAG, "UpdateLevel : Success");
                        ((MainActivity) context).HandleResponseData();
                        sweetAlertDialog.dismiss();
                    } else {
                        try {
                            assert response.errorBody() != null;
                            String errorBodyString = response.errorBody().string();
                            Log.e(UserConstants.APP_LOG_TAG, "UpdateLevel: " + errorBodyString);
                            final SweetAlertDialog alertDialog = new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE);
                            alertDialog.setTitle("Something wrong!");
                            alertDialog.setContentText("Please check your internet connection.");
                            alertDialog.setCancelable(false);
                            alertDialog.setConfirmButton("Retry", sweetAlertDialog1 -> {
                                UpdateLevel(level,context);
                                alertDialog.dismiss();
                            }).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e(UserConstants.APP_LOG_TAG, "UpdateLevel: Error parsing error body");
                        }
                    }
                }
                @Override
                public void onFailure(@NonNull Call<Users> call, @NonNull Throwable t) {
                    Log.e(UserConstants.APP_LOG_TAG, "UpdateLevel: onFailure :", t);
                }
            });
        }).show();
    }
}
