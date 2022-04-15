package com.example.taraziot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ConfigSensorActivity extends AppCompatActivity {
    TextView sensorNameTxt;
    Button wfiSettingBtn, confirmWifiBtn;
    Thread Thread1 = null;
    int SERVER_PORT;
    String SERVER_IP, ssidName, mainMessage;
    WifiManager wifiManager;
    List myWifiList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_sensor);
        sensorNameTxt = findViewById(R.id.sensorNameTxt);
        wfiSettingBtn = findViewById(R.id.wfiSettingBtn);
        confirmWifiBtn = findViewById(R.id.confirmWifiBtn);
        Context context = ConfigSensorActivity.this.getApplicationContext();
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        final String[] macAddr = new String[1];

//        SERVER_IP = "192.168.0.100";
        SERVER_PORT = 8888;
        Thread1 = new Thread(new Thread1());
        Thread1.start();


        wfiSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_MAIN, null);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                    intent.setComponent(cn);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (ActivityNotFoundException ignored) {
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                }
            }
        });

        confirmWifiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = wifiManager.getConnectionInfo();

                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ConfigSensorActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                    }, 0);

                } else {
                    ssidName = wifiManager.getConnectionInfo().getSSID();
                    SERVER_IP = String.valueOf(wifiManager.getConnectionInfo().getIpAddress());
                    String ssid = info.getSSID();

                }

//                ssidName = String.valueOf(wifiInfo.getIpAddress());
                Toast.makeText(context, SERVER_IP, Toast.LENGTH_SHORT).show();
//                new Thread(new Thread3(mainMessage)).start();
            }
        });


    }


    private PrintWriter output;
    private BufferedReader input;

    class Thread1 implements Runnable {
        public void run() {
            Socket socket;
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                output = new PrintWriter(socket.getOutputStream());
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        ssidName.setText("متصل به دستگاه\n");
//                        btnConfig.setEnabled(true);
//                        ssidName.setBackgroundColor(Color.parseColor("#E0FFEB"));
//                        ssidName.setTextColor(Color.parseColor("#0EBF01"));
                    }
                });
                new Thread(new ConfigSensorActivity.Thread2()).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Thread2 implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    final String message = input.readLine();
                    if (message != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//
                                if (message.contains("68752")) {
//                                    userManagerSharedPrefs.registered(true);
//                                    userManagerSharedPrefs.destinationAddress(simCard);
//                                    Toast.makeText(ConfigServerActivity.this, "اطلاعات با موفقیت ذخیره شد.", Toast.LENGTH_LONG).show();
//                                    Toast.makeText(ConfigServerActivity.this, "پس از ۵ ثانیه اپلیکیشن بسته میشود.", Toast.LENGTH_LONG).show();
//                                    Intent intent = new Intent(ConfigServerActivity.this, ConfigSensorActivity.class);
//                                    startActivity(intent);
//                                    finish();
                                } else {
//                                    Toast.makeText(ConfigServerActivity.this, "مشکلی پیش آمد. لطفا مجددا تلاش کنید.", Toast.LENGTH_LONG).show();
//                                    userManagerSharedPrefs.registered(false);


                                }
                            }
                        });
                    } else {
                        Thread1 = new Thread(new ConfigSensorActivity.Thread1());
                        Thread1.start();
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Thread3 implements Runnable {
        private String message;

        Thread3(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            output.write(message);
            output.flush();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    ssidName.append("client: " + message + "\n");
//                    edtSimCard.setText("");
                    System.out.println(message);
                    // TODO: 3/9/22 Above line
                }
            });
        }

    }
}