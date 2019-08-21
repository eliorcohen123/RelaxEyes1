package com.app.relaxeyes.ScreenThreePck;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
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

import com.app.relaxeyes.BroadcastReceiverPck.MyReceiverAlarmRest;
import com.app.relaxeyes.R;
import com.app.relaxeyes.BroadcastReceiverPck.MyReceiverAlarmScreen;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class TimerActivity extends AppCompatActivity implements View.OnClickListener {

    private long timeCountInMilliSecondsScreen, timeCountInMilliSecondsRest;

    private enum TimerStatusScreen {
        STARTED_SCREEN,
        STOPPED_SCREEN
    }

    private enum TimerStatusRest {
        STARTED_REST,
        STOPPED_REST
    }

    private TimerStatusScreen timerStatusScreen = TimerStatusScreen.STOPPED_SCREEN;
    private TimerStatusRest timerStatusRest = TimerStatusRest.STOPPED_REST;

    private ProgressBar progressBarCircleScreen, progressBarCircleRest;
    private TextView textViewTimeScreen, textViewTimeRest, titleTimerScreen, titleTimerRest, textViewAlarmTimeScreen, textViewAlarmTimeRest;
    private ImageView imageViewResetScreen, imageViewResetRest, imageViewStartStopScreen, imageViewStartStopRest, imageStopScreen, imageStopRest;
    private CountDownTimer countDownTimerScreen, countDownTimerRest;
    private PendingIntent pendingIntentScreen, pendingIntentRest;
    private Button backBtnScreen, backBtnRest;
    private AlarmManager alarmManagerScreen, alarmManagerRest;
    private NotificationManager notificationManagerScreen, notificationManagerRest;
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;
    private Calendar alarmStartTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {

        }
        setContentView(R.layout.activity_timer);

        initUI();
        myTextValueScreen();
        initListeners();
        showUI();
        myTextValueRest();
        checkStopScreen();
        checkStopRest();
    }

    private void initUI() {
        progressBarCircleScreen = findViewById(R.id.progressBarCircleScreen);
        textViewTimeScreen = findViewById(R.id.textViewTimeScreen);
        imageViewResetScreen = findViewById(R.id.imageViewResetScreen);
        imageViewStartStopScreen = findViewById(R.id.imageViewStartStopScreen);
        backBtnScreen = findViewById(R.id.backBtnScreen);
        titleTimerScreen = findViewById(R.id.titleTimerScreen);
        imageStopScreen = findViewById(R.id.imageStopScreen);
        textViewAlarmTimeScreen = findViewById(R.id.textViewAlarmTimeScreen);

        progressBarCircleRest = findViewById(R.id.progressBarCircleRest);
        textViewTimeRest = findViewById(R.id.textViewTimeRest);
        imageViewResetRest = findViewById(R.id.imageViewResetRest);
        imageViewStartStopRest = findViewById(R.id.imageViewStartStopRest);
        backBtnRest = findViewById(R.id.backBtnRest);
        titleTimerRest = findViewById(R.id.titleTimerRest);
        imageStopRest = findViewById(R.id.imageStopRest);
        textViewAlarmTimeRest = findViewById(R.id.textViewAlarmTimeRest);
    }

    private void initListeners() {
        imageViewResetScreen.setOnClickListener(this);
        imageViewStartStopScreen.setOnClickListener(this);
        imageViewResetRest.setOnClickListener(this);
        imageViewStartStopRest.setOnClickListener(this);
        backBtnScreen.setOnClickListener(this);
        backBtnRest.setOnClickListener(this);
        imageStopScreen.setOnClickListener(this);
        imageStopRest.setOnClickListener(this);
    }

    private void showUI() {
        textViewTimeRest.setVisibility(View.GONE);
        imageViewResetRest.setVisibility(View.GONE);
        progressBarCircleRest.setVisibility(View.GONE);
        imageViewStartStopRest.setVisibility(View.GONE);
        backBtnRest.setVisibility(View.GONE);
        titleTimerRest.setVisibility(View.GONE);
        imageStopRest.setVisibility(View.GONE);
        textViewAlarmTimeRest.setVisibility(View.GONE);

        prefs = getSharedPreferences("total_val_rest", MODE_PRIVATE);
        int idNumRest = prefs.getInt("total_rest", 0);

        prefs = getSharedPreferences("total_stop_screen_me", MODE_PRIVATE);
        int idNumScreen = prefs.getInt("screen_me", 1);

        prefs = getSharedPreferences("total_stop_rest_me", MODE_PRIVATE);
        int idNumRest2 = prefs.getInt("rest_me", 1);

        if (idNumRest != 0 && idNumScreen == 900000) {
            textViewTimeRest.setVisibility(View.VISIBLE);
            progressBarCircleRest.setVisibility(View.VISIBLE);
            imageViewStartStopRest.setVisibility(View.VISIBLE);
            backBtnRest.setVisibility(View.VISIBLE);
            titleTimerRest.setVisibility(View.VISIBLE);
            imageStopRest.setVisibility(View.VISIBLE);
            textViewAlarmTimeRest.setVisibility(View.VISIBLE);

            textViewTimeScreen.setVisibility(View.GONE);
            imageViewResetScreen.setVisibility(View.GONE);
            progressBarCircleScreen.setVisibility(View.GONE);
            imageViewStartStopScreen.setVisibility(View.GONE);
            backBtnScreen.setVisibility(View.GONE);
            titleTimerScreen.setVisibility(View.GONE);
            imageStopScreen.setVisibility(View.GONE);
            textViewAlarmTimeScreen.setVisibility(View.GONE);
        }

        if (idNumRest2 == 900000) {
            textViewTimeRest.setVisibility(View.GONE);
            imageViewResetRest.setVisibility(View.GONE);
            progressBarCircleRest.setVisibility(View.GONE);
            imageViewStartStopRest.setVisibility(View.GONE);
            backBtnRest.setVisibility(View.GONE);
            titleTimerRest.setVisibility(View.GONE);
            imageStopRest.setVisibility(View.GONE);
            textViewAlarmTimeRest.setVisibility(View.GONE);

            textViewTimeScreen.setVisibility(View.VISIBLE);
            progressBarCircleScreen.setVisibility(View.VISIBLE);
            imageViewStartStopScreen.setVisibility(View.VISIBLE);
            backBtnScreen.setVisibility(View.VISIBLE);
            titleTimerScreen.setVisibility(View.VISIBLE);
            imageStopScreen.setVisibility(View.VISIBLE);
            textViewAlarmTimeScreen.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewResetScreen:
                resetScreen();
                break;
            case R.id.imageViewStartStopScreen:
                startStopScreen();
                break;
            case R.id.imageViewResetRest:
                resetRest();
                break;
            case R.id.imageViewStartStopRest:
                startStopRest();
                break;
            case R.id.backBtnScreen:
                delData();

                onBackPressed();
                break;
            case R.id.backBtnRest:
                delData();

                getSharedPref("total_stop_rest_me", "rest_me", 900000);

                onBackPressed();
                break;
            case R.id.imageStopScreen:
                imageStopScreen.setVisibility(View.GONE);
                textViewAlarmTimeScreen.setVisibility(View.GONE);

                stopAlarmScreen();

                delData();

                onBackPressed();
                break;
            case R.id.imageStopRest:
                imageStopRest.setVisibility(View.GONE);
                textViewAlarmTimeRest.setVisibility(View.GONE);

                stopAlarmRest();

                delData();

                getSharedPref("total_stop_rest_me", "rest_me", 900000);

                onBackPressed();
                break;
        }
    }

    private void delData() {
        prefs.edit().clear().apply();
        getSharedPref("total_stop_screen", "screen", 900000);
        getSharedPref("total_stop_rest", "rest", 900000);
    }

    private void resetScreen() {
        stopCountDownTimerScreen();
        startCountDownTimerScreen();
        startAlarmScreen();
    }

    private void resetRest() {
        stopCountDownTimerRest();
        startCountDownTimerRest();
        startAlarmRest();
    }

    private void startStopScreen() {
        imageStopScreen.setVisibility(View.GONE);
        textViewAlarmTimeScreen.setVisibility(View.GONE);

        if (timerStatusScreen == TimerStatusScreen.STOPPED_SCREEN) {
            setTimerValuesScreen();
            setProgressBarValuesScreen();
            imageViewResetScreen.setVisibility(View.VISIBLE);
            imageViewStartStopScreen.setImageResource(R.drawable.icon_stop_screen);
            timerStatusScreen = TimerStatusScreen.STARTED_SCREEN;
            startCountDownTimerScreen();
            startAlarmScreen();
        } else {
            imageViewResetScreen.setVisibility(View.GONE);
            imageViewStartStopScreen.setImageResource(R.drawable.icon_start_screen);
            timerStatusScreen = TimerStatusScreen.STOPPED_SCREEN;
            stopCountDownTimerScreen();
            stopAlarmScreen();
        }
    }

    private void checkStopScreen() {
        prefs = getSharedPreferences("total_stop_screen", MODE_PRIVATE);
        int idNumStopScreen = prefs.getInt("screen", 900000);

        prefs = getSharedPreferences("total_time_screen", MODE_PRIVATE);
        int idNumStopScreenHour = prefs.getInt("time_screen_hour", 0);
        int idNumStopScreenMin = prefs.getInt("time_screen_min", 0);
        int idNumStopScreenSec = prefs.getInt("time_screen_sec", 0);

        if (idNumStopScreenHour <= 9 && idNumStopScreenMin <= 9 && idNumStopScreenSec <= 9) {
            textViewAlarmTimeScreen.setText("0" + idNumStopScreenHour + ":0" + idNumStopScreenMin + ":0" + idNumStopScreenSec);
        } else if (idNumStopScreenHour <= 9 && idNumStopScreenMin <= 9 && idNumStopScreenSec > 9) {
            textViewAlarmTimeScreen.setText("0" + idNumStopScreenHour + ":0" + idNumStopScreenMin + ":" + idNumStopScreenSec);
        } else if (idNumStopScreenHour > 9 && idNumStopScreenMin <= 9 && idNumStopScreenSec <= 9) {
            textViewAlarmTimeScreen.setText(idNumStopScreenHour + ":0" + idNumStopScreenMin + ":0" + idNumStopScreenSec);
        } else if (idNumStopScreenHour <= 9 && idNumStopScreenMin > 9 && idNumStopScreenSec <= 9) {
            textViewAlarmTimeScreen.setText("0" + idNumStopScreenHour + ":" + idNumStopScreenMin + ":0" + idNumStopScreenSec);
        } else if (idNumStopScreenHour > 9 && idNumStopScreenMin > 9 && idNumStopScreenSec <= 9) {
            textViewAlarmTimeScreen.setText(idNumStopScreenHour + ":" + idNumStopScreenMin + ":0" + idNumStopScreenSec);
        } else if (idNumStopScreenHour <= 9 && idNumStopScreenMin > 9 && idNumStopScreenSec > 9) {
            textViewAlarmTimeScreen.setText("0" + idNumStopScreenHour + ":" + idNumStopScreenMin + ":" + idNumStopScreenSec);
        } else if (idNumStopScreenHour > 9 && idNumStopScreenMin <= 9 && idNumStopScreenSec > 9) {
            textViewAlarmTimeScreen.setText(idNumStopScreenHour + ":0" + idNumStopScreenMin + ":" + idNumStopScreenSec);
        } else if (idNumStopScreenHour > 9 && idNumStopScreenMin > 9 && idNumStopScreenSec > 9) {
            textViewAlarmTimeScreen.setText(idNumStopScreenHour + ":" + idNumStopScreenMin + ":" + idNumStopScreenSec);
        }

        if (idNumStopScreen != 1) {
            imageStopScreen.setVisibility(View.GONE);
            textViewAlarmTimeScreen.setVisibility(View.GONE);
        } else {
            imageStopScreen.setVisibility(View.VISIBLE);
            textViewAlarmTimeScreen.setVisibility(View.VISIBLE);
        }
    }

    private void startStopRest() {
        imageStopRest.setVisibility(View.GONE);
        textViewAlarmTimeRest.setVisibility(View.GONE);

        if (timerStatusRest == TimerStatusRest.STOPPED_REST) {
            setTimerValuesRest();
            setProgressBarValuesRest();
            imageViewResetRest.setVisibility(View.VISIBLE);
            imageViewStartStopRest.setImageResource(R.drawable.icon_stop_rest);
            timerStatusRest = TimerStatusRest.STARTED_REST;
            startCountDownTimerRest();
            startAlarmRest();
        } else {
            imageViewResetRest.setVisibility(View.GONE);
            imageViewStartStopRest.setImageResource(R.drawable.icon_start_rest);
            timerStatusRest = TimerStatusRest.STOPPED_REST;
            stopCountDownTimerRest();
            stopAlarmRest();
        }
    }

    private void checkStopRest() {
        prefs = getSharedPreferences("total_stop_rest", MODE_PRIVATE);
        int idNumStopRest = prefs.getInt("rest", 900000);

        prefs = getSharedPreferences("total_time_rest", MODE_PRIVATE);
        int idNumStopRestHour = prefs.getInt("time_rest_hour", 0);
        int idNumStopRestMin = prefs.getInt("time_rest_min", 0);
        int idNumStopRestSec = prefs.getInt("time_rest_sec", 0);

        if (idNumStopRestHour <= 9 && idNumStopRestMin <= 9 && idNumStopRestSec <= 9) {
            textViewAlarmTimeRest.setText("0" + idNumStopRestHour + ":0" + idNumStopRestMin + ":0" + idNumStopRestSec);
        } else if (idNumStopRestHour <= 9 && idNumStopRestMin <= 9 && idNumStopRestSec > 9) {
            textViewAlarmTimeRest.setText("0" + idNumStopRestHour + ":0" + idNumStopRestMin + ":" + idNumStopRestSec);
        } else if (idNumStopRestHour > 9 && idNumStopRestMin <= 9 && idNumStopRestSec <= 9) {
            textViewAlarmTimeRest.setText(idNumStopRestHour + ":0" + idNumStopRestMin + ":0" + idNumStopRestSec);
        } else if (idNumStopRestHour <= 9 && idNumStopRestMin > 9 && idNumStopRestSec <= 9) {
            textViewAlarmTimeRest.setText("0" + idNumStopRestHour + ":" + idNumStopRestMin + ":0" + idNumStopRestSec);
        } else if (idNumStopRestHour > 9 && idNumStopRestMin > 9 && idNumStopRestSec <= 9) {
            textViewAlarmTimeRest.setText(idNumStopRestHour + ":" + idNumStopRestMin + ":0" + idNumStopRestSec);
        } else if (idNumStopRestHour <= 9 && idNumStopRestMin > 9 && idNumStopRestSec > 9) {
            textViewAlarmTimeRest.setText("0" + idNumStopRestHour + ":" + idNumStopRestMin + ":" + idNumStopRestSec);
        } else if (idNumStopRestHour > 9 && idNumStopRestMin <= 9 && idNumStopRestSec > 9) {
            textViewAlarmTimeRest.setText(idNumStopRestHour + ":0" + idNumStopRestMin + ":" + idNumStopRestSec);
        } else if (idNumStopRestHour > 9 && idNumStopRestMin > 9 && idNumStopRestSec > 9) {
            textViewAlarmTimeRest.setText(idNumStopRestHour + ":" + idNumStopRestMin + ":" + idNumStopRestSec);
        }

        if (idNumStopRest != 1) {
            imageStopRest.setVisibility(View.GONE);
            textViewAlarmTimeRest.setVisibility(View.GONE);
        } else {
            imageStopRest.setVisibility(View.VISIBLE);
            textViewAlarmTimeRest.setVisibility(View.VISIBLE);
        }
    }

    private void startAlarmScreen() {
        prefs = getSharedPreferences("total_val_screen", MODE_PRIVATE);
        float idNum = prefs.getFloat("total_screen", (float) 1000.0);

        Calendar cal = Calendar.getInstance();
        int currentHour = cal.get(Calendar.HOUR_OF_DAY);
        int currentMinutes = cal.get(Calendar.MINUTE);
        int currentSeconds = cal.get(Calendar.SECOND);

        timeCountInMilliSecondsScreen = (long) (30 * 60 * idNum);
        int hours = (int) timeCountInMilliSecondsScreen / 3600000;
        int temp = (int) timeCountInMilliSecondsScreen - hours * 3600000;
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

        Intent alarmIntent = new Intent(TimerActivity.this, MyReceiverAlarmScreen.class); // AlarmReceiver1 = broadcast receiver
        pendingIntentScreen = PendingIntent.getBroadcast(TimerActivity.this, 1, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManagerScreen = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmIntent.setData((Uri.parse("custom://" + System.currentTimeMillis())));

        getSharedPrefMulti("total_time_screen", "time_screen_hour", "time_screen_min", "time_screen_sec", hours4, mins3, secs2);

        alarmStartTime = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        alarmStartTime.set(Calendar.HOUR_OF_DAY, hours4);
        alarmStartTime.set(Calendar.MINUTE, mins3);
        alarmStartTime.set(Calendar.SECOND, secs2);
        if (now.after(alarmStartTime)) {
            alarmStartTime.add(Calendar.DATE, 1);
        }
        alarmManagerScreen.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), 999999999, pendingIntentScreen);

        ComponentName receiver = new ComponentName(TimerActivity.this, MyReceiverAlarmScreen.class);
        PackageManager pm = getPackageManager();

        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

        getSharedPref("total_stop_screen", "screen", 1);
    }

    private void startAlarmRest() {
        prefs = getSharedPreferences("total_val_rest", MODE_PRIVATE);
        int idNumRest = prefs.getInt("total_rest", 3);

        Calendar cal = Calendar.getInstance();
        int currentHour = cal.get(Calendar.HOUR_OF_DAY);
        int currentMinutes = cal.get(Calendar.MINUTE);
        int currentSeconds = cal.get(Calendar.SECOND);

        timeCountInMilliSecondsRest = (long) (60 * 1000 * idNumRest);
        int hours = (int) timeCountInMilliSecondsRest / 3600000;
        int temp = (int) timeCountInMilliSecondsRest - hours * 3600000;
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

        Intent alarmIntent = new Intent(TimerActivity.this, MyReceiverAlarmRest.class); // AlarmReceiver1 = broadcast receiver
        pendingIntentRest = PendingIntent.getBroadcast(TimerActivity.this, 3, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManagerRest = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmIntent.setData((Uri.parse("custom://" + System.currentTimeMillis())));

        getSharedPrefMulti("total_time_rest", "time_rest_hour", "time_rest_min", "time_rest_sec", hours4, mins3, secs2);

        alarmStartTime = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        alarmStartTime.set(Calendar.HOUR_OF_DAY, hours4);
        alarmStartTime.set(Calendar.MINUTE, mins3);
        alarmStartTime.set(Calendar.SECOND, secs2);
        if (now.after(alarmStartTime)) {
            alarmStartTime.add(Calendar.DATE, 1);
        }
        alarmManagerRest.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), 999999999, pendingIntentRest);

        ComponentName receiver = new ComponentName(TimerActivity.this, MyReceiverAlarmRest.class);
        PackageManager pm = getPackageManager();

        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

        getSharedPref("total_stop_rest", "rest", 1);
        getSharedPref("total_stop_screen_me", "screen_me", 900000);
    }

    private void stopAlarmScreen() {
        getSharedPref("total_stop_screen", "screen", 900000);

        alarmManagerScreen.cancel(pendingIntentScreen);

        if (notificationManagerScreen != null) {
            notificationManagerScreen = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            String id = "1";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManagerScreen.deleteNotificationChannel(id);
            }
        }

        ComponentName receiver = new ComponentName(TimerActivity.this, MyReceiverAlarmScreen.class);
        PackageManager pm = getPackageManager();

        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    private void stopAlarmRest() {
        getSharedPref("total_stop_rest", "rest", 900000);

        Intent alarmIntent = new Intent(TimerActivity.this, MyReceiverAlarmRest.class); // AlarmReceiver1 = broadcast receiver
        pendingIntentRest = PendingIntent.getBroadcast(TimerActivity.this, 3, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManagerRest = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmIntent.setData((Uri.parse("custom://" + System.currentTimeMillis())));

        alarmManagerRest.cancel(pendingIntentRest);

        if (notificationManagerRest != null) {
            notificationManagerRest = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            String id = "3";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManagerRest.deleteNotificationChannel(id);
            }
        }

        ComponentName receiver = new ComponentName(TimerActivity.this, MyReceiverAlarmRest.class);
        PackageManager pm = getPackageManager();

        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    private void myTextValueScreen() {
        prefs = getSharedPreferences("total_val_screen", MODE_PRIVATE);
        float idNum = prefs.getFloat("total_screen", (float) 1000.0);

        timeCountInMilliSecondsScreen = (long) (30 * 60 * idNum);
        int hours = (int) timeCountInMilliSecondsScreen / 3600000;
        int temp = (int) timeCountInMilliSecondsScreen - hours * 3600000;
        int mins = temp / 60000;
        temp = temp - mins * 60000;
        int secs = temp / 1000;
        if (hours <= 9 && mins <= 9 && secs <= 9) {
            textViewTimeScreen.setText("0" + hours + ":0" + mins + ":0" + secs);
        } else if (hours <= 9 && mins <= 9 && secs > 9) {
            textViewTimeScreen.setText("0" + hours + ":0" + mins + ":" + secs);
        } else if (hours > 9 && mins <= 9 && secs <= 9) {
            textViewTimeScreen.setText(hours + ":0" + mins + ":0" + secs);
        } else if (hours <= 9 && mins > 9 && secs <= 9) {
            textViewTimeScreen.setText("0" + hours + ":" + mins + ":0" + secs);
        } else if (hours > 9 && mins > 9 && secs <= 9) {
            textViewTimeScreen.setText(hours + ":" + mins + ":0" + secs);
        } else if (hours <= 9 && mins > 9 && secs > 9) {
            textViewTimeScreen.setText("0" + hours + ":" + mins + ":" + secs);
        } else if (hours > 9 && mins <= 9 && secs > 9) {
            textViewTimeScreen.setText(hours + ":0" + mins + ":" + secs);
        } else if (hours > 9 && mins > 9 && secs > 9) {
            textViewTimeScreen.setText(hours + ":" + mins + ":" + secs);
        }
    }

    private void myTextValueRest() {
        prefs = getSharedPreferences("total_val_rest", MODE_PRIVATE);
        int idNumRest = prefs.getInt("total_rest", 3);

        timeCountInMilliSecondsRest = (long) (60 * 1000 * idNumRest);
        int hours = (int) timeCountInMilliSecondsRest / 3600000;
        int temp = (int) timeCountInMilliSecondsRest - hours * 3600000;
        int mins = temp / 60000;
        temp = temp - mins * 60000;
        int secs = temp / 1000;
        if (hours <= 9 && mins <= 9 && secs <= 9) {
            textViewTimeRest.setText("0" + hours + ":0" + mins + ":0" + secs);
        } else if (hours <= 9 && mins <= 9 && secs > 9) {
            textViewTimeRest.setText("0" + hours + ":0" + mins + ":" + secs);
        } else if (hours > 9 && mins <= 9 && secs <= 9) {
            textViewTimeRest.setText(hours + ":0" + mins + ":0" + secs);
        } else if (hours <= 9 && mins > 9 && secs <= 9) {
            textViewTimeRest.setText("0" + hours + ":" + mins + ":0" + secs);
        } else if (hours > 9 && mins > 9 && secs <= 9) {
            textViewTimeRest.setText(hours + ":" + mins + ":0" + secs);
        } else if (hours <= 9 && mins > 9 && secs > 9) {
            textViewTimeRest.setText("0" + hours + ":" + mins + ":" + secs);
        } else if (hours > 9 && mins <= 9 && secs > 9) {
            textViewTimeRest.setText(hours + ":0" + mins + ":" + secs);
        } else if (hours > 9 && mins > 9 && secs > 9) {
            textViewTimeRest.setText(hours + ":" + mins + ":" + secs);
        }
    }

    private void setTimerValuesScreen() {
        prefs = getSharedPreferences("total_val_screen", MODE_PRIVATE);
        float idNum = prefs.getFloat("total_screen", (float) 1000.0);

        timeCountInMilliSecondsScreen = (long) (30 * 60 * idNum);
    }

    private void setTimerValuesRest() {
        prefs = getSharedPreferences("total_val_rest", MODE_PRIVATE);
        int idNumRest = prefs.getInt("total_rest", 3);

        timeCountInMilliSecondsRest = (long) (60 * 1000 * idNumRest);
    }

    private void startCountDownTimerScreen() {
        countDownTimerScreen = new CountDownTimer(timeCountInMilliSecondsScreen, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textViewTimeScreen.setText(hmsTimeFormatterScreen(millisUntilFinished));
                progressBarCircleScreen.setProgress((int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                setProgressBarValuesScreen();
                myTextValueRest();

                textViewTimeScreen.setVisibility(View.GONE);
                imageViewResetScreen.setVisibility(View.GONE);
                progressBarCircleScreen.setVisibility(View.GONE);
                imageViewStartStopScreen.setVisibility(View.GONE);
                backBtnScreen.setVisibility(View.GONE);
                titleTimerScreen.setVisibility(View.GONE);
                imageStopScreen.setVisibility(View.GONE);
                textViewAlarmTimeScreen.setVisibility(View.GONE);
                timerStatusScreen = TimerStatusScreen.STOPPED_SCREEN;

                textViewTimeRest.setVisibility(View.VISIBLE);
                progressBarCircleRest.setVisibility(View.VISIBLE);
                imageViewStartStopRest.setVisibility(View.VISIBLE);
                backBtnRest.setVisibility(View.VISIBLE);
                titleTimerRest.setVisibility(View.VISIBLE);

                imageViewStartStopRest.setImageResource(R.drawable.icon_start_rest);

                getSharedPref("total_stop_screen", "screen", 900000);
                getSharedPref("total_stop_screen_me", "screen_me", 900000);
            }
        }.start();
        countDownTimerScreen.start();
    }

    private void startCountDownTimerRest() {
        countDownTimerRest = new CountDownTimer(timeCountInMilliSecondsRest, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textViewTimeRest.setText(hmsTimeFormatterRest(millisUntilFinished));
                progressBarCircleRest.setProgress((int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                setProgressBarValuesRest();
                myTextValueScreen();

                textViewTimeScreen.setVisibility(View.VISIBLE);
                progressBarCircleScreen.setVisibility(View.VISIBLE);
                imageViewStartStopScreen.setVisibility(View.VISIBLE);
                backBtnScreen.setVisibility(View.VISIBLE);
                titleTimerScreen.setVisibility(View.VISIBLE);

                textViewTimeRest.setVisibility(View.GONE);
                imageViewResetRest.setVisibility(View.GONE);
                progressBarCircleRest.setVisibility(View.GONE);
                imageViewStartStopRest.setVisibility(View.GONE);
                backBtnRest.setVisibility(View.GONE);
                titleTimerRest.setVisibility(View.GONE);
                imageStopRest.setVisibility(View.GONE);
                textViewAlarmTimeRest.setVisibility(View.GONE);
                timerStatusRest = TimerStatusRest.STOPPED_REST;

                imageViewStartStopScreen.setImageResource(R.drawable.icon_start_screen);

                getSharedPref("total_stop_rest", "rest", 900000);
                getSharedPref("total_stop_rest_me", "rest_me", 900000);

                Toast toast = Toast.makeText(TimerActivity.this, getString(R.string.restart_details), Toast.LENGTH_LONG);
                View view = toast.getView();
                view.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
                TextView text = view.findViewById(android.R.id.message);
                text.setTextColor(getResources().getColor(R.color.colorYellow));
                toast.show();

                onBackPressed();
            }
        }.start();
        countDownTimerRest.start();
    }

    private void stopCountDownTimerScreen() {
        countDownTimerScreen.cancel();
    }

    private void stopCountDownTimerRest() {
        countDownTimerRest.cancel();
    }

    private void setProgressBarValuesScreen() {
        progressBarCircleScreen.setMax((int) timeCountInMilliSecondsScreen / 1000);
        progressBarCircleScreen.setProgress((int) timeCountInMilliSecondsScreen / 1000);
    }

    private void setProgressBarValuesRest() {
        progressBarCircleRest.setMax((int) timeCountInMilliSecondsRest / 1000);
        progressBarCircleRest.setProgress((int) timeCountInMilliSecondsRest / 1000);
    }

    private String hmsTimeFormatterScreen(long milliSeconds) {
        String hms = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));
        return hms;
    }

    private String hmsTimeFormatterRest(long milliSeconds) {
        String hms = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));
        return hms;
    }

    private void getSharedPref(String name, String key, int val) {
        editor = getSharedPreferences(name, MODE_PRIVATE).edit();
        editor.putInt(key, val);
        editor.apply();
    }

    private void getSharedPrefMulti(String name, String key1, String key2, String key3, int val1, int val2, int val3) {
        editor = getSharedPreferences(name, MODE_PRIVATE).edit();
        editor.putInt(key1, val1);
        editor.putInt(key2, val2);
        editor.putInt(key3, val3);
        editor.apply();
    }

}
