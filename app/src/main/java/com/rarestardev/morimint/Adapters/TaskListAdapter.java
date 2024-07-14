package com.rarestardev.morimint.Adapters;

import android.content.Context;
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

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder>{

    Context context;

    List<TaskModel> taskModels;

    public TaskListAdapter(Context context, List<TaskModel> taskModels) {
        this.context = context;
        this.taskModels = taskModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTaskTitle.setText(taskModels.get(position).getTitle());
        holder.tvBonusTask.setText(String.valueOf(taskModels.get(position).getReward()));
    }

    @Override
    public int getItemCount() {
        return taskModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

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
