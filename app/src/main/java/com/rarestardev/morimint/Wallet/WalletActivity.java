package com.rarestardev.morimint.Wallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.rarestardev.morimint.Activities.MainActivity;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.Utilities.NoDoubleClickListener;
import com.rarestardev.morimint.databinding.ActivityWalletBinding;

import java.util.Objects;

public class WalletActivity extends AppCompatActivity {

    private ActivityWalletBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_wallet);


        binding.connectWallet.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onSingleClick(View v) {
                super.onSingleClick(v);
                ShowDialogConnectionWallet();
            }
        });

    }

    private void ShowDialogConnectionWallet() {
        Dialog dialog = new Dialog(WalletActivity.this);
        dialog.setContentView(R.layout.connect_wallet_layout_dialog);
        Objects.requireNonNull(getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        getWindow().setGravity(Gravity.BOTTOM);

        dialog.setCancelable(true);
        dialog.show();
    }
}