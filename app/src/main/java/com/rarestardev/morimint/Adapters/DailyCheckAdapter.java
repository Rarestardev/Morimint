package com.rarestardev.morimint.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
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

import com.rarestardev.morimint.Constants.UserConstants;
import com.rarestardev.morimint.OfflineModel.DailyCheckModel;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.Repository.CoinManagerRepository;
import com.rarestardev.morimint.Utilities.DialogType;
import com.rarestardev.morimint.Utilities.StatusDialog;
import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;
import com.startapp.sdk.adsbase.adlisteners.AdDisplayListener;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;

import java.util.List;

public class DailyCheckAdapter extends RecyclerView.Adapter<DailyCheckAdapter.ViewHolder> {
    Context context;
    List<DailyCheckModel> dailyCheckModels;
    int currentDay;
    private static final String SHARED_PREF_NAME = "DailyCheck";
    private StartAppAd startAppAd;
    private static final String ADS_TAG = "StartApp";

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
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        StartAppSDK.init(context, UserConstants.startAppId, true);
        StartAppSDK.setTestAdsEnabled(UserConstants.startAppIsTested);
        startAppAd = new StartAppAd(context);

        holder.tvDaySmall.setText("Day : " + dailyCheckModels.get(position).getDay());
        holder.tvRewardSmall.setText(dailyCheckModels.get(position).getReward());

        boolean isClaim = dailyCheckModels.get(position).isClaimed();
        int Day = dailyCheckModels.get(position).getDay();

        if (Day < currentDay){
            holder.bonusLockedSmall.setVisibility(View.GONE);

            holder.isCompleteSmall.setVisibility(View.VISIBLE);
            if (isClaim){
                holder.isCompleteSmall.setImageDrawable(context.getDrawable(R.drawable.green_tick));
            }else {
                holder.isCompleteSmall.setImageDrawable(context.getDrawable(R.drawable.close_ic));
            }
        }else if (Day > currentDay){
            holder.bonusLockedSmall.setVisibility(View.VISIBLE);
            holder.isCompleteSmall.setVisibility(View.GONE);
        }else {
            holder.isCompleteSmall.setVisibility(View.GONE);
            holder.bonusLockedSmall.setVisibility(View.GONE);
            if (!isClaim){
                holder.items.setOnClickListener(v -> {
                    long coin = dailyCheckModels.get(position).getCoin();
                    final StatusDialog dialog = new StatusDialog(context, DialogType.LOADING);
                    dialog.setTitleDialog("Loading");
                    dialog.setMessageDialog("Please wait");
                    dialog.setCancelable(false);
                    dialog.show();
                    startAppAd.loadAd(StartAppAd.AdMode.AUTOMATIC, new AdEventListener() {
                        @Override
                        public void onReceiveAd(@NonNull Ad ad) {
                            startAppAd.showAd(new AdDisplayListener() {
                                @Override
                                public void adHidden(Ad ad) {
                                    dialog.dismiss();
                                    StatusDialog dialog = new StatusDialog(context, DialogType.SUCCESS);
                                    dialog.setTitleDialog("Success");
                                    dialog.setMessageDialog("You are won :" + coin + "MoriBit");
                                    dialog.setCancelable(false);
                                    dialog.setButtonText("Claim");
                                    dialog.setButtonListener(v1 -> {
                                        dailyCheckModels.get(position).setClaimed(true);
                                        notifyItemChanged(position);
                                        saveRewardStatus(dailyCheckModels.get(position).getDay());
                                        CoinManagerRepository coinManagerRepository = new CoinManagerRepository();
                                        coinManagerRepository.UpdateCoin(coin, context);
                                        holder.isCompleteSmall.setVisibility(View.VISIBLE);
                                        holder.isCompleteSmall.setImageDrawable(context.getDrawable(R.drawable.green_tick));

                                        dialog.dismiss();
                                    });
                                    dialog.show();
                                }

                                @Override
                                public void adDisplayed(Ad ad) {
                                }

                                @Override
                                public void adClicked(Ad ad) {
                                }

                                @Override
                                public void adNotDisplayed(Ad ad) {
                                    dialog.dismiss();
                                }
                            });
                        }

                        @Override
                        public void onFailedToReceiveAd(Ad ad) {
                            dialog.dismiss();
                            Log.e(ADS_TAG, "Failed to receive interstitial ad.");
                        }
                    });
                });
            }else {
                holder.isCompleteSmall.setVisibility(View.VISIBLE);
                holder.isCompleteSmall.setImageDrawable(context.getDrawable(R.drawable.green_tick));
                holder.items.setOnClickListener(v ->
                    Toast.makeText(context, "You have won today's prize", Toast.LENGTH_LONG).show()
                );
            }

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
