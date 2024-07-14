package com.rarestardev.morimint.Repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rarestardev.morimint.Adapters.DailyRewardAdapter;
import com.rarestardev.morimint.Api.ApiClient;
import com.rarestardev.morimint.Api.ApiService;
import com.rarestardev.morimint.Model.DailyRewardModel;
import com.rarestardev.morimint.Model.MoriNewsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplicationDataRepository {

    ApiService apiService;

    public ApplicationDataRepository(){
        apiService = ApiClient.getClientStaticData().create(ApiService.class);
    }

    public LiveData<List<MoriNewsModel>> GetDataMoriNews() {
        MutableLiveData<List<MoriNewsModel>> data = new MutableLiveData<>();
        Call<List<MoriNewsModel>> call = apiService.GetMoriNews();
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

    public void DailyRewardData(RecyclerView recyclerView , Context context){
        Call<List<DailyRewardModel>> call = apiService.GetDailyReward();
        call.enqueue(new Callback<List<DailyRewardModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<DailyRewardModel>> call, @NonNull Response<List<DailyRewardModel>> response) {
                if (response.body() != null){
                    List<DailyRewardModel> dailyRewardModels = response.body();
                    DailyRewardAdapter adapter = new DailyRewardAdapter(dailyRewardModels,context);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setHasFixedSize(true);
                    recyclerView.refreshDrawableState();
                    recyclerView.setAdapter(adapter);
                    Log.d("DailyRewardRepo","Success");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<DailyRewardModel>> call, @NonNull Throwable t) {
                Log.e("DailyRewardRepo","Error",t);
            }
        });
    }
}
