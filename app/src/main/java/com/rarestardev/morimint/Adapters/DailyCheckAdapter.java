package com.rarestardev.morimint.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rarestardev.morimint.OfflineModel.DailyBonusModel;
import com.rarestardev.morimint.R;

import java.util.List;

public class DailyBonusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_LARGE = 0;
    private static final int VIEW_TYPE_SMALL = 1;
    Context context;
    List<DailyBonusModel> dailyBonusModels;

    public DailyBonusAdapter(Context context, List<DailyBonusModel> dailyBonusModels) {
        this.context = context;
        this.dailyBonusModels = dailyBonusModels;
    }

    @Override
    public int getItemViewType(int position) {
        DailyBonusModel bonusModel = dailyBonusModels.get(position);
        return bonusModel.isLarge() ? VIEW_TYPE_LARGE : VIEW_TYPE_SMALL;
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

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DailyBonusModel bonusModel = dailyBonusModels.get(position);
        if (holder.getItemViewType() == VIEW_TYPE_LARGE) {
            LargeViewHolder largeViewHolder = (LargeViewHolder) holder;
            largeViewHolder.tvReward.setText(bonusModel.getBonus());
            largeViewHolder.tvDay.setText(bonusModel.getDays());
            if (bonusModel.isLocked()) {
                largeViewHolder.bonusLocked.setVisibility(View.VISIBLE);
            } else {
                largeViewHolder.bonusLocked.setVisibility(View.GONE);
                if (bonusModel.isComplete()) {
                    largeViewHolder.isComplete.setVisibility(View.VISIBLE);
                } else {
                    largeViewHolder.isComplete.setVisibility(View.GONE);
                }
            }

        } else {
            SmallViewHolder smallViewHolder = (SmallViewHolder) holder;
            smallViewHolder.tvRewardSmall.setText(bonusModel.getBonus());
            smallViewHolder.tvDaySmall.setText(bonusModel.getDays());
            if (bonusModel.isLocked()) {
                smallViewHolder.bonusLockedSmall.setVisibility(View.VISIBLE);
            } else {
                smallViewHolder.bonusLockedSmall.setVisibility(View.GONE);
                if (bonusModel.isComplete()) {
                    smallViewHolder.isCompleteSmall.setVisibility(View.VISIBLE);
                } else {
                    smallViewHolder.isCompleteSmall.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return dailyBonusModels.size();
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
