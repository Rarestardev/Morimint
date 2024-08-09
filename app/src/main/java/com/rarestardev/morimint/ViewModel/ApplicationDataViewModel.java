package com.rarestardev.morimint.ViewModel;

import android.content.Context;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.rarestardev.morimint.Model.ApplicationSetupModel;
import com.rarestardev.morimint.Repository.ApplicationDataRepository;

public class ApplicationDataViewModel extends ViewModel {

    ApplicationDataRepository applicationDataRepository;

    public ApplicationDataViewModel(){
        applicationDataRepository = new ApplicationDataRepository();
    }


    public void GetDataDailyReward(RecyclerView recyclerView, Context context,TextView textView){
        applicationDataRepository.DailyRewardData(recyclerView,context,textView);
    }


    public void SetDataMoriNews(Context context, RecyclerView recyclerView, AppCompatTextView textView){
        applicationDataRepository.GetDataMoriNews(context, recyclerView,textView);
    }

    public void PinnedNews(TextView textView, AppCompatImageView imageView){
        applicationDataRepository.GetPinnedNews(textView,imageView);
    }

    public LiveData<ApplicationSetupModel> SetApplicationSetup(){
        return applicationDataRepository.ApplicationConfig();
    }

    public void SiteGiftCode(Context context,String code){
        applicationDataRepository.SiteGiftCode(context, code);
    }

    public void GiftCode(Context context,String code){
        applicationDataRepository.GiftCode(context, code);
    }

    public void getTasks(Context context, RecyclerView recyclerView, TextView textView){
        applicationDataRepository.GetTasks(context, recyclerView, textView);
    }

    public void MiniAppCode(Context context,String miniAppCode){
        applicationDataRepository.MiniAppBonusCode(context, miniAppCode);
    }

}
