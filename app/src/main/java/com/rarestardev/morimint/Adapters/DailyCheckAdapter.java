package com.rarestardev.morimint.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rarestardev.morimint.Model.DailyCheckModel;
import com.rarestardev.morimint.R;

import java.util.List;

public class DailyCheckAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_LARGE = 0;
    private static final int VIEW_TYPE_SMALL = 1;
    Context context;
    List<DailyCheckModel> dailyCheckModels;

    long days;

    public DailyCheckAdapter(Context context, List<DailyCheckModel> dailyCheckModels) {
        this.context = context;
        this.dailyCheckModels = dailyCheckModels;
    }

    @Override
    public int getItemViewType(int position) {
        if (days == 10 || days == 20 || days == 30){
            return VIEW_TYPE_LARGE;
        }else {
            return VIEW_TYPE_SMALL;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LARGE) {
            View viewLarge = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_bonus_item_large, parent, false);
            return new LargeViewHolder(viewLarge);
        } else {
            View viewSmall = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_bonus_item_small, parent, false);
            return new SmallViewHolder(viewSmall);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        days = dailyCheckModels.get(position).getDays();
        DailyCheckModel dailyCheckModel = dailyCheckModels.get(position);

        if (holder.getItemViewType() == VIEW_TYPE_SMALL){
            SmallViewHolder smallViewHolder = (SmallViewHolder) holder;
            smallViewHolder.tvDaySmall.setText("Day " + days);
            smallViewHolder.tvRewardSmall.setText(String.valueOf(dailyCheckModel.getGift()));
        }else {
            LargeViewHolder largeViewHolder = (LargeViewHolder) holder;
            largeViewHolder.tvDay.setText("Day " + days);
            largeViewHolder.tvReward.setText(String.valueOf(dailyCheckModel.getGift()));
        }


    }

    @Override
    public int getItemCount() {
        return dailyCheckModels.size();
    }

    private static class SmallViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvDaySmall, tvRewardSmall;
        AppCompatImageView isCompleteSmall;
        CardView bonusLockedSmall;

        public SmallViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDaySmall = itemView.findViewById(R.id.tvDaySmall);
            isCompleteSmall = itemView.findViewById(R.id.isCompleteSmall);
            tvRewardSmall = itemView.findViewById(R.id.tvRewardSmall);
            bonusLockedSmall = itemView.findViewById(R.id.bonusLockedSmall);
        }
    }

    private static class LargeViewHolder extends RecyclerView.ViewHolder {

        CardView bonusLocked;
        AppCompatTextView tvReward, tvDay;
        AppCompatImageView isComplete;

        public LargeViewHolder(@NonNull View itemView) {
            super(itemView);
            bonusLocked = itemView.findViewById(R.id.bonusLocked);
            tvReward = itemView.findViewById(R.id.tvReward);
            isComplete = itemView.findViewById(R.id.isComplete);
            tvDay = itemView.findViewById(R.id.tvDay);
        }
    }
}
