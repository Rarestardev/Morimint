package com.rarestardev.morimint.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.rarestardev.morimint.Constants.MintValues;
import com.rarestardev.morimint.Model.Users;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.UsersManagement.CheckActiveUser;
import com.rarestardev.morimint.UsersManagement.Counter;
import com.rarestardev.morimint.UsersManagement.MintCounter;
import com.rarestardev.morimint.UsersManagement.UserProgress;
import com.rarestardev.morimint.Utilities.InternetConnection;
import com.rarestardev.morimint.ViewModel.ApplicationDataViewModel;
import com.rarestardev.morimint.ViewModel.UserDataViewModel;
import com.rarestardev.morimint.databinding.ActivityMainBinding;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private int clickerCounter; // number of clicked for mint counter
    private int clickerCounterTurbo; // number of clicked for mint counter
    private int TurboCount = 0; // Turbo Counter
    private static final String TAG = "Main Activity"; // Log tag
    private boolean isClicked = false; // Clicked Coin for Mint = true or false
    private final Handler handler = new Handler(); // energy timer
    private Runnable runnable; // energy charger
    int energy; // energy
    private SharedPreferences.Editor editor; // current energy counter
    private Counter counter; // Counter Charge Energy   // true = clicked    // false = not clicked recharged energy
    private MintCounter mintCounter; // balance coin manager
    private CountDownTimer turboDownTimer; // Turbo timer Counter 12 second
    ApplicationDataViewModel applicationDataViewModel;
    UserDataViewModel userDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("CurrentTime", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        StartActivities();
        NavigationDrawerHandle();
        MintHandler();
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
                mintCounter = new MintCounter(MintValues.mint);
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
                                mintCounter.IncreaseBalance(clickerCounterTurbo, true);
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
        mintCounter = new MintCounter(MintValues.mint);
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
                        if (!counter.mintStop()) {
                            clickerCounter++;
                            mintCounter.IncreaseBalance(clickerCounter, false);
                            counter.decrement();
                            binding.tvEnergy.setText(counter.getValue() + " / " + MintValues.MaxEnergy);
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
    public void CreateAnimation(float x, float y) {
        YoYo.with(Techniques.Shake).duration(MintValues.COIN_SHAKE_ANIMATION_TIMER).playOn(binding.coin);
        YoYo.with(Techniques.Shake).duration(MintValues.COIN_SHAKE_ANIMATION_TIMER).playOn(binding.turbo);

        NumberFormat(mintCounter.getTotalBalance(), binding.tvBalanceCoin);

        UserProgress userProgress = new UserProgress(mintCounter.getTotalBalance(), binding.tvLevelShow, binding.levelXpProgressBar);
        userProgress.levelUserManagement();

        TextView animationTextView = new TextView(this);

        animationTextView.setText("+" + MintValues.mint);
        animationTextView.setTextSize(30);
        animationTextView.setTypeface(Typeface.DEFAULT_BOLD);
        animationTextView.setTextColor(getColor(MintValues.TEXT_ANIMATION_COLOR[0]));
        animationTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        animationTextView.setX(x);
        animationTextView.setY(y);

        Thread thread = new Thread(() -> {

            animationTextView.postDelayed(() -> animationTextView.setTextColor(getColor(MintValues.TEXT_ANIMATION_COLOR[1])), 300);

            animationTextView.postDelayed(() -> animationTextView.setTextColor(getColor(MintValues.TEXT_ANIMATION_COLOR[2])), 500);

            animationTextView.postDelayed(() -> animationTextView.setTextColor(getColor(MintValues.TEXT_ANIMATION_COLOR[3])), 700);
        });
        thread.start();


        binding.parent.addView(animationTextView);

        Random randomAxisNumber = new Random();

        int random = randomAxisNumber.nextInt(MintValues.animationAxis.length - 1);

        TranslateAnimation animation = new TranslateAnimation(
                binding.CoinLayout.getX(),
                MintValues.animationAxis[random],
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

    @SuppressLint("SetTextI18n")
    private void RechargedEnergy() {
        counter = new Counter(MintValues.MaxEnergy, MintValues.MaxEnergy, 1, MintValues.STEP_ENERGY);
        binding.tvEnergy.setText(counter.getValue() + " / " + MintValues.MaxEnergy);

        if (counter.getValue() >= MintValues.MaxEnergy) {
            editor.clear();
        }

        runnable = new Runnable() {
            @Override
            public void run() {
                if (!isClicked) {
                    if (counter.getValue() < MintValues.MaxEnergy) {
                        counter.increment();
                    } else if (counter.getValue() >= MintValues.MaxEnergy) {
                        binding.tvEnergy.setText(MintValues.MaxEnergy + " / " + MintValues.MaxEnergy);
                    }
                }

                binding.tvEnergy.setText(counter.getValue() + " / " + MintValues.MaxEnergy);
                handler.postDelayed(this, MintValues.IncreaseEnergyTime);
            }
        };
        handler.post(runnable);


        SharedPreferences sharedPreferences = getSharedPreferences("CurrentTime", MODE_PRIVATE);
        long currentTime = sharedPreferences.getLong("Time", -1);
        energy = sharedPreferences.getInt("Energy", MintValues.MaxEnergy);

        if (currentTime != -1) {
            long lastTime = System.currentTimeMillis();
            long timeDifInSecond = (lastTime - currentTime) / 1000;

            if (energy < MintValues.MaxEnergy) {
                counter.increase((int) timeDifInSecond, energy);
            }
            if (energy > MintValues.MaxEnergy) {
                energy = MintValues.MaxEnergy;
                editor.clear();
            }

            binding.tvEnergy.setText(counter.getValue() + " / " + MintValues.MaxEnergy);
        }
    }

    private void NumberFormat(int balance, @NonNull TextView tvBalance) {
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
        binding.profileLayout.setOnClickListener(v ->
                startActivity(new Intent(this, ProfileActivity.class)));

        binding.referral.setOnClickListener(v ->
                startActivity(new Intent(this, ReferralActivity.class)));

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

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
        getCurrentTimeUserActiveToOfflineMode();
    }

    private void getCurrentTimeUserActiveToOfflineMode() {
        CheckActiveUser activeUser = new CheckActiveUser(MainActivity.this);
        activeUser.isActiveUserInApp(false);

        editor.putLong("Time", System.currentTimeMillis());
        editor.putInt("Energy", counter.getValue());
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");

        handler.removeCallbacks(runnable);
        CheckActiveUser activeUser = new CheckActiveUser(MainActivity.this);
        activeUser.isActiveUserInApp(false);
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CheckActiveUser activeUser = new CheckActiveUser(MainActivity.this);
        activeUser.isActiveUserInApp(false);
        finish();
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

        userDataViewModel = new ViewModelProvider(this).get(UserDataViewModel.class);
        userDataViewModel.getUserData(MainActivity.this);

        InternetConnection connection = new InternetConnection();
        if (!connection.isConnectedNetwork(this)) {
            Toast.makeText(this, "No Internet !", Toast.LENGTH_SHORT).show();
        } else {
            RechargedEnergy();
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

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }
}