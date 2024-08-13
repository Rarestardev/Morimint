package com.rarestardev.morimint.Repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.rarestardev.morimint.Activities.MainActivity;
import com.rarestardev.morimint.Api.ApiClient;
import com.rarestardev.morimint.Api.ApiService;
import com.rarestardev.morimint.Constants.UserConstants;
import com.rarestardev.morimint.Model.Users;
import com.rarestardev.morimint.Utilities.DialogType;
import com.rarestardev.morimint.Utilities.StatusDialog;

import java.io.IOException;

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
                    Log.d(UserConstants.APP_LOG_TAG, "UpdateCoin : Success");
                } else {
                    try {
                        assert response.errorBody() != null;
                        String errorBodyString = response.errorBody().string();
                        Log.e(UserConstants.APP_LOG_TAG, "UpdateCoin : Error: " + errorBodyString);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(UserConstants.APP_LOG_TAG, "UpdateCoin : Error parsing error body");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Users> call, @NonNull Throwable t) {
                Log.e(UserConstants.APP_LOG_TAG, "UpdateCoin : onFailure :", t);
                Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void UpdateLevel(int level, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(UserConstants.SHARED_PREF_USER, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(UserConstants.SHARED_KEY_TOKEN, "");

        RequestBody levelRequest = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(level));
        MultipartBody.Part levelPart = MultipartBody.Part.createFormData("level", null, levelRequest);

        final StatusDialog dialog = new StatusDialog(context, DialogType.SUCCESS);
        dialog.setTitleDialog("Good Job!");
        dialog.setMessageDialog("Your Level is upgraded");
        dialog.setCancelable(false);
        dialog.setButtonText("Thanks");
        dialog.setButtonListener(v -> {
            Call<Users> call = apiService.putLevelData(levelPart, token);
            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(@NonNull Call<Users> call, @NonNull Response<Users> response) {
                    if (response.isSuccessful()) {
                        Log.d(UserConstants.APP_LOG_TAG, "UpdateLevel : Success");
                        ((MainActivity) context).HandleResponseData();
                        dialog.dismiss();
                    } else {
                        try {
                            assert response.errorBody() != null;
                            String errorBodyString = response.errorBody().string();
                            Log.e(UserConstants.APP_LOG_TAG, "UpdateLevel: " + errorBodyString);
                            final StatusDialog alertDialog = new StatusDialog(context,DialogType.WARNING);
                            alertDialog.setTitleDialog("Something wrong!");
                            alertDialog.setMessageDialog("Please check your internet connection.");
                            alertDialog.setCancelable(false);
                            alertDialog.setButtonText("Retry");
                            alertDialog.setButtonListener(v -> {
                                UpdateLevel(level,context);
                                alertDialog.dismiss();
                            });
                            alertDialog.show();
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
        });
        dialog.show();
    }
}
