package com.rarestardev.morimint.Utilities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.rarestardev.morimint.R;
import com.rarestardev.morimint.databinding.WelcomeDialogBinding;

import java.util.Objects;

public class WelcomeDialog extends Dialog {

    WelcomeDialogBinding binding;

    Context context;


    public WelcomeDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.welcome_dialog,null,false);
        setContentView(binding.getRoot());
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Objects.requireNonNull(getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.CENTER);

        NextPage();
        InitTextViews();

        SharedPreferences sharedPreferences = context.getSharedPreferences("WelcomeDialog",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        final String SHARED_KEY = "Show";
        editor.putBoolean(SHARED_KEY,false);
        editor.apply();

        binding.ShowAgain.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean(SHARED_KEY,isChecked);
            editor.apply();
        });


    }


    @SuppressLint("SetTextI18n")
    private void NextPage(){
        binding.tvNext.setText("Next");
        binding.tvNext.setOnClickListener(v -> {
            if (binding.LayoutPage1.getVisibility() == View.VISIBLE){
                binding.LayoutPage1.setVisibility(View.GONE);
                binding.LayoutPage2.setVisibility(View.VISIBLE);
                binding.tvPrevious.setVisibility(View.VISIBLE);
                binding.page1.setBackgroundColor(context.getColor(R.color.MidWhite));
                binding.page2.setBackgroundColor(context.getColor(R.color.white));
                PreviousPage();
            }else if (binding.LayoutPage2.getVisibility() == View.VISIBLE){
                binding.LayoutPage2.setVisibility(View.GONE);
                binding.LayoutPage3.setVisibility(View.VISIBLE);
                binding.page2.setBackgroundColor(context.getColor(R.color.MidWhite));
                binding.page3.setBackgroundColor(context.getColor(R.color.white));
                PreviousPage();
            }else if (binding.LayoutPage3.getVisibility() == View.VISIBLE){
                binding.LayoutPage3.setVisibility(View.GONE);
                binding.LayoutPage4.setVisibility(View.VISIBLE);
                binding.page3.setBackgroundColor(context.getColor(R.color.MidWhite));
                binding.page4.setBackgroundColor(context.getColor(R.color.white));
                binding.tvNext.setText("Done");
                PreviousPage();
            }else if (binding.LayoutPage4.getVisibility() == View.VISIBLE){
                PreviousPage();
                binding.tvNext.setOnClickListener(v1 -> dismiss());
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void PreviousPage(){
        binding.tvPrevious.setOnClickListener(v -> {
            if (binding.LayoutPage4.getVisibility() == View.VISIBLE){
                binding.LayoutPage4.setVisibility(View.GONE);
                binding.LayoutPage3.setVisibility(View.VISIBLE);
                binding.page4.setBackgroundColor(context.getColor(R.color.MidWhite));
                binding.page3.setBackgroundColor(context.getColor(R.color.white));
                NextPage();
            }else if (binding.LayoutPage3.getVisibility() == View.VISIBLE){
                binding.LayoutPage3.setVisibility(View.GONE);
                binding.LayoutPage2.setVisibility(View.VISIBLE);
                binding.page3.setBackgroundColor(context.getColor(R.color.MidWhite));
                binding.page2.setBackgroundColor(context.getColor(R.color.white));
                NextPage();
            }else if (binding.LayoutPage2.getVisibility() == View.VISIBLE){
                binding.LayoutPage2.setVisibility(View.GONE);
                binding.LayoutPage1.setVisibility(View.VISIBLE);
                binding.page2.setBackgroundColor(context.getColor(R.color.MidWhite));
                binding.page1.setBackgroundColor(context.getColor(R.color.white));
                binding.tvPrevious.setVisibility(View.GONE);
                NextPage();
            }else if (binding.LayoutPage1.getVisibility() == View.VISIBLE){
                NextPage();
            }
        });
    }



    @SuppressLint("SetTextI18n")
    private void InitTextViews(){
        binding.LayoutTitle1.setText("Receive Free Tokens");
        binding.LayoutTitle2.setText("Important Program Rules");
        binding.LayoutTitle3.setText("No Tokens Will Be Deducted from You");
        binding.LayoutTitle4.setText("High Income from Two Apps");


        binding.LayoutDesc1.setText("In this app, by minting, completing tasks, and collecting daily rewards, you can earn more tokens.");
        binding.LayoutDesc2.setText("After entering the app, be sure to read the program rules page to avoid any issues with your account during minting.");
        binding.LayoutDesc3.setText("You do not need to spend your tokens to use the app's features. Simply watch in-app ads and earn tokens from viewing them.");
        binding.LayoutDesc4.setText("You can earn a substantial income simultaneously from this app and the " +
                "MORI mini-app and start building your personal economy today. Both apps are officially " +
                "registered on the TON network.");
    }
}
