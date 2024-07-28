package com.rarestardev.morimint.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.rarestardev.morimint.Model.DailyRewardModel;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.Repository.ApplicationDataRepository;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class DailyRewardAdapter extends RecyclerView.Adapter<DailyRewardAdapter.DailyRewardViewHolder> {

    List<DailyRewardModel> dailyRewardModels;
    Context context;

    public DailyRewardAdapter(List<DailyRewardModel> dailyRewardModels, Context context) {
        this.dailyRewardModels = dailyRewardModels;
        this.context = context;
    }

    @NonNull
    @Override
    public DailyRewardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_reward_items, parent, false);
        return new DailyRewardViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DailyRewardViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setGroupingSeparator(',');
        DecimalFormat numberFormat = new DecimalFormat("###,###,###,###", decimalFormatSymbols);
        numberFormat.setGroupingSize(3);
        numberFormat.setMaximumFractionDigits(2);

        holder.reward.setText("+" + numberFormat.format(dailyRewardModels.get(position).getGift_coin()));
        holder.title.setText(dailyRewardModels.get(position).getTitle());

        final String Shared_pref_daily_reward = "Daily Reward";
        final String Shared_pref_daily_reward_KEY = "Daily Reward ID";
        SharedPreferences sharedPreferences = context.getSharedPreferences(Shared_pref_daily_reward,Context.MODE_PRIVATE);
        int dailyID = sharedPreferences.getInt(Shared_pref_daily_reward_KEY + dailyRewardModels.get(position).getId(),0);

        if (dailyID == dailyRewardModels.get(position).getId()){
            holder.dailyCheck.setVisibility(View.VISIBLE);
            holder.rewardSuccess.setVisibility(View.VISIBLE);
            holder.getRewardItem.setOnClickListener(null);
        }else {
            holder.getRewardItem.setOnClickListener(v -> {
                holder.dailyCheck.setVisibility(View.VISIBLE);
                holder.rewardTimer.setVisibility(View.VISIBLE);
                final long timeCheckTask = 15 * 1000;
                CountDownTimer countDownTimer = new CountDownTimer(timeCheckTask, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        long second = (millisUntilFinished / 1000) % 60;
                        holder.rewardTimer.setText(second + "s");
                        holder.getRewardItem.setOnClickListener(null);
                    }

                    @Override
                    public void onFinish() {
                        holder.rewardTimer.setVisibility(View.GONE);
                        holder.rewardCheck.setVisibility(View.VISIBLE);
                        holder.rewardCheck.setOnClickListener(v1 -> {
                            SharedPreferences preferences = context.getSharedPreferences(Shared_pref_daily_reward,Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putInt(Shared_pref_daily_reward_KEY + dailyRewardModels.get(position).getId(),dailyRewardModels.get(position).getId());
                            editor.apply();
                            ApplicationDataRepository applicationDataRepository = new ApplicationDataRepository();
                            applicationDataRepository.ClaimDailyReward(context,dailyRewardModels.get(position));
                            holder.rewardSuccess.setVisibility(View.VISIBLE);
                        });
                    }
                };
                countDownTimer.start();

                String link = dailyRewardModels.get(position).getLink();
                OpenLink(link);
            });
        }
    }

    private void OpenLink(String link) {
        Intent openBrowser = new Intent(Intent.ACTION_VIEW);
        openBrowser.setData(Uri.parse(link));
        context.startActivity(openBrowser);
    }


    @Override
    public int getItemCount() {
        return dailyRewardModels.size();
    }

    public static class DailyRewardViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView title, reward, rewardCheck, rewardTimer;
        AppCompatImageView rewardSuccess;
        RelativeLayout getRewardItem, dailyCheck;

        public DailyRewardViewHolder(@NonNull View itemView) {
            super(itemView);

            getRewardItem = itemView.findViewById(R.id.getRewardItem);
            title = itemView.findViewById(R.id.title);
            reward = itemView.findViewById(R.id.reward);
            dailyCheck = itemView.findViewById(R.id.dailyCheck);
            rewardCheck = itemView.findViewById(R.id.rewardCheck);
            rewardTimer = itemView.findViewById(R.id.rewardTimer);
            rewardSuccess = itemView.findViewById(R.id.rewardSuccess);
        }
    }
}
