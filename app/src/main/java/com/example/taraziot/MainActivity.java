package com.example.taraziot;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    Button btnRefresh;
    TextView smsNumberText;
    private final int SMS_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        smsNumberText = findViewById(R.id.smsNumberText);
        btnRefresh = findViewById(R.id.refresh);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {

            requestReceiveSMSpermission();

        } else {

            Toast.makeText(this, "مجوز قبلا دریافت شده", Toast.LENGTH_SHORT).show();

        }

    }

    private void requestReceiveSMSpermission() {

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SMS_REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "مجوز تایید شد", Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(this, "مجوز رد شد", Toast.LENGTH_SHORT).show();

            }

        }


        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getSMSDetails();
//                Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });


    }
}

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