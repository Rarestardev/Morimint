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

import com.rarestardev.morimint.Model.ReferralTeamModel;
import com.rarestardev.morimint.R;
import java.util.List;

public class ReferralTeamAdapter extends RecyclerView.Adapter<ReferralTeamAdapter.TeamViewHolder>{

    List<ReferralTeamModel> referralTeamModels;
    Context context;
    private LayoutInflater inflater;
    private View view;

    private static final int[] LEVEL_ITEM = {R.drawable.level_one, R.drawable.level_two, R.drawable.level_three, R.drawable.level_four,
            R.drawable.level_five, R.drawable.level_six, R.drawable.level_seven, R.drawable.level_eight,
            R.drawable.level_nine, R.drawable.level_ten, R.drawable.level_eleven, R.drawable.level_twelve, R.drawable.level_thirteen,
            R.drawable.level_fourteen, R.drawable.level_fifteen};

    public ReferralTeamAdapter(Context context , List<ReferralTeamModel> referralTeamModels) {
        this.context = context;
        this.referralTeamModels = referralTeamModels;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.referral_item,parent,false);
        }
        return new TeamViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        holder.tvLevel.setText("Level : " + referralTeamModels.get(position).getLevel());
        holder.tvRefUsername.setText(referralTeamModels.get(position).getUsername());

        int level = referralTeamModels.get(position).getLevel();
        switch (level){
            case 1:
                holder.imageViewProfile.setImageDrawable(context.getDrawable(LEVEL_ITEM[0]));
                break;
            case 2:
                holder.imageViewProfile.setImageDrawable(context.getDrawable(LEVEL_ITEM[1]));
                break;
            case 3:
                holder.imageViewProfile.setImageDrawable(context.getDrawable(LEVEL_ITEM[2]));
                break;
            case 4:
                holder.imageViewProfile.setImageDrawable(context.getDrawable(LEVEL_ITEM[3]));
                break;
            case 5:
                holder.imageViewProfile.setImageDrawable(context.getDrawable(LEVEL_ITEM[4]));
                break;
            case 6:
                holder.imageViewProfile.setImageDrawable(context.getDrawable(LEVEL_ITEM[5]));
                break;
            case 7:
                holder.imageViewProfile.setImageDrawable(context.getDrawable(LEVEL_ITEM[6]));
                break;
            case 8:
                holder.imageViewProfile.setImageDrawable(context.getDrawable(LEVEL_ITEM[7]));
                break;
            case 9:
                holder.imageViewProfile.setImageDrawable(context.getDrawable(LEVEL_ITEM[8]));
                break;
            case 10:
                holder.imageViewProfile.setImageDrawable(context.getDrawable(LEVEL_ITEM[9]));
                break;
            case 11:
                holder.imageViewProfile.setImageDrawable(context.getDrawable(LEVEL_ITEM[10]));
                break;
            case 12:
                holder.imageViewProfile.setImageDrawable(context.getDrawable(LEVEL_ITEM[11]));
                break;
            case 13:
                holder.imageViewProfile.setImageDrawable(context.getDrawable(LEVEL_ITEM[12]));
                break;
            case 14:
                holder.imageViewProfile.setImageDrawable(context.getDrawable(LEVEL_ITEM[13]));
                break;
            case 15:
                holder.imageViewProfile.setImageDrawable(context.getDrawable(LEVEL_ITEM[14]));
                break;


        }
    }

    @Override
    public int getItemCount() {
        return referralTeamModels.size();
    }

    public static class TeamViewHolder extends RecyclerView.ViewHolder{

        AppCompatImageView imageViewProfile;
        AppCompatTextView tvRefUsername,tvLevel;

        public TeamViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewProfile = itemView.findViewById(R.id.imageViewProfile);
            tvRefUsername = itemView.findViewById(R.id.tvRefUsername);
            tvLevel = itemView.findViewById(R.id.tvLevel);

        }
    }
}
