//package com.example.taraziot;
//
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.Service;
//import android.content.Intent;
//import android.media.MediaPlayer;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.IBinder;
//import android.os.Vibrator;
//import android.telephony.SmsMessage;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//import androidx.core.app.NotificationCompat;
//
//public class MyService extends Service {
//    private MediaPlayer soundPlayer;
//    private String CHANNEL_ID = "channelId";
//    private NotificationManager notifManager;
//    Vibrator vibrator;
//
//    @Override
//    public void onCreate() {
//
//        Toast.makeText(this, "سرویس ساخته شد", Toast.LENGTH_SHORT).show();
//        soundPlayer = MediaPlayer.create(this, R.raw.star);
//        soundPlayer.setLooping(false);
//
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//
//        MediaPlayer mp, mp2;
//        Toast.makeText(this, "سرویس استارت شد", Toast.LENGTH_SHORT).show();
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            String offerChannelName = "Service Channel";
//            String offerChannelDescription = "Music Channel";
//            int offerChannelImportance = NotificationManager.IMPORTANCE_DEFAULT;
//
//            NotificationChannel notifChannel = new NotificationChannel(CHANNEL_ID, offerChannelName, offerChannelImportance);
//            notifChannel.setDescription(offerChannelDescription);
//            notifManager = getSystemService(NotificationManager.class);
//            notifManager.createNotificationChannel(notifChannel);
//
//        }
//
//        NotificationCompat.Builder sNotifBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                .setContentTitle("موسیقی")
//                .setContentText("یک موسیقی در حال اجراست");
//
//        Notification servNotification = sNotifBuilder.build();
//        startForeground(1, servNotification);
//
//        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
//
//            Bundle mBundle = intent.getExtras();
//            SmsMessage[] msg;
//            String smsFrom;
//            mp = MediaPlayer.create(this, R.raw.star);
//            mp2 = MediaPlayer.create(this, R.raw.army);
//
//            if (mBundle != null) {
//                Toast.makeText(this, "salam", Toast.LENGTH_SHORT).show();
////                try {
////                    Object[] mPdus = (Object[]) mBundle.get("pdus");
////                    msg = new SmsMessage[mPdus.length];
////
////                    for (int i = 0; i < mPdus.length; i++) {
////                        msg[i] = SmsMessage.createFromPdu((byte[]) mPdus[i]);
////                        smsFrom = msg[i].getOriginatingAddress();
////                        String smsBody = msg[i].getMessageBody();
////
////                        Toast.makeText(this, "شماره: " + smsFrom + " / پیام: " + smsBody, Toast.LENGTH_SHORT).show();
////                        if (smsBody.contains("1")) {
////                          vibrator = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);
////                            vibrator.vibrate(VibrationEffect.createOneShot(5000, VibrationEffect.DEFAULT_AMPLITUDE));
//////                            mp.start();
////                            soundPlayer.start();
////                            Toast.makeText(this, "Alarm is for 1....", Toast.LENGTH_LONG).show();
////                        } else {
//////                            mp2.start();
////                            Toast.makeText(this, "Wasn't 1", Toast.LENGTH_SHORT).show();
////                        }
////                    }
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
//            } else {
//                Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
//            }
//
//        }
////*****************************
//        return START_STICKY;
//
//    }
//
//    @Override
//    public void onDestroy() {
//
//        Toast.makeText(this, "سرویس متوقف شد", Toast.LENGTH_SHORT).show();
//        soundPlayer.stop();
//
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//
////------------------------------------------------------------
////    private MediaPlayer soundPlayer;
////    private String CHANNEL_ID = "channelId";
////    private NotificationManager notifManager;
////    Vibrator vibrator;
////
////    @Override
////    public void onCreate() {
////
////        Toast.makeText(this, "سرویس ساخته شد", Toast.LENGTH_SHORT).show();
////        soundPlayer = MediaPlayer.create(this, R.raw.star);
////        soundPlayer.setLooping(false);
////
////
////    }
////
////    @Override
////    public int onStartCommand(Intent intent, int flags, int startId) {
////        MediaPlayer mp, mp2;
////
////        Toast.makeText(this, "سرویس استارت شد", Toast.LENGTH_SHORT).show();
////
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////
////            String offerChannelName = "Service Channel";
////            String offerChannelDescription= "Music Channel";
////            int offerChannelImportance = NotificationManager.IMPORTANCE_DEFAULT;
////
////            NotificationChannel notifChannel = new NotificationChannel(CHANNEL_ID, offerChannelName, offerChannelImportance);
////            notifChannel.setDescription(offerChannelDescription);
////            notifManager = getSystemService(NotificationManager.class);
////            notifManager.createNotificationChannel(notifChannel);
////
////        }
////
////        NotificationCompat.Builder sNotifBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
////                .setSmallIcon(R.drawable.ic_launcher_foreground)
////                .setContentTitle("موسیقی")
////                .setContentText("یک موسیقی در حال اجراست");
////
////        Notification servNotification = sNotifBuilder.build();
////
////        startForeground(1, servNotification);
////
////
////
////        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
////
////            Bundle mBundle = intent.getExtras();
////            SmsMessage[] msg;
////            String smsFrom;
////            mp = MediaPlayer.create(this, R.raw.star);
////            mp2 = MediaPlayer.create(this, R.raw.army);
////
////            if (mBundle != null) {
////                try {
////                    Object[] mPdus = (Object[]) mBundle.get("pdus");
////                    msg = new SmsMessage[mPdus.length];
////
////                    for (int i = 0; i < mPdus.length; i++) {
////                        msg[i] = SmsMessage.createFromPdu((byte[]) mPdus[i]);
////                        smsFrom = msg[i].getOriginatingAddress();
////                        String smsBody = msg[i].getMessageBody();
////
////                        Toast.makeText(this, "شماره: " + smsFrom + " / پیام: " + smsBody, Toast.LENGTH_SHORT).show();
////                        if (smsBody.contains("1")) {
////                          vibrator = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);
////                            vibrator.vibrate(VibrationEffect.createOneShot(5000, VibrationEffect.DEFAULT_AMPLITUDE));
//////                            mp.start();
////                            soundPlayer.start();
////                            Toast.makeText(this, "Alarm is for 1....", Toast.LENGTH_LONG).show();
////                        } else {
////                            mp2.start();
////                            Toast.makeText(this, "Wasn't 1", Toast.LENGTH_SHORT).show();
////                        }
////                    }
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
////            }
////
////        }
////
////        return START_STICKY;
////
////    }
////
////    @Override
////    public void onDestroy() {
////
////        Toast.makeText(this, "سرویس متوقف شد", Toast.LENGTH_SHORT).show();
////        soundPlayer.stop();
////        vibrator.cancel();
////
////    }
////
////    @Nullable
////    @Override
////    public IBinder onBind(Intent intent) {
////        return null;
////    }
//}
