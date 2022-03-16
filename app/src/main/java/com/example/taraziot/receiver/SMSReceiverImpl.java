package com.example.taraziot.receiver;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.RawRes;

import com.example.taraziot.MainActivity;
import com.example.taraziot.R;
import com.example.taraziot.UserManagerSharedPrefs;
import com.example.taraziot.service.AudioService;

public class SMSReceiverImpl extends SMSReceiver {
    UserManagerSharedPrefs userManagerSharedPrefs;
    String statusFromServer;

    @Override
    protected void onMessageReceived(Intent intent, String phone, String message) {
//        public static int var1 =20;
        configSharedP();


        if (
                isValidPhone(phone, "50004001847347") ||
                        isValidPhone(phone, "100058452000") ||
                        isValidPhone(phone, "09127938973") ||
                        isValidPhone(phone, "09352622917") ||
                        isValidPhone(phone, "+989352622917") ||
                        isValidPhone(phone, "+989353368463") ||
                        isValidPhone(phone, "09353368463")
        ) {

            switch (message) {

                case "1":

                    if (isServiceRunning(AudioService.class)) {
                        stopAudioService();
                    }

                    long dot = 1000;
                    long gap = 50;

                    long[] pattern = {
                            0, dot, gap, dot, gap
                    };

                    startAudioService(R
                            .raw.sirenn, true, pattern
                    );

                    break;

                case "2":


                    if (isServiceRunning(AudioService.class)) {
                        stopAudioService();
                    }

                    long dot2 = 1000;
                    long gap2 = 50;

                    long[] pattern2 = {
                            0, dot2, gap2, dot2, gap2
                    };


                    startAudioService(R.raw.star, true, pattern2);

                    break;


                case "3":
                    MainActivity.statusTxt.setText(message);

//                    Intent i = new Intent(context, MainActivity.class);
//                    i.putExtra("msgContent", message);
////                    i.putExtra("sender",from);
//                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // adding this flag starts the new Activity in a new Task


//                    Intent i = new Intent(context, SMSReceiverImpl.class);
//                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    i.putExtra("message", message);
//                    context.startActivity(i);
//
//                    Bundle extras = intent.getExtras();
//                    Intent i = new Intent("broadCastName");
//                    // Data you need to pass to activity
//                    i.putExtra("message", extras.getString(message
//                    ));
//
//                    context.sendBroadcast(i);
                    break;

            }
        }
    }

    private boolean isValidPhone(String phone, String regex) {
        return phone.toLowerCase().trim().contains(
                regex.toLowerCase().trim()
        );
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void stopAudioService() {
        context.stopService(
                new Intent(
                        context, AudioService.class
                )
        );
    }

    private void startAudioService(@RawRes int resId, boolean isLooping, long[] vibrateEffect) {
        context.startService(
                new Intent(
                        context, AudioService.class
                ).setAction(AudioService.START_SELF_KEY).putExtra(
                        AudioService.AUDIO_RES_KEY,
                        new AudioService.AudioData(
                                resId, isLooping, vibrateEffect
                        )
                )
        );
    }

    private void configSharedP() {

        this.userManagerSharedPrefs = new UserManagerSharedPrefs(context);
//        token = userManagerSharedPrefs.getFullName();
//        verifiedAt = userManagerSharedPrefs.getVerifiedAt();
//        agreed = userManagerSharedPrefs.getAgreeToTerms();
        statusFromServer = userManagerSharedPrefs.getDestinationAddress(statusFromServer);
    }

}
