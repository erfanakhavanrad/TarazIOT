package com.example.taraziot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.telephony.SmsMessage;
import android.widget.Button;
import android.widget.Toast;

public class SMSBroadcastReceiver extends BroadcastReceiver {
    MediaPlayer mp, mp2;
    Button stop;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {

            Bundle mBundle = intent.getExtras();
            SmsMessage[] msg;
            String smsFrom;
            mp = MediaPlayer.create(context, R.raw.star);
            mp2 = MediaPlayer.create(context, R.raw.army);

            if (mBundle != null) {
                try {
                    Object[] mPdus = (Object[]) mBundle.get("pdus");
                    msg = new SmsMessage[mPdus.length];

                    for (int i = 0; i < mPdus.length; i++) {
                        msg[i] = SmsMessage.createFromPdu((byte[]) mPdus[i]);
                        smsFrom = msg[i].getOriginatingAddress();
                        String smsBody = msg[i].getMessageBody();

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
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }


}


//                            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//                            Toast.makeText(context, "Wake the fuck up samurai", Toast.LENGTH_SHORT).show();
//                            vibrator.vibrate(50000);
//                            vibrator.vibrate(Vibrator.VIBRATION_EFFECT_SUPPORT_YES,);
//                            vibrator.vibrate(VibrationEffect.createOneShot(5000, VibrationEffect.DEFAULT_AMPLITUDE));