package com.rarestardev.morimint.Repository;

import android.app.Dialog;
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
import com.rarestardev.morimint.Api.ApiClient;
import com.rarestardev.morimint.Api.ApiService;
import com.rarestardev.morimint.Api.ApiResponse;
import com.rarestardev.morimint.Constants.UserConstants;
import com.rarestardev.morimint.Model.Users;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
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
        Log.d("UserData", token);
        MutableLiveData<Users> data = new MutableLiveData<>();
        Call<Users> call = apiService.UserData(token);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(@NonNull Call<Users> call, @NonNull Response<Users> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("UserData", "Success");
                    data.setValue(response.body());
                } else {
                    Log.e("UserData", "Failed :" + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Users> call, @NonNull Throwable t) {
                Log.e("UserData", "Error", t);
            }
        });
        return data;
    }


    public void SendUserDataSignUp(Context context, String username, String email, String password, long referral) {
        RequestBody Username = RequestBody.create(MediaType.parse("text/plain"), username);
        RequestBody Email = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody Password = RequestBody.create(MediaType.parse("text/plain"), password);

        RequestBody coinRequest = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(referral));

        Call<ApiResponse> call = apiService.Sign_up(Username, Email, Password, coinRequest);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    String status = apiResponse.getStatus();
                    String message = apiResponse.getMessage();
                    Log.d("Sign UP", status + "\n" + message);

                    if (status.equals("success")) {
                        Toast.makeText(context, "Registered account success.", Toast.LENGTH_SHORT).show();
                        CoinManagerRepository coinManagerRepository = new CoinManagerRepository();
                        coinManagerRepository.UpdateCoin(100000,context);
                        Intent intent = new Intent(context, SignInActivity.class);
                        context.startActivity(intent);
                    }
                } else {
                    Log.e("Signup", "Failed :" + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                Log.e("Signup", "Failed" + t.getMessage());
            }
        });
    }

    public void LoginData(Context context, String username, String password) {
        RequestBody Username = RequestBody.create(MediaType.parse("text/plain"), username);
        RequestBody Password = RequestBody.create(MediaType.parse("text/plain"), password);

        Call<ApiResponse> call = apiService.Login(Username, Password);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    String token = apiResponse.getToken();
                    Log.d("Login", token);

                    SharedPreferences preferences = context.getSharedPreferences(UserConstants.SHARED_PREF_USER, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(UserConstants.SHARED_KEY_USERNAME, username);
                    editor.putString(UserConstants.SHARED_KEY_TOKEN, "Token " + token);
                    editor.apply();

                    Intent intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                } else {
                    Toast.makeText(context, "Wrong username or password!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                Log.e("Login", "Failed" + t.getMessage());
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
                    final SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                    dialog.setTitle(apiResponse.getStatus());
                    dialog.setContentText(apiResponse.getMessage());
                    dialog.setCancelable(false);
                    dialog.setConfirmButton("Ok", Dialog::dismiss);
                    dialog.show();
                } else {
                    try {
                        assert response.errorBody() != null;
                        Log.e("ChangePassword", response.errorBody().string());
                        ApiResponse apiResponse = response.body();
                        final SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                        assert apiResponse != null;
                        dialog.setTitle(apiResponse.getStatus());
                        dialog.setContentText(apiResponse.getMessage());
                        dialog.setCancelable(false);
                        dialog.setConfirmButton("Ok", Dialog::dismiss);
                        dialog.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                Log.e("ChangePassword", "Error", t);
            }
        });
    }
}
