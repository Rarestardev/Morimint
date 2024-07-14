package com.rarestardev.morimint.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import java.util.List;

public class DailyRewardAdapter extends RecyclerView.Adapter<DailyRewardAdapter.DailyRewardViewHolder>{

    List<DailyRewardModel> dailyRewardModels;
    Context context;

    public DailyRewardAdapter(List<DailyRewardModel> dailyRewardModels, Context context) {
        this.dailyRewardModels = dailyRewardModels;
        this.context = context;
    }

    @NonNull
    @Override
    public DailyRewardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_reward_items,parent,false);
        return new DailyRewardViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DailyRewardViewHolder holder, int position) {
        holder.reward.setText("+" + dailyRewardModels.get(position).getGift_coin());
        holder.title.setText(dailyRewardModels.get(position).getTitle());
        holder.getRewardItem.setOnClickListener(v -> {
            String link = dailyRewardModels.get(position).getLink();
            Intent openBrowser = new Intent(Intent.ACTION_VIEW);
            openBrowser.setData(Uri.parse(link));
            context.startActivity(openBrowser);
        });
    }

    @Override
    public int getItemCount() {
        return dailyRewardModels.size();
    }

    public class DailyRewardViewHolder extends RecyclerView.ViewHolder{
        AppCompatImageView dailyIcon;
        AppCompatTextView title,reward;
        RelativeLayout getRewardItem;
        public DailyRewardViewHolder(@NonNull View itemView) {
            super(itemView);

            getRewardItem = itemView.findViewById(R.id.getRewardItem);
            title = itemView.findViewById(R.id.title);
            reward = itemView.findViewById(R.id.reward);
            dailyIcon = itemView.findViewById(R.id.dailyIcon);
        }
    }
}
