package com.rarestardev.morimint.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.rarestardev.morimint.Adapters.MoriNewsAdapter;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.ViewModel.ApplicationDataViewModel;

public class NewsActivity extends AppCompatActivity {
    private RecyclerView recyclerViewMoriNews;
    private MoriNewsAdapter adapter;
    ApplicationDataViewModel applicationDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        recyclerViewMoriNews = findViewById(R.id.recyclerViewMoriNews);

        // View Model
        applicationDataViewModel = new ViewModelProvider(this).get(ApplicationDataViewModel.class);
        applicationDataViewModel.SetDataMoriNews().observe(this, moriNewsModels -> {
            adapter = new MoriNewsAdapter(moriNewsModels, NewsActivity.this);
            recyclerViewMoriNews.setLayoutManager(new LinearLayoutManager(NewsActivity.this));
            recyclerViewMoriNews.refreshDrawableState();
            recyclerViewMoriNews.setHasFixedSize(true);
            recyclerViewMoriNews.setAdapter(adapter);
        });
    }
}