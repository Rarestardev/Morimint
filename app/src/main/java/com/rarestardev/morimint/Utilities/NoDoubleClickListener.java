package com.rarestardev.morimint.Utilities;

import android.view.View;

public class NoDoubleClickListener implements View.OnClickListener {

    private static final long DOUBLE_CLICK_TIME_DELTA = 300;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long clickTime = System.currentTimeMillis();

        if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA){
            return;
        }

        lastClickTime = clickTime;

        onSingleClick(v);
    }

    public void onSingleClick(View v) {

    }
}
