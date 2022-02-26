package com.example.taraziot;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    Button btnRefresh, b1, stop, vibrate;
    TextView smsNumberText;
    EditText text;
    private final int SMS_REQUEST_CODE = 100;
    String SMS_SENT = "SMS_SENT";
    String SMS_DELIVERED = "SMS_DELIVERED";
    private final long startTime = 50000;
    private final long interval = 1000;
    Intent servIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        smsNumberText = findViewById(R.id.smsNumberText);
        btnRefresh = findViewById(R.id.refresh);
        b1 = findViewById(R.id.button1);
        text = findViewById(R.id.timeText);
        stop = findViewById(R.id.stop);
        vibrate = findViewById(R.id.vibrate);
        servIntent = new Intent(this, MyService.class);
//        int time=Integer.parseInt(editText.getText().toString());
//        time=time*1000;
//        startTime = time;
//
//        // place this code on button listener if you want.
//        countDownTimer = new MyTimer(startTime, interval);

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
//                MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.star);
                MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.star);
                vibrator.cancel();
                mediaPlayer.stop();
//                startActivity(new Intent(MainActivity.this, ActivityTwo.class));
            }
        });


        vibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonVibrate(view);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startAlert(view);
//
//setAlarm();


            }
        });

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {

            requestReceiveSMSPermission();

        } else {

            Toast.makeText(this, "مجوز قبلا دریافت شده", Toast.LENGTH_SHORT).show();

        }

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

                    requestSendSMSpermission();

                } else {

                    sendMessage();

                }
            }
        });


    }
// End of onCreate

    public void buttonVibrate(View view){
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
        int i = 1;
        Intent intent = new Intent(this, SMSBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 23432, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (i * 1000), pendingIntent);
        Toast.makeText(this, "Alarm is set in " + i + " seconds.", Toast.LENGTH_SHORT).show();

    }


    private void sendMessage() {

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
            smsManager.sendTextMessage("+989359698705", null, " تست ارسال پیامک", sentSMS, deliverSMS);

            Toast.makeText(MainActivity.this, " ارسال پیامک آغاز شد", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {

            Toast.makeText(MainActivity.this, " پیامک ارسال نشد", Toast.LENGTH_SHORT).show();

        }

    }

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

            reqPermission2();

        }

    }

    private void reqPermission2() {

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, SMS_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == SMS_REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                sendMessage();

            } else {

                Toast.makeText(this, " مجوز رد شد", Toast.LENGTH_SHORT).show();

            }

        }

    }


//-*******************************************************************************************************************

    private void requestReceiveSMSPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.RECEIVE_SMS)) {

            new AlertDialog.Builder(this)
                    .setTitle("درخواست مجوز")
                    .setMessage("برای عملکرد صحیح برنامه باید دسترسی به دریافت پیامکتایید شود")
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
//                smsNumberText.setText(stringBuffer);
//            }
//            cursor.close();
//        } else {
//            final int REQUEST_CODE_ASK_PERMISSIONS = 123;
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
//        }
//    }