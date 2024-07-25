package com.rarestardev.morimint.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.rarestardev.morimint.Model.MoriNewsModel;
import com.rarestardev.morimint.R;

import java.util.List;

public class MoriNewsAdapter extends RecyclerView.Adapter<MoriNewsAdapter.NewsHolder> {

    List<MoriNewsModel> moriNewsModels;
    Context context;

    public MoriNewsAdapter(List<MoriNewsModel> moriNewsModels, Context context) {
        this.moriNewsModels = moriNewsModels;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mori_news_items, parent, false);
        return new NewsHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        holder.title.setText(moriNewsModels.get(position).getTitle() + ":");
        holder.tvNews.setText(moriNewsModels.get(position).getContent());
        holder.tvLink.setText(moriNewsModels.get(position).getLink());

        holder.tvLink.setOnClickListener(v -> {
            String link = moriNewsModels.get(position).getLink();
            Intent openBrowser = new Intent(Intent.ACTION_VIEW);
            openBrowser.setData(Uri.parse(link));
            context.startActivity(openBrowser);
        });
    }

    @Override
    public int getItemCount() {
        return moriNewsModels.size();
    }

    static class NewsHolder extends RecyclerView.ViewHolder {

        AppCompatTextView title, tvNews, tvLink;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            tvNews = itemView.findViewById(R.id.tvNews);
            tvLink = itemView.findViewById(R.id.tvLink);

        }
    }

}
