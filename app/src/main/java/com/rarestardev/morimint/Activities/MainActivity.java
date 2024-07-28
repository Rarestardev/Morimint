package com.rarestardev.morimint.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
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
import com.rarestardev.morimint.ApplicationSetup.ApplicationManager;
import com.rarestardev.morimint.ApplicationSetup.CustomLevelDialog;
import com.rarestardev.morimint.ApplicationSetup.EnergyManager;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.ApplicationSetup.CoinMintManager;
import com.rarestardev.morimint.Utilities.NetworkChangeReceiver;
import com.rarestardev.morimint.ViewModel.ApplicationDataViewModel;
import com.rarestardev.morimint.ViewModel.CoinManagerViewModel;
import com.rarestardev.morimint.ViewModel.UserDataViewModel;
import com.rarestardev.morimint.databinding.ActivityMainBinding;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity implements NetworkChangeReceiver.NetworkChangeListener {

    private ActivityMainBinding binding;
    // Constants
    private static final long COIN_SHAKE_ANIMATION_TIMER = 250;
    private static final int[] animationAxis = {-250, 70, 500, 700, -200, 70, 500, -100, 70, 200};
    private static final int[] TEXT_ANIMATION_COLOR = {R.color.white, R.color.MidWhite, R.color.DarkGray, R.color.Gray};
    private static final String TAG = "HomeLog";// Log tag

    private ApplicationManager applicationManager;

    // value
    private int clickerCounter;
    private int clickerCounterTurbo; // number of clicked for mint applicationManager
    boolean isUserData;
    private int TurboCount = 0; // Turbo ApplicationManager
    private boolean is_active;
    private boolean is_mint_on;
    private long DayValue;

    // Class
    private CoinMintManager coinMintManager;
    private CountDownTimer turboDownTimer; // Turbo timer ApplicationManager 12 second
    private NetworkChangeReceiver networkChangeReceiver;
    private EnergyManager energyManager;

    // ViewModels
    ApplicationDataViewModel applicationDataViewModel;
    UserDataViewModel userDataViewModel;
    private CoinManagerViewModel coinManagerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        networkChangeReceiver = new NetworkChangeReceiver(this);
        coinMintManager = new CoinMintManager(MainActivity.this);
        applicationManager = new ApplicationManager(MainActivity.this);
        energyManager = new EnergyManager(this, binding.tvLevelShow, binding.tvEnergy, binding.iconEnergy);
        energyManager.IncreasedEnergy();

        StartActivities();
        NavigationDrawerHandle();

        binding.applicationManagerLayout.setOnClickListener(v -> {
            CustomLevelDialog customLevelDialog = new CustomLevelDialog(MainActivity.this, coinMintManager.getBalance());
            customLevelDialog.show();
        });
    }

    private void MintHandler() {
        if (TurboCount != 0) {
            binding.turboMint.setVisibility(View.VISIBLE);
            binding.coin.setVisibility(View.VISIBLE);
            binding.turbo.setVisibility(View.GONE);
            MintCoinAnimation();
            binding.turboMint.setOnClickListener(v -> TurboAnimation());

        } else {
            binding.turboMint.setVisibility(View.GONE);
            binding.coin.setVisibility(View.VISIBLE);
            binding.turbo.setVisibility(View.GONE);
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
                                coinMintManager.IncreaseBalance(clickerCounterTurbo, true, applicationManager.getMinter());
                                CreateAnimation(x, y);
                                break;
                        }
                    }
                    return true;
                });
            }

            @Override
            public void onFinish() {
                binding.turbo.setVisibility(View.GONE);
                MintHandler();
                if (TurboCount > 0) {
                    TurboCount--;
                } else {
                    TurboCount = 0;
                    turboDownTimer.cancel();
                }
            }
        };
        turboDownTimer.start();
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    public void MintCoinAnimation() {
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
                        if (!energyManager.mintStop(applicationManager.getMinter())) {
                            clickerCounter++;
                            coinMintManager.IncreaseBalance(clickerCounter, false, applicationManager.getMinter());
                            applicationManager.initLevelValues(coinMintManager.getBalance(),
                                    binding.levelXpProgressBar);
                            energyManager.clicked(true);

                            energyManager.ReduceEnergy(applicationManager.getMinter());

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
        animationTextView.setText("+" + applicationManager.getMinter());
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
            if (item.getItemId() == R.id.exitApp) {
                binding.drawer.closeDrawers();
                finish();
            }

            return true;
        });
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
        coinManagerViewModel = new ViewModelProvider(this).get(CoinManagerViewModel.class);
        coinManagerViewModel.UpdateCoin(coinMintManager.SendNewValue(), MainActivity.this);
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        energyManager.onDestroyApp();
        coinManagerViewModel = new ViewModelProvider(this).get(CoinManagerViewModel.class);
        coinManagerViewModel.UpdateCoin(coinMintManager.SendNewValue(), MainActivity.this);
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

        applicationDataViewModel = new ViewModelProvider(this).get(ApplicationDataViewModel.class);
        applicationDataViewModel.SetApplicationSetup().observe(this, applicationSetupModel -> {
            if (applicationSetupModel != null) {
                is_active = applicationSetupModel.isIs_active();
                is_mint_on = applicationSetupModel.isIs_mint_on();
                DayValue = applicationSetupModel.getCount();
                binding.tvDay.setText(String.valueOf(DayValue));
                if (!is_active) {
                    final SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
                    dialog.setCancelable(false);
                    dialog.setTitle("Under repair");
                    dialog.setContentText("Please try again later");
                    dialog.setConfirmButton("Exit", sweetAlertDialog -> finish());
                    dialog.show();
                } else {
                    HandleResponseData();
                    applicationDataViewModel = new ViewModelProvider(this).get(ApplicationDataViewModel.class);
                    applicationDataViewModel.PinnedNews(binding.moriNewsDot, binding.tvNewsMessage);
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

    private void HandleResponseData() {
        userDataViewModel = new ViewModelProvider(this).get(UserDataViewModel.class);
        userDataViewModel.getUserData(MainActivity.this).observe(this, users -> {
            if (users != null) {
                isUserData = true;
                DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
                decimalFormatSymbols.setGroupingSeparator(',');
                DecimalFormat numberFormat = new DecimalFormat("###,###,###,###,###", decimalFormatSymbols);
                numberFormat.setGroupingSize(3);
                numberFormat.setMaximumFractionDigits(2);

                binding.tvUsername.setText(users.getUsername());
                binding.drawerTvUsername.setText(users.getUsername());
                coinMintManager.setBalance(users.getCoin());
                binding.tvBalanceCoin.setText(numberFormat.format(coinMintManager.getBalance()));
                energyManager.getLevelFromServer(users.getLevel());
                applicationManager.setImageWithCurrentLevel(users.getLevel(), binding.imageViewProfile);
                applicationManager.setImageWithCurrentLevel(users.getLevel(),binding.drawerProfile);

            } else {
                isUserData = false;
            }
        });
    }
}