package com.rarestardev.morimint.Activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;

import com.rarestardev.morimint.R;
import com.rarestardev.morimint.Utilities.NoDoubleClickListener;
import com.rarestardev.morimint.databinding.ActivitySubmitContentBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class SubmitContentActivity extends AppCompatActivity {
    ActivitySubmitContentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_submit_content);


        binding.btnSubmit.setOnClickListener(v ->
                Toast.makeText(this, "Coming soon", Toast.LENGTH_LONG).show());

        binding.editTextLinkAddress.setOnClickListener(v ->
                Toast.makeText(this, "Coming soon", Toast.LENGTH_LONG).show());


        binding.contentRules.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onSingleClick(View v) {
                super.onSingleClick(v);

                ShowDialogAboutApp();
            }
        });

    }


    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    private void ShowDialogAboutApp() {
        Dialog dialog = new Dialog(SubmitContentActivity.this);
        dialog.setContentView(R.layout.info_menu_dialog);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.setCancelable(true);
        dialog.show();
        AppCompatTextView tvInfo = dialog.findViewById(R.id.tvInfo);
        AppCompatImageView dialogImage = dialog.findViewById(R.id.dialogImage);
        AppCompatTextView dialogTitle = dialog.findViewById(R.id.dialogTitle);
        dialogTitle.setText("Content Creation Rules");
        dialogImage.setImageDrawable(getDrawable(R.drawable.info_ic));
        tvInfo.setText(readFromAssets());
    }

    private String readFromAssets() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream is = getAssets().open("content_creation_rules.txt");
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
}