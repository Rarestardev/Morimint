package com.rarestardev.morimint.Utilities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.rarestardev.morimint.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class WelcomeDialog extends Dialog {

    private LinearLayoutCompat moriAiLayout,roadmapLayout,appLayout;

    private AppCompatCheckBox ShowAgain;

    private View page1,page2,page3;

    private AppCompatTextView tvPrevious,tvNext,moriAiText,roadmapText,appDescText;

    Context context;


    public WelcomeDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_dialog);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Objects.requireNonNull(getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.CENTER);

        initViews();
        NextPage();

        moriAiText.setText(readFromAssets("mori_ai_desc.txt"));
        roadmapText.setText(readFromAssets("roadmap.txt"));
        appDescText.setText(readFromAssets("info_app.txt"));


        SharedPreferences sharedPreferences = context.getSharedPreferences("WelcomeDialog",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        final String SHARED_KEY = "Show";
        editor.putBoolean(SHARED_KEY,false);
        editor.apply();

        ShowAgain.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean(SHARED_KEY,isChecked);
            editor.apply();
        });


    }


    private String readFromAssets(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream is = context.getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append('\n');
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    @SuppressLint("SetTextI18n")
    private void NextPage(){
        tvNext.setText("Next");
        tvNext.setOnClickListener(v -> {
            if (moriAiLayout.getVisibility() == View.VISIBLE){

                moriAiLayout.setVisibility(View.GONE);
                tvPrevious.setVisibility(View.VISIBLE);
                roadmapLayout.setVisibility(View.VISIBLE);
                page1.setBackgroundColor(context.getColor(R.color.ViewPage));
                page2.setBackgroundColor(context.getColor(R.color.white));


            }else if (roadmapLayout.getVisibility() == View.VISIBLE){

                roadmapLayout.setVisibility(View.GONE);
                appLayout.setVisibility(View.VISIBLE);
                page1.setBackgroundColor(context.getColor(R.color.ViewPage));
                page2.setBackgroundColor(context.getColor(R.color.ViewPage));
                page3.setBackgroundColor(context.getColor(R.color.white));
                tvNext.setText("Done");
                tvNext.setOnClickListener(v1 -> dismiss());
                PreviousPage();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void PreviousPage(){
        if (tvPrevious.getVisibility() == View.VISIBLE){
            tvPrevious.setOnClickListener(v2 -> {
                if (appLayout.getVisibility() == View.VISIBLE){
                    roadmapLayout.setVisibility(View.VISIBLE);
                    appLayout.setVisibility(View.GONE);
                    page1.setBackgroundColor(context.getColor(R.color.ViewPage));
                    page2.setBackgroundColor(context.getColor(R.color.white));
                    page3.setBackgroundColor(context.getColor(R.color.ViewPage));
                    tvNext.setText("Next");
                    NextPage();

                }else if (roadmapLayout.getVisibility() == View.VISIBLE){
                    moriAiLayout.setVisibility(View.VISIBLE);
                    tvPrevious.setVisibility(View.GONE);
                    roadmapLayout.setVisibility(View.GONE);
                    page1.setBackgroundColor(context.getColor(R.color.white));
                    page2.setBackgroundColor(context.getColor(R.color.ViewPage));
                    tvNext.setText("Next");
                    NextPage();
                }
            });
        }
    }


    private void initViews(){
        moriAiLayout = findViewById(R.id.moriAiLayout);
        roadmapLayout = findViewById(R.id.roadmapLayout);
        appLayout = findViewById(R.id.appLayout);
        ShowAgain = findViewById(R.id.ShowAgain);

        page1 = findViewById(R.id.page1);
        page2 = findViewById(R.id.page2);
        page3 = findViewById(R.id.page3);

        tvPrevious = findViewById(R.id.tvPrevious);
        tvNext = findViewById(R.id.tvNext);

        moriAiText = findViewById(R.id.moriAiText);
        roadmapText = findViewById(R.id.roadmapText);
        appDescText = findViewById(R.id.appDescText);
    }
}
