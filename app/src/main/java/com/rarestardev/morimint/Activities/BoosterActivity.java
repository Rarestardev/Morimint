package com.rarestardev.morimint.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.widget.Toast;

import com.rarestardev.morimint.R;

public class BoosterActivity extends AppCompatActivity {
    AppCompatButton btnPay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booster);

        btnPay = findViewById(R.id.btnPay);
        btnPay.setOnClickListener(v -> Toast.makeText(BoosterActivity.this, "Coming Soon...", Toast.LENGTH_LONG).show());
    }
}