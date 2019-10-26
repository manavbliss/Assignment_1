package com.manav.assignment_1;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNotifications(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
    }
    public void showNotifications(String tittle, String message){
        NotificationCompat.Builder Builder = new NotificationCompat.Builder(this, "MyNotifications")
                .setContentTitle(tittle)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_notification);
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(999,Builder.build());


    }
}
