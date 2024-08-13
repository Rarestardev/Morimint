package com.rarestardev.morimint.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rarestardev.morimint.OfflineModel.RoadmapModel;
import com.rarestardev.morimint.R;

import java.util.List;

public class RoadmapAdapter extends RecyclerView.Adapter<RoadmapAdapter.RoadmapViewHolder>{

    Context context;

    List<RoadmapModel> roadmapModels;

    LayoutInflater layoutInflater;

    View view;

    public RoadmapAdapter(Context context, List<RoadmapModel> roadmapModels) {
        this.context = context;
        this.roadmapModels = roadmapModels;
    }

    @NonNull
    @Override
    public RoadmapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.roadmap_items,parent,false);
        }
        return new RoadmapViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoadmapViewHolder holder, int position) {
        holder.tvNumber.setText(String.valueOf(roadmapModels.get(position).getNumber()));
        holder.tvRoadmap.setText(roadmapModels.get(position).getRoadmap());

        boolean isCompleteRoadmaps = roadmapModels.get(position).isComplete();

        if (isCompleteRoadmaps){
            holder.success.setVisibility(View.VISIBLE);
            holder.progress.setVisibility(View.GONE);
        }else {
            holder.progress.setVisibility(View.VISIBLE);

            RotateAnimation rotate = new RotateAnimation(0, 360,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(800);
            rotate.setInterpolator(new LinearInterpolator());
            rotate.setRepeatCount(Animation.INFINITE);
            holder.progress.setAnimation(rotate);
            holder.success.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return roadmapModels.size();
    }

    static class RoadmapViewHolder extends RecyclerView.ViewHolder{

        AppCompatImageView progress;
        CardView success;
        AppCompatTextView tvRoadmap,tvNumber;

        public RoadmapViewHolder(@NonNull View itemView) {
            super(itemView);

            progress = itemView.findViewById(R.id.progress);
            success = itemView.findViewById(R.id.success);
            tvRoadmap = itemView.findViewById(R.id.tvRoadmap);
            tvNumber = itemView.findViewById(R.id.tvNumber);

        }
    }
}
