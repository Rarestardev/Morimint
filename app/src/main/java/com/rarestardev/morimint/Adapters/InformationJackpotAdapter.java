package com.rarestardev.morimint.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.rarestardev.morimint.OfflineModel.InformationJackpotModel;
import com.rarestardev.morimint.R;

import java.util.List;

public class InformationJackpotAdapter extends RecyclerView.Adapter<InformationJackpotAdapter.Holder>{
    private List<InformationJackpotModel> jackpotModels;
    private Context context;

    public InformationJackpotAdapter(List<InformationJackpotModel> jackpotModels, Context context) {
        this.jackpotModels = jackpotModels;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.jackpot_information_item,parent,false);
        return new Holder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.icons.setImageResource(jackpotModels.get(position).getImageView());
        holder.tvDesc.setText(jackpotModels.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return jackpotModels.size();
    }

    public class Holder extends RecyclerView.ViewHolder{

        AppCompatImageView icons;
        AppCompatTextView tvDesc;

        public Holder(@NonNull View itemView) {
            super(itemView);

            icons = itemView.findViewById(R.id.icons);
            tvDesc = itemView.findViewById(R.id.tvDesc);

        }
    }
}
