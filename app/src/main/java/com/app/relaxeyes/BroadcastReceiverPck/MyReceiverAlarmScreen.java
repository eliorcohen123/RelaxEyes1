package com.app.relaxeyes.BroadcastReceiverPck;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.app.relaxeyes.R;
import com.app.relaxeyes.ScreenThreePck.TimerActivity;

public class MyReceiverAlarmScreen extends BroadcastReceiver {

    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    private static PendingIntent pendingIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        final int NOTIFY_ID = 1; // ID of notification
        String id = "1"; // default_channel_id
        String title = "רענון עיניים"; // Default Channel
        if (notificationManager == null) {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notificationManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notificationManager.createNotificationChannel(mChannel);
            }
            getBuilder(context, id);
        } else {
            getBuilder(context, id);
        }
        Notification notification = builder.build();
        notificationManager.notify(NOTIFY_ID, notification);
    }

    private void getBuilder(Context context, String id) {
        builder = new NotificationCompat.Builder(context, id);
        Intent intent = new Intent(context, TimerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(context, 3, intent, 0);
        builder.setContentTitle("רענון עיניים")
                .setContentText("היי… הגיע הזמן לרענן את העיניים :)")  // required
                .setSmallIcon(R.drawable.eye_icon)  // required
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setTicker("ריענון העיניים")
                .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                .setPriority(Notification.PRIORITY_HIGH);
    }

}
