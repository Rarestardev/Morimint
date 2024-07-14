package com.rarestardev.morimint.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.rarestardev.morimint.R;
import com.rarestardev.morimint.ViewModel.UserDataViewModel;
import com.rarestardev.morimint.databinding.ActivitySignInBinding;

public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;

    UserDataViewModel userDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_in);

        binding.tvSignUp.setOnClickListener(v -> finish());

        binding.tvForgetPassword.setOnClickListener(v -> {
            Intent intent = new Intent(SignInActivity.this,ForgetPasswordActivity.class);
            startActivity(intent);
        });

        binding.btnSignIn.setOnClickListener(v -> {
            String username = String.valueOf(binding.editTextUsername.getText());
            String password = String.valueOf(binding.editTextPassword.getText());
            LoginUserAccount(username,password);
        });


    }

    private void LoginUserAccount(String username, String password) {
        if (username.isEmpty() && password.isEmpty()){
            Toast.makeText(this, "Please fill the fields!", Toast.LENGTH_SHORT).show();
        }else {
            userDataViewModel = new ViewModelProvider(this).get(UserDataViewModel.class);
            userDataViewModel.LoginUserAccount(SignInActivity.this,username,password);
        }
    }


}