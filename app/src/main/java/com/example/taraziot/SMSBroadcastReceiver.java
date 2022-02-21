package com.example.taraziot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context, "New message received", Toast.LENGTH_SHORT).show();
        // Toast.makeText(context, "یک پیامک دریافت شد", Toast.LENGTH_SHORT).show();

        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {

            Bundle mBundle = intent.getExtras();
            SmsMessage[] msg;
            String smsFrom;

            if (mBundle != null) {
                try {
                    Object[] mPdus = (Object[]) mBundle.get("pdus");
                    msg = new SmsMessage[mPdus.length];

                    for (int i = 0; i < mPdus.length; i++) {
                        msg[i] = SmsMessage.createFromPdu((byte[]) mPdus[i]);
                        smsFrom = msg[i].getOriginatingAddress();
                        String smsBody = msg[i].getMessageBody();

                        Toast.makeText(context, "شماره: " + smsFrom + " / پیام: " + smsBody, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
