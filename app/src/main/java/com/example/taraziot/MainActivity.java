package com.example.taraziot;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    Button btnRefresh, b1, stop, vibrate, startService, stopService, stopSMS, startSMS, configServerBtn, statusRefreshButton,
            armAlarmButton, disarmAlarmButton, disableAlarmSoundButton, deleteInfoBtn, disableNotificationAlarmButton;
    public static TextView smsNumberText, statusTxt, armedStatusTxt;
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
        statusRefreshButton = findViewById(R.id.statusRefreshButton);
        statusTxt = findViewById(R.id.statusTxt);
        armedStatusTxt = findViewById(R.id.armedStatusTxt);
        armAlarmButton = findViewById(R.id.armAlarmButton);
        disarmAlarmButton = findViewById(R.id.disarmAlarmButton);
        disableAlarmSoundButton = findViewById(R.id.disableAlarmSoundButton);
        disableNotificationAlarmButton = findViewById(R.id.disableNotificationAlarmButton);
        configServerBtn = findViewById(R.id.configServerBtn);
        deleteInfoBtn = findViewById(R.id.deleteInfoBtn);
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
//                        Toast.makeText(MainActivity.this, "شماره: " + smsFrom + " / پیام: " + smsBody, Toast.LENGTH_SHORT).show();
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

        disableAlarmSoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDisableAlarmSoundSMS(modifiedDestinationAddress);
            }
        });

        disarmAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDisarmAlarmSMS(modifiedDestinationAddress);
            }
        });


        armAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendArmAlarmSMS(modifiedDestinationAddress);
            }
        });


        statusRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 3/14/22 send sms


//                statusTxt.setText("hjjh");
                sendRefreshStatusSMS(modifiedDestinationAddress);
//                Intent intent = getIntent();
//                String message = intent.getStringExtra("message");
//                Toast.makeText(MainActivity.this, "MAin "+ message, Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this, destinationAddress, Toast.LENGTH_SHORT).show();

            }
        });

        deleteInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("خروج از حساب")
                        .setMessage("آیا میخواهید از حساب کاربری خود خارج شوید؟")
                        .setPositiveButton("موافقم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                userManagerSharedPrefs.clearAllInformation();
                                Intent intent = new Intent(MainActivity.this, PermissionsActivity.class);
                                startActivity(intent);
                                finish();

//                                System.exit(0);

                            }
                        })
                        .setNegativeButton("لغو", new DialogInterface.OnClickListener() {
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
//            Toast.makeText(this, "مجوز قبلا دریافت شده", Toast.LENGTH_SHORT).show();

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


        disableNotificationAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this,AudioService.class));
                Toast.makeText(MainActivity.this, "آلارم گوشی قطع شد", Toast.LENGTH_SHORT).show();
            }
        });


    }
// End of onCreate

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
                            state = "پیامک ارسال شد";
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            state = "یک خطای عمومی رخ داد";
                            break;
                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                            state = "اپراتور در دسترس نیست";
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            state = " پروتکل PDU در دسترس نیست";
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            state = "سیم کارت در دسترس نیست";
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
                            state = "پیامک تحویل داده شد";
                            break;
                        case Activity.RESULT_CANCELED:
                            state = "پیامک تحویل داده نشد";
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

            Toast.makeText(MainActivity.this, " ارسال پیامک آغاز شد", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {

//            Toast.makeText(MainActivity.this, " پیامک ارسال نشد", Toast.LENGTH_SHORT).show();
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
                            state = "پیامک ارسال شد";
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            state = "یک خطای عمومی رخ داد";
                            break;
                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                            state = "اپراتور در دسترس نیست";
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            state = " پروتکل PDU در دسترس نیست";
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            state = "سیم کارت در دسترس نیست";
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
                            state = "پیامک تحویل داده شد";
                            break;
                        case Activity.RESULT_CANCELED:
                            state = "پیامک تحویل داده نشد";
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

            Toast.makeText(MainActivity.this, " ارسال پیامک آغاز شد", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {

//            Toast.makeText(MainActivity.this, " پیامک ارسال نشد", Toast.LENGTH_SHORT).show();
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
                            state = "پیامک ارسال شد";
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            state = "یک خطای عمومی رخ داد";
                            break;
                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                            state = "اپراتور در دسترس نیست";
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            state = " پروتکل PDU در دسترس نیست";
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            state = "سیم کارت در دسترس نیست";
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
                            state = "پیامک تحویل داده شد";
                            break;
                        case Activity.RESULT_CANCELED:
                            state = "پیامک تحویل داده نشد";
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

            Toast.makeText(MainActivity.this, " ارسال پیامک آغاز شد", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {

//            Toast.makeText(MainActivity.this, " پیامک ارسال نشد", Toast.LENGTH_SHORT).show();
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
                            state = "پیامک ارسال شد";
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            state = "یک خطای عمومی رخ داد";
                            break;
                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                            state = "اپراتور در دسترس نیست";
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            state = " پروتکل PDU در دسترس نیست";
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            state = "سیم کارت در دسترس نیست";
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
                            state = "پیامک تحویل داده شد";
                            break;
                        case Activity.RESULT_CANCELED:
                            state = "پیامک تحویل داده نشد";
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

            Toast.makeText(MainActivity.this, " ارسال پیامک آغاز شد", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {

//            Toast.makeText(MainActivity.this, " پیامک ارسال نشد", Toast.LENGTH_SHORT).show();
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
//                            state = "پیامک ارسال شد";
//                            break;
//                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
//                            state = "یک خطای عمومی رخ داد";
//                            break;
//                        case SmsManager.RESULT_ERROR_NO_SERVICE:
//                            state = "اپراتور در دسترس نیست";
//                            break;
//                        case SmsManager.RESULT_ERROR_NULL_PDU:
//                            state = " پروتکل PDU در دسترس نیست";
//                            break;
//                        case SmsManager.RESULT_ERROR_RADIO_OFF:
//                            state = "سیم کارت در دسترس نیست";
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
//                            state = "پیامک تحویل داده شد";
//                            break;
//                        case Activity.RESULT_CANCELED:
//                            state = "پیامک تحویل داده نشد";
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
//            Toast.makeText(MainActivity.this, " ارسال پیامک آغاز شد", Toast.LENGTH_SHORT).show();
//
//        } catch (Exception e) {
//
//            Toast.makeText(MainActivity.this, " پیامک ارسال نشد", Toast.LENGTH_SHORT).show();
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
//                            state = "پیامک ارسال شد";
//                            break;
//                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
//                            state = "یک خطای عمومی رخ داد";
//                            break;
//                        case SmsManager.RESULT_ERROR_NO_SERVICE:
//                            state = "اپراتور در دسترس نیست";
//                            break;
//                        case SmsManager.RESULT_ERROR_NULL_PDU:
//                            state = " پروتکل PDU در دسترس نیست";
//                            break;
//                        case SmsManager.RESULT_ERROR_RADIO_OFF:
//                            state = "سیم کارت در دسترس نیست";
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
//                            state = "پیامک تحویل داده شد";
//                            break;
//                        case Activity.RESULT_CANCELED:
//                            state = "پیامک تحویل داده نشد";
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
//            Toast.makeText(MainActivity.this, " ارسال پیامک آغاز شد", Toast.LENGTH_SHORT).show();
//
//        } catch (Exception e) {
//
//            Toast.makeText(MainActivity.this, " پیامک ارسال نشد", Toast.LENGTH_SHORT).show();
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
//                            state = "پیامک ارسال شد";
//                            break;
//                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
//                            state = "یک خطای عمومی رخ داد";
//                            break;
//                        case SmsManager.RESULT_ERROR_NO_SERVICE:
//                            state = "اپراتور در دسترس نیست";
//                            break;
//                        case SmsManager.RESULT_ERROR_NULL_PDU:
//                            state = " پروتکل PDU در دسترس نیست";
//                            break;
//                        case SmsManager.RESULT_ERROR_RADIO_OFF:
//                            state = "سیم کارت در دسترس نیست";
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
//                            state = "پیامک تحویل داده شد";
//                            break;
//                        case Activity.RESULT_CANCELED:
//                            state = "پیامک تحویل داده نشد";
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
//            Toast.makeText(MainActivity.this, " ارسال پیامک آغاز شد", Toast.LENGTH_SHORT).show();
//
//        } catch (Exception e) {
//
//            Toast.makeText(MainActivity.this, " پیامک ارسال نشد", Toast.LENGTH_SHORT).show();
//
//        }
//
//    }

    private void requestSendSMSpermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.SEND_SMS)) {

            new AlertDialog.Builder(this)
                    .setTitle("درخواست مجوز")
                    .setMessage("برای عملکرد صحیح برنامه باید دسترسی به ارسال پیامک تایید شود")
                    .setPositiveButton("موافقم", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            reqPermission2();

                        }
                    })
                    .setNegativeButton("لغو", new DialogInterface.OnClickListener() {
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
                                    .setTitle("درخواست مجوز")
                                    .setMessage("برای عملکرد صحیح برنامه باید دسترسی به دریافت پیامک تایید شود")
                                    .setPositiveButton("موافقم", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            checkAndRequestPermissions();

                                        }
                                    })
                                    .setNegativeButton("لغو", new DialogInterface.OnClickListener() {
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
//                Toast.makeText(this, " مجوز رد شد", Toast.LENGTH_SHORT).show();
//
//            }
//
//        }

    }


//-*******************************************************************************************************************

    private void requestReceiveSMSPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.RECEIVE_SMS)) {

            new AlertDialog.Builder(this)
                    .setTitle("درخواست مجوز")
                    .setMessage("برای عملکرد صحیح برنامه باید دسترسی به دریافت پیامک تایید شود")
                    .setPositiveButton("موافقم", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            reqPermission();

                        }
                    })
                    .setNegativeButton("لغو", new DialogInterface.OnClickListener() {
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

//
//    private void getSMSDetails() {
//        if (ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {
//            StringBuffer stringBuffer = new StringBuffer();
//            stringBuffer.append("*********SMS History*************** :");
//            Uri uri = Uri.parse("content://sms");
//            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//            if (cursor.moveToFirst()) {
//                for (int i = 0; i < cursor.getCount(); i++) {
//                    String body = cursor.getString(cursor.getColumnIndexOrThrow("body"))
//                            .toString();
//                    String number = cursor.getString(cursor.getColumnIndexOrThrow("address"))
//                            .toString();
//                    String date = cursor.getString(cursor.getColumnIndexOrThrow("date"))
//                            .toString();
//                    Date smsDayTime = new Date(Long.valueOf(date));
//                    String type = cursor.getString(cursor.getColumnIndexOrThrow("type"))
//                            .toString();
//                    String typeOfSMS = null;
//                    switch (Integer.parseInt(type)) {
//                        case 1:
//                            typeOfSMS = "INBOX";
//                            break;
//                        case 2:
//                            typeOfSMS = "SENT";
//                            break;
//                        case 3:
//                            typeOfSMS = "DRAFT";
//                            break;
//                    }
//                    stringBuffer.append("\nPhone Number:--- " + number + " \nMessage Type:--- "
//                            + typeOfSMS + " \nMessage Date:--- " + smsDayTime
//                            + " \nMessage Body:--- " + body);
//                    stringBuffer.append("\n----------------------------------");
//                    cursor.moveToNext();
//                }
////                smsNumberText.setText(stringBuffer);
//                Toast.makeText(this, stringBuffer, Toast.LENGTH_SHORT).show();
//            }
//            cursor.close();
//        } else {
//            final int REQUEST_CODE_ASK_PERMISSIONS = 123;
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
//        }
//    }


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
//                Toast.makeText(this, "مجوز تایید شد", Toast.LENGTH_SHORT).show();
//
//            } else {
//
//                Toast.makeText(this, "مجوز رد شد", Toast.LENGTH_SHORT).show();
//
//            }
//
//        }
//
//
//    }


//    }