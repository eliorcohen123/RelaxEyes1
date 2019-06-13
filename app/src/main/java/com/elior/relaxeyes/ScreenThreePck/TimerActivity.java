package com.elior.relaxeyes.ScreenThreePck;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.elior.relaxeyes.R;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class TimerActivity extends AppCompatActivity implements View.OnClickListener{

    private long timeCountInMilliSeconds;

    private enum TimerStatus {
        STARTED,
        STOPPED
    }

    private TimerStatus timerStatus = TimerStatus.STOPPED;

    private ProgressBar progressBarCircle;
    private TextView textViewTime;
    private ImageView imageViewReset, imageViewStartStop;
    private CountDownTimer countDownTimer;
    private PendingIntent pendingIntent;
    private Button backAlarm;
    private AlarmManager alarmManager;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {

        }
        setContentView(R.layout.activity_timer);

        initUI();
        myTextValue();
        initListeners();
        btnBack();
    }

    private void initUI() {
        progressBarCircle = findViewById(R.id.progressBarCircle);
        textViewTime = findViewById(R.id.textViewTime);
        imageViewReset = findViewById(R.id.imageViewReset);
        imageViewStartStop = findViewById(R.id.imageViewStartStop);
        backAlarm = findViewById(R.id.backAlarm);
    }

    private void initListeners() {
        imageViewReset.setOnClickListener(this);
        imageViewStartStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewReset:
                reset();
                break;
            case R.id.imageViewStartStop:
                startStop();
                break;
        }
    }

    private void reset() {
        stopCountDownTimer();
        startCountDownTimer();
        startAlarm();
    }

    private void startStop() {
        if (timerStatus == TimerStatus.STOPPED) {
            setTimerValues();
            setProgressBarValues();
            imageViewReset.setVisibility(View.VISIBLE);
            imageViewStartStop.setImageResource(R.drawable.icon_stop);
            timerStatus = TimerStatus.STARTED;
            startCountDownTimer();
            startAlarm();
        } else {
            imageViewReset.setVisibility(View.GONE);
            imageViewStartStop.setImageResource(R.drawable.icon_start);
            timerStatus = TimerStatus.STOPPED;
            stopCountDownTimer();
            stopAlarm();
        }
    }

    private void startAlarm() {
        SharedPreferences prefs = getSharedPreferences("total_val", MODE_PRIVATE);
        float idNum = prefs.getFloat("total", (float) 1000.0);

        Calendar cal = Calendar.getInstance();
        int currentHour = cal.get(Calendar.HOUR_OF_DAY);
        int currentMinutes = cal.get(Calendar.MINUTE);
        int currentSeconds = cal.get(Calendar.SECOND);

        timeCountInMilliSeconds = (long) (30 * 60 * idNum);
        int hours = (int) timeCountInMilliSeconds / 3600000;
        int temp = (int) timeCountInMilliSeconds - hours * 3600000;
        int mins = temp / 60000;
        temp = temp - mins * 60000;
        int secs = temp / 1000;

        int myHours = currentHour + hours;
        int myMinutes = currentMinutes + mins;
        int mySeconds = currentSeconds + secs;

        int mins2;
        int secs2;
        if (mySeconds >= 60) {
            mins2 = myMinutes + 1;
            secs2 = mySeconds - 60;
        } else {
            mins2 = myMinutes;
            secs2 = mySeconds;
        }

        int hours3;
        int mins3;
        if (mins2 >= 60) {
            hours3 = myHours + 1;
            mins3 = mins2 - 60;
        } else {
            hours3 = myHours;
            mins3 = mins2;
        }

        int hours4;
        if (hours3 >= 24) {
            hours4 = hours3 - 24;
        } else {
            hours4 = hours3;
        }

        ComponentName receiver = new ComponentName(TimerActivity.this, MyReceiverAlarm.class);
        PackageManager pm = getPackageManager();

        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

        Intent alarmIntent = new Intent(TimerActivity.this, MyReceiverAlarm.class); // AlarmReceiver1 = broadcast receiver
        pendingIntent = PendingIntent.getBroadcast(TimerActivity.this, 1, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmIntent.setData((Uri.parse("custom://" + System.currentTimeMillis())));

        Calendar alarmStartTime = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        alarmStartTime.set(Calendar.HOUR_OF_DAY, hours4);
        alarmStartTime.set(Calendar.MINUTE, mins3);
        alarmStartTime.set(Calendar.SECOND, secs2);
        if (now.after(alarmStartTime)) {
            alarmStartTime.add(Calendar.DATE, 1);
        }
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        ComponentName receiver2 = new ComponentName(TimerActivity.this, MyReceiverAlarm.class);
        PackageManager pm2 = getPackageManager();

        pm2.setComponentEnabledSetting(receiver2, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    private void stopAlarm() {
        ComponentName receiver = new ComponentName(TimerActivity.this, MyReceiverAlarm.class);
        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

        SharedPreferences.Editor editor = getSharedPreferences("textTime", MODE_PRIVATE).edit();
        editor.putInt("idHour", 0);
        editor.putInt("idMinute", 0);
        editor.apply();

        Intent alarmIntent = new Intent(TimerActivity.this, MyReceiverAlarm.class); // AlarmReceiver1 = broadcast receiver
        pendingIntent = PendingIntent.getBroadcast(TimerActivity.this, 1, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmIntent.setData((Uri.parse("custom://" + System.currentTimeMillis())));

        Calendar alarmStartTime = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        alarmStartTime.set(Calendar.HOUR_OF_DAY, 900000);
        alarmStartTime.set(Calendar.MINUTE, 900000);
        alarmStartTime.set(Calendar.SECOND, 900000);
        if (now.after(alarmStartTime)) {
            alarmStartTime.add(Calendar.DATE, 1);
        }
        alarmManager.cancel(pendingIntent);

        if (notificationManager != null) {
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            String id = "1";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.deleteNotificationChannel(id);
            }
        }

        ComponentName receiver2 = new ComponentName(TimerActivity.this, MyReceiverAlarm.class);
        PackageManager pm2 = getPackageManager();
        pm2.setComponentEnabledSetting(receiver2, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    private void myTextValue() {
        SharedPreferences prefs = getSharedPreferences("total_val", MODE_PRIVATE);
        float idNum = prefs.getFloat("total", (float) 1000.0);

        timeCountInMilliSeconds = (long) (30 * 60 * idNum);
        int hours = (int) timeCountInMilliSeconds / 3600000;
        int temp = (int) timeCountInMilliSeconds - hours * 3600000;
        int mins = temp / 60000;
        temp = temp - mins * 60000;
        int secs = temp / 1000;
        if (hours <= 9 && mins <= 9 && secs <= 9) {
            textViewTime.setText("0" + hours + ":0" + mins + ":0" + secs);
        } else if (hours <= 9 && mins <= 9 && secs > 9) {
            textViewTime.setText("0" + hours + ":0" + mins + ":" + secs);
        } else if (hours > 9 && mins <= 9 && secs <= 9) {
            textViewTime.setText(hours + ":0" + mins + ":0" + secs);
        } else if (hours <= 9 && mins > 9 && secs <= 9) {
            textViewTime.setText("0" + hours + ":" + mins + ":0" + secs);
        } else if (hours > 9 && mins > 9 && secs <= 9) {
            textViewTime.setText(hours + ":" + mins + ":0" + secs);
        } else if (hours <= 9 && mins > 9 && secs > 9) {
            textViewTime.setText("0" + hours + ":" + mins + ":" + secs);
        } else if (hours > 9 && mins <= 9 && secs > 9) {
            textViewTime.setText(hours + ":0" + mins + ":" + secs);
        } else if (hours > 9 && mins > 9 && secs > 9) {
            textViewTime.setText(hours + ":" + mins + ":" + secs);
        }
    }

    private void setTimerValues() {
        SharedPreferences prefs = getSharedPreferences("total_val", MODE_PRIVATE);
        float idNum = prefs.getFloat("total", (float) 1000.0);

        timeCountInMilliSeconds = (long) (30 * 60 * idNum);
    }

    private void startCountDownTimer() {
        countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textViewTime.setText(hmsTimeFormatter(millisUntilFinished));
                progressBarCircle.setProgress((int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                textViewTime.setText(hmsTimeFormatter(timeCountInMilliSeconds));
                setProgressBarValues();
                imageViewReset.setVisibility(View.GONE);
                imageViewStartStop.setImageResource(R.drawable.icon_start);
                timerStatus = TimerStatus.STOPPED;
            }
        }.start();
        countDownTimer.start();
    }

    private void stopCountDownTimer() {
        countDownTimer.cancel();
    }

    private void setProgressBarValues() {
        progressBarCircle.setMax((int) timeCountInMilliSeconds / 1000);
        progressBarCircle.setProgress((int) timeCountInMilliSeconds / 1000);
    }

    private String hmsTimeFormatter(long milliSeconds) {
        String hms = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));
        return hms;
    }

    private void btnBack() {
        backAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
