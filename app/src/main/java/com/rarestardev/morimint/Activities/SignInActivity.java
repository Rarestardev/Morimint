package com.rarestardev.morimint.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.rarestardev.morimint.R;
import com.rarestardev.morimint.ViewModel.UserDataViewModel;
import com.rarestardev.morimint.databinding.ActivitySignInBinding;

public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;
    private boolean showing_password = false;

    UserDataViewModel userDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isTabletMode()) {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in_tablet);
        } else {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
        }


        binding.tvSignUp.setOnClickListener(v -> finish());

        binding.tvForgetPassword.setOnClickListener(v -> {
            Intent intent = new Intent(SignInActivity.this, ForgetPasswordActivity.class);
            startActivity(intent);
        });

        binding.btnSignIn.setOnClickListener(v -> {
            String username = String.valueOf(binding.editTextUsername.getText());
            String password = String.valueOf(binding.editTextPassword.getText());
            LoginUserAccount(username, password);
        });

        binding.passwordState.setOnClickListener(v -> {

            // checking if password showing or not
            if (showing_password) {
                // visible
                showing_password = false;
                binding.editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                binding.passwordState.setImageResource(R.drawable.icon_show_password);
            } else {
                // gone
                showing_password = true;
                binding.editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                binding.passwordState.setImageResource(R.drawable.icon_hide_password);
            }
            // move the cursor at last of the text
            binding.editTextPassword.setSelection(binding.editTextPassword.length());
        });


    }

    private void LoginUserAccount(String username, String password) {
        if (username.isEmpty() && password.isEmpty()) {
            Toast.makeText(this, "Please fill the fields!", Toast.LENGTH_SHORT).show();
        } else {
            userDataViewModel = new ViewModelProvider(this).get(UserDataViewModel.class);
            userDataViewModel.LoginUserAccount(SignInActivity.this, username, password);
        }
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