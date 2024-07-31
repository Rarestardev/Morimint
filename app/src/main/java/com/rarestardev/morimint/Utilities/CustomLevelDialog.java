package com.rarestardev.morimint.Utilities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rarestardev.morimint.Adapters.LevelManagerAdapter;
import com.rarestardev.morimint.ApplicationSetup.CoinMintManager;
import com.rarestardev.morimint.Constants.UserConstants;
import com.rarestardev.morimint.OfflineModel.LevelManagerModel;
import com.rarestardev.morimint.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomLevelDialog extends Dialog {

    boolean[] LEVEL_PASSED = {true, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    private static final int[] LEVEL = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    private static final int[] LEVEL_ENERGY = {1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000, 11000, 12000, 14000, 16000, 18000};
    private static final int[] LEVEL_TAP = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 16, 18};

    private static final int[] LEVEL_ITEM = {R.drawable.level_one, R.drawable.level_two,
            R.drawable.level_three, R.drawable.level_four,
            R.drawable.level_five, R.drawable.level_six,
            R.drawable.level_seven, R.drawable.level_eight,
            R.drawable.level_nine, R.drawable.level_ten,
            R.drawable.level_eleven, R.drawable.level_twelve,
            R.drawable.level_thirteen, R.drawable.level_fourteen,
            R.drawable.level_fifteen};

    private static final String SHARED_COIN_MINT_NAME = "Balance";
    private static final String SHARED_COIN_MINT_NAME_KEY = "Coin";

    AppCompatImageView imageViewCharacter, iconCloseDialog;
    AppCompatTextView tvLevel, tvBalanceCoin, tvTap;
    ProgressBar level_progressBar;
    RecyclerView levelsRecyclerView;
    Context context;
    long current_coin;
    int currentLevel;

    CoinMintManager coinMintManager;
    List<LevelManagerModel> levelManagerModels = new ArrayList<>();

    public CustomLevelDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @SuppressLint("SetTextI18n")
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

        SharedPreferences preferences = context.getSharedPreferences(SHARED_COIN_MINT_NAME, Context.MODE_PRIVATE);
        current_coin = preferences.getLong(SHARED_COIN_MINT_NAME_KEY, 0);

        SharedPreferences sharedPreferences = context.getSharedPreferences("Energy Manager", Context.MODE_PRIVATE);
        currentLevel = sharedPreferences.getInt("LEVEL", 0);


        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setGroupingSeparator(',');
        DecimalFormat numberFormat = new DecimalFormat("###,###,###,###", decimalFormatSymbols);
        numberFormat.setGroupingSize(3);
        numberFormat.setMaximumFractionDigits(2);
        tvBalanceCoin.setText(numberFormat.format(current_coin));

        iconCloseDialog.setOnClickListener(v -> this.dismiss());
        tvLevel.setText("Level : " + currentLevel);
        setImageWithLevel();
        levelManager();
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    private void setImageWithLevel() {
        switch (currentLevel) {
            case 1:
                imageViewCharacter.setImageDrawable(context.getDrawable(LEVEL_ITEM[0]));
                tvTap.setText("Tap : " + LEVEL_TAP[0]);
                break;
            case 2:
                imageViewCharacter.setImageDrawable(context.getDrawable(LEVEL_ITEM[1]));
                tvTap.setText("Tap : " + LEVEL_TAP[1]);
                break;
            case 3:
                imageViewCharacter.setImageDrawable(context.getDrawable(LEVEL_ITEM[2]));
                tvTap.setText("Tap : " + LEVEL_TAP[2]);
                break;
            case 4:
                imageViewCharacter.setImageDrawable(context.getDrawable(LEVEL_ITEM[3]));
                tvTap.setText("Tap : " + LEVEL_TAP[3]);
                break;
            case 5:
                imageViewCharacter.setImageDrawable(context.getDrawable(LEVEL_ITEM[4]));
                tvTap.setText("Tap : " + LEVEL_TAP[4]);
                break;
            case 6:
                imageViewCharacter.setImageDrawable(context.getDrawable(LEVEL_ITEM[5]));
                tvTap.setText("Tap : " + LEVEL_TAP[5]);
                break;
            case 7:
                imageViewCharacter.setImageDrawable(context.getDrawable(LEVEL_ITEM[6]));
                tvTap.setText("Tap : " + LEVEL_TAP[6]);
                break;
            case 8:
                imageViewCharacter.setImageDrawable(context.getDrawable(LEVEL_ITEM[7]));
                tvTap.setText("Tap : " + LEVEL_TAP[7]);
                break;
            case 9:
                imageViewCharacter.setImageDrawable(context.getDrawable(LEVEL_ITEM[8]));
                tvTap.setText("Tap : " + LEVEL_TAP[8]);
                break;
            case 10:
                imageViewCharacter.setImageDrawable(context.getDrawable(LEVEL_ITEM[9]));
                tvTap.setText("Tap : " + LEVEL_TAP[9]);
                break;
            case 11:
                imageViewCharacter.setImageDrawable(context.getDrawable(LEVEL_ITEM[10]));
                tvTap.setText("Tap : " + LEVEL_TAP[10]);
                break;
            case 12:
                imageViewCharacter.setImageDrawable(context.getDrawable(LEVEL_ITEM[11]));
                tvTap.setText("Tap : " + LEVEL_TAP[11]);
                break;
            case 13:
                imageViewCharacter.setImageDrawable(context.getDrawable(LEVEL_ITEM[12]));
                tvTap.setText("Tap : " + LEVEL_TAP[12]);
                break;
            case 14:
                imageViewCharacter.setImageDrawable(context.getDrawable(LEVEL_ITEM[13]));
                tvTap.setText("Tap : " + LEVEL_TAP[13]);
                break;
            case 15:
                imageViewCharacter.setImageDrawable(context.getDrawable(LEVEL_ITEM[14]));
                tvTap.setText("Tap : " + LEVEL_TAP[14]);
                break;
        }
    }


    private void levelManager() {
        for (int i = 0; i < LEVEL.length; i++) {
            if (i <= currentLevel - 1) {
                LEVEL_PASSED[i] = true;
            }
            levelManagerModels.add(new LevelManagerModel(
                    LEVEL[i],
                    LEVEL_ITEM[i],
                    LEVEL_PASSED[i],
                    LEVEL_ENERGY[i],
                    LEVEL_TAP[i],
                    UserConstants.LEVEL_COIN[i]
            ));
        }

        LevelManagerAdapter adapter = new LevelManagerAdapter(getContext(), levelManagerModels, currentLevel);
        levelsRecyclerView.setHasFixedSize(true);
        levelsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        levelsRecyclerView.refreshDrawableState();
        levelsRecyclerView.setAdapter(adapter);
    }
}
