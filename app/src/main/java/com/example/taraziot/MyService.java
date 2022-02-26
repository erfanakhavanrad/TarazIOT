package com.example.taraziot;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyService extends Service {

    private MediaPlayer soundPlayer;

    @Override
    public void onCreate() {
//        super.onCreate();
        Toast.makeText(this, "سرویس ساخته شد", Toast.LENGTH_SHORT).show();
        soundPlayer = MediaPlayer.create(this, R.raw.song);
        soundPlayer.setLooping(false);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        return super.onStartCommand(intent, flags, startId);
        Toast.makeText(this, "سرویس استارت شد", Toast.LENGTH_SHORT).show();
        soundPlayer.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
//        super.onDestroy();
        Toast.makeText(this, "سرویس متوقف شد", Toast.LENGTH_SHORT).show();
        soundPlayer.stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
