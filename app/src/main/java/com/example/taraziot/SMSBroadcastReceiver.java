package com.example.taraziot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.widget.Toast;

public class SMSBroadcastReceiver extends BroadcastReceiver {
    MediaPlayer mp;
    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context, "New message received", Toast.LENGTH_SHORT).show();
        // Toast.makeText(context, "یک پیامک دریافت شد", Toast.LENGTH_SHORT).show();


        Toast.makeText(context, "Wake the fuck up samurai", Toast.LENGTH_SHORT).show();
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(50000);

        mp = MediaPlayer.create(context, R.raw.star);
//        mp.start();
        Toast.makeText(context, "Alarm is playing....", Toast.LENGTH_LONG).show();
//        mp = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
        mp.start();
//        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
//
//            Bundle mBundle = intent.getExtras();
//            SmsMessage[] msg;
//            String smsFrom;
//
//            if (mBundle != null) {
//                try {
//                    Object[] mPdus = (Object[]) mBundle.get("pdus");
//                    msg = new SmsMessage[mPdus.length];
//
//                    for (int i = 0; i < mPdus.length; i++) {
//                        msg[i] = SmsMessage.createFromPdu((byte[]) mPdus[i]);
//                        smsFrom = msg[i].getOriginatingAddress();
//                        String smsBody = msg[i].getMessageBody();
//
////                        Toast.makeText(context, "شماره: " + smsFrom + " / پیام: " + smsBody, Toast.LENGTH_SHORT).show();
//                        if (smsBody.contains("1")) {
////write here
//                        } else {
//                            Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
    }


}
