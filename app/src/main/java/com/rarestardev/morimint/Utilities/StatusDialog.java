package com.rarestardev.morimint.Utilities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.rarestardev.morimint.Constants.UserConstants;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.databinding.CustomStatusDialogBinding;

import java.util.Objects;

public class StatusDialog extends Dialog {

    CustomStatusDialogBinding binding;

    DialogType type;
    Context context;
    String message, title, btnText;
    View.OnClickListener listener;

    private static final int[] DIALOG_ICON_BACKGROUND = {R.color.DialogWarning, R.color.DialogLoading, R.color.DialogFailed, R.color.DialogSuccess};


    public StatusDialog(@NonNull Context context, DialogType type) {
        super(context);
        this.type = type;
        this.context = context;
    }

    @Override
    @SuppressLint({"InflateParams", "UseCompatLoadingForDrawables"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.custom_status_dialog, null, false);

        Objects.requireNonNull(getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setGravity(Gravity.CENTER);
        setContentView(binding.getRoot());

        binding.statusTitle.setText(title);
        Log.d(UserConstants.APP_LOG_TAG, message);
        binding.statusMessage.setText(message);
        binding.statusBtn.setOnClickListener(listener);


        switch (type) {
            case FAILED:
                binding.statusBtn.setVisibility(View.VISIBLE);
                binding.statusIconBackground.setCardBackgroundColor(context.getColor(DIALOG_ICON_BACKGROUND[2]));
                binding.statusIcon.setImageDrawable(context.getDrawable(R.drawable.failed_ic));
                YoYo.with(Techniques.RotateIn).duration(1000).playOn(binding.statusIconBackground);
                break;
            case LOADING:
                binding.statusBtn.setVisibility(View.GONE);
                binding.statusIconBackground.setCardBackgroundColor(context.getColor(DIALOG_ICON_BACKGROUND[1]));
                binding.statusIcon.setImageDrawable(context.getDrawable(R.drawable.loading_dialog_ic));

                RotateAnimation rotate = new RotateAnimation(0, 360,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(700);
                rotate.setInterpolator(new LinearInterpolator());
                rotate.setRepeatCount(Animation.INFINITE);
                binding.statusIcon.setAnimation(rotate);
                setOnDismissListener(dialog -> rotate.cancel());
                break;
            case SUCCESS:
                binding.statusBtn.setVisibility(View.VISIBLE);
                binding.statusIconBackground.setCardBackgroundColor(context.getColor(DIALOG_ICON_BACKGROUND[3]));
                binding.statusIcon.setImageDrawable(context.getDrawable(R.drawable.task_tick_ic));
                YoYo.with(Techniques.RotateIn).duration(1000).playOn(binding.statusIconBackground);
                break;
            case WARNING:
                binding.statusBtn.setVisibility(View.VISIBLE);
                binding.statusIconBackground.setCardBackgroundColor(context.getColor(DIALOG_ICON_BACKGROUND[0]));
                binding.statusIcon.setImageDrawable(context.getDrawable(R.drawable.warning_ic));
                YoYo.with(Techniques.RotateIn).duration(1000).playOn(binding.statusIconBackground);
                break;
        }
    }


    public void setButtonText(String text) {
        this.btnText = text;
    }


    public void setButtonListener(View.OnClickListener listener) {
        this.listener = listener;
    }


    public void setTitleDialog(String title) {
        this.title = title;
    }

    public void setMessageDialog(String message) {
        this.message = message;
    }

}
