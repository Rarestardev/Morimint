package com.rarestardev.morimint.Wallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rarestardev.morimint.Constants.UserConstants;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.Utilities.NoDoubleClickListener;
import com.rarestardev.morimint.databinding.ActivityWalletBinding;
import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.adsbase.StartAppSDK;

import java.util.Objects;

public class WalletActivity extends AppCompatActivity {

    ActivityWalletBinding binding;

    private static final String ADS_TAG = "StartApp";
    private static final String TOKEN_DESC_URL = "https://tonviewer.com/EQBhX8aPPjQiZSCutal6n10VIeGW5N7uqGuXI_NdCarPNxFj";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_wallet);

        StartAppSDK.init(this, UserConstants.startAppId, true);
        StartAppSDK.setTestAdsEnabled(UserConstants.startAppIsTested);
        Banner startAppBanner = binding.startappBanner;
        Log.d(ADS_TAG,startAppBanner + "");
        startAppBanner.loadAd();


        binding.connectWallet.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onSingleClick(View v) {
                super.onSingleClick(v);
                Toast.makeText(WalletActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });

        binding.tokenIdLayout.setOnClickListener(v -> OpenLink());

    }

    private void OpenLink() {
        Intent openBrowser = new Intent(Intent.ACTION_VIEW);
        openBrowser.setData(Uri.parse(TOKEN_DESC_URL));
        startActivity(openBrowser);
    }

    private void ShowDialogConnectionWallet() {
        Dialog dialog = new Dialog(WalletActivity.this);
        dialog.setContentView(R.layout.connect_wallet_layout_dialog);
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        dialog.setCancelable(true);
        dialog.show();


       /* AppCompatTextView textAnim = dialog.findViewById(R.id.textAnim);
       // ProgressBar progressBarStatus = dialog.findViewById(R.id.progressBarStatus);
       // CardView isConnectedWallet = dialog.findViewById(R.id.isConnectedWallet);
       // CardView errorConnectedWallet = dialog.findViewById(R.id.errorConnectedWallet);
        //CardView btnConnectWallet = dialog.findViewById(R.id.btnConnectWallet);*/
    }
}