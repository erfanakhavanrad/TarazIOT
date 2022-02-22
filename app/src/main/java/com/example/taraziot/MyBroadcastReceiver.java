package com.example.taraziot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    MediaPlayer mp;

    @Override
    public void onReceive(Context context, Intent intent) {
        mp = MediaPlayer.create(context, R.raw.army);
        mp.start();
        Toast.makeText(context, "Alarm is playing....", Toast.LENGTH_LONG).show();
    }
}
