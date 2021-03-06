package com.example.taraziot;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.taraziot.receiver.SMSReceiverImpl;
import com.example.taraziot.service.AudioService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: 4/26/2022 1- Clean the code. 2- Turn WIFI on in all pages. 3- In the Wild test the application. 4- UI Change. 5- IP test for wifi 6- Thread change when sending data from sensor after server. 
// TODO: 5/24/22 1- Clean server and sensor message. 2- Loading 3- Help 4- Night mode 5- icon and splash 6- Remove one from alarm and remove extra tracks. 7- With VPN device is recognized as connected.

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    Button btnRefresh, b1, stop, vibrate, startService, stopService, stopSMS, startSMS, configServerBtn, statusRefreshButton, disableAlarmSoundButton,
            deleteInfoBtn, disableNotificationAlarmButton, onAndOffLight;
    public static TextView smsNumberText, statusTxt;
    public static int valueOfEnableNumber;
    public static Button disarmAndArmAlarmButton;
    LinearLayout firstLinear;
    EditText text;
    private final int SMS_REQUEST_CODE = 100;
    String SMS_SENT = "SMS_SENT";
    String SMS_DELIVERED = "SMS_DELIVERED";
    private final long startTime = 50000;
    private final long interval = 1000;
    Intent servIntent;
    UserManagerSharedPrefs userManagerSharedPrefs;
    String destinationAddress, statusFromServer;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    Boolean value = false;
//    SMSReceiverImpl smsReceiver = new SMSReceiverImpl();


//    BroadcastReceiver broadcastReceiver =  new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            Bundle b = intent.getExtras();
//
//           lasrrrrrrrr  = b.getString("message");
//
//            Log.e("newmesage", "" + lasrrrrrrrr);
//            Toast.makeText(context, lasrrrrrrrr, Toast.LENGTH_SHORT).show();
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstLinear = findViewById(R.id.firstLinear);
        statusRefreshButton = findViewById(R.id.statusRefreshButton);
        statusTxt = findViewById(R.id.statusTxt);
//        armedStatusTxt = findViewById(R.id.armedStatusTxt);
//        armAlarmButton = findViewById(R.id.armAlarmButton);
//        disarmAlarmButton = findViewById(R.id.disarmAlarmButton);
        disarmAndArmAlarmButton = findViewById(R.id.disarmAndArmAlarmButton);
        disableAlarmSoundButton = findViewById(R.id.disableAlarmSoundButton);
        disableNotificationAlarmButton = findViewById(R.id.disableNotificationAlarmButton);
        configServerBtn = findViewById(R.id.configServerBtn);
        deleteInfoBtn = findViewById(R.id.deleteInfoBtn);
        onAndOffLight = findViewById(R.id.onAndOffLight);


//destinationAddress = "9127938973";
//destinationAddress = "9944420283";
        checkAndRequestPermissions();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestSendSMSpermission();
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
            SMSReceiverImpl smsListener = new SMSReceiverImpl();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
            registerReceiver(smsListener, intentFilter);
        }
//reqPermission();
//reqPermission2();
//requestReceiveSMSPermission();

//        registerReceiver(smsReceiver, new IntentFilter("broadCastName"));

        // old views
//        smsNumberText = findViewById(R.id.smsNumberText);
//        btnRefresh = findViewById(R.id.refresh);
//        b1 = findViewById(R.id.button1);
//        text = findViewById(R.id.timeText);
//        stop = findViewById(R.id.stop);
//        startSMS = findViewById(R.id.startSMS);
//        stopSMS = findViewById(R.id.stopSMS);
//        startService = findViewById(R.id.start_btn);
//        stopService = findViewById(R.id.stop_btn);
//        vibrate = findViewById(R.id.vibrate);
//        servIntent = new Intent(this, MyService.class);
//        int time=Integer.parseInt(editText.getText().toString());
//        time=time*1000;
//        startTime = time;
//
//        // place this code on button listener if you want.
//        countDownTimer = new MyTimer(startTime, interval);

//        getSMSDetails();
//        configSharedP();
//        Toast.makeText(this, lasrrrrrrrr, Toast.LENGTH_SHORT).show();

//        Intent intent = new Intent();
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
//                        Toast.makeText(MainActivity.this, "??????????: " + smsFrom + " / ????????: " + smsBody, Toast.LENGTH_SHORT).show();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
        configSharedP();
        String modifiedDestinationAddress = destinationAddress.substring(1);
//        destinationAddress.substring(1,3);
//        destinationAddress = "awdwad";
//        Toast.makeText(this, modified, Toast.LENGTH_SHORT).show();

// hey
        onAndOffLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!value) {
                    sendLightOffSMS(modifiedDestinationAddress);
                } else if (value) {
                    sendLightOnSMS(modifiedDestinationAddress);

                }
            }
        });


        disableAlarmSoundButton.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    final Handler handler = new Handler();

                    final Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            disableAlarmSoundButton.setBackgroundColor(Color.parseColor("#009ed6"));
                        }
                    };
                    handler.postDelayed(runnable, 50);


                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    disableAlarmSoundButton.setBackgroundColor(Color.parseColor("#004760"));
                }
                return false;
            }

        });


        disableAlarmSoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDisableAlarmSoundSMS(modifiedDestinationAddress);
            }
        });


        disarmAndArmAlarmButton.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    final Handler handler = new Handler();

                    final Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            disarmAndArmAlarmButton.setBackgroundColor(Color.parseColor("#009ed6"));
                        }
                    };
                    handler.postDelayed(runnable, 50);


                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    disarmAndArmAlarmButton.setBackgroundColor(Color.parseColor("#004760"));
                }
                return false;
            }

        });


        disarmAndArmAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valueOfEnableNumber == 1) {
                    sendDisarmAlarmSMS(modifiedDestinationAddress);
                } else if (valueOfEnableNumber == 0) {
                    sendArmAlarmSMS(modifiedDestinationAddress);
                } else {
                    Toast.makeText(MainActivity.this, "?????????? ?????? ??????", Toast.LENGTH_SHORT).show();
                }
            }
        });
//
//        disarmAlarmButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sendDisarmAlarmSMS(modifiedDestinationAddress);
//            }
//        });
//
//
//        armAlarmButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sendArmAlarmSMS(modifiedDestinationAddress);
//            }
//        });

        firstLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRefreshStatusSMS(modifiedDestinationAddress);
                startTimer();
            }
        });

        statusRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                onShakeImage();
                sendRefreshStatusSMS(modifiedDestinationAddress);
                startTimer();
            }
        });

        deleteInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("???????? ???? ????????")
                        .setMessage("?????? ???????????????? ???? ???????? ???????????? ?????? ???????? ??????????")
                        .setPositiveButton("????????????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                userManagerSharedPrefs.clearAllInformation();
                                Intent intent = new Intent(MainActivity.this, PermissionsActivity.class);
                                startActivity(intent);
                                finish();

//                                System.exit(0);

                            }
                        })
                        .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.dismiss();

                            }
                        })
                        .create()
                        .show();
            }
        });

        configServerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChooseDeviceActivity.class);
                startActivity(intent);
            }
        });

// TODO: 3/15/22 here
//        startSMS.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
//
//                    requestSendSMSpermission();
//
//                } else {
//
//                    sendStartSMS();
//
//                }
//            }
//        });

// TODO: 3/15/22 here 
//        stopSMS.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
//
//                    requestSendSMSpermission();
//
//                } else {
//
//                    sendStopSMS();
//
//                }
//            }
//        });


        // TODO: 3/15/22 here 
//        startService.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                ContextCompat.startForegroundService(MainActivity.this, servIntent);
//                DataPref.saveServiceStatus(MainActivity.this, true);
//            }
//        });


        // TODO: 3/15/22 here
//        stopService.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                stopService(servIntent);
//                DataPref.saveServiceStatus(MainActivity.this, false);
//            }
//        });

// TODO: 3/15/22 here
//        stop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
////                MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.star);
//                MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.star);
//                vibrator.cancel();
//                mediaPlayer.stop();
////                startActivity(new Intent(MainActivity.this, ActivityTwo.class));
//            }
//        });
//
//
//        vibrate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                buttonVibrate(view);
//            }
//        });
//
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                startAlert(view);
////
////setAlarm();
//
//
//            }
//        });

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {

            requestReceiveSMSPermission();

        } else {
//here
//            Toast.makeText(this, "???????? ???????? ???????????? ??????", Toast.LENGTH_SHORT).show();

        }

//        btnRefresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
//
//                    requestSendSMSpermission();
//
//                } else {
//
//                    sendMessage();
//
//                }
//            }
//        });


//        final Handler handler = new Handler();
//
//        final Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                finish();
//                System.exit(0);
//            }
//        };
//        handler.postDelayed(runnable, 1000);

        disableNotificationAlarmButton.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    final Handler handler = new Handler();

                    final Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            disableNotificationAlarmButton.setBackgroundColor(Color.parseColor("#009ed6"));
                        }
                    };
                    handler.postDelayed(runnable, 50);


                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    disableNotificationAlarmButton.setBackgroundColor(Color.parseColor("#004760"));
                }
                return false;
            }

        });


        disableNotificationAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMyServiceRunning(AudioService.class)) {
                    stopService(new Intent(MainActivity.this, AudioService.class));
                    Toast.makeText(MainActivity.this, "?????????? ???????? ?????? ????", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(MainActivity.this, "???????????? ?????? ????????????", Toast.LENGTH_SHORT).show();

            }
        });


    }
// End of onCreate


    public void onShakeImage() {

        ObjectAnimator rotate = ObjectAnimator.ofFloat(disableAlarmSoundButton, "rotation", 0f, 1f, 0f, -1f, 0f); // rotate o degree then 20 degree and so on for one loop of rotation.
// animateView (View object)
        rotate.setRepeatCount(10); // repeat the loop 20 times
        rotate.setDuration(50); // animation play time 100 ms
        rotate.start();
    }


    CountDownTimer cTimer = null;

    //start timer function
    void startTimer() {
        cTimer = new CountDownTimer(20000, 1000) {
            public void onTick(long millisUntilFinished) {
                statusRefreshButton.setEnabled(false);
                firstLinear.setEnabled(false);
                statusRefreshButton.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                statusRefreshButton.setText("");
                firstLinear.setEnabled(true);
                statusRefreshButton.setEnabled(true);
            }
        };
        cTimer.start();
    }

    void startTimer2() {
        cTimer = new CountDownTimer(20000, 1000) {
            public void onTick(long millisUntilFinished) {
                if (isMyServiceRunning(SMSReceiverImpl.class)) {
                    cancelTimer();
                }
            }

            public void onFinish() {
//                Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                statusTxt.setText("?????????? ???????????? ??????. ?????????? ???????????? ???? ?????????? ????????");
            }
        };
        cTimer.start();
    }


    void startTimer3() {
        cTimer = new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
                onAndOffLight.setEnabled(false);
//                onAndOffLight.append("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
//                onAndOffLight.append("");
                onAndOffLight.setEnabled(true);
            }
        };
        cTimer.start();
    }


    void cancelTimer() {
        if (cTimer != null)
            cTimer.cancel();
    }


    public boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    private void configSharedP() {

        this.userManagerSharedPrefs = new UserManagerSharedPrefs(this);
//        token = userManagerSharedPrefs.getFullName();
//        verifiedAt = userManagerSharedPrefs.getVerifiedAt();
//        agreed = userManagerSharedPrefs.getAgreeToTerms();
        destinationAddress = userManagerSharedPrefs.getDestinationAddress(destinationAddress);
        statusFromServer = userManagerSharedPrefs.getStatusFromServer(statusFromServer);
    }

    public void buttonVibrate(View view) {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
//        textView.setText("Successful");
        Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
    }

    //    private void setAlarm() {
//        Intent intent = new Intent(this, MyService.class);
//        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 1, intent, 0);
//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
//        Toast.makeText(this, "Alarm will ring after every 15 minutes interval",Toast.LENGTH_LONG).show();
//    }
//
//
    public void startAlert(View view) {
//        int time_second = 2;
//        Intent intent = new Intent(this, MyBroadcastReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(
//                this.getApplicationContext(), 234324243, intent, 0);
//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
//                + (time_second * 1000), pendingIntent);
//        Toast.makeText(this, "Alarm set in " + time_second + " seconds", Toast.LENGTH_LONG).show();

//        int i = Integer.parseInt(text.getText().toString());
//        int i = 1;
//        Intent intent = new Intent(this, SMSBroadcastReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 23432, intent, 0);
//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (i * 1000), pendingIntent);
//        Toast.makeText(this, "Alarm is set in " + i + " seconds.", Toast.LENGTH_SHORT).show();

    }

    private void sendDisableAlarmSoundSMS(String destinationAddress) {

        try {

            //Broadcast for Sent SMS
            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    String state = "";
                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            state = "?????????? ?????????? ????";
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            state = "???? ???????? ?????????? ???? ??????";
                            break;
                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                            state = "?????????????? ???? ?????????? ????????";
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            state = " ???????????? PDU ???? ?????????? ????????";
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            state = "?????? ???????? ???? ?????????? ????????";
                            break;
                    }
                    Toast.makeText(context, state, Toast.LENGTH_SHORT).show();
                }
            }, new IntentFilter(SMS_SENT));

            //Broadcast for Delivered SMS
            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String state = "";
                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            state = "?????????? ?????????? ???????? ????";
                            break;
                        case Activity.RESULT_CANCELED:
                            state = "?????????? ?????????? ???????? ??????";
                            break;
                    }
                    Toast.makeText(context, state, Toast.LENGTH_SHORT).show();
                }
            }, new IntentFilter(SMS_DELIVERED));

            PendingIntent sentSMS = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
            PendingIntent deliverSMS = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);

            SmsManager smsManager = SmsManager.getDefault();
//            Toast.makeText(this, destinationAddress, Toast.LENGTH_SHORT).show();
            String newdestination = "+98" + destinationAddress;
            smsManager.sendTextMessage(newdestination, null, "AS42P", sentSMS, deliverSMS);
//            smsManager.sendTextMessage("+989359698705", null, "START", sentSMS, deliverSMS);

            Toast.makeText(MainActivity.this, " ?????????? ?????????? ???????? ????", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {

//            Toast.makeText(MainActivity.this, " ?????????? ?????????? ??????", Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

        }

    }


    private void sendLightOffSMS(String destinationAddress) {

        try {

            //Broadcast for Sent SMS
            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    String state = "";
                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            state = "?????????? ?????????? ????";
                            value = true;
                            onAndOffLight.setText("?????????????? ???????? ??????");
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            state = "???? ???????? ?????????? ???? ??????";
                            break;
                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                            state = "?????????????? ???? ?????????? ????????";
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            state = " ???????????? PDU ???? ?????????? ????????";
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            state = "?????? ???????? ???? ?????????? ????????";
                            break;
                    }
                    Toast.makeText(context, state, Toast.LENGTH_SHORT).show();
                }
            }, new IntentFilter(SMS_SENT));

            //Broadcast for Delivered SMS
            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String state = "";
                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            state = "?????????? ?????????? ???????? ????";
                            break;
                        case Activity.RESULT_CANCELED:
                            state = "?????????? ?????????? ???????? ??????";
                            break;
                    }
                    Toast.makeText(context, state, Toast.LENGTH_SHORT).show();
                }
            }, new IntentFilter(SMS_DELIVERED));

            PendingIntent sentSMS = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
            PendingIntent deliverSMS = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);

            SmsManager smsManager = SmsManager.getDefault();
//            Toast.makeText(this, destinationAddress, Toast.LENGTH_SHORT).show();
            String newdestination = "+98" + destinationAddress;
            smsManager.sendTextMessage(newdestination, null, "3W9T6", sentSMS, deliverSMS);
//            smsManager.sendTextMessage("+989359698705", null, "START", sentSMS, deliverSMS);

            Toast.makeText(MainActivity.this, " ?????????? ?????????? ???????? ????", Toast.LENGTH_SHORT).show();
            startTimer3();

        } catch (Exception e) {

//            Toast.makeText(MainActivity.this, " ?????????? ?????????? ??????", Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

        }

    }

    private void sendLightOnSMS(String destinationAddress) {

        try {

            //Broadcast for Sent SMS
            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    String state = "";
                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            state = "?????????? ?????????? ????";
                            value = false;
                            onAndOffLight.setText("???????? ???????? ??????");
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            state = "???? ???????? ?????????? ???? ??????";
                            break;
                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                            state = "?????????????? ???? ?????????? ????????";
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            state = " ???????????? PDU ???? ?????????? ????????";
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            state = "?????? ???????? ???? ?????????? ????????";
                            break;
                    }
                    Toast.makeText(context, state, Toast.LENGTH_SHORT).show();
                }
            }, new IntentFilter(SMS_SENT));

            //Broadcast for Delivered SMS
            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String state = "";
                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            state = "?????????? ?????????? ???????? ????";
                            break;
                        case Activity.RESULT_CANCELED:
                            state = "?????????? ?????????? ???????? ??????";
                            break;
                    }
                    Toast.makeText(context, state, Toast.LENGTH_SHORT).show();
                }
            }, new IntentFilter(SMS_DELIVERED));

            PendingIntent sentSMS = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
            PendingIntent deliverSMS = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);

            SmsManager smsManager = SmsManager.getDefault();
//            Toast.makeText(this, destinationAddress, Toast.LENGTH_SHORT).show();
            String newdestination = "+98" + destinationAddress;
            smsManager.sendTextMessage(newdestination, null, "6T9W3", sentSMS, deliverSMS);
//            smsManager.sendTextMessage("+989359698705", null, "START", sentSMS, deliverSMS);

            Toast.makeText(MainActivity.this, " ?????????? ?????????? ???????? ????", Toast.LENGTH_SHORT).show();
            startTimer3();

        } catch (Exception e) {

//            Toast.makeText(MainActivity.this, " ?????????? ?????????? ??????", Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

        }

    }


    private void sendDisarmAlarmSMS(String destinationAddress) {

        try {

            //Broadcast for Sent SMS
            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    String state = "";
                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            state = "?????????? ?????????? ????";
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            state = "???? ???????? ?????????? ???? ??????";
                            break;
                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                            state = "?????????????? ???? ?????????? ????????";
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            state = " ???????????? PDU ???? ?????????? ????????";
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            state = "?????? ???????? ???? ?????????? ????????";
                            break;
                    }
                    Toast.makeText(context, state, Toast.LENGTH_SHORT).show();
                }
            }, new IntentFilter(SMS_SENT));

            //Broadcast for Delivered SMS
            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String state = "";
                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            state = "?????????? ?????????? ???????? ????";
                            break;
                        case Activity.RESULT_CANCELED:
                            state = "?????????? ?????????? ???????? ??????";
                            break;
                    }
                    Toast.makeText(context, state, Toast.LENGTH_SHORT).show();
                }
            }, new IntentFilter(SMS_DELIVERED));

            PendingIntent sentSMS = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
            PendingIntent deliverSMS = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);

            SmsManager smsManager = SmsManager.getDefault();
//            Toast.makeText(this, destinationAddress, Toast.LENGTH_SHORT).show();
            String newdestination = "+98" + destinationAddress;
            smsManager.sendTextMessage(newdestination, null, "MQ43M", sentSMS, deliverSMS);
//            smsManager.sendTextMessage("+989359698705", null, "START", sentSMS, deliverSMS);

            Toast.makeText(MainActivity.this, " ?????????? ?????????? ???????? ????", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {

//            Toast.makeText(MainActivity.this, " ?????????? ?????????? ??????", Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

        }

    }

    private void sendArmAlarmSMS(String destinationAddress) {

        try {

            //Broadcast for Sent SMS
            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    String state = "";
                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            state = "?????????? ?????????? ????";
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            state = "???? ???????? ?????????? ???? ??????";
                            break;
                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                            state = "?????????????? ???? ?????????? ????????";
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            state = " ???????????? PDU ???? ?????????? ????????";
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            state = "?????? ???????? ???? ?????????? ????????";
                            break;
                    }
                    Toast.makeText(context, state, Toast.LENGTH_SHORT).show();
                }
            }, new IntentFilter(SMS_SENT));

            //Broadcast for Delivered SMS
            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String state = "";
                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            state = "?????????? ?????????? ???????? ????";
                            break;
                        case Activity.RESULT_CANCELED:
                            state = "?????????? ?????????? ???????? ??????";
                            break;
                    }
                    Toast.makeText(context, state, Toast.LENGTH_SHORT).show();
                }
            }, new IntentFilter(SMS_DELIVERED));

            PendingIntent sentSMS = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
            PendingIntent deliverSMS = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);

            SmsManager smsManager = SmsManager.getDefault();
//            Toast.makeText(this, destinationAddress, Toast.LENGTH_SHORT).show();
            String newdestination = "+98" + destinationAddress;
            smsManager.sendTextMessage(newdestination, null, "3QM4M", sentSMS, deliverSMS);
//            smsManager.sendTextMessage("+989359698705", null, "START", sentSMS, deliverSMS);

            Toast.makeText(MainActivity.this, " ?????????? ?????????? ???????? ????", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {

//            Toast.makeText(MainActivity.this, " ?????????? ?????????? ??????", Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

        }

    }

    private void sendRefreshStatusSMS(String destinationAddress) {

        try {

            //Broadcast for Sent SMS
            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    String state = "";
                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            state = "?????????? ?????????? ????";
                            startTimer2();
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            state = "???? ???????? ?????????? ???? ??????";
                            break;
                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                            state = "?????????????? ???? ?????????? ????????";
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            state = " ???????????? PDU ???? ?????????? ????????";
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            state = "?????? ???????? ???? ?????????? ????????";
                            break;
                    }
                    Toast.makeText(context, state, Toast.LENGTH_SHORT).show();
                }
            }, new IntentFilter(SMS_SENT));

            //Broadcast for Delivered SMS
            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String state = "";
                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            state = "?????????? ?????????? ???????? ????";

//                            *da * ad * awd * awd
                            break;
                        case Activity.RESULT_CANCELED:
                            state = "?????????? ?????????? ???????? ??????";
                            break;
                    }
                    Toast.makeText(context, state, Toast.LENGTH_SHORT).show();
                }
            }, new IntentFilter(SMS_DELIVERED));

            PendingIntent sentSMS = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
            PendingIntent deliverSMS = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);

            SmsManager smsManager = SmsManager.getDefault();
//            Toast.makeText(this, destinationAddress, Toast.LENGTH_SHORT).show();
            String newdestination = "+98" + destinationAddress;
            smsManager.sendTextMessage(newdestination, null, "I236E", sentSMS, deliverSMS);
//            smsManager.sendTextMessage("+989359698705", null, "START", sentSMS, deliverSMS);

            Toast.makeText(MainActivity.this, " ?????????? ?????????? ???????? ????", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {

//            Toast.makeText(MainActivity.this, " ?????????? ?????????? ??????", Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

        }

    }

//    private void sendStartSMS() {
//
//        try {
//
//            //Broadcast for Sent SMS
//            registerReceiver(new BroadcastReceiver() {
//                @Override
//                public void onReceive(Context context, Intent intent) {
//
//                    String state = "";
//                    switch (getResultCode()) {
//                        case Activity.RESULT_OK:
//                            state = "?????????? ?????????? ????";
//                            break;
//                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
//                            state = "???? ???????? ?????????? ???? ??????";
//                            break;
//                        case SmsManager.RESULT_ERROR_NO_SERVICE:
//                            state = "?????????????? ???? ?????????? ????????";
//                            break;
//                        case SmsManager.RESULT_ERROR_NULL_PDU:
//                            state = " ???????????? PDU ???? ?????????? ????????";
//                            break;
//                        case SmsManager.RESULT_ERROR_RADIO_OFF:
//                            state = "?????? ???????? ???? ?????????? ????????";
//                            break;
//                    }
//                    Toast.makeText(context, state, Toast.LENGTH_SHORT).show();
//                }
//            }, new IntentFilter(SMS_SENT));
//
//            //Broadcast for Delivered SMS
//            registerReceiver(new BroadcastReceiver() {
//                @Override
//                public void onReceive(Context context, Intent intent) {
//                    String state = "";
//                    switch (getResultCode()) {
//                        case Activity.RESULT_OK:
//                            state = "?????????? ?????????? ???????? ????";
//                            break;
//                        case Activity.RESULT_CANCELED:
//                            state = "?????????? ?????????? ???????? ??????";
//                            break;
//                    }
//                    Toast.makeText(context, state, Toast.LENGTH_SHORT).show();
//                }
//            }, new IntentFilter(SMS_DELIVERED));
//
//            PendingIntent sentSMS = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
//            PendingIntent deliverSMS = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);
//
//            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage("+989127938973", null, "START", sentSMS, deliverSMS);
////            smsManager.sendTextMessage("+989359698705", null, "START", sentSMS, deliverSMS);
//
//            Toast.makeText(MainActivity.this, " ?????????? ?????????? ???????? ????", Toast.LENGTH_SHORT).show();
//
//        } catch (Exception e) {
//
//            Toast.makeText(MainActivity.this, " ?????????? ?????????? ??????", Toast.LENGTH_SHORT).show();
//
//        }
//
//    }
//
//    private void sendStopSMS() {
//
//        try {
//
//            //Broadcast for Sent SMS
//            registerReceiver(new BroadcastReceiver() {
//                @Override
//                public void onReceive(Context context, Intent intent) {
//
//                    String state = "";
//                    switch (getResultCode()) {
//                        case Activity.RESULT_OK:
//                            state = "?????????? ?????????? ????";
//                            break;
//                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
//                            state = "???? ???????? ?????????? ???? ??????";
//                            break;
//                        case SmsManager.RESULT_ERROR_NO_SERVICE:
//                            state = "?????????????? ???? ?????????? ????????";
//                            break;
//                        case SmsManager.RESULT_ERROR_NULL_PDU:
//                            state = " ???????????? PDU ???? ?????????? ????????";
//                            break;
//                        case SmsManager.RESULT_ERROR_RADIO_OFF:
//                            state = "?????? ???????? ???? ?????????? ????????";
//                            break;
//                    }
//                    Toast.makeText(context, state, Toast.LENGTH_SHORT).show();
//                }
//            }, new IntentFilter(SMS_SENT));
//
//            //Broadcast for Delivered SMS
//            registerReceiver(new BroadcastReceiver() {
//                @Override
//                public void onReceive(Context context, Intent intent) {
//                    String state = "";
//                    switch (getResultCode()) {
//                        case Activity.RESULT_OK:
//                            state = "?????????? ?????????? ???????? ????";
//                            break;
//                        case Activity.RESULT_CANCELED:
//                            state = "?????????? ?????????? ???????? ??????";
//                            break;
//                    }
//                    Toast.makeText(context, state, Toast.LENGTH_SHORT).show();
//                }
//            }, new IntentFilter(SMS_DELIVERED));
//
//            PendingIntent sentSMS = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
//            PendingIntent deliverSMS = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);
//
//            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage("+989127938973", null, "STOP", sentSMS, deliverSMS);
////            smsManager.sendTextMessage("+989359698705", null, "STOP", sentSMS, deliverSMS);
//
//            Toast.makeText(MainActivity.this, " ?????????? ?????????? ???????? ????", Toast.LENGTH_SHORT).show();
//
//        } catch (Exception e) {
//
//            Toast.makeText(MainActivity.this, " ?????????? ?????????? ??????", Toast.LENGTH_SHORT).show();
//
//        }
//
//    }
//
//    private void sendMessage() {
//
//        try {
//
//            //Broadcast for Sent SMS
//            registerReceiver(new BroadcastReceiver() {
//                @Override
//                public void onReceive(Context context, Intent intent) {
//
//                    String state = "";
//                    switch (getResultCode()) {
//                        case Activity.RESULT_OK:
//                            state = "?????????? ?????????? ????";
//                            break;
//                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
//                            state = "???? ???????? ?????????? ???? ??????";
//                            break;
//                        case SmsManager.RESULT_ERROR_NO_SERVICE:
//                            state = "?????????????? ???? ?????????? ????????";
//                            break;
//                        case SmsManager.RESULT_ERROR_NULL_PDU:
//                            state = " ???????????? PDU ???? ?????????? ????????";
//                            break;
//                        case SmsManager.RESULT_ERROR_RADIO_OFF:
//                            state = "?????? ???????? ???? ?????????? ????????";
//                            break;
//                    }
//                    Toast.makeText(context, state, Toast.LENGTH_SHORT).show();
//                }
//            }, new IntentFilter(SMS_SENT));
//
//            //Broadcast for Delivered SMS
//            registerReceiver(new BroadcastReceiver() {
//                @Override
//                public void onReceive(Context context, Intent intent) {
//                    String state = "";
//                    switch (getResultCode()) {
//                        case Activity.RESULT_OK:
//                            state = "?????????? ?????????? ???????? ????";
//                            break;
//                        case Activity.RESULT_CANCELED:
//                            state = "?????????? ?????????? ???????? ??????";
//                            break;
//                    }
//                    Toast.makeText(context, state, Toast.LENGTH_SHORT).show();
//                }
//            }, new IntentFilter(SMS_DELIVERED));
//
//            PendingIntent sentSMS = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
//            PendingIntent deliverSMS = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);
//
//            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage("+989359698705", null, "Start", sentSMS, deliverSMS);
//
//            Toast.makeText(MainActivity.this, " ?????????? ?????????? ???????? ????", Toast.LENGTH_SHORT).show();
//
//        } catch (Exception e) {
//
//            Toast.makeText(MainActivity.this, " ?????????? ?????????? ??????", Toast.LENGTH_SHORT).show();
//
//        }
//
//    }

    private void requestSendSMSpermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.SEND_SMS)) {

            new AlertDialog.Builder(this)
                    .setTitle("?????????????? ????????")
                    .setMessage("???????? ???????????? ???????? ???????????? ???????? ???????????? ???? ?????????? ?????????? ?????????? ??????")
                    .setPositiveButton("????????????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            reqPermission2();

                        }
                    })
                    .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();

                        }
                    })
                    .create()
                    .show();

        } else {

            SMSReceiverImpl smsListener = new SMSReceiverImpl();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
            registerReceiver(smsListener, intentFilter);

        }

    }

    private void reqPermission2() {

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, SMS_REQUEST_CODE);

    }

    private boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);
        int receiveSMSPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        int readSMSPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (receiveSMSPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_SMS);
        }
        if (readSMSPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.SEND_SMS, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.RECEIVE_SMS, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_SMS, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "sms & location services permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)
                        ) {
//
                            new AlertDialog.Builder(this)
                                    .setTitle("?????????????? ????????")
                                    .setMessage("???????? ???????????? ???????? ???????????? ???????? ???????????? ???? ???????????? ?????????? ?????????? ??????")
                                    .setPositiveButton("????????????", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            checkAndRequestPermissions();

                                        }
                                    })
                                    .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            dialogInterface.dismiss();

                                        }
                                    })
                                    .create()
                                    .show();

//                            showDialogOK("SMS and Location Services Permission required for this app",
//                                    new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            switch (which) {
//                                                case DialogInterface.BUTTON_POSITIVE:
//                                                    checkAndRequestPermissions();
//                                                    break;
//                                                case DialogInterface.BUTTON_NEGATIVE:
//                                                    // proceed with logic by disabling the related features or quit the app.
//                                                    break;
//                                            }
//                                        }
//                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

//        if (requestCode == SMS_REQUEST_CODE) {
//
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
////                sendMessage();
//                SMSReceiverImpl smsListener = new SMSReceiverImpl();
//                IntentFilter intentFilter = new IntentFilter();
//                intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
//                registerReceiver(smsListener, intentFilter);
//
//            } else {
//
//                Toast.makeText(this, " ???????? ???? ????", Toast.LENGTH_SHORT).show();
//
//            }
//
//        }

    }


//-*******************************************************************************************************************

    private void requestReceiveSMSPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.RECEIVE_SMS)) {

            new AlertDialog.Builder(this)
                    .setTitle("?????????????? ????????")
                    .setMessage("???????? ???????????? ???????? ???????????? ???????? ???????????? ???? ???????????? ?????????? ?????????? ??????")
                    .setPositiveButton("????????????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            reqPermission();

                        }
                    })
                    .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();

                        }
                    })
                    .create()
                    .show();

        } else {

            reqPermission();

        }

    }

    private void reqPermission() {

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECEIVE_SMS}, SMS_REQUEST_CODE);

    }


    private void getSMSDetails() {
        if (ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("*********SMS History*************** :");
            Uri uri = Uri.parse("content://sms");
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor.moveToFirst()) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    String body = cursor.getString(cursor.getColumnIndexOrThrow("body"))
                            .toString();
                    String number = cursor.getString(cursor.getColumnIndexOrThrow("address"))
                            .toString();
                    String date = cursor.getString(cursor.getColumnIndexOrThrow("date"))
                            .toString();
                    Date smsDayTime = new Date(Long.valueOf(date));
                    String type = cursor.getString(cursor.getColumnIndexOrThrow("type"))
                            .toString();
                    String typeOfSMS = null;
                    switch (Integer.parseInt(type)) {
                        case 1:
                            typeOfSMS = "INBOX";
                            break;
                        case 2:
                            typeOfSMS = "SENT";
                            break;
                        case 3:
                            typeOfSMS = "DRAFT";
                            break;
                    }
                    stringBuffer.append("\nPhone Number:--- " + number + " \nMessage Type:--- "
                            + typeOfSMS + " \nMessage Date:--- " + smsDayTime
                            + " \nMessage Body:--- " + body);
                    stringBuffer.append("\n----------------------------------");
                    cursor.moveToNext();
                }
//                smsNumberText.setText(stringBuffer);
                Toast.makeText(this, stringBuffer, Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        } else {
            final int REQUEST_CODE_ASK_PERMISSIONS = 123;
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
        }
    }


}
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == SMS_REQUEST_CODE) {
//
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                Toast.makeText(this, "???????? ?????????? ????", Toast.LENGTH_SHORT).show();
//
//            } else {
//
//                Toast.makeText(this, "???????? ???? ????", Toast.LENGTH_SHORT).show();
//
//            }
//
//        }
//
//
//    }


//    }