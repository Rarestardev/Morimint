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

    boolean[] LEVEL_PASSED = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    private static final int[] LEVEL = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    private static final int[] LEVEL_ENERGY = {1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000, 11000, 12000, 14000, 16000, 18000};

    private static final int[] LEVEL_ITEM = {R.drawable.level_one, R.drawable.level_two,
            R.drawable.level_three, R.drawable.level_four,
            R.drawable.level_five, R.drawable.level_six,
            R.drawable.level_seven, R.drawable.level_eight,
            R.drawable.level_nine, R.drawable.level_ten,
            R.drawable.level_eleven, R.drawable.level_twelve,
            R.drawable.level_thirteen, R.drawable.level_fourteen,
            R.drawable.level_fifteen};

    AppCompatImageView imageViewCharacter,iconCloseDialog;
    AppCompatTextView tvLevel,tvBalanceCoin,tvTap;
    ProgressBar level_progressBar;
    RecyclerView levelsRecyclerView;
    Context context;
    long coin;

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

        coinMintManager = new CoinMintManager(context);

        imageViewCharacter = findViewById(R.id.imageViewCharacter);
        tvLevel = findViewById(R.id.tvLevel);
        tvBalanceCoin = findViewById(R.id.tvBalanceCoin);
        level_progressBar = findViewById(R.id.level_progressBar);
        iconCloseDialog = findViewById(R.id.iconCloseDialog);
        tvTap = findViewById(R.id.tvTap);
        levelsRecyclerView = findViewById(R.id.levelsRecyclerView);


        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setGroupingSeparator(',');
        DecimalFormat numberFormat = new DecimalFormat("###,###,###,###", decimalFormatSymbols);
        numberFormat.setGroupingSize(3);
        numberFormat.setMaximumFractionDigits(2);
        tvBalanceCoin.setText(numberFormat.format(coin));

        iconCloseDialog.setOnClickListener(v -> this.dismiss());

    }
}
