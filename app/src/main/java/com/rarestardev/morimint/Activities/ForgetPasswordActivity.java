package com.rarestardev.morimint.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.widget.Toast;

import com.rarestardev.morimint.R;
import com.rarestardev.morimint.ViewModel.UserDataViewModel;
import com.rarestardev.morimint.databinding.ActivityForgetPasswordBinding;

public class ForgetPasswordActivity extends AppCompatActivity {
    private ActivityForgetPasswordBinding binding;
    UserDataViewModel userDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isTabletMode()){
            binding = DataBindingUtil.setContentView(this, R.layout.activity_forget_password_tablet);
        }else {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_forget_password);
        }



        binding.btnSendLink.setOnClickListener(v -> {
            String email_address = String.valueOf(binding.editTextEmail.getText());
            ChangePassword(email_address);
        });
    }

    private boolean isTabletMode(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;

        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);

        return diagonalInches >= 7.0; // Tablet 7 inches
    }

    private void ChangePassword(String email_address) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email_address).matches()) {
            Toast.makeText(this, "Wrong email address", Toast.LENGTH_SHORT).show();
        } else if (email_address.isEmpty()) {
            Toast.makeText(this, "Email is empty", Toast.LENGTH_SHORT).show();
        } else {
            OpenDialogCreateNewPassword(email_address);
        }
    }

    private void OpenDialogCreateNewPassword(String email) {
        Dialog dialog = new Dialog(ForgetPasswordActivity.this);
        dialog.setContentView(R.layout.dialog_change_password);
        dialog.setCancelable(false);
        dialog.create();
        dialog.show();

        AppCompatImageView close_dialog = dialog.findViewById(R.id.close_dialog);
        AppCompatEditText editTextPassword = dialog.findViewById(R.id.editTextPassword);
        AppCompatButton btn_change = dialog.findViewById(R.id.btn_change);
        close_dialog.setOnClickListener(v -> dialog.dismiss());


        btn_change.setOnClickListener(v -> {
            String pass = String.valueOf(editTextPassword.getText());
            if (pass.length() >= 8) {

                ApplyNewPassword(email, pass);
                dialog.dismiss();

            } else {
                Toast.makeText(ForgetPasswordActivity.this, "Password is small", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ApplyNewPassword(String email, String pass) {
        userDataViewModel = new ViewModelProvider(this).get(UserDataViewModel.class);
        userDataViewModel.ChangeUserPassword(email, pass, ForgetPasswordActivity.this);
    }
}