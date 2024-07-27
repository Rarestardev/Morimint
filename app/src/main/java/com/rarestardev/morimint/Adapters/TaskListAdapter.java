package com.rarestardev.morimint.Adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.rarestardev.morimint.Model.TaskModel;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.Repository.CoinManagerRepository;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    Context context;

    List<TaskModel> taskModels;

    private static final String SHARED_TASK = "Tasks";
    private static final String SHARED_TASK_KEY_ID = "ID";

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
        holder.tvTaskTitle.setText(taskModels.get(position).getTitle());
        holder.tvBonusTask.setText("+ " + taskModels.get(position).getGift_coin());

        String openLink = taskModels.get(position).getLink();

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_TASK, Context.MODE_PRIVATE);
        int taskID = sharedPreferences.getInt(SHARED_TASK_KEY_ID, 0);

        if (taskID == taskModels.get(position).getId()){
            holder.taskComplete.setVisibility(View.VISIBLE);
        }


        holder.item.setOnClickListener(v -> {
            if (taskID != taskModels.get(position).getId()) {
                OpenLink(openLink);
                final long second = 1000;
                final long time = 10 * 1000;
                SweetAlertDialog checkDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
                CountDownTimer countDownTimer = new CountDownTimer(time, second) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        checkDialog.setCancelable(false);
                        checkDialog.setTitle("Loading...");
                        checkDialog.setContentText("Check task complete...");
                        checkDialog.show();
                    }

                    @Override
                    public void onFinish() {
                        checkDialog.dismiss();
                        holder.item.setOnClickListener(null);
                        holder.taskComplete.setVisibility(View.VISIBLE);
                        final SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                        dialog.setTitle("Success");
                        dialog.setContentText("");
                        dialog.setCancelable(true);
                        dialog.setConfirmButton("Claim", sweetAlertDialog -> {
                            SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_TASK, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt(SHARED_TASK_KEY_ID, taskModels.get(position).getId());
                            editor.apply();

                            CoinManagerRepository coinManagerRepository = new CoinManagerRepository();
                            coinManagerRepository.UpdateCoin(taskModels.get(position).getGift_coin(), context);

                            sweetAlertDialog.dismiss();
                        });
                        dialog.show();
                    }
                };
                countDownTimer.start();
            } else {
                final SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
                dialog.setTitle("Failed");
                dialog.setContentText("You won the prize for this section");
                dialog.setCancelable(false);
                dialog.setConfirmButton("Ok", Dialog::dismiss);
                dialog.show();
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
