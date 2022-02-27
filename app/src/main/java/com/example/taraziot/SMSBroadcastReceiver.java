package com.example.taraziot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSBroadcastReceiver extends BroadcastReceiver {
    MediaPlayer mp, mp2;

    SmsMessage[] msgs = null;
    String smsBody ;
    String smsFrom;

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            try {
                Object[] pdus = (Object[]) bundle.get("pdus");
                msgs = new SmsMessage[pdus.length];
                smsBody = "";
                smsFrom = "";
                for (int i = 0; i < msgs.length; i++) {
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    smsFrom = msgs[i].getOriginatingAddress();
                    smsBody += msgs[i].getMessageBody();
                }

                if (smsFrom == null || smsFrom.length() <= 0) {
                    smsFrom = "Unknown Number";
                }

                mp = MediaPlayer.create(context, R.raw.star);
                mp2 = MediaPlayer.create(context, R.raw.army);

                Toast.makeText(context, "شماره: " + smsFrom + " / پیام: " + smsBody, Toast.LENGTH_SHORT).show();
                if (smsBody.contains("1")) {
                    Vibrator vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
                    vibrator.vibrate(VibrationEffect.createOneShot(5000, VibrationEffect.DEFAULT_AMPLITUDE));
                    mp.start();
                    Toast.makeText(context, "Alarm is for 1....", Toast.LENGTH_LONG).show();
                } else {
                    mp2.start();
                    Toast.makeText(context, "Wasn't 1", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


}


//                            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//                            Toast.makeText(context, "Wake the fuck up samurai", Toast.LENGTH_SHORT).show();
//                            vibrator.vibrate(50000);
//                            vibrator.vibrate(Vibrator.VIBRATION_EFFECT_SUPPORT_YES,);
//                            vibrator.vibrate(VibrationEffect.createOneShot(5000, VibrationEffect.DEFAULT_AMPLITUDE));