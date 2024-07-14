package com.rarestardev.morimint.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.widget.Toast;

import com.rarestardev.morimint.R;
import com.rarestardev.morimint.ViewModel.UserDataViewModel;
import com.rarestardev.morimint.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private boolean showing_password = false;
    private boolean showing_rePassword = false;

    UserDataViewModel userDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);


        // start sign in
        binding.signIn.setOnClickListener(v ->
                startActivity(new Intent(this, SignInActivity.class)));


        // show or hide password handle
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


        // show or hide password handle
        binding.repeatState.setOnClickListener(v -> {
            // checking if password showing or not
            if (showing_rePassword) {
                // visible
                showing_rePassword = false;
                binding.editTextRePass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                binding.repeatState.setImageResource(R.drawable.icon_show_password);
            } else {
                // gone
                showing_rePassword = true;
                binding.editTextRePass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                binding.repeatState.setImageResource(R.drawable.icon_hide_password);
            }
            // move the cursor at last of the text
            binding.editTextPassword.setSelection(binding.editTextRePass.length());
        });


        // check field to invalid and not empty
        binding.btnSignUp.setOnClickListener(v -> {

            String username = String.valueOf(binding.editTextUsername.getText());
            String email = String.valueOf(binding.editTextEmail.getText());
            String password = String.valueOf(binding.editTextPassword.getText());
            String repeat = String.valueOf(binding.editTextRePass.getText());
            int referral = Integer.parseInt(String.valueOf(binding.editTextReferral.getText()));


            CheckInputFieldsValue(username, email, password, repeat, referral);
        });
    }

    private void CheckInputFieldsValue(String username, String email, String password, String repeat, int referral) {
        // check input fields
        if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !repeat.isEmpty()) {

            if (username.length() < 8) {

                // check username length
                // error
                binding.editTextUsername.setError("Username must be more than 8 characters!");

            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                // check pattern email input
                // error
                binding.editTextEmail.setError("Wrong email address!");

            } else if (password.length() < 8) {

                // check password length
                // error

                binding.editTextPassword.setError("Password must be more than 8 characters!");

            } else if (!repeat.equals(password)) {

                // check repeat passwords
                // error
                binding.editTextRePass.setError("The password is wrong!");

            } else {

                // all fields not error and complete register user account

                SignUpAccount(username, email, password);

            }

        } else {

            // error message
            Toast.makeText(this, "Please fill the fields!", Toast.LENGTH_SHORT).show();

        }// check empty

    }

    private void SignUpAccount(String username, String email, String password) {

        userDataViewModel = new ViewModelProvider(this).get(UserDataViewModel.class);
        userDataViewModel.SignUp(SignUpActivity.this,username,email,password);
    }
}