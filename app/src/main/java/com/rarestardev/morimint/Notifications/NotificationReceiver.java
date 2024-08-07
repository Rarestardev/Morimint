package com.rarestardev.morimint.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.rarestardev.morimint.Activities.NewsActivity;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent newsIntent = new Intent(context, NewsActivity.class);
        newsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newsIntent);
    }
}

