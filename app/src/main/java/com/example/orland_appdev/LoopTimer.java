package com.example.orland_appdev;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;

public class LoopTimer extends BaseActivity {

    TextView lblTimer, lblStart, lblEnd, lblElapsed;
    Button btnStart, btnStop;
    Timer timer;
    long startTime, millis, buffer, updatedTime;
    Handler myHandler = new Handler();
    boolean hasStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Instantiate
        lblTimer = findViewById(R.id.lblTimer);
        lblStart = findViewById(R.id.lblStart);
        lblEnd = findViewById(R.id.lblEnd);
        lblElapsed = findViewById(R.id.lblElapsed);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);

        //Inital Settings
        btnStop.setEnabled(false);

        // Add Action Listener to Buttons
        btnStart.setOnClickListener(v -> {
            startTime = SystemClock.uptimeMillis();
            myHandler.postDelayed(runningTime, 0);
            btnStart.setEnabled(false);
            btnStop.setEnabled(true);
        });

        btnStop.setOnClickListener(v -> {
            hasStarted = false;
            buffer += millis;
            lblEnd.setText(getDisplayTime(buffer));
            lblElapsed.setText(getDisplayTime(millis));
            myHandler.removeCallbacks(runningTime);
            btnStart.setEnabled(true);
            btnStop.setEnabled(false);
            lblTimer.setText("00:00:00");
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_loop_timer;
    }

    @Override
    protected String getActivityName() {
        return "Activity 4";
    }

    private Runnable runningTime = new Runnable() {
        @Override
        public void run() {
            if(!hasStarted){
                hasStarted = true;
                lblStart.setText(getDisplayTime(buffer));
            }
            millis = SystemClock.uptimeMillis() - startTime;
            updatedTime = buffer + millis;
            lblTimer.setText(getDisplayTime(updatedTime));
            myHandler.postDelayed(this, 0);
        }
    };

    private String getDisplayTime(long time){
        int sec = (int) (time/1000);
        int min = sec / 60;
        sec = sec % 60;
        int milliseconds = (int) (time % 1000);
        return String.format("%02d:%02d:%02d",min,sec,milliseconds);
    }
}