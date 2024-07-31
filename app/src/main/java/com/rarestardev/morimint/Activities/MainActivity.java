package com.rarestardev.morimint.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.rarestardev.morimint.ApplicationSetup.DaysManager;
import com.rarestardev.morimint.ApplicationSetup.ProgressBarManager;
import com.rarestardev.morimint.Utilities.CustomLevelDialog;
import com.rarestardev.morimint.ApplicationSetup.EnergyManager;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.ApplicationSetup.CoinMintManager;
import com.rarestardev.morimint.Utilities.NetworkChangeReceiver;
import com.rarestardev.morimint.Utilities.NoDoubleClickListener;
import com.rarestardev.morimint.Utilities.RewardTimer;
import com.rarestardev.morimint.ViewModel.ApplicationDataViewModel;
import com.rarestardev.morimint.ViewModel.UserDataViewModel;
import com.rarestardev.morimint.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Objects;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity implements NetworkChangeReceiver.NetworkChangeListener {

    private ActivityMainBinding binding;
    // Constants
    private static final long COIN_SHAKE_ANIMATION_TIMER = 250;
    private static final int[] animationAxis = {-250, 70, 500, 700, -200, 70, 500, -100, 70, 200};
    private static final int[] TEXT_ANIMATION_COLOR = {R.color.white, R.color.MidWhite, R.color.DarkGray, R.color.Gray};
    private static final String TAG = "HomeLog";// Log tag
    final String SHARED_TIMER_NAME = "RewardTimer";
    final String SHARED_TIMER_KEY_Turbo = "Turbo";

    private ProgressBarManager progressBarManager;

    // value
    private int clickerCounter;
    private int clickerCounterTurbo; // number of clicked for mint progressBarManager
    boolean isUserData;
    private int TurboCount; // Turbo
    private boolean is_active;
    private boolean is_mint_on;
    private long DayValue;

    // Class
    private CoinMintManager coinMintManager;
    private CountDownTimer turboDownTimer; // Turbo timer ProgressBarManager 12 second
    private NetworkChangeReceiver networkChangeReceiver;
    private EnergyManager energyManager;
    private RewardTimer rewardTimer;

    // ViewModels
    ApplicationDataViewModel applicationDataViewModel;
    UserDataViewModel userDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        networkChangeReceiver = new NetworkChangeReceiver(this);

        progressBarManager = new ProgressBarManager(MainActivity.this);

        energyManager = new EnergyManager(this, binding.tvLevelShow, binding.tvEnergy, binding.iconEnergy);
        energyManager.IncreasedEnergy();

        rewardTimer = new RewardTimer(this);
        rewardTimer.StartTimer();

        StartActivities();
        NavigationDrawerHandle();

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_TIMER_NAME,MODE_PRIVATE);
        int currentTurbo = sharedPreferences.getInt(SHARED_TIMER_KEY_Turbo,0);
        binding.tvTurboCount.setText(String.valueOf(currentTurbo));

        binding.applicationManagerLayout.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onSingleClick(View v) {
                super.onSingleClick(v);
                CustomLevelDialog customLevelDialog = new CustomLevelDialog(MainActivity.this, coinMintManager.getBalance());
                customLevelDialog.show();
            }
        });
    }

    public void getTurboCount(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_TIMER_NAME,MODE_PRIVATE);
        int currentTurbo = sharedPreferences.getInt(SHARED_TIMER_KEY_Turbo,0);
        if (currentTurbo == 0){
            binding.tvTurboCount.setText(String.valueOf(2));
        } else {
            binding.tvTurboCount.setText(String.valueOf(currentTurbo));
        }
    }

    private void MintHandler() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_TIMER_NAME,MODE_PRIVATE);
        int currentTurbo = sharedPreferences.getInt(SHARED_TIMER_KEY_Turbo,0);
        if (currentTurbo > 0) {
            binding.turboMint.setVisibility(View.VISIBLE);
            binding.turboCountLayout.setVisibility(View.VISIBLE);
            binding.coin.setVisibility(View.VISIBLE);
            binding.turbo.setVisibility(View.GONE);
            MintCoinAnimation();
            binding.turboMint.setOnClickListener(v -> TurboAnimation());
        } else if (currentTurbo == 0){
            binding.turboMint.setVisibility(View.GONE);
            binding.coin.setVisibility(View.VISIBLE);
            binding.turbo.setVisibility(View.GONE);
            binding.turboCountLayout.setVisibility(View.GONE);
            MintCoinAnimation();
        }
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    private void TurboAnimation() {
        turboDownTimer = new CountDownTimer(12000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.turboMint.setVisibility(View.GONE);
                binding.coin.setVisibility(View.GONE);
                binding.turboCountLayout.setVisibility(View.GONE);
                binding.turbo.setVisibility(View.VISIBLE);
                coinMintManager = new CoinMintManager(MainActivity.this);
                binding.CoinLayout.setOnTouchListener((v, event) -> {
                    float x = event.getX();
                    float y = event.getY();
                    int actionType = event.getActionMasked();
                    if (actionType == MotionEvent.ACTION_DOWN || actionType == MotionEvent.ACTION_POINTER_DOWN) {

                        switch (event.getPointerCount()) {
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                                clickerCounterTurbo++;
                                coinMintManager.IncreaseBalanceWithTurbo(clickerCounterTurbo, progressBarManager.getMinter());
                                progressBarManager.getCurrentCoin(coinMintManager.getBalance(), binding.levelXpProgressBar);
                                CreateAnimation(x, y);
                                break;
                        }
                    }
                    return true;
                });
            }

            @Override
            public void onFinish() {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_TIMER_NAME,MODE_PRIVATE);
                TurboCount = sharedPreferences.getInt(SHARED_TIMER_KEY_Turbo,0);
                binding.turbo.setVisibility(View.GONE);
                if (TurboCount > 0) {
                    TurboCount--;
                    MintHandler();
                } else {
                    TurboCount = 0;
                    binding.tvTurboCount.setText(String.valueOf(TurboCount));
                    turboDownTimer.cancel();
                    MintHandler();
                }
                binding.tvTurboCount.setText(String.valueOf(TurboCount));
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(SHARED_TIMER_KEY_Turbo,TurboCount);
                editor.apply();
            }
        };
        turboDownTimer.start();
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    private void MintCoinAnimation() {
        binding.CoinLayout.setOnTouchListener((v, event) -> {
            float x = event.getX();
            float y = event.getY();
            int actionType = event.getActionMasked();

            if (actionType == MotionEvent.ACTION_DOWN || actionType == MotionEvent.ACTION_POINTER_DOWN) {

                switch (event.getPointerCount()) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        if (!energyManager.mintStop(progressBarManager.getMinter())) {
                            clickerCounter++;
                            coinMintManager.IncreaseBalance(clickerCounter, progressBarManager.getMinter());
                            energyManager.clicked(true);

                            energyManager.ReduceEnergy(progressBarManager.getMinter());

                            progressBarManager.getCurrentCoin(coinMintManager.getBalance(), binding.levelXpProgressBar);

                            CreateAnimation(x, y);
                        }
                        break;
                }

            } else if (actionType == MotionEvent.ACTION_UP) {
                energyManager.clicked(false);
            }
            return true;
        });
    }

    @SuppressLint("SetTextI18n")
    private void CreateAnimation(float x, float y) {
        YoYo.with(Techniques.Shake).duration(COIN_SHAKE_ANIMATION_TIMER).playOn(binding.coin);
        YoYo.with(Techniques.Shake).duration(COIN_SHAKE_ANIMATION_TIMER).playOn(binding.turbo);

        NumberFormat(coinMintManager.getBalance(), binding.tvBalanceCoin);

        TextView animationTextView = new TextView(MainActivity.this);

        if (binding.turbo.getVisibility() == View.GONE){
            animationTextView.setText("+" + progressBarManager.getMinter());
        }else {
            animationTextView.setText("+" + progressBarManager.getMinter() * 3);
        }
        animationTextView.setTextSize(30);
        animationTextView.setTypeface(Typeface.DEFAULT_BOLD);
        animationTextView.setTextColor(getColor(TEXT_ANIMATION_COLOR[0]));
        animationTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        animationTextView.setX(x);
        animationTextView.setY(y);

        Thread thread = new Thread(() -> {

            animationTextView.postDelayed(() -> animationTextView.setTextColor(getColor(TEXT_ANIMATION_COLOR[1])), 500);

            animationTextView.postDelayed(() -> animationTextView.setTextColor(getColor(TEXT_ANIMATION_COLOR[2])), 700);

            animationTextView.postDelayed(() -> animationTextView.setTextColor(getColor(TEXT_ANIMATION_COLOR[3])), 800);
        });
        thread.start();


        binding.parent.addView(animationTextView);

        Random randomAxisNumber = new Random();

        int random = randomAxisNumber.nextInt(animationAxis.length - 1);

        TranslateAnimation animation = new TranslateAnimation(
                binding.CoinLayout.getX(),
                animationAxis[random],
                binding.CoinLayout.getY(),
                -200
        );
        animation.setDuration(1000);
        animation.setFillAfter(true);
        animation.setFillBefore(true);
        animation.setDetachWallpaper(true);


        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.parent.removeView(animationTextView);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animationTextView.startAnimation(animation);

    }

    private void NumberFormat(long balance, @NonNull TextView tvBalance) {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setGroupingSeparator(',');
        DecimalFormat numberFormat = new DecimalFormat("###,###,###,###", decimalFormatSymbols);
        numberFormat.setGroupingSize(3);
        numberFormat.setMaximumFractionDigits(2);
        tvBalance.setText(numberFormat.format(balance));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void NavigationDrawerHandle() {
        binding.drawerNavigationView.bringToFront();
        binding.openDrawer.setOnClickListener(v ->
                binding.drawer.openDrawer(GravityCompat.START)
        );

        binding.menuNavigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.wallet_menu) {
                binding.drawer.closeDrawers();
                startActivity(new Intent(MainActivity.this, WalletActivity.class));
            }
            if (item.getItemId() == R.id.booster_menu) {
                binding.drawer.closeDrawers();
                startActivity(new Intent(MainActivity.this, BoosterActivity.class));
            }
            if (item.getItemId() == R.id.jackpot_menu) {
                binding.drawer.closeDrawers();
                startActivity(new Intent(MainActivity.this, JackpotActivity.class));
            }
            if (item.getItemId() == R.id.task_menu) {
                binding.drawer.closeDrawers();
                startActivity(new Intent(MainActivity.this, TaskActivity.class));
            }
            if (item.getItemId() == R.id.ads_menu) {
                binding.drawer.closeDrawers();
                startActivity(new Intent(MainActivity.this, AdvertisingActivity.class));
            }
            if (item.getItemId() == R.id.info_app_menu) {
                ShowDialogAboutApp(R.drawable.info_ic, getString(R.string.features), "info_app.txt");
            }

            if (item.getItemId() == R.id.road_map_menu) {
                ShowDialogAboutApp(R.drawable.roadmap_ic, getString(R.string.roadmap), "roadmap.txt");
            }

            if (item.getItemId() == R.id.blue_icon_menu) {
                ShowDialogAboutApp(R.drawable.blue_tick_menu_ic, getString(R.string.blue_tick), "blue_tick.txt");
            }

            if (item.getItemId() == R.id.exitApp) {
                binding.drawer.closeDrawers();
                finish();
            }

            return true;
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void ShowDialogAboutApp(int drawable, String title, String fileName) {
        binding.drawer.closeDrawers();
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.info_menu_dialog);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.setCancelable(true);
        dialog.show();
        AppCompatTextView tvInfo = dialog.findViewById(R.id.tvInfo);
        AppCompatImageView dialogImage = dialog.findViewById(R.id.dialogImage);
        AppCompatTextView dialogTitle = dialog.findViewById(R.id.dialogTitle);
        dialogTitle.setText(title);
        dialogImage.setImageDrawable(getDrawable(drawable));
        tvInfo.setText(readFromAssets(fileName));
    }

    private String readFromAssets(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream is = getAssets().open(fileName);
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

    private void StartActivities() {
        binding.moriNews.setOnClickListener(v ->
                startActivity(new Intent(this, NewsActivity.class)));

        binding.newsLayout.setOnClickListener(v ->
                startActivity(new Intent(this, NewsActivity.class)));

        binding.task.setOnClickListener(v ->
                startActivity(new Intent(this, TaskActivity.class)));

        binding.earn.setOnClickListener(v ->
                startActivity(new Intent(this, EarnActivity.class)));

        binding.jackpot.setOnClickListener(v ->
                startActivity(new Intent(this, JackpotActivity.class)));

        binding.wallet.setOnClickListener(v ->
                startActivity(new Intent(this, WalletActivity.class)));

        binding.referral.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ReferralActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStop() {
        energyManager.getTimeFromSystemsOnStopMethod();
        rewardTimer.OnStopActivity();
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        energyManager.onDestroyApp();
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        if (binding.tvBalanceCoin.getText().toString().isEmpty()) {
            binding.tvBalanceCoin.setText("0");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, filter);
        getData();
        rewardTimer.OnResumeActivity();
    }

    private void getData() {
        applicationDataViewModel = new ViewModelProvider(this).get(ApplicationDataViewModel.class);
        applicationDataViewModel.SetApplicationSetup().observe(this, applicationSetupModel -> {
            if (applicationSetupModel != null) {
                is_active = applicationSetupModel.isIs_active();
                is_mint_on = applicationSetupModel.isIs_mint_on();
                DayValue = applicationSetupModel.getCount();
                binding.tvDay.setText(String.valueOf(DayValue));

                SharedPreferences preferences = getSharedPreferences("Days", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                long allDays = preferences.getLong("Day", 0);
                if (allDays == 0) {
                    editor.putLong("Day", DayValue);
                    editor.apply();
                }
                DaysManager daysManager = new DaysManager(MainActivity.this);
                daysManager.getCurrentDay(DayValue);

                if (!is_active) {
                    final SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
                    dialog.setCancelable(false);
                    dialog.setTitle("Under repair");
                    dialog.setContentText("Please try again later or check for new update");
                    dialog.setConfirmButton("Exit", sweetAlertDialog -> finish());
                    dialog.show();
                } else {
                    HandleResponseData();
                    applicationDataViewModel = new ViewModelProvider(this).get(ApplicationDataViewModel.class);
                    applicationDataViewModel.PinnedNews(MainActivity.this);
                }

                if (is_mint_on) {
                    MintHandler();
                } else {
                    binding.CoinLayout.setOnClickListener(v -> {
                        final SweetAlertDialog dialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE);
                        dialog.setCancelable(false);
                        dialog.setTitle("Mint is closed");
                        dialog.setContentText("Please try again later");
                        dialog.setConfirmButton("Exit", sweetAlertDialog -> finish());
                        dialog.show();
                    });
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkChangeReceiver);
        rewardTimer.StartTimer();
    }

    @Override
    public void onNetworkChange(boolean isConnected) {
        if (!isConnected) {
            SweetAlertDialog dialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE);
            dialog.setCancelable(false);
            dialog.setTitle("No Internet!");
            dialog.setContentText("Please check the internet connection");
            dialog.setConfirmButton("Exit", sweetAlertDialog -> {
                finish();
                dialog.dismiss();
            });
            dialog.show();
        }
    }

    public void HandleResponseData() {
        userDataViewModel = new ViewModelProvider(this).get(UserDataViewModel.class);
        userDataViewModel.getUserData(MainActivity.this).observe(this, users -> {
            if (users != null) {
                isUserData = true;

                coinMintManager = new CoinMintManager(MainActivity.this);
                binding.tvUsername.setText(users.getUsername());
                binding.drawerTvUsername.setText(users.getUsername());
                coinMintManager.setBalance(users.getCoin());

                coinMintManager.SendNewValue(users.getCoin());
                NumberFormat(coinMintManager.getBalance(), binding.tvBalanceCoin);

                energyManager.getLevelFromServer(users.getLevel());

                progressBarManager.setImageWithCurrentLevel(users.getLevel(), binding.imageViewProfile);
                progressBarManager.setImageWithCurrentLevel(users.getLevel(), binding.drawerProfile);
                progressBarManager.setImageWithCurrentLevel(users.getLevel(), binding.coinImage);
                progressBarManager.setImageWithCurrentLevel(users.getLevel(), binding.turboImage);
                progressBarManager.ProgressBar(users.getLevel(), binding.levelXpProgressBar);
                progressBarManager.getCurrentCoin(coinMintManager.getBalance(), binding.levelXpProgressBar);

            } else {
                isUserData = false;
            }
        });
    }
}