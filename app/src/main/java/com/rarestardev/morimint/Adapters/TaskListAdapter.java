package com.rarestardev.morimint.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.rarestardev.morimint.Constants.UserConstants;
import com.rarestardev.morimint.Model.TaskModel;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.Repository.ApplicationDataRepository;
import com.rarestardev.morimint.Utilities.DialogType;
import com.rarestardev.morimint.Utilities.StatusDialog;
import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    Context context;

    List<TaskModel> taskModels;

    private static final String SHARED_TASK = "Tasks";
    private static final String SHARED_TASK_KEY_ID = "ID";

    private StartAppAd startAppAd;
    private static final String ADS_TAG = "StartApp";

    public TaskListAdapter(Context context, List<TaskModel> taskModels) {
        this.context = context;
        this.taskModels = taskModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        StartAppSDK.init(context, UserConstants.startAppId, true);
        StartAppSDK.setTestAdsEnabled(UserConstants.startAppIsTested);
        startAppAd = new StartAppAd(context);


        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setGroupingSeparator(',');
        DecimalFormat numberFormat = new DecimalFormat("###,###,###,###", decimalFormatSymbols);
        numberFormat.setGroupingSize(3);
        numberFormat.setMaximumFractionDigits(2);

        holder.tvTaskTitle.setText(taskModels.get(position).getTitle());
        holder.tvBonusTask.setText("+ " + numberFormat.format(taskModels.get(position).getGift_coin()));

        String openLink = taskModels.get(position).getLink();

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_TASK, Context.MODE_PRIVATE);
        int taskID = sharedPreferences.getInt(SHARED_TASK_KEY_ID + taskModels.get(position).getId(), 0);

        if (taskID == taskModels.get(position).getId()) {
            holder.taskComplete.setVisibility(View.VISIBLE);
        }


        holder.item.setOnClickListener(v -> {
            if (taskID != taskModels.get(position).getId()) {
                OpenLink(openLink);
                final long second = 1000;
                final long time = 10 * 1000;
                StatusDialog checkDialog = new StatusDialog(context, DialogType.LOADING);
                CountDownTimer countDownTimer = new CountDownTimer(time, second) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        checkDialog.setCancelable(false);
                        checkDialog.setTitleDialog("Loading...");
                        checkDialog.setMessageDialog("Check task complete...");
                        checkDialog.show();
                    }

                    @Override
                    public void onFinish() {
                        startAppAd.setVideoListener(() -> {
                            checkDialog.dismiss();
                            holder.item.setOnClickListener(null);
                            SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_TASK, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt(SHARED_TASK_KEY_ID + taskModels.get(position).getId(), taskModels.get(position).getId());
                            editor.apply();
                            ApplicationDataRepository applicationDataRepository = new ApplicationDataRepository();
                            applicationDataRepository.ClaimTask(context, taskModels.get(position));
                            holder.taskComplete.setVisibility(View.VISIBLE);
                        });
                        startAppAd.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
                            @Override
                            public void onReceiveAd(@NonNull Ad ad) {
                                Log.d(ADS_TAG, "Video ad received.");
                                checkDialog.dismiss();
                                startAppAd.showAd();
                            }

                            @Override
                            public void onFailedToReceiveAd(Ad ad) {
                                Log.e(ADS_TAG, "Failed to receive video ad.");
                                StatusDialog dialog = new StatusDialog(context, DialogType.FAILED);
                                dialog.setCancelable(false);
                                dialog.setTitleDialog("Try again");
                                dialog.setMessageDialog("Failed to receive ad");
                                dialog.setButtonText("Retry");
                                dialog.setButtonListener(v -> {
                                    startAppAd.showAd();
                                    dialog.dismiss();
                                });
                                dialog.show();
                            }
                        });
                    }
                };
                countDownTimer.start();
            } else {
                holder.item.setOnClickListener(null);
            }
        });


    }

    private void OpenLink(String link) {
        Intent openBrowser = new Intent(Intent.ACTION_VIEW);
        openBrowser.setData(Uri.parse(link));
        context.startActivity(openBrowser);
    }

    @Override
    public int getItemCount() {
        return taskModels.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout item;

        AppCompatTextView tvTaskTitle;

        AppCompatTextView tvBonusTask;

        AppCompatImageView taskComplete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.task_item);
            tvTaskTitle = itemView.findViewById(R.id.tvTaskTitle);
            tvBonusTask = itemView.findViewById(R.id.tvBonusTask);
            taskComplete = itemView.findViewById(R.id.taskComplete);

        }
    }
}
