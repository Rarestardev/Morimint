package com.rarestardev.morimint.Repository;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rarestardev.morimint.Activities.NewsActivity;
import com.rarestardev.morimint.Adapters.DailyRewardAdapter;
import com.rarestardev.morimint.Adapters.MoriNewsAdapter;
import com.rarestardev.morimint.Adapters.TaskListAdapter;
import com.rarestardev.morimint.Api.ApiClient;
import com.rarestardev.morimint.Api.ApiResponse;
import com.rarestardev.morimint.Api.ApiService;
import com.rarestardev.morimint.Api.SingleResponse;
import com.rarestardev.morimint.Constants.UserConstants;
import com.rarestardev.morimint.Model.ApplicationSetupModel;
import com.rarestardev.morimint.Model.DailyRewardModel;
import com.rarestardev.morimint.Model.GiftCodeModel;
import com.rarestardev.morimint.Model.MoriNewsModel;
import com.rarestardev.morimint.Model.TaskModel;

import java.io.IOException;
import java.net.SocketTimeoutException;
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

    public void GetDataMoriNews(Context context, RecyclerView recyclerView) {

        final SweetAlertDialog dialog = new SweetAlertDialog(context,SweetAlertDialog.PROGRESS_TYPE);
        dialog.setTitle("Updated");
        dialog.setContentText("Please wait");
        dialog.setCancelable(false);
        dialog.show();

        Call<List<MoriNewsModel>> call = apiService.GetMoriNews(ApiClient.SERVER_TOKEN);
        call.enqueue(new Callback<List<MoriNewsModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<MoriNewsModel>> call, @NonNull Response<List<MoriNewsModel>> response) {
                if (response.body() != null) {
                    dialog.dismiss();
                    List<MoriNewsModel> moriNewsModel = response.body();
                    MoriNewsAdapter adapter = new MoriNewsAdapter(moriNewsModel, context);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.refreshDrawableState();
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(adapter);
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
                    SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
                    dialog.setCancelable(false);
                    dialog.setTitle("No Internet!");
                    dialog.setContentText("Please check the internet connection");
                    dialog.setConfirmButton("Exit", sweetAlertDialog -> {
                        ((NewsActivity) context).finish();
                        dialog.dismiss();
                    });
                    dialog.show();
                }
            }
        });
    }

    public void GetPinnedNews(TextView textView) {
        Call<List<MoriNewsModel>> call = apiService.GetMoriNewsPinned(ApiClient.SERVER_TOKEN);
        call.enqueue(new Callback<List<MoriNewsModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<MoriNewsModel>> call, @NonNull Response<List<MoriNewsModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MoriNewsModel> moriNewsModel = response.body();

                    String msg = moriNewsModel.get(0).getContent();
                    boolean isPin = moriNewsModel.get(0).isIs_published();

                    if (msg != null){
                        if (isPin){
                            textView.setText(msg);
                        }else {
                            textView.setText("");
                        }
                    }else {
                        textView.setText("");
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MoriNewsModel>> call, @NonNull Throwable t) {
                Log.e(UserConstants.APP_LOG_TAG, "Pinned news : onFailure", t);
            }
        });
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

        final SweetAlertDialog alertDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitle("Checked...");
        alertDialog.setContentText("Please Wait");
        alertDialog.setCancelable(false);
        alertDialog.show();

        GiftCodeModel giftCodeModel = new GiftCodeModel(code);

        Call<ApiResponse> call = apiService.SiteGiftCode(token, giftCodeModel);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    alertDialog.dismiss();
                    ApiResponse apiResponse = response.body();
                    String status = apiResponse.getStatus();
                    String message = apiResponse.getMessage();

                    if (status.equals("error")) {
                        final SweetAlertDialog alertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
                        alertDialog.setTitle(status);
                        alertDialog.setContentText(message);
                        alertDialog.setCancelable(false);
                        alertDialog.setConfirmButton("Ok", Dialog::dismiss).show();
                    } else {
                        final SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                        dialog.setTitle(status);
                        dialog.setContentText(message);
                        dialog.setCancelable(false);
                        dialog.setConfirmButton("Claim", Dialog::dismiss).show();
                    }
                    Log.d(UserConstants.APP_LOG_TAG, "GiftCoinSite : Success");
                } else {
                    alertDialog.dismiss();
                    Log.e(UserConstants.APP_LOG_TAG, "GiftCoinSite : Error" + response.errorBody());
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

        final SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        dialog.setTitle("Checked...");
        dialog.setContentText("Please Wait");
        dialog.setCancelable(false);
        dialog.show();

        GiftCodeModel giftCodeModel = new GiftCodeModel(code);

        Call<ApiResponse> call = apiService.GiftCode(token, giftCodeModel.getCode());
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    String status = apiResponse.getStatus();
                    String message = apiResponse.getMessage();
                    dialog.dismiss();

                    if (status.equals("error")) {
                        final SweetAlertDialog alertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
                        alertDialog.setTitle(status);
                        alertDialog.setContentText(message);
                        alertDialog.setCancelable(false);
                        alertDialog.setConfirmButton("Ok", Dialog::dismiss).show();
                    } else {
                        final SweetAlertDialog alertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                        alertDialog.setTitle(status);
                        alertDialog.setContentText(message);
                        alertDialog.setCancelable(false);
                        alertDialog.setConfirmButton("Claim", Dialog::dismiss).show();
                    }
                    Log.d(UserConstants.APP_LOG_TAG, "GiftCoin : Success");
                } else {
                    dialog.dismiss();
                    Log.e(UserConstants.APP_LOG_TAG, "GiftCoin : Error " + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                Log.e(UserConstants.APP_LOG_TAG, "GiftCoin : onFailure :", t);
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
                    textView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    List<TaskModel> tasks = response.body();
                    TaskListAdapter adapter = new TaskListAdapter(context, tasks);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.refreshDrawableState();
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(adapter);
                } else {
                    textView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
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
                    final SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                    dialog.setTitle("Success");
                    dialog.setContentText(task.getDetail());
                    dialog.setCancelable(false);
                    dialog.setConfirmButton("Ok", Dialog::dismiss).show();
                } else {
                    SingleResponse task = response.body();
                    final SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                    dialog.setTitle("Failed");
                    dialog.setContentText(task != null ? task.getDetail() : null);
                    dialog.setCancelable(false);
                    dialog.setConfirmButton("Ok", Dialog::dismiss).show();

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
