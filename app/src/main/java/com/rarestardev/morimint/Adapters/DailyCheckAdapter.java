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
    Context context;
    List<DailyCheckModel> dailyCheckModels;

    public DailyCheckAdapter(Context context, List<DailyCheckModel> dailyCheckModels) {
        this.context = context;
        this.dailyCheckModels = dailyCheckModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewSmall = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_bonus_item_small, parent, false);
        return new ViewHolder(viewSmall);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dailyCheckModels.size();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvDaySmall, tvRewardSmall;
        AppCompatImageView isCompleteSmall;
        CardView bonusLockedSmall;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDaySmall = itemView.findViewById(R.id.tvDaySmall);
            isCompleteSmall = itemView.findViewById(R.id.isCompleteSmall);
            tvRewardSmall = itemView.findViewById(R.id.tvRewardSmall);
            bonusLockedSmall = itemView.findViewById(R.id.bonusLockedSmall);
        }
    }
}
