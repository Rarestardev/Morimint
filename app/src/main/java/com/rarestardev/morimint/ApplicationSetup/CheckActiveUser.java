package com.rarestardev.morimint.ApplicationSetup;

import android.content.Context;
import android.util.Log;

public class CheckActiveUser {
    Context context;

    public CheckActiveUser(Context context) {
        this.context = context;
    }

    public void isActiveUserInApp(boolean isActive){
        if (isActive){
            Log.d("Active","Active");
        }else {
            Log.d("Active","Not Active");
        }
    }
}
