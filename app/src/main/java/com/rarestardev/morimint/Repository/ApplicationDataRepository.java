package com.rarestardev.morimint.Repository;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rarestardev.morimint.Adapters.DailyRewardAdapter;
import com.rarestardev.morimint.Api.ApiClient;
import com.rarestardev.morimint.Api.ApiResponse;
import com.rarestardev.morimint.Api.ApiService;
import com.rarestardev.morimint.Api.DailyRewardResponse;
import com.rarestardev.morimint.Constants.UserConstants;
import com.rarestardev.morimint.Model.ApplicationSetupModel;
import com.rarestardev.morimint.Model.DailyRewardModel;
import com.rarestardev.morimint.Model.GiftCodeModel;
import com.rarestardev.morimint.Model.MoriNewsModel;

import java.io.IOException;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplicationDataRepository {

    ApiService apiService;

    public ApplicationDataRepository() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public LiveData<List<MoriNewsModel>> GetDataMoriNews() {
        MutableLiveData<List<MoriNewsModel>> data = new MutableLiveData<>();
        Call<List<MoriNewsModel>> call = apiService.GetMoriNews(ApiClient.SERVER_TOKEN);
        call.enqueue(new Callback<List<MoriNewsModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<MoriNewsModel>> call, @NonNull Response<List<MoriNewsModel>> response) {
                if (response.body() != null) {
                    data.setValue(response.body());
                }
                if (response.isSuccessful()) {
                    Log.d("MoriNews", "Success");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MoriNewsModel>> call, @NonNull Throwable t) {
                if (t instanceof java.net.SocketTimeoutException) {
                    Log.e("MoriNews", "Timeout", t);
                } else {
                    Log.e("MoriNews", "Failed", t);
                }
            }
        });
        return data;
    }

    public void DailyRewardData(RecyclerView recyclerView, Context context) {
        Call<List<DailyRewardModel>> call = apiService.GetDailyReward(ApiClient.SERVER_TOKEN);
        call.enqueue(new Callback<List<DailyRewardModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<DailyRewardModel>> call, @NonNull Response<List<DailyRewardModel>> response) {
                if (response.body() != null) {
                    List<DailyRewardModel> dailyRewardModels = response.body();
                    DailyRewardAdapter adapter = new DailyRewardAdapter(dailyRewardModels, context);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setHasFixedSize(true);
                    recyclerView.refreshDrawableState();
                    recyclerView.setAdapter(adapter);
                    Log.d("DailyRewardRepo", "Success");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<DailyRewardModel>> call, @NonNull Throwable t) {
                Log.e("DailyRewardRepo", "Error", t);
            }
        });
    }


    public LiveData<ApplicationSetupModel> ApplicationConfig() {
        MutableLiveData<ApplicationSetupModel> data = new MutableLiveData<>();
        Call<ApplicationSetupModel> call = apiService.GetApplicationSetup(ApiClient.SERVER_TOKEN);
        call.enqueue(new Callback<ApplicationSetupModel>() {
            @Override
            public void onResponse(@NonNull Call<ApplicationSetupModel> call, @NonNull Response<ApplicationSetupModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("ApplicationConfig :", "Success");
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApplicationSetupModel> call, @NonNull Throwable t) {
                Log.e("ApplicationConfig :", "ERROR", t);
            }
        });
        return data;
    }


    public void ClaimDailyReward(Context context, DailyRewardModel dailyRewardModel) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(UserConstants.SHARED_PREF_USER, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(UserConstants.SHARED_KEY_TOKEN, "");

        Call<DailyRewardResponse> call = apiService.SetDailyReward(token, dailyRewardModel);
        call.enqueue(new Callback<DailyRewardResponse>() {
            @Override
            public void onResponse(@NonNull Call<DailyRewardResponse> call, @NonNull Response<DailyRewardResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("ClaimDailyReward", "Success");
                    DailyRewardResponse dailyRewardResponse = response.body();
                    String detail = dailyRewardResponse.getDetail();
                    final SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                    dialog.setTitle("Success");
                    dialog.setContentText(detail);
                    dialog.setConfirmButton("Claim", Dialog::dismiss);
                    dialog.show();

                } else {
                    try {
                        assert response.errorBody() != null;
                        String error = response.errorBody().string();
                        final SweetAlertDialog dialogWarning = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                        dialogWarning.setTitle("Failed!");
                        dialogWarning.setContentText("You have already claimed the daily bonus");
                        dialogWarning.setConfirmButton("Done", Dialog::dismiss);
                        dialogWarning.show();
                        Log.e("ClaimDailyReward :", error);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<DailyRewardResponse> call, @NonNull Throwable t) {
                Log.e("ClaimDailyReward", "ERROR", t);
            }
        });
    }


    public void SiteGiftCode(Context context, String code) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(UserConstants.SHARED_PREF_USER, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(UserConstants.SHARED_KEY_TOKEN, "");

        GiftCodeModel giftCodeModel = new GiftCodeModel(code);

        Call<ApiResponse> call = apiService.SiteGiftCode(token, giftCodeModel);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("SiteGiftCoin :", "Success");
                } else {
                    Log.e("SiteGiftCoin", "Error" + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                Log.e("SiteGiftCoin", "Error", t);
            }
        });
    }


    public void GiftCode(Context context, String code) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(UserConstants.SHARED_PREF_USER, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(UserConstants.SHARED_KEY_TOKEN, "");

        GiftCodeModel giftCodeModel = new GiftCodeModel(code);

        Call<ApiResponse> call = apiService.GiftCode(token, giftCodeModel);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("GiftCoin :", "Success");
                } else {
                    Log.e("GiftCoin", "Error" + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                Log.e("GiftCoin", "Error", t);
            }
        });
    }
}
