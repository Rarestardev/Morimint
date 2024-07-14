package com.rarestardev.morimint.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.rarestardev.morimint.Model.MoriNewsModel;
import com.rarestardev.morimint.Repository.ApplicationDataRepository;

import java.util.List;

public class ApplicationDataViewModel extends ViewModel {

    ApplicationDataRepository applicationDataRepository;

    public ApplicationDataViewModel(){
        applicationDataRepository = new ApplicationDataRepository();
    }


    public void GetDataDailyReward(RecyclerView recyclerView, Context context){
        applicationDataRepository.DailyRewardData(recyclerView,context);
    }


    public LiveData<List<MoriNewsModel>> SetDataMoriNews(){
        return applicationDataRepository.GetDataMoriNews();
    }

}
