package com.example.to_do;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Notification extends Application {
    public static final String channelID="channel1";
    @Override
    public void onCreate() {
        super.onCreate();
        creatNotificationChannel();
    }
    public void creatNotificationChannel()
    {
        if(Build.VERSION.SDK_INT==Build.VERSION_CODES.O)
        {
            NotificationChannel channel1=new NotificationChannel(channelID,"channel1", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("test");
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }




    }
}
