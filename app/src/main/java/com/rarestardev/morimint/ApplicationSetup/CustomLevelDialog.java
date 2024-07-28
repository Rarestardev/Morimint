package com.rarestardev.morimint.ApplicationSetup;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.rarestardev.morimint.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Objects;

public class CustomLevelDialog extends Dialog {

    AppCompatImageView imageViewCharacter,iconCloseDialog;
    AppCompatTextView tvLevel,tvBalanceCoin,tvTap;
    ProgressBar level_progressBar;
    RecyclerView levelsRecyclerView;
    Context context;
    long coin;

    ApplicationManager applicationManager;
    CoinMintManager coinMintManager;

    public CustomLevelDialog(@NonNull Context context,long coin) {
        super(context);
        this.context = context;
        this.coin = coin;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog_layout);
        Objects.requireNonNull(getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        getWindow().setGravity(Gravity.BOTTOM);

        applicationManager = new ApplicationManager(context);
        coinMintManager = new CoinMintManager(context);

        imageViewCharacter = findViewById(R.id.imageViewCharacter);
        tvLevel = findViewById(R.id.tvLevel);
        tvBalanceCoin = findViewById(R.id.tvBalanceCoin);
        level_progressBar = findViewById(R.id.level_progressBar);
        iconCloseDialog = findViewById(R.id.iconCloseDialog);
        tvTap = findViewById(R.id.tvTap);
        levelsRecyclerView = findViewById(R.id.levelsRecyclerView);


        applicationManager.initLevelDialog(context,coin,level_progressBar,imageViewCharacter,tvLevel,levelsRecyclerView,tvTap);

        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setGroupingSeparator(',');
        DecimalFormat numberFormat = new DecimalFormat("###,###,###,###", decimalFormatSymbols);
        numberFormat.setGroupingSize(3);
        numberFormat.setMaximumFractionDigits(2);
        tvBalanceCoin.setText(numberFormat.format(coin));

        iconCloseDialog.setOnClickListener(v -> this.dismiss());

    }
}
