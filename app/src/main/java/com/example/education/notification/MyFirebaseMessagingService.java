package com.example.education.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationManagerCompat;

import com.example.education.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    final String CHANNEL_ID = "HEADS_UP_NOTIFICATION";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID ,"elearner", NotificationManager.IMPORTANCE_HIGH);
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification =
                new Notification.Builder(this, CHANNEL_ID)
                        .setContentTitle(remoteMessage.getNotification().getTitle())
                        .setContentText(remoteMessage.getNotification().getBody())
                        .setSmallIcon(R.drawable.logo)
                         .setSound(alarmSound)
                        .setColor(getResources().getColor(R.color.red))
                        .setAutoCancel(false);
        NotificationManagerCompat.from(this).notify(1, notification.build());
        super.onMessageReceived(remoteMessage);
    }
}
