package com.example.taraziot.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.core.app.NotificationCompat;

import java.io.Serializable;

public class AudioService extends Service {

    public static final String AUDIO_RES_KEY = "resId";
    public static final String START_SELF_KEY = "start";
    public static final String STOP_SELF_KEY = "stop";
    public static final String NOTIFICATION_CHANNEL_ID = "AudioService";

    private MediaPlayer mediaPlayer = null;

    @Nullable @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int returnValue = START_NOT_STICKY;

        if(intent.getAction().equals(STOP_SELF_KEY)) {
            stopSelf();
            return returnValue;
        }

        AudioData audioData = (AudioData) intent.getSerializableExtra(AUDIO_RES_KEY);

        if (audioData == null) {
            stopSelf();
            return returnValue;
        }

        mediaPlayer = MediaPlayer.create(this, audioData.resId);
        mediaPlayer.setLooping(audioData.isLooping);
        mediaPlayer.setVolume(100, 100);
        mediaPlayer.start();

        startForeground(1, createNotification());

        return returnValue;
    }

    public static class AudioData implements Serializable {
        @RawRes
        public int resId;
        public boolean isLooping;
        public AudioData(@RawRes int resId, boolean isLooping) {
            this.resId = resId;
            this.isLooping = isLooping;
        }
    }

    public Notification createNotification() {

        createNotificationChannel();

        Intent intent = new Intent(this, AudioService.class);
        intent.setAction(STOP_SELF_KEY);

        PendingIntent pendingIntent = PendingIntent.getService(
                this, 0 , intent, 0
        );

        return new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("this is a title")
                .setContentText("this is a text")
                .setSmallIcon(android.R.drawable.ic_dialog_email)
                .addAction(
                        android.R.drawable.ic_media_pause, "Stop",
                        pendingIntent
                )
                .build();
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID, "Audio Service", NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
