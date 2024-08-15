package com.rarestardev.morimint.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import com.rarestardev.morimint.Adapters.ReferralTeamAdapter;
import com.rarestardev.morimint.Model.ReferralTeamModel;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.ViewModel.UserDataViewModel;
import com.rarestardev.morimint.databinding.ActivityReferralBinding;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class ReferralActivity extends AppCompatActivity {
    ActivityReferralBinding binding;

    UserDataViewModel userDataViewModel;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isTabletMode()) {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_referral_tablet);
        } else {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_referral);
        }


        userDataViewModel = new ViewModelProvider(this).get(UserDataViewModel.class);
        userDataViewModel.getUserData(this).observe(this, users -> {
            List<ReferralTeamModel> referralTeamModels = users.getInvited_users();
            if (referralTeamModels != null) {
                ReferralTeamAdapter referralTeamAdapter = new ReferralTeamAdapter(ReferralActivity.this, referralTeamModels);
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(ReferralActivity.this));
                binding.recyclerView.setHasFixedSize(true);
                binding.recyclerView.setAdapter(referralTeamAdapter);
                binding.recyclerView.refreshDrawableState();
                int total_friend = referralTeamModels.size();

                if (total_friend == 0) {
                    binding.recyclerView.setVisibility(View.GONE);
                    binding.tvNoData.setVisibility(View.VISIBLE);
                    binding.tvNoData.setText("No friends!");
                } else {
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    binding.tvNoData.setVisibility(View.GONE);
                }

                binding.totalInvite.setText(total_friend + " Referral");

                DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
                decimalFormatSymbols.setGroupingSeparator(',');
                DecimalFormat numberFormat = new DecimalFormat("###,###,###,###", decimalFormatSymbols);
                numberFormat.setGroupingSize(3);
                numberFormat.setMaximumFractionDigits(2);

                int total_bonus = total_friend * 20000;
                binding.totalBonus.setText("+ " + numberFormat.format(total_bonus));

                binding.inviteCode.setText("Invite code : " + users.getReferral_code());

                binding.btnCopyReferralCode.setOnClickListener(v -> {
                    String code = String.valueOf(users.getReferral_code());
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Morimint", code);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(this, "Copied!", Toast.LENGTH_LONG).show();
                });

            }
        });
    }

    private boolean isTabletMode() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;

        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);

        return diagonalInches >= 7.0; // Tablet 7 inches
    }
}