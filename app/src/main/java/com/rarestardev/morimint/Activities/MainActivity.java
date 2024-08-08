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
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.rarestardev.morimint.ApplicationSetup.ProgressBarManager;
import com.rarestardev.morimint.ApplicationSetup.EnergyManager;
import com.rarestardev.morimint.Constants.UserConstants;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.ApplicationSetup.CoinMintManager;
import com.rarestardev.morimint.ApplicationSetup.DailyUpdater;
import com.rarestardev.morimint.Utilities.NetworkChangeReceiver;
import com.rarestardev.morimint.Utilities.NoDoubleClickListener;
import com.rarestardev.morimint.Utilities.WelcomeDialog;
import com.rarestardev.morimint.ViewModel.ApplicationDataViewModel;
import com.rarestardev.morimint.ViewModel.UserDataViewModel;
import com.rarestardev.morimint.Wallet.WalletActivity;
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
    private static final String TAG = UserConstants.APP_LOG_TAG;// Log tag

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

    // ViewModels
    ApplicationDataViewModel applicationDataViewModel;
    UserDataViewModel userDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isTabletMode()) {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_main_tablet);
        } else {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        }

        networkChangeReceiver = new NetworkChangeReceiver(this);

        progressBarManager = new ProgressBarManager(MainActivity.this);

        energyManager = new EnergyManager(this, binding.tvLevelShow, binding.tvEnergy, binding.iconEnergy);
        energyManager.IncreasedEnergy();

        DailyUpdater dailyUpdater = new DailyUpdater(this);
        dailyUpdater.updateValueIfNeeded();


        StartActivities();
        NavigationDrawerHandle();

        SharedPreferences sharedPreferences = getSharedPreferences("DailyUpdater", MODE_PRIVATE);
        TurboCount = sharedPreferences.getInt("turbo", 0);
        binding.tvTurboCount.setText(String.valueOf(TurboCount));
        MintHandler();
    }


    private boolean isTabletMode() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;

        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);

        return diagonalInches >= 7.0; // Tablet 7 inches
    }


    // handle turbo mode is on or off
    private void MintHandler() {
        SharedPreferences sharedPreferences = getSharedPreferences("DailyUpdater", MODE_PRIVATE);
        int currentTurbo = sharedPreferences.getInt("turbo", 0);
        if (currentTurbo == 2 || currentTurbo == 1) {
            binding.turboMint.setVisibility(View.VISIBLE);
            binding.turboCountLayout.setVisibility(View.VISIBLE);
            binding.coin.setVisibility(View.VISIBLE);
            binding.turbo.setVisibility(View.GONE);
            MintCoinAnimation();
            binding.turboMint.setOnClickListener(v -> TurboAnimation());
        } else if (currentTurbo == 0) {
            binding.turboMint.setVisibility(View.GONE);
            binding.coin.setVisibility(View.VISIBLE);
            binding.turbo.setVisibility(View.GONE);
            binding.turboCountLayout.setVisibility(View.GONE);
            MintCoinAnimation();
        }
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    private void TurboAnimation() {
        SharedPreferences sharedPreferences = getSharedPreferences("DailyUpdater", MODE_PRIVATE);
        int currentTurbo = sharedPreferences.getInt("turbo", 0);
        if (currentTurbo != 0) {
            turboDownTimer = new CountDownTimer(12000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    binding.turboMint.setVisibility(View.GONE);
                    binding.coin.setVisibility(View.GONE);
                    binding.turboCountLayout.setVisibility(View.GONE);
                    binding.turbo.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.Swing).duration(500).playOn(binding.turbo);
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
                                    progressBarManager.getCurrentCoin(binding.levelXpProgressBar);
                                    CreateAnimation(x, y);
                                    break;
                            }
                        }
                        return true;
                    });
                }

                @Override
                public void onFinish() {
                    SharedPreferences sharedPreferences = getSharedPreferences("DailyUpdater", MODE_PRIVATE);
                    TurboCount = sharedPreferences.getInt("turbo", 0);
                    binding.turbo.setVisibility(View.GONE);
                    if (TurboCount == 2) {
                        TurboCount = 1;
                    } else if (TurboCount == 1) {
                        TurboCount = 0;
                        binding.turboCountLayout.setVisibility(View.GONE);
                        turboDownTimer.cancel();
                    }
                    binding.tvTurboCount.setText(String.valueOf(TurboCount));
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("turbo", TurboCount);
                    editor.apply();

                    MintHandler();
                }
            };
            turboDownTimer.start();
        } else {
            MintHandler();
        }
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

                            progressBarManager.getCurrentCoin(binding.levelXpProgressBar);

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
        progressBarManager.ProgressBar(binding.levelXpProgressBar);

        NumberFormat(coinMintManager.getBalance(), binding.tvBalanceCoin);

        TextView animationTextView = new TextView(MainActivity.this);

        if (binding.turbo.getVisibility() == View.GONE) {
            animationTextView.setText("+" + progressBarManager.getMinter());
        } else {
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

            animationTextView.postDelayed(() -> animationTextView.setTextColor(getColor(TEXT_ANIMATION_COLOR[1])), 400);

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

            if (item.getItemId() == R.id.mori_ai_menu) {
                ShowDialogAboutApp(R.drawable.mori_info_icon, getString(R.string.moriAi), "mori_ai_desc.txt");
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
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
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

        binding.jackpotViewLayout.setOnClickListener(v ->
                startActivity(new Intent(this, JackpotActivity.class)));

        binding.wallet.setOnClickListener(v ->
                startActivity(new Intent(this, WalletActivity.class)));

        binding.referral.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ReferralActivity.class);
            startActivity(intent);
        });

        binding.applicationManagerLayout.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onSingleClick(View v) {
                startActivity(new Intent(MainActivity.this, LevelActivity.class));
                super.onSingleClick(v);
            }
        });
    }

    @Override
    protected void onStop() {
        energyManager.getTimeFromSystemsOnStopMethod();
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        energyManager.onDestroyApp();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        if (binding.tvBalanceCoin.getText().toString().isEmpty()) {
            binding.tvBalanceCoin.setText("0");
        }

        SharedPreferences sharedPreferences = getSharedPreferences("WelcomeDialog", Context.MODE_PRIVATE);
        final String SHARED_KEY = "Show";

        boolean showWelcome = sharedPreferences.getBoolean(SHARED_KEY, false);

        WelcomeDialog welcomeDialog = new WelcomeDialog(MainActivity.this);
        if (!showWelcome) {
            welcomeDialog.show();
        } else {
            welcomeDialog.dismiss();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, filter);
        getData();
    }

    private void getData() {
        applicationDataViewModel = new ViewModelProvider(this).get(ApplicationDataViewModel.class);
        applicationDataViewModel.SetApplicationSetup().observe(this, applicationSetupModel -> {
            if (applicationSetupModel != null) {
                is_active = applicationSetupModel.isIs_active();
                is_mint_on = applicationSetupModel.isIs_mint_on();
                DayValue = applicationSetupModel.getCount();

                if (DayValue == 0) {
                    binding.tvDay.setText(String.valueOf(90));
                } else {
                    binding.tvDay.setText(String.valueOf(DayValue));
                }

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
                    applicationDataViewModel.PinnedNews(binding.tvNewsMessage, binding.moriNewsDot);
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

    @SuppressLint("SetTextI18n")
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
                progressBarManager.ProgressBar(binding.levelXpProgressBar);

                energyManager.getLevelFromServer(users.getLevel());

                progressBarManager.setImageWithCurrentLevel(binding.imageViewProfile);
                progressBarManager.setImageWithCurrentLevel(binding.drawerProfile);
                progressBarManager.setImageWithCurrentLevel(binding.coinImage);
                progressBarManager.setImageWithCurrentLevel(binding.turboImage);
                progressBarManager.getCurrentCoin(binding.levelXpProgressBar);

                if (users.getLevel() == 15) {
                    binding.tvYourLevel.setText("Level Max");
                }

            } else {
                isUserData = false;
            }
        });
    }
}