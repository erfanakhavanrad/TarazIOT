package com.example.taraziot;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class LoginPageActivity extends AppCompatActivity {
    Button btnEnter, btnRegister;
    TextInputEditText edtUsername, edtPassword;
    Boolean number = true;
    UserManagerSharedPrefs userManagerSharedPrefs;
    private String TAG;
    String SMS_SENT = "SMS_SENT";
    String SMS_DELIVERED = "SMS_DELIVERED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        btnEnter = findViewById(R.id.btnEnter);
        btnRegister = findViewById(R.id.btnRegister);

        edtPassword = findViewById(R.id.edtPassword);
        edtUsername = findViewById(R.id.edtUsername);
        userManagerSharedPrefs = new UserManagerSharedPrefs(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginPageActivity.this, ConfigServerActivity.class);
                startActivity(i);
            }
        });



        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String serverPhoneNumber = edtUsername.getText().toString();
                String pass = edtPassword.getText().toString();
                if (TextUtils.isEmpty(pass) || TextUtils.isEmpty(serverPhoneNumber)) {
                    Toast.makeText(LoginPageActivity.this, "لطفا تمام کادرها را پر کنید", Toast.LENGTH_SHORT).show();
                    return;
                }
//                else if (edtPassword.getText().toString() >)
                number = false;
                sendStartSMS(serverPhoneNumber);

            }
//                        if (response.isSuccessful()) {
//                            Users users = response.body();
//                            if (users != null) {
//                                token = users.getAccess_token();
//                                verifiedAt = users.getExpires_at();
//                                Log.d(TAG, "onResponse: " + token);
//                                userManagerSharedPrefs.saveUserInformation(edtUsername.getText().toString(),
//                                        edtPassword.getText().toString(),
//                                        token, verifiedAt);
//
//                                Intent i = new Intent(LoginPageActivity.this, MainActivity.class);
//                                startActivity(i);
//                                finish();
//                            } else if (response.code() == 404) {
//                                Toast.makeText(LoginPageActivity.this, "not found", Toast.LENGTH_SHORT).show();
//                            } else
//                                Toast.makeText(LoginPageActivity.this, "on response error", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<Users> call, Throwable t) {
//                        Toast.makeText(LoginPageActivity.this, "ابتدا باید ثبت نام کنید", Toast.LENGTH_SHORT).show();
//                        Log.i(TAG, "onFailure: " + t.getMessage());
//                        number = true;
////                        showLoading();
//                    }
//                });

            private void sendStartSMS(String serverPhoneNumber) {

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

                    PendingIntent sentSMS = PendingIntent.getBroadcast(LoginPageActivity.this, 0, new Intent(SMS_SENT), 0);
                    PendingIntent deliverSMS = PendingIntent.getBroadcast(LoginPageActivity.this, 0, new Intent(SMS_DELIVERED), 0);

                    SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage("+989127938973", null, "START", sentSMS, deliverSMS);
//            smsManager.sendTextMessage("+989359698705", null, "START", sentSMS, deliverSMS);
                    smsManager.sendTextMessage(serverPhoneNumber, null, "START", sentSMS, deliverSMS);

                    Toast.makeText(LoginPageActivity.this, " ارسال پیامک آغاز شد", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {

                    Toast.makeText(LoginPageActivity.this, " پیامک ارسال نشد", Toast.LENGTH_SHORT).show();

                }

            }

        });
    }
}