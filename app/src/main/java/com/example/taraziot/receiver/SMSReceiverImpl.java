package com.example.taraziot.receiver;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.annotation.RawRes;

import com.example.taraziot.R;
import com.example.taraziot.service.AudioService;

public class SMSReceiverImpl extends SMSReceiver {

    private Vibrator vibrator = null;

    @Override
    protected void onMessageReceived(Intent intent, String phone, String message) {

        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

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

                    startAudioService(R.raw.sirenn, true);

                    break;

                case "2":

                    if (isServiceRunning(AudioService.class)) {
                        stopAudioService();
                    }

                    startAudioService(R.raw.star, true);

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

    private void startAudioService(@RawRes int resId, boolean isLooping) {
        context.startService(
                new Intent(
                        context, AudioService.class
                ).setAction(AudioService.START_SELF_KEY).putExtra(
                        AudioService.AUDIO_RES_KEY,
                        new AudioService.AudioData(
                            resId, isLooping
                        )
                )
        );
    }

    private void vibrateDevice(Long duration) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(
                    VibrationEffect.createOneShot(
                            duration, VibrationEffect.DEFAULT_AMPLITUDE
                    )
            );
        } else {
            vibrator.vibrate(duration);
        }
    }
}
