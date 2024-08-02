package com.rarestardev.morimint.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.rarestardev.morimint.Model.DailyCheckModel;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.Repository.CoinManagerRepository;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DailyCheckAdapter extends RecyclerView.Adapter<DailyCheckAdapter.ViewHolder> {
    Context context;
    List<DailyCheckModel> dailyCheckModels;
    int currentDay;
    private static final String SHARED_PREF_NAME = "DailyCheck";

    public DailyCheckAdapter(Context context, List<DailyCheckModel> dailyCheckModels, int currentDay) {
        this.context = context;
        this.dailyCheckModels = dailyCheckModels;
        this.currentDay = currentDay;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewSmall = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_bonus_item_small, parent, false);
        return new ViewHolder(viewSmall);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvDaySmall.setText("Day : " + dailyCheckModels.get(position).getDay());
        holder.tvRewardSmall.setText(dailyCheckModels.get(position).getReward());
        boolean isClaim = dailyCheckModels.get(position).isClaimed();

        boolean isToday = dailyCheckModels.get(position).getDay() == currentDay;


        if (dailyCheckModels.get(position).getDay() == currentDay && !isClaim) {
            holder.isCompleteSmall.setImageDrawable(context.getDrawable(R.drawable.close_ic));
        } else {
            holder.isCompleteSmall.setImageDrawable(context.getDrawable(R.drawable.green_tick));
        }

        if (holder.isCompleteSmall.getDrawable() == context.getDrawable(R.drawable.green_tick)){
            holder.bonusLockedSmall.setVisibility(View.GONE);
        }else if (holder.isCompleteSmall.getDrawable() == context.getDrawable(R.drawable.close_ic)){
            holder.bonusLockedSmall.setVisibility(View.GONE);
        }else if (holder.isCompleteSmall.getVisibility() == View.GONE){
            holder.bonusLockedSmall.setVisibility(View.VISIBLE);
        }

        if (isToday) {
            holder.bonusLockedSmall.setVisibility(View.GONE);
            holder.items.setOnClickListener(v -> {
                if (!isClaim) {
                    long coin = dailyCheckModels.get(position).getCoin();
                    SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                    dialog.setTitle("Success");
                    dialog.setContentText("You are won :" + coin + "MoriBit");
                    dialog.setCancelable(false);
                    dialog.setConfirmButton("Claim", sweetAlertDialog -> {
                        dailyCheckModels.get(position).setClaimed(true);
                        notifyItemChanged(position);
                        saveRewardStatus(dailyCheckModels.get(position).getDay());
                        CoinManagerRepository coinManagerRepository = new CoinManagerRepository();
                        coinManagerRepository.UpdateCoin(coin, context);

                        sweetAlertDialog.dismiss();
                    }).show();
                }
            });
        } else {
            holder.bonusLockedSmall.setVisibility(View.VISIBLE);
        }


        if (isClaim) {
            holder.isCompleteSmall.setVisibility(View.VISIBLE);
            holder.bonusLockedSmall.setVisibility(View.GONE);
            holder.items.setOnClickListener(v -> Toast.makeText(context, "You won today's prize!", Toast.LENGTH_SHORT).show());
        } else {
            holder.isCompleteSmall.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return dailyCheckModels.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvDaySmall, tvRewardSmall;
        AppCompatImageView isCompleteSmall;
        CardView bonusLockedSmall;
        ConstraintLayout items;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDaySmall = itemView.findViewById(R.id.tvDaySmall);
            isCompleteSmall = itemView.findViewById(R.id.isCompleteSmall);
            tvRewardSmall = itemView.findViewById(R.id.tvRewardSmall);
            bonusLockedSmall = itemView.findViewById(R.id.bonusLockedSmall);
            items = itemView.findViewById(R.id.items);
        }
    }

    private void saveRewardStatus(int day) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(String.valueOf(day), true);
        editor.apply();
    }
}
