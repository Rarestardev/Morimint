package com.rarestardev.morimint.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.jinatonic.confetti.CommonConfetti;
import com.github.jinatonic.confetti.ConfettiSource;
import com.rarestardev.morimint.Constants.JackpotValues;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.Utilities.NoDoubleClickListener;
import com.rarestardev.morimint.ViewModel.CoinManagerViewModel;
import com.rarestardev.morimint.databinding.ActivityJackpotBinding;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class JackpotActivity extends AppCompatActivity {
    private ActivityJackpotBinding binding;
    private int ROTATE_COUNT_ONE = 0;
    private int ROTATE_COUNT_TWO = 0;
    private int ROTATE_COUNT_THREE = 0;
    private int value_one;
    private int value_two;
    private int value_three;
    private int Play_chance;
    private long Score = 0;
    double[] probabilities = {0.05, 0.10, 0.15, 0.15, 0.20, 0.35};
    private static final int playJackpot_ad = 5;
    private int jackpotAds;

    CoinManagerViewModel coinManagerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_jackpot);

        coinManagerViewModel = new ViewModelProvider(this).get(CoinManagerViewModel.class);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Worker", Context.MODE_PRIVATE);
        Play_chance = sharedPreferences.getInt("jackpot", 0);
        jackpotAds = sharedPreferences.getInt("jackpotAds", 0);

        binding.jackpotInfo.setOnClickListener(v ->
                startActivity(new Intent(JackpotActivity.this, JackpotInformationActivity.class)));

        UpdatePlayedJackpot();
    }

    private void UpdatePlayedJackpot() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Worker", Context.MODE_PRIVATE);
        Play_chance = sharedPreferences.getInt("jackpot", 0);
        jackpotAds = sharedPreferences.getInt("jackpotAds", 0);
        binding.tvChanceJackpot.setText(String.valueOf(Play_chance));
        if (Play_chance == 0) {
            binding.playJackpot.setVisibility(View.GONE);
            binding.stopJackpot.setVisibility(View.VISIBLE);
            if (jackpotAds != 0){
                MoreChanceJackpotPlay();
            } else {
                binding.playJackpot.setVisibility(View.GONE);
                binding.stopJackpot.setVisibility(View.VISIBLE);
            }
        } else {
            binding.playJackpot.setVisibility(View.VISIBLE);
            binding.stopJackpot.setVisibility(View.GONE);
            JackpotPlayHandle();
        }
    }


    private void JackpotPlayHandle() {
        binding.playJackpot.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onSingleClick(View v) {
                super.onSingleClick(v);
                Play_chance--;
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Worker", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("jackpot", Play_chance);
                editor.apply();

                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (vibrator != null) {
                    // newest device
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        vibrator.vibrate(50); // oldest device
                    }
                }

                binding.tvChanceJackpot.setText(String.valueOf(Play_chance));

                new Handler().postDelayed(() -> StartJackpotColumnOne(), 100);

                new Handler().postDelayed(() -> StartJackpotColumnTwo(), 200);

                new Handler().postDelayed(() -> StartJackpotColumnThree(), 300);
            }
        });
    }

    private void StartJackpotColumnOne() {
        Random random = new Random();
        binding.playJackpot.setVisibility(View.GONE);
        binding.stopJackpot.setVisibility(View.VISIBLE);
        animateColumn(binding.Column1, binding.imageView1, binding.imageView2, binding.imageView3, random, 1);
    }

    private void StartJackpotColumnTwo() {
        Random random = new Random();
        animateColumn(binding.Column2, binding.imageView4, binding.imageView5, binding.imageView6, random, 2);
    }

    private void StartJackpotColumnThree() {
        Random random = new Random();
        animateColumn(binding.Column3, binding.imageView7, binding.imageView8, binding.imageView9, random, 3);
    }

    private void animateColumn(FrameLayout item, ImageView imageView1, ImageView imageView2, ImageView imageView3, Random random, int column) {
        item.animate().translationY(-item.getHeight())
                .setDuration(JackpotValues.ANIMATION_DURATION)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animation) {
                        item.setY(0);

                        if (getRotateCount(column) != JackpotValues.rotate_count) {
                            incrementRotateCount(column);

                            item.animate().setDuration(JackpotValues.ANIMATION_DURATION).translationY(-item.getHeight()).start();


                            int image1 = random.nextInt(JackpotValues.JACKPOT_ICON_OFF.length);
                            int image2 = getRandomWeightedIcon();
                            int image3 = random.nextInt(JackpotValues.JACKPOT_ICON_OFF.length);

                            setValue(column, image2);

                            imageView1.setImageResource(JackpotValues.JACKPOT_ICON_OFF[image1]);
                            imageView2.setImageResource(JackpotValues.JACKPOT_ICON_OFF[image2]);
                            imageView3.setImageResource(JackpotValues.JACKPOT_ICON_OFF[image3]);
                        } else {
                            resetRotateCount(column);
                            item.setY(0);
                            new Handler().postDelayed(() -> updateImageView(imageView2, getValue(column)), getPostDelay(column));
                        }
                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animation) {
                        resetRotateCount(column);

                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animation) {
                    }
                });
    }

    private int getRandomWeightedIcon() {
        Random random = new Random();
        double r = random.nextDouble();

        double cumulativeProbability = 0.0;
        for (int i = 0; i < probabilities.length; i++) {
            cumulativeProbability += probabilities[i];
            if (r <= cumulativeProbability) {
                return i;
            }
        }
        return probabilities.length - 1;
    }

    private int getRotateCount(int column) {
        switch (column) {
            case 1:
                return ROTATE_COUNT_ONE;
            case 2:
                return ROTATE_COUNT_TWO;
            case 3:
                return ROTATE_COUNT_THREE;
            default:
                return 0;
        }
    }

    private void incrementRotateCount(int column) {
        switch (column) {
            case 1:
                ROTATE_COUNT_ONE++;
                break;
            case 2:
                ROTATE_COUNT_TWO++;
                break;
            case 3:
                ROTATE_COUNT_THREE++;
                break;
        }
    }

    private void resetRotateCount(int column) {
        switch (column) {
            case 1:
                ROTATE_COUNT_ONE = 0;
                break;
            case 2:
                ROTATE_COUNT_TWO = 0;
                break;
            case 3:
                ROTATE_COUNT_THREE = 0;
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (vibrator != null) {
                    // newest device
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(70, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        vibrator.vibrate(70); // oldest device
                    }
                }
                UpdatePlayedJackpot();

                checkAndAwardPoints();
                break;
        }
    }

    private void MoreChanceJackpotPlay() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Worker", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        jackpotAds --;

        if (jackpotAds < 0){
            jackpotAds = 0;
        }

        editor.putInt("jackpotAds",jackpotAds);
        editor.apply();

        SweetAlertDialog dialog = new SweetAlertDialog(JackpotActivity.this, SweetAlertDialog.WARNING_TYPE);
        dialog.setCancelable(false);
        dialog.setTitle("Try again");
        dialog.setContentText("More chance by seeing the ad");
        dialog.setConfirmButton("Show Ad", sweetAlertDialog -> {
            editor.putInt("jackpot", playJackpot_ad);
            editor.apply();
            binding.playJackpot.setVisibility(View.VISIBLE);
            binding.stopJackpot.setVisibility(View.GONE);
            UpdatePlayedJackpot();
            dialog.dismiss();
        });
        dialog.setCancelButton("Cancel", Dialog::dismiss);
        dialog.show();
    }

    private void checkAndAwardPoints() {
        if (value_one == value_two && value_two == value_three) {
            Score += calculateReward(value_one);
        }
    }

    private int calculateReward(int value) {
        switch (value) {
            case 0:
                ShowDialogClimbReward(5000000);
                return 5000000;
            case 1:
                ShowDialogClimbReward(1000000);
                return 1000000;
            case 2:
                ShowDialogClimbReward(500000);
                return 500000;
            case 3:
                ShowDialogClimbReward(100000);
                return 100000;
            case 4:
                ShowDialogClimbReward(50000);
                return 50000;
            case 5:
                ShowDialogClimbReward(10000);
                return 10000;

            default:
                return 0;
        }
    }

    private void ShowDialogClimbReward(int reward) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (reward == 5000000) {
            if (vibrator != null) {
                // newest device
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(600, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(600); // oldest device
                }
            }
        } else {
            if (vibrator != null) {
                // newest device
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(300); // oldest device
                }
            }
        }
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setGroupingSeparator(',');
        DecimalFormat numberFormat = new DecimalFormat("###,###,###,###", decimalFormatSymbols);
        numberFormat.setGroupingSize(3);
        numberFormat.setMaximumFractionDigits(2);
        showConfetti();
        SweetAlertDialog dialog = new SweetAlertDialog(JackpotActivity.this, SweetAlertDialog.SUCCESS_TYPE);
        dialog.setCancelable(false);
        dialog.setTitle("Good Job!");
        dialog.setContentText("Great , you won " + numberFormat.format(reward) + " MoriBit Coin");
        dialog.setConfirmButton("Claim", sweetAlertDialog -> {
            if (reward != 0) {
                SharedPreferences sharedPreferences = getSharedPreferences("Balance",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong("Coin",reward);
                editor.apply();
                coinManagerViewModel.UpdateCoin(reward, JackpotActivity.this);
                updateScoreDisplay();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showConfetti() {
        final int containerMiddleX = binding.parentRelative.getWidth() / 2;
        final int containerMiddleY = 0;
        final ConfettiSource confettiSource = new ConfettiSource(containerMiddleX, containerMiddleY);

        CommonConfetti.rainingConfetti(binding.parentRelative, confettiSource,
                        new int[]{getColor(R.color.JackpotBonusAnim1), getColor(R.color.JackpotBonusAnim2),
                                getColor(R.color.JackpotBonusAnim3)})

                .infinite()
                .setEmissionRate(150)
                .setEmissionDuration(4000);

    }


    private void updateScoreDisplay() {
        binding.tvBonusJackpot.setText(String.valueOf(Score));
        NumberFormat(Score, binding.tvBonusJackpot);
    }

    private void NumberFormat(long balance, @NonNull TextView tvBalance) {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setGroupingSeparator(',');
        DecimalFormat numberFormat = new DecimalFormat("###,###,###,###", decimalFormatSymbols);
        numberFormat.setGroupingSize(3);
        numberFormat.setMaximumFractionDigits(2);
        tvBalance.setText(numberFormat.format(balance));
    }

    private void setValue(int column, int value) {
        switch (column) {
            case 1:
                value_one = value;
                break;
            case 2:
                value_two = value;
                break;
            case 3:
                value_three = value;
                break;
        }
    }

    private int getValue(int column) {
        switch (column) {
            case 1:
                return value_one;
            case 2:
                return value_two;
            case 3:
                return value_three;
            default:
                return 0;
        }
    }

    private int getPostDelay(int column) {
        switch (column) {
            case 1:
                return 400;
            case 2:
                return 700;
            case 3:
                return 900;
            default:
                return 500;
        }
    }

    private void updateImageView(ImageView imageView, int value) {
        switch (value) {
            case 0:
                imageView.setImageResource(JackpotValues.JACKPOT_ICON_ON[0]);
                break;
            case 1:
                imageView.setImageResource(JackpotValues.JACKPOT_ICON_ON[1]);
                break;
            case 2:
                imageView.setImageResource(JackpotValues.JACKPOT_ICON_ON[2]);
                break;
            case 3:
                imageView.setImageResource(JackpotValues.JACKPOT_ICON_ON[3]);
                break;
            case 4:
                imageView.setImageResource(JackpotValues.JACKPOT_ICON_ON[4]);
                break;
            case 5:
                imageView.setImageResource(JackpotValues.JACKPOT_ICON_ON[5]);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.tvBonusJackpot.setText("0");
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.tvChanceJackpot.setText(String.valueOf(Play_chance));
    }
}