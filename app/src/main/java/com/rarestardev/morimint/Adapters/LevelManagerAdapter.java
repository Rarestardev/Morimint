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

import com.rarestardev.morimint.OfflineModel.LevelManagerModel;
import com.rarestardev.morimint.R;

import java.util.List;

public class LevelManagerAdapter extends RecyclerView.Adapter<LevelManagerAdapter.LevelManagerViewHolder>{

    List<LevelManagerModel> levelManagerModels;
    Context context;
    LayoutInflater inflater;
    View view;
    int level;

    @SuppressLint("NotifyDataSetChanged")
    public LevelManagerAdapter(Context context,List<LevelManagerModel> levelManagerModels,int level) {
        this.levelManagerModels = levelManagerModels;
        this.context = context;
        this.level = level;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LevelManagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.level_items,parent,false);
        }
        return new LevelManagerViewHolder(view);
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull LevelManagerViewHolder holder, int position) {
        holder.imageViewCharacter.setImageDrawable(context.getDrawable(levelManagerModels.get(position).getImageId()));
        holder.textViewEnergy.setText("+ " + levelManagerModels.get(position).getEnergy());
        holder.textViewLevel.setText("Level : " + levelManagerModels.get(position).getLevel());
        holder.textViewTap.setText("Tap : " + "+ " + levelManagerModels.get(position).getTap());
        holder.textViewMinCoin.setText("+ " + levelManagerModels.get(position).getCoin());
        boolean isPassed = levelManagerModels.get(position).isPassed();

        if (isPassed){
            holder.layoutIsPassed.setVisibility(View.GONE);
        }else {
            holder.layoutIsPassed.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return levelManagerModels.size();
    }

    static class LevelManagerViewHolder extends RecyclerView.ViewHolder{

        AppCompatImageView imageViewCharacter;
        AppCompatTextView textViewLevel,textViewMinCoin,textViewTap,textViewEnergy;
        CardView layoutIsPassed;


        public LevelManagerViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewCharacter = itemView.findViewById(R.id.imageViewCharacter);
            textViewLevel = itemView.findViewById(R.id.textViewLevel);
            textViewMinCoin = itemView.findViewById(R.id.textViewMinCoin);
            textViewTap = itemView.findViewById(R.id.textViewTap);
            textViewEnergy = itemView.findViewById(R.id.textViewEnergy);
            layoutIsPassed = itemView.findViewById(R.id.layoutIsPassed);

        }
    }


    private void ActiveLevelManager(){

    }
}
