package com.rarestardev.morimint.Repository;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rarestardev.morimint.Activities.MainActivity;
import com.rarestardev.morimint.Activities.SignInActivity;
import com.rarestardev.morimint.Activities.SignUpActivity;
import com.rarestardev.morimint.Api.ApiClient;
import com.rarestardev.morimint.Api.ApiService;
import com.rarestardev.morimint.Response.ApiResponse;
import com.rarestardev.morimint.Constants.UserConstants;
import com.rarestardev.morimint.Model.Users;
import com.rarestardev.morimint.Utilities.DialogType;
import com.rarestardev.morimint.Utilities.StatusDialog;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDataRepository {

    ApiService apiService;

    public UserDataRepository() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }


    public LiveData<Users> UserData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(UserConstants.SHARED_PREF_USER, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(UserConstants.SHARED_KEY_TOKEN, "");
        final StatusDialog dialog = new StatusDialog(context, DialogType.LOADING);
        dialog.setTitleDialog("Update");
        dialog.setMessageDialog("Please wait");
        dialog.setCancelable(false);
        dialog.show();
        MutableLiveData<Users> data = new MutableLiveData<>();
        Call<Users> call = apiService.UserData(token);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(@NonNull Call<Users> call, @NonNull Response<Users> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(UserConstants.APP_LOG_TAG, "UserData : Success");
                    data.setValue(response.body());
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                    Log.e(UserConstants.APP_LOG_TAG, "UserData : Failed : " + response.errorBody());
                    final StatusDialog alertDialog = new StatusDialog(context, DialogType.WARNING);
                    alertDialog.setTitleDialog("Something wrong!");
                    alertDialog.setMessageDialog("Please check your internet connection");
                    alertDialog.setCancelable(false);
                    alertDialog.setButtonText("Exit");
                    alertDialog.setButtonListener(v -> {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        ((MainActivity) context).finish();
                        alertDialog.dismiss();
                    });
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Users> call, @NonNull Throwable t) {
                Log.e(UserConstants.APP_LOG_TAG, "UserData : onFailure : ", t);
                Toast.makeText(context, "Something wrong try again later", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        return data;
    }

    /*private void ClearAllUserData(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("WelcomeDialog",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        SharedPreferences sharedPreferences2 = context.getSharedPreferences("ShowTutorial",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        editor2.clear();
        editor2.apply();

        SharedPreferences sharedPreferences3 = context.getSharedPreferences("DailyUpdater",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor3 = sharedPreferences3.edit();
        editor3.clear();
        editor3.apply();

        SharedPreferences sharedPreferences4 = context.getSharedPreferences("DailyCheck",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor4 = sharedPreferences4.edit();
        editor4.clear();
        editor4.apply();

        SharedPreferences sharedPreferences5 = context.getSharedPreferences("Energy Manager",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor5 = sharedPreferences5.edit();
        editor5.clear();
        editor5.apply();

        SharedPreferences sharedPreferences6 = context.getSharedPreferences("Balance",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor6 = sharedPreferences6.edit();
        editor6.clear();
        editor6.apply();
    }*/

    public void SendUserDataSignUp(Context context, String username, String email, String password, long referral) {
        RequestBody Username = RequestBody.create(MediaType.parse("text/plain"), username);
        RequestBody Email = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody Password = RequestBody.create(MediaType.parse("text/plain"), password);

        RequestBody refRequest = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(referral));

        final StatusDialog dialog = new StatusDialog(context, DialogType.LOADING);
        dialog.setTitleDialog("Checked");
        dialog.setMessageDialog("Please wait");
        dialog.setCancelable(false);
        dialog.show();

        Call<ApiResponse> call = apiService.Sign_up(Username, Email, Password, refRequest);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dialog.dismiss();
                    ApiResponse apiResponse = response.body();
                    String status = apiResponse.getStatus();
                    String message = apiResponse.getMessage();
                    Log.d(UserConstants.APP_LOG_TAG, "SignUp : " + status + "\n" + message);

                    if (status.equals("success")) {
                        dialog.dismiss();
                        Toast.makeText(context, "Registered account success.", Toast.LENGTH_SHORT).show();
                        CoinManagerRepository coinManagerRepository = new CoinManagerRepository();
                        coinManagerRepository.UpdateCoin(100000, context);
                        Intent intent = new Intent(context, SignInActivity.class);
                        context.startActivity(intent);
                    }else {
                        Toast.makeText(context, status + message, Toast.LENGTH_LONG).show();
                    }
                } else {
                    dialog.dismiss();
                    Log.e(UserConstants.APP_LOG_TAG, "SignUp : Failed :" + response.errorBody());
                    StatusDialog statusDialog = new StatusDialog(context,DialogType.FAILED);
                    statusDialog.setTitleDialog("Failed");
                    statusDialog.setMessageDialog("Try Again Later");
                    statusDialog.setButtonText("Exit");
                    statusDialog.setButtonListener(v -> {
                        ((SignUpActivity) context).finish();
                        statusDialog.dismiss();
                    });
                    statusDialog.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                Log.e(UserConstants.APP_LOG_TAG, "SignUp : onFailure :", t);
                Toast.makeText(context, "Something wrong try again later", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
    }

    public void LoginData(Context context, String username, String password) {
        RequestBody Username = RequestBody.create(MediaType.parse("text/plain"), username);
        RequestBody Password = RequestBody.create(MediaType.parse("text/plain"), password);

        final StatusDialog dialog = new StatusDialog(context, DialogType.LOADING);
        dialog.setTitleDialog("Check");
        dialog.setMessageDialog("Please wait");
        dialog.setCancelable(false);
        dialog.show();

        Call<ApiResponse> call = apiService.Login(Username, Password);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    String token = apiResponse.getToken();
                    Log.d(UserConstants.APP_LOG_TAG, "Login : token" + token);

                    SharedPreferences preferences = context.getSharedPreferences(UserConstants.SHARED_PREF_USER, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(UserConstants.SHARED_KEY_USERNAME, username);
                    editor.putString(UserConstants.SHARED_KEY_TOKEN, "Token " + token);
                    editor.apply();
                    dialog.dismiss();
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                } else {
                    dialog.dismiss();
                    Toast.makeText(context, "Wrong username or password!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                Log.e(UserConstants.APP_LOG_TAG, "Login : onFailure :" + t.getMessage());
                Toast.makeText(context, "Something wrong try again later", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
    }

    public void ChangePassword(String email, String newPassword, Context context) {
        RequestBody Email = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody Password = RequestBody.create(MediaType.parse("text/plain"), newPassword);

        Call<ApiResponse> call = apiService.ChangePassword(Email, Password);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    final StatusDialog dialog = new StatusDialog(context, DialogType.SUCCESS);
                    dialog.setTitleDialog(apiResponse.getStatus());
                    dialog.setMessageDialog(apiResponse.getMessage());
                    dialog.setCancelable(false);
                    dialog.setButtonText("Ok");
                    dialog.setButtonListener(v -> dialog.dismiss());
                    dialog.show();
                } else {
                    try {
                        assert response.errorBody() != null;
                        Log.e(UserConstants.APP_LOG_TAG, "ChangePass :" + response.errorBody().string());
                        ApiResponse apiResponse = response.body();
                        final StatusDialog dialog = new StatusDialog(context, DialogType.WARNING);
                        assert apiResponse != null;
                        dialog.setTitleDialog(apiResponse.getStatus());
                        dialog.setMessageDialog(apiResponse.getMessage());
                        dialog.setCancelable(false);
                        dialog.setButtonText("Ok");
                        dialog.setButtonListener(v -> dialog.dismiss());
                        dialog.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                Log.e(UserConstants.APP_LOG_TAG, "ChangePass : onFailure :", t);
                Toast.makeText(context, "Something wrong try again later", Toast.LENGTH_LONG).show();
            }
        });
    }
}
