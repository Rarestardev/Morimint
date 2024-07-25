package com.rarestardev.morimint.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.rarestardev.morimint.ApplicationSetup.ProgramManager;
import com.rarestardev.morimint.Constants.EnergyValue;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.ApplicationSetup.ChargeEnergyCounter;
import com.rarestardev.morimint.ApplicationSetup.CheckActiveUser;
import com.rarestardev.morimint.ApplicationSetup.CoinMintManager;
import com.rarestardev.morimint.Utilities.NetworkChangeReceiver;
import com.rarestardev.morimint.ViewModel.ApplicationDataViewModel;
import com.rarestardev.morimint.ViewModel.CoinManagerViewModel;
import com.rarestardev.morimint.ViewModel.UserDataViewModel;
import com.rarestardev.morimint.databinding.ActivityMainBinding;

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
    private static final long IncreaseEnergyTime = 1000;
    private static final String TAG = "HomeLog";// Log tag

    // Energy
    private final Handler handler = new Handler(); // energy timer
    private Runnable runnable; // energy charger
    int energy; // energy
    private SharedPreferences.Editor editor; // current energy chargeEnergyCounter
    private ChargeEnergyCounter chargeEnergyCounter; // ChargeEnergyCounter Charge Energy   // true = clicked    // false = not clicked recharged energy

    // value
    private int clickerCounter;
    private int clickerCounterTurbo; // number of clicked for mint chargeEnergyCounter
    private boolean isClicked = false;
    boolean isUserData;
    private int TurboCount = 0; // Turbo ChargeEnergyCounter
    private boolean is_active;
    private boolean is_mint_on;
    private long DayValue;

    // Class
    private CoinMintManager coinMintManager;
    private CountDownTimer turboDownTimer; // Turbo timer ChargeEnergyCounter 12 second
    private NetworkChangeReceiver networkChangeReceiver;
    ProgramManager programManager;

    // ViewModels
    private ApplicationDataViewModel applicationDataViewModel;
    UserDataViewModel userDataViewModel;
    private CoinManagerViewModel coinManagerViewModel;

    private Dialog levelProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        programManager = new ProgramManager(binding.levelXpProgressBar,MainActivity.this,
                binding.tvLevelShow,binding.coinImage,binding.turboImage);

        levelProgressDialog = new Dialog(MainActivity.this);
        networkChangeReceiver = new NetworkChangeReceiver(this);
        coinMintManager = new CoinMintManager(MainActivity.this);
        chargeEnergyCounter = new ChargeEnergyCounter(EnergyValue.Energy,EnergyValue.Energy,0,3);


        SharedPreferences sharedPreferences = getSharedPreferences("CurrentTime", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        StartActivities();
        NavigationDrawerHandle();
        RechargedEnergy();

        binding.levelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogUserTask();
            }
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
                                coinMintManager.IncreaseBalance(clickerCounterTurbo, true, programManager.getMint());
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
                        if (!chargeEnergyCounter.mintStop(programManager.getMint())) {
                            clickerCounter++;
                            coinMintManager.IncreaseBalance(clickerCounter, false, EnergyValue.Mint);
                            chargeEnergyCounter.decrement(EnergyValue.Mint);
                            binding.tvEnergy.setText(chargeEnergyCounter.getValue() + " / " + EnergyValue.Energy);
                            programManager.initLevelValues();
                            programManager.initTotalCoin(coinMintManager.getBalance());
                            isClicked = true;

                            CreateAnimation(x, y);
                        }
                        break;
                }

            } else if (actionType == MotionEvent.ACTION_UP) {
                isClicked = false;
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
        animationTextView.setText("+" + programManager.getMint());
        animationTextView.setTextSize(30);
        animationTextView.setTypeface(Typeface.DEFAULT_BOLD);
        animationTextView.setTextColor(getColor(TEXT_ANIMATION_COLOR[0]));
        animationTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        animationTextView.setX(x);
        animationTextView.setY(y);

        Thread thread = new Thread(() -> {

            animationTextView.postDelayed(() -> animationTextView.setTextColor(getColor(TEXT_ANIMATION_COLOR[1])), 300);

            animationTextView.postDelayed(() -> animationTextView.setTextColor(getColor(TEXT_ANIMATION_COLOR[2])), 500);

            animationTextView.postDelayed(() -> animationTextView.setTextColor(getColor(TEXT_ANIMATION_COLOR[3])), 700);
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

    @SuppressLint("SetTextI18n")
    private void RechargedEnergy() {
        if (chargeEnergyCounter.getValue() >= programManager.getEnergy()) {
            editor.clear();
        }

        runnable = new Runnable() {
            @Override
            public void run() {
                if (!isClicked) {
                    if (chargeEnergyCounter.getValue() < EnergyValue.Energy) {
                        chargeEnergyCounter.increment(EnergyValue.Energy);
                    }
                }
                binding.tvEnergy.setText(chargeEnergyCounter.getValue() + " / " + EnergyValue.Energy);
                handler.postDelayed(this, IncreaseEnergyTime);
            }
        };
        handler.post(runnable);


        SharedPreferences sharedPreferences = getSharedPreferences("CurrentTime", MODE_PRIVATE);
        long currentTime = sharedPreferences.getLong("Time", -1);
        energy = sharedPreferences.getInt("Energy", EnergyValue.Energy);

        if (currentTime != -1) {
            long lastTime = System.currentTimeMillis();
            long timeDifInSecond = (lastTime - currentTime) / 1000;

            if (energy < EnergyValue.Energy) {
                chargeEnergyCounter.increase((int) timeDifInSecond, energy, EnergyValue.Energy);
            }
            if (energy > EnergyValue.Energy) {
                energy = EnergyValue.Energy;
                editor.clear();
            }

            //binding.tvEnergy.setText(chargeEnergyCounter.getValue() + " / " + programManager.getEnergy());
        }
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
    }

    private void ShowDialogUserTask() {
        levelProgressDialog.setContentView(R.layout.custom_dialog_layout);
        levelProgressDialog.show();
        Objects.requireNonNull(levelProgressDialog.getWindow()).setLayout(1350, ViewGroup.LayoutParams.WRAP_CONTENT);
        levelProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        levelProgressDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        levelProgressDialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    protected void onStop() {
        coinManagerViewModel = new ViewModelProvider(this).get(CoinManagerViewModel.class);
        coinManagerViewModel.UpdateCoin(coinMintManager.SendNewValue(), MainActivity.this);
        Log.d(TAG, "onStop");
        super.onStop();
        getCurrentTimeUserActiveToOfflineMode();
    }

    private void getCurrentTimeUserActiveToOfflineMode() {
        CheckActiveUser activeUser = new CheckActiveUser(MainActivity.this);
        activeUser.isActiveUserInApp(false);

        editor.putLong("Time", System.currentTimeMillis());
        editor.putInt("Energy", chargeEnergyCounter.getValue());
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        coinManagerViewModel = new ViewModelProvider(this).get(CoinManagerViewModel.class);
        coinManagerViewModel.UpdateCoin(coinMintManager.SendNewValue(), MainActivity.this);
        Log.d(TAG, "onDestroy");

        handler.removeCallbacks(runnable);
        CheckActiveUser activeUser = new CheckActiveUser(MainActivity.this);
        activeUser.isActiveUserInApp(false);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CheckActiveUser activeUser = new CheckActiveUser(MainActivity.this);
        activeUser.isActiveUserInApp(false);
        if (levelProgressDialog.isShowing()) {
            levelProgressDialog.dismiss();
        } else {
            finish();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        if (binding.tvBalanceCoin.getText().toString().isEmpty()) {
            binding.tvBalanceCoin.setText("0");
        }

        CheckActiveUser activeUser = new CheckActiveUser(MainActivity.this);
        activeUser.isActiveUserInApp(true);

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
                binding.referral.setOnClickListener(v -> {
                    Intent intent = new Intent(MainActivity.this, ReferralActivity.class);
                    intent.putExtra("Referral", users.getReferral_code());
                    intent.putExtra("TotalReferral", users.getTotal_invites());
                    startActivity(intent);
                });

                DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
                decimalFormatSymbols.setGroupingSeparator(',');
                DecimalFormat numberFormat = new DecimalFormat("###,###,###,###,###", decimalFormatSymbols);
                numberFormat.setGroupingSize(3);
                numberFormat.setMaximumFractionDigits(2);

                binding.tvUsername.setText(users.getUsername());
                binding.drawerTvUsername.setText(users.getUsername());
                coinMintManager.setBalance(users.getCoin());
                binding.tvBalanceCoin.setText(numberFormat.format(coinMintManager.getBalance()));

                programManager.initTotalCoin(coinMintManager.getBalance());

            } else {
                isUserData = false;
            }
        });

        applicationDataViewModel = new ViewModelProvider(this).get(ApplicationDataViewModel.class);
        applicationDataViewModel.SetDataMoriNews().observe(this, moriNewsModels -> {
            boolean is_pinned_news = moriNewsModels.get(0).isIs_published();

            if (is_pinned_news) {
                binding.tvNewsMessage.setText(moriNewsModels.get(0).getContent());
            } else {
                binding.tvNewsMessage.setText("");
            }

            String isNews = moriNewsModels.get(0).getTitle();

            if (isNews.isEmpty()) {
                binding.moriNewsDot.setVisibility(View.GONE);
            } else {
                binding.moriNewsDot.setVisibility(View.VISIBLE);
            }

        });
    }
}