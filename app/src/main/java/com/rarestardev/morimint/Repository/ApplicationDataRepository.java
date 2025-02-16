package com.rarestardev.morimint.Repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rarestardev.morimint.Activities.NewsActivity;
import com.rarestardev.morimint.Adapters.DailyRewardAdapter;
import com.rarestardev.morimint.Adapters.MoriNewsAdapter;
import com.rarestardev.morimint.Adapters.TaskListAdapter;
import com.rarestardev.morimint.Api.ApiClient;
import com.rarestardev.morimint.Response.ApiResponse;
import com.rarestardev.morimint.Api.ApiService;
import com.rarestardev.morimint.Response.AppGiftCodeResponse;
import com.rarestardev.morimint.Response.MiniAppResponse;
import com.rarestardev.morimint.Response.SingleResponse;
import com.rarestardev.morimint.Constants.UserConstants;
import com.rarestardev.morimint.Model.ApplicationSetupModel;
import com.rarestardev.morimint.Model.DailyRewardModel;
import com.rarestardev.morimint.Response.SiteGiftCodeResponse;
import com.rarestardev.morimint.Model.MoriNewsModel;
import com.rarestardev.morimint.Model.TaskModel;
import com.rarestardev.morimint.Utilities.DialogType;
import com.rarestardev.morimint.Utilities.StatusDialog;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplicationDataRepository {

    private List<MoriNewsModel> previousNewsList = null;
    long lastRequestTime = 0;

    ApiService apiService;

    public ApplicationDataRepository() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public void GetDataMoriNews(Context context, RecyclerView recyclerView, AppCompatTextView noData) {
        final StatusDialog dialog = new StatusDialog(context, DialogType.LOADING);
        dialog.setTitleDialog("Updated");
        dialog.setMessageDialog("Please wait");
        dialog.setCancelable(false);
        dialog.show();

        Call<List<MoriNewsModel>> call = apiService.GetMoriNews(ApiClient.SERVER_TOKEN);
        call.enqueue(new Callback<List<MoriNewsModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<MoriNewsModel>> call, @NonNull Response<List<MoriNewsModel>> response) {
                if (response.body() != null) {
                    dialog.dismiss();
                    List<MoriNewsModel> moriNewsModel = response.body();

                    if (moriNewsModel.isEmpty()){
                        noData.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }else {
                        noData.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        MoriNewsAdapter adapter = new MoriNewsAdapter(moriNewsModel, context);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView.refreshDrawableState();
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                    }
                }
                if (response.isSuccessful()) {
                    Log.d(UserConstants.APP_LOG_TAG, "MoriNews : Success");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MoriNewsModel>> call, @NonNull Throwable t) {
                dialog.dismiss();
                if (t instanceof SocketTimeoutException) {
                    Log.e(UserConstants.APP_LOG_TAG, "MoriNews : Timeout", t);
                } else {
                    Log.e(UserConstants.APP_LOG_TAG, "MoriNews : Failed", t);
                    StatusDialog dialog = new StatusDialog(context, DialogType.WARNING);
                    dialog.setCancelable(false);
                    dialog.setTitleDialog("No Internet!");
                    dialog.setMessageDialog("Please check the internet connection");
                    dialog.setButtonText("Exit");
                    dialog.setButtonListener(v -> {
                        ((NewsActivity) context).finish();
                        dialog.dismiss();
                    });
                    dialog.show();
                }
            }
        });
    }

    public void GetPinnedNews(TextView textView, AppCompatImageView imageView) {
        Call<List<MoriNewsModel>> call = apiService.GetMoriNewsPinned(ApiClient.SERVER_TOKEN);
        call.enqueue(new Callback<List<MoriNewsModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<MoriNewsModel>> call, @NonNull Response<List<MoriNewsModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MoriNewsModel> newsList = response.body();
                    checkForNewNews(newsList, imageView);

                    MoriNewsModel latestPublishedNews = null;
                    for (MoriNewsModel news : newsList) {
                        if (news.isIs_published()) {
                            latestPublishedNews = news;
                            break;
                        }
                    }

                    if (latestPublishedNews != null) {
                        displayNews(latestPublishedNews, textView);
                    } else if (!newsList.isEmpty()){
                        displayNews(newsList.get(0), textView);
                    }


                    previousNewsList = newsList;
                    lastRequestTime = System.currentTimeMillis();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MoriNewsModel>> call, @NonNull Throwable t) {
                Log.e(UserConstants.APP_LOG_TAG, "Pinned news : onFailure", t);
            }
        });
    }

    private void checkForNewNews(List<MoriNewsModel> newsList, AppCompatImageView imageView) {
        if (previousNewsList == null || newsList.size() > previousNewsList.size() ||
                (!newsList.isEmpty() && !newsList.get(0).equals(previousNewsList.get(0)))) {

            if (newsList.isEmpty()){
                imageView.setVisibility(View.GONE);
            }else {
                imageView.setVisibility(View.VISIBLE);
            }

        } else {
            imageView.setVisibility(View.GONE);
        }
    }

    private void displayNews(MoriNewsModel news, TextView textView) {
        String mNews = news.getContent();

        if (!TextUtils.isEmpty(mNews)){
            textView.setText(news.getContent());
        }else {
            textView.setText("");
        }
    }

    public void DailyRewardData(RecyclerView recyclerView, Context context, TextView textView) {
        Call<List<DailyRewardModel>> call = apiService.GetDailyReward(ApiClient.SERVER_TOKEN);
        call.enqueue(new Callback<List<DailyRewardModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<DailyRewardModel>> call, @NonNull Response<List<DailyRewardModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isEmpty()) {
                        recyclerView.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.GONE);
                        List<DailyRewardModel> dailyRewardModels = response.body();
                        DailyRewardAdapter adapter = new DailyRewardAdapter(dailyRewardModels, context);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView.setHasFixedSize(true);
                        recyclerView.refreshDrawableState();
                        recyclerView.setAdapter(adapter);
                        Log.d(UserConstants.APP_LOG_TAG, "DailyRewardRepo : Success");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        Log.e(UserConstants.APP_LOG_TAG, "DailyRewardRepo :" + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<DailyRewardModel>> call, @NonNull Throwable t) {
                Log.e(UserConstants.APP_LOG_TAG, "DailyRewardRepo : onFailure", t);
                Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
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
                    Log.d(UserConstants.APP_LOG_TAG, "ApplicationConfig : Success");
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApplicationSetupModel> call, @NonNull Throwable t) {
                Log.e(UserConstants.APP_LOG_TAG, "ApplicationConfig : onFailure", t);
            }
        });
        return data;
    }

    public void ClaimDailyReward(Context context, DailyRewardModel dailyRewardModel) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(UserConstants.SHARED_PREF_USER, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(UserConstants.SHARED_KEY_TOKEN, "");

        Call<SingleResponse> call = apiService.SetDailyReward(token, dailyRewardModel);
        call.enqueue(new Callback<SingleResponse>() {
            @Override
            public void onResponse(@NonNull Call<SingleResponse> call, @NonNull Response<SingleResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(UserConstants.APP_LOG_TAG, "ClaimDailyReward : Success");
                    SingleResponse singleResponse = response.body();
                    String detail = singleResponse.getDetail();
                    final StatusDialog dialog = new StatusDialog(context, DialogType.SUCCESS);
                    dialog.setTitleDialog("Success");
                    dialog.setMessageDialog(detail);
                    dialog.setButtonText("Claim");
                    dialog.setButtonListener(v -> dialog.dismiss());
                    dialog.show();

                } else {
                    try {
                        assert response.errorBody() != null;
                        String error = response.errorBody().string();
                        final StatusDialog dialogWarning = new StatusDialog(context, DialogType.FAILED);
                        dialogWarning.setTitleDialog("Failed!");
                        dialogWarning.setMessageDialog("You have already claimed the daily bonus");
                        dialogWarning.setButtonText("Done");
                        dialogWarning.setButtonListener(v -> dialogWarning.dismiss());
                        dialogWarning.show();
                        Log.e(UserConstants.APP_LOG_TAG, "ClaimDailyReward : " + error);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SingleResponse> call, @NonNull Throwable t) {
                Log.e(UserConstants.APP_LOG_TAG, "ClaimDailyReward : onFailure", t);
                Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void SiteGiftCode(Context context, String code) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(UserConstants.SHARED_PREF_USER, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(UserConstants.SHARED_KEY_TOKEN, "");

        final StatusDialog alertDialog = new StatusDialog(context, DialogType.LOADING);
        alertDialog.setTitleDialog("Checked...");
        alertDialog.setMessageDialog("Please Wait");
        alertDialog.setCancelable(false);
        alertDialog.show();

        SiteGiftCodeResponse siteGiftCodeResponse = new SiteGiftCodeResponse(code);

        Call<ApiResponse> call = apiService.SiteGiftCode(token, siteGiftCodeResponse);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    alertDialog.dismiss();
                    ApiResponse apiResponse = response.body();
                    String status = apiResponse.getStatus();
                    String message = apiResponse.getMessage();

                    if (status.equals("error")) {
                        final StatusDialog alertDialog = new StatusDialog(context, DialogType.FAILED);
                        alertDialog.setTitle(status);
                        alertDialog.setMessageDialog(message);
                        alertDialog.setCancelable(false);
                        alertDialog.setButtonText("Ok");
                        alertDialog.setButtonListener(v -> alertDialog.dismiss());
                        alertDialog.show();
                    } else {
                        final StatusDialog dialog = new StatusDialog(context, DialogType.SUCCESS);
                        dialog.setTitleDialog(status);
                        dialog.setMessageDialog(message);
                        dialog.setCancelable(false);
                        dialog.setButtonText("Claim");
                        dialog.setButtonListener(v -> dialog.dismiss());
                        dialog.show();
                    }
                    Log.d(UserConstants.APP_LOG_TAG, "GiftCoinSite : Success");
                } else {
                    alertDialog.dismiss();
                    final StatusDialog alertDialog = new StatusDialog(context, DialogType.FAILED);
                    alertDialog.setTitleDialog("Failed");
                    alertDialog.setMessageDialog("Wrong code");
                    alertDialog.setCancelable(false);
                    alertDialog.setButtonText("Ok");
                    alertDialog.setButtonListener(v -> alertDialog.dismiss());
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                Log.e(UserConstants.APP_LOG_TAG, "GiftCoinSite : onFailure :", t);
                Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });
    }

    public void GiftCode(Context context, String code) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(UserConstants.SHARED_PREF_USER, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(UserConstants.SHARED_KEY_TOKEN, "");

        final StatusDialog dialog = new StatusDialog(context, DialogType.LOADING);
        dialog.setTitleDialog("Checked...");
        dialog.setMessageDialog("Please Wait");
        dialog.setCancelable(false);
        dialog.show();

        RequestBody codec = RequestBody.create(MediaType.parse("text/plain"), code);

        Call<AppGiftCodeResponse> call = apiService.GiftCode(token, codec);
        call.enqueue(new Callback<AppGiftCodeResponse>() {
            @Override
            public void onResponse(@NonNull Call<AppGiftCodeResponse> call, @NonNull Response<AppGiftCodeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AppGiftCodeResponse apiResponse = response.body();
                    Log.d(UserConstants.APP_LOG_TAG, "GiftCoin : Success" + response.body());
                    String status = apiResponse.getStatus();
                    String message = apiResponse.getMessage();
                    dialog.dismiss();

                    final StatusDialog alertDialog;
                    if (status.equals("error")) {
                        alertDialog = new StatusDialog(context, DialogType.FAILED);
                        alertDialog.setTitleDialog(status);
                        alertDialog.setMessageDialog(message);
                        alertDialog.setCancelable(false);
                        alertDialog.setButtonText("Ok");
                    } else {
                        alertDialog = new StatusDialog(context, DialogType.SUCCESS);
                        alertDialog.setTitleDialog(status);
                        alertDialog.setMessageDialog(message);
                        alertDialog.setCancelable(false);
                        alertDialog.setButtonText("Claim");
                    }
                    alertDialog.setButtonListener(v -> alertDialog.dismiss());
                    alertDialog.show();
                    Log.d(UserConstants.APP_LOG_TAG, "GiftCoin : Success");
                } else {
                    dialog.dismiss();
                    Log.e(UserConstants.APP_LOG_TAG, "GiftCoin : Error " + response.errorBody());
                    final StatusDialog alertDialog = new StatusDialog(context, DialogType.FAILED);
                    alertDialog.setTitleDialog("Failed");
                    alertDialog.setMessageDialog("Wrong code");
                    alertDialog.setCancelable(false);
                    alertDialog.setButtonText("Ok");
                    alertDialog.setButtonListener(v -> alertDialog.dismiss());
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AppGiftCodeResponse> call, @NonNull Throwable t) {
                Log.e(UserConstants.APP_LOG_TAG, "GiftCoin : onFailure :", t);
                Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void MiniAppBonusCode(Context context, String code) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(UserConstants.SHARED_PREF_USER, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(UserConstants.SHARED_KEY_TOKEN, "");

        final StatusDialog dialog = new StatusDialog(context, DialogType.LOADING);
        dialog.setTitleDialog("Checked...");
        dialog.setMessageDialog("Please Wait");
        dialog.setCancelable(false);
        dialog.show();

        RequestBody codec = RequestBody.create(MediaType.parse("text/plain"), code);

        Call<MiniAppResponse> call = apiService.MiniAppBonusCode(token, codec);
        call.enqueue(new Callback<MiniAppResponse>() {
            @Override
            public void onResponse(@NonNull Call<MiniAppResponse> call, @NonNull Response<MiniAppResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MiniAppResponse miniAppResponse = response.body();
                    dialog.dismiss();
                    String status = miniAppResponse.getStatus();
                    String msg = miniAppResponse.getMessage();

                    final StatusDialog alertDialog;
                    if (status.equals("error")) {
                        alertDialog = new StatusDialog(context, DialogType.FAILED);
                        alertDialog.setTitleDialog("Failed");
                        alertDialog.setMessageDialog(msg);
                        alertDialog.setCancelable(false);
                        alertDialog.setButtonText("Ok");

                    } else {
                        alertDialog = new StatusDialog(context, DialogType.SUCCESS);
                        alertDialog.setTitleDialog(status);
                        alertDialog.setMessageDialog(msg);
                        alertDialog.setCancelable(false);
                        alertDialog.setButtonText("Claim");
                    }
                    alertDialog.setButtonListener(v -> alertDialog.dismiss());
                    alertDialog.show();


                    Log.d(UserConstants.APP_LOG_TAG, "MiniAppCode : Success");
                } else {
                    dialog.dismiss();
                    Log.e(UserConstants.APP_LOG_TAG, "MiniAppCode : Error " + response.errorBody());

                    final StatusDialog alertDialog = new StatusDialog(context, DialogType.FAILED);
                    alertDialog.setTitleDialog("Failed");
                    alertDialog.setMessageDialog("Wrong code");
                    alertDialog.setCancelable(false);
                    alertDialog.setButtonText("Ok");
                    alertDialog.setButtonListener(v -> alertDialog.dismiss());
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MiniAppResponse> call, @NonNull Throwable t) {
                Log.e(UserConstants.APP_LOG_TAG, "MiniAppCode : onFailure :", t);
                Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void GetTasks(Context context, RecyclerView recyclerView, TextView textView) {
        Call<List<TaskModel>> call = apiService.GetTasks(ApiClient.SERVER_TOKEN);
        call.enqueue(new Callback<List<TaskModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<TaskModel>> call, @NonNull Response<List<TaskModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<TaskModel> tasks = response.body();

                    if (tasks.isEmpty()){
                        textView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }else {
                        textView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        TaskListAdapter adapter = new TaskListAdapter(context, tasks);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.refreshDrawableState();
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView.setAdapter(adapter);
                    }

                } else {

                    try {
                        assert response.errorBody() != null;
                        Log.e(UserConstants.APP_LOG_TAG, "GetTask :" + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<TaskModel>> call, @NonNull Throwable t) {
                Log.e(UserConstants.APP_LOG_TAG, "GetTask : OnFailure", t);
                Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ClaimTask(Context context, TaskModel taskModel) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(UserConstants.SHARED_PREF_USER, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(UserConstants.SHARED_KEY_TOKEN, "");

        Call<SingleResponse> call = apiService.ClaimTask(token, taskModel);
        call.enqueue(new Callback<SingleResponse>() {
            @Override
            public void onResponse(@NonNull Call<SingleResponse> call, @NonNull Response<SingleResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SingleResponse task = response.body();
                    final StatusDialog dialog = new StatusDialog(context, DialogType.SUCCESS);
                    dialog.setTitleDialog("Success");
                    dialog.setMessageDialog(task.getDetail());
                    dialog.setCancelable(false);
                    dialog.setButtonText("Claim");
                    dialog.setButtonListener(v -> dialog.dismiss());
                    dialog.show();
                } else {
                    SingleResponse task = response.body();
                    final StatusDialog dialog = new StatusDialog(context, DialogType.FAILED);
                    dialog.setTitleDialog("Failed");
                    dialog.setMessageDialog(task != null ? task.getDetail() : null);
                    dialog.setCancelable(false);
                    dialog.setButtonText("Ok");
                    dialog.setButtonListener(v -> dialog.dismiss());
                    dialog.show();

                    try {
                        assert response.errorBody() != null;
                        Log.e(UserConstants.APP_LOG_TAG, "ClaimTask : " + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SingleResponse> call, @NonNull Throwable t) {
                Log.e(UserConstants.APP_LOG_TAG, "ClaimTask : onFailure ", t);
                Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
