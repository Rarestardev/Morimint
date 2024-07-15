package com.rarestardev.morimint.ApplicationSetup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TaskCalculator {

    Context context;
    public static boolean doTask = false;
    int level;

    public TaskCalculator(Context context,int level) {
        this.context = context;
        this.level = level;
    }


    @SuppressLint("SetTextI18n")
    public void TaskManager(int task, TextView textView, ProgressBar progressBar){
        switch (task){
            case 1:
                textView.setText("1" + " / " + 5);
                doTask = false;
                level = 1;
                break;
            case 2:
                textView.setText("2" + " / " + 5);
                doTask = false;
                level = 1;
                break;
            case 3:
                textView.setText("3" + " / " + 5);
                doTask = false;
                level = 1;
                break;
            case 4:
                textView.setText("4" + " / " + 5);
                doTask = false;
                level = 1;
                break;
            case 5:
                textView.setText("5" + " / " + 5);
                doTask = true;
                level = 1;
                progressBar.setSecondaryProgress(20);
                break;
        }
    }
}
