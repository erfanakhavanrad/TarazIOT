package com.example.taraziot.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.util.HashMap;
import java.util.Map;


public abstract class SMSReceiver extends BroadcastReceiver {

    protected Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        Map<String, String> smsMap = getMessage(intent);
        for(String phone : smsMap.keySet()) {
            String msg = smsMap.get(phone);
            onMessageReceived(intent , phone , msg);
        }
    }

    private Map<String, String> getMessage(Intent intent) {

        HashMap<String, String> map = new HashMap<>();

        Bundle bundle = intent.getExtras();

        if(bundle == null) return map;

        Object[] pdus = (Object[]) bundle.get("pdus");

        if(pdus == null) return map;

        SmsMessage[] messages = new SmsMessage[pdus.length];

        for(int i = 0; i < pdus.length; i++) {

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String format = bundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
            } else {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            }

            if(map.containsKey(messages[i].getDisplayOriginatingAddress())) {
                String body = map.get(messages[i].getDisplayOriginatingAddress());
                body += messages[i].getDisplayMessageBody();
                map.put(messages[i].getDisplayOriginatingAddress(), body);
            } else {
                map.put(messages[i].getDisplayOriginatingAddress(), messages[i].getDisplayMessageBody());
            }
        }

        return map;
    }

    protected abstract void onMessageReceived(Intent intent, String phone, String message);

}
