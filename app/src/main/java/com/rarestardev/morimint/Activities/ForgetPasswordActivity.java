package com.rarestardev.morimint.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.rarestardev.morimint.R;
import com.rarestardev.morimint.databinding.ActivityForgetPasswordBinding;

public class ForgetPasswordActivity extends AppCompatActivity {
    private ActivityForgetPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_forget_password);

        binding.btnSendLink.setOnClickListener(v -> {
            String email_address = String.valueOf(binding.editTextEmail.getText());
            ChangePassword(email_address);
        });
    }

    private void ChangePassword(String email_address) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email_address).matches()){
            Toast.makeText(this, "Wrong email address", Toast.LENGTH_SHORT).show();
        }else if (email_address.isEmpty()){
            Toast.makeText(this, "Email is empty", Toast.LENGTH_SHORT).show();
        }else {
            OpenDialogCreateNewPassword();
        }
    }

    private void OpenDialogCreateNewPassword() {
        Dialog dialog = new Dialog(ForgetPasswordActivity.this);
        dialog.setContentView(R.layout.dialog_change_password);
        dialog.setCancelable(false);
        dialog.create();
        dialog.show();

        AppCompatImageView close_dialog = dialog.findViewById(R.id.close_dialog);
        AppCompatEditText editTextPassword = dialog.findViewById(R.id.editTextPassword);
        AppCompatButton btn_change = dialog.findViewById(R.id.btn_change);
        close_dialog.setOnClickListener(v -> dialog.dismiss());


        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = String.valueOf(editTextPassword.getText());
                if (pass.length() >= 8){

                    ApplyNewPassword(pass);
                    dialog.dismiss();

                }else {
                    Toast.makeText(ForgetPasswordActivity.this, "Password is small", Toast.LENGTH_SHORT).show();
                }
            }

            private void ApplyNewPassword(String pass) {
                Toast.makeText(ForgetPasswordActivity.this, pass, Toast.LENGTH_SHORT).show();
            }
        });
    }
}