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
import android.widget.Toast;

import com.elior.relaxeyes.R;
import com.elior.relaxeyes.BroadcastReceiverPck.MyReceiverAlarmRest;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class TimerActivityRest extends AppCompatActivity implements View.OnClickListener {

    private long timeCountInMilliSecondsGreen;

    private enum TimerStatusGreen {
        STARTED,
        STOPPED
    }

    private TimerStatusGreen timerStatusGreen = TimerStatusGreen.STOPPED;

    private ProgressBar progressBarCircleGreen;
    private TextView textViewTimeGreen;
    private ImageView imageViewResetGreen, imageViewStartStopGreen;
    private CountDownTimer countDownTimerGreen;
    private PendingIntent pendingIntentGreen;
    private Button backAlarmGreen;
    private AlarmManager alarmManagerGreen;
    private NotificationManager notificationManagerGreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {

        }
        setContentView(R.layout.activity_timer_rest);

        initUI();
        myTextValueGreen();
        initListeners();
        btnBackGreen();
    }

    private void initUI() {
        progressBarCircleGreen = findViewById(R.id.progressBarCircleGreen);
        textViewTimeGreen = findViewById(R.id.textViewTimeGreen);
        imageViewResetGreen = findViewById(R.id.imageViewResetGreen);
        imageViewStartStopGreen = findViewById(R.id.imageViewStartStopGreen);
        backAlarmGreen = findViewById(R.id.backAlarmGreen);
    }

    private void initListeners() {
        imageViewResetGreen.setOnClickListener(this);
        imageViewStartStopGreen.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewResetGreen:
                resetGreen();
                break;
            case R.id.imageViewStartStopGreen:
                startStopGreen();
                break;
        }
    }

    private void resetGreen() {
        stopCountDownTimerGreen();
        startCountDownTimerGreen();
        startAlarmGreen();
    }

    private void startStopGreen() {
        if (timerStatusGreen == TimerStatusGreen.STOPPED) {
            setTimerValuesGreen();
            setProgressBarValuesGreen();
            imageViewResetGreen.setVisibility(View.VISIBLE);
            imageViewStartStopGreen.setImageResource(R.drawable.icon_stop_green);
            timerStatusGreen = TimerStatusGreen.STARTED;
            startCountDownTimerGreen();
            startAlarmGreen();
        } else {
            imageViewResetGreen.setVisibility(View.GONE);
            imageViewStartStopGreen.setImageResource(R.drawable.icon_start_green);
            timerStatusGreen = TimerStatusGreen.STOPPED;
            stopCountDownTimerGreen();
            stopAlarmGreen();
        }
    }

    private void startAlarmGreen() {
        SharedPreferences prefs = getSharedPreferences("total_mins", MODE_PRIVATE);
        int idNumRest = prefs.getInt("mins", 3);

        Calendar cal = Calendar.getInstance();
        int currentHour = cal.get(Calendar.HOUR_OF_DAY);
        int currentMinutes = cal.get(Calendar.MINUTE);
        int currentSeconds = cal.get(Calendar.SECOND);

        timeCountInMilliSecondsGreen = (long) (60 * 1000 * idNumRest);
        int hours = (int) timeCountInMilliSecondsGreen / 3600000;
        int temp = (int) timeCountInMilliSecondsGreen - hours * 3600000;
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

        ComponentName receiver = new ComponentName(TimerActivityRest.this, MyReceiverAlarmRest.class);
        PackageManager pm = getPackageManager();

        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

        Toast.makeText(TimerActivityRest.this, hours4 + ":" + mins3 + ":" + secs2, Toast.LENGTH_LONG).show();

        Intent alarmIntent = new Intent(TimerActivityRest.this, MyReceiverAlarmRest.class); // AlarmReceiver1 = broadcast receiver
        pendingIntentGreen = PendingIntent.getBroadcast(TimerActivityRest.this, 3, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManagerGreen = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmIntent.setData((Uri.parse("custom://" + System.currentTimeMillis())));

        Calendar alarmStartTime = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        alarmStartTime.set(Calendar.HOUR_OF_DAY, hours4);
        alarmStartTime.set(Calendar.MINUTE, mins3);
        alarmStartTime.set(Calendar.SECOND, secs2);
        if (now.after(alarmStartTime)) {
            alarmStartTime.add(Calendar.DATE, 1);
        }
        alarmManagerGreen.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), 999999999, pendingIntentGreen);

        ComponentName receiver2 = new ComponentName(TimerActivityRest.this, MyReceiverAlarmRest.class);
        PackageManager pm2 = getPackageManager();

        pm2.setComponentEnabledSetting(receiver2, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    private void stopAlarmGreen() {
        ComponentName receiver = new ComponentName(TimerActivityRest.this, MyReceiverAlarmRest.class);
        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

        Intent alarmIntent = new Intent(TimerActivityRest.this, MyReceiverAlarmRest.class); // AlarmReceiver1 = broadcast receiver
        pendingIntentGreen = PendingIntent.getBroadcast(TimerActivityRest.this, 3, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManagerGreen = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmIntent.setData((Uri.parse("custom://" + System.currentTimeMillis())));

        Calendar alarmStartTime = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        alarmStartTime.set(Calendar.HOUR_OF_DAY, 900000);
        alarmStartTime.set(Calendar.MINUTE, 900000);
        alarmStartTime.set(Calendar.SECOND, 900000);
        if (now.after(alarmStartTime)) {
            alarmStartTime.add(Calendar.DATE, 1);
        }
        alarmManagerGreen.cancel(pendingIntentGreen);

        if (notificationManagerGreen != null) {
            notificationManagerGreen = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            String id = "3";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManagerGreen.deleteNotificationChannel(id);
            }
        }

        ComponentName receiver2 = new ComponentName(TimerActivityRest.this, MyReceiverAlarmRest.class);
        PackageManager pm2 = getPackageManager();
        pm2.setComponentEnabledSetting(receiver2, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    private void myTextValueGreen() {
        SharedPreferences prefs = getSharedPreferences("total_mins", MODE_PRIVATE);
        int idNumRest = prefs.getInt("mins", 3);

        timeCountInMilliSecondsGreen = (long) (60 * 1000 * idNumRest);
        int hours = (int) timeCountInMilliSecondsGreen / 3600000;
        int temp = (int) timeCountInMilliSecondsGreen - hours * 3600000;
        int mins = temp / 60000;
        temp = temp - mins * 60000;
        int secs = temp / 1000;
        if (hours <= 9 && mins <= 9 && secs <= 9) {
            textViewTimeGreen.setText("0" + hours + ":0" + mins + ":0" + secs);
        } else if (hours <= 9 && mins <= 9 && secs > 9) {
            textViewTimeGreen.setText("0" + hours + ":0" + mins + ":" + secs);
        } else if (hours > 9 && mins <= 9 && secs <= 9) {
            textViewTimeGreen.setText(hours + ":0" + mins + ":0" + secs);
        } else if (hours <= 9 && mins > 9 && secs <= 9) {
            textViewTimeGreen.setText("0" + hours + ":" + mins + ":0" + secs);
        } else if (hours > 9 && mins > 9 && secs <= 9) {
            textViewTimeGreen.setText(hours + ":" + mins + ":0" + secs);
        } else if (hours <= 9 && mins > 9 && secs > 9) {
            textViewTimeGreen.setText("0" + hours + ":" + mins + ":" + secs);
        } else if (hours > 9 && mins <= 9 && secs > 9) {
            textViewTimeGreen.setText(hours + ":0" + mins + ":" + secs);
        } else if (hours > 9 && mins > 9 && secs > 9) {
            textViewTimeGreen.setText(hours + ":" + mins + ":" + secs);
        }
    }

    private void setTimerValuesGreen() {
        SharedPreferences prefs = getSharedPreferences("total_mins", MODE_PRIVATE);
        int idNumRest = prefs.getInt("mins", 3);

        timeCountInMilliSecondsGreen = (long) (60 * 1000 * idNumRest);
    }

    private void startCountDownTimerGreen() {
        countDownTimerGreen = new CountDownTimer(timeCountInMilliSecondsGreen, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textViewTimeGreen.setText(hmsTimeFormatterGreen(millisUntilFinished));
                progressBarCircleGreen.setProgress((int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                textViewTimeGreen.setText(hmsTimeFormatterGreen(timeCountInMilliSecondsGreen));
                setProgressBarValuesGreen();
                imageViewResetGreen.setVisibility(View.GONE);
                imageViewStartStopGreen.setImageResource(R.drawable.icon_start_green);
                timerStatusGreen = TimerStatusGreen.STOPPED;

                Intent intent = new Intent(TimerActivityRest.this, TimerActivity.class);
                startActivity(intent);
            }
        }.start();
        countDownTimerGreen.start();
    }

    private void stopCountDownTimerGreen() {
        countDownTimerGreen.cancel();
    }

    private void setProgressBarValuesGreen() {
        progressBarCircleGreen.setMax((int) timeCountInMilliSecondsGreen / 1000);
        progressBarCircleGreen.setProgress((int) timeCountInMilliSecondsGreen / 1000);
    }

    private String hmsTimeFormatterGreen(long milliSeconds) {
        String hms = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));
        return hms;
    }

    private void btnBackGreen() {
        backAlarmGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimerActivityRest.this, TimerActivity.class);
                startActivity(intent);
            }
        });
    }

}
