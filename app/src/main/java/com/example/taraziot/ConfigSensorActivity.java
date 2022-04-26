package com.example.taraziot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@SuppressLint("SetTextI18n")
public class ConfigSensorActivity extends AppCompatActivity {
//    Thread Thread1 = null;
//    TextView sensorNameTxt, helpTxt;
//    Button wfiSettingBtn, confirmWifiBtn, sendBtn;
//    int SERVER_PORT;
//    String SERVER_IP, ssidName, mainMessage, deviceIpTrimmed, baseIp = "192.168.0.", ssid;
//    WifiManager wifiManager;
//    WifiInfo info;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_config_sensor);
//        sensorNameTxt = findViewById(R.id.sensorNameTxt);
//        helpTxt = findViewById(R.id.helpTxt);
//        wfiSettingBtn = findViewById(R.id.wfiSettingBtn);
//        confirmWifiBtn = findViewById(R.id.confirmWifiBtn);
//        sendBtn = findViewById(R.id.sendBtn);
////        Context context = ConfigSensorActivity.this.getApplicationContext(); //Get context
//        SERVER_PORT = 8888;
////        SERVER_IP = "192.168.0.100";
//        WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
//        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
//        int ip = wifiInfo.getIpAddress();
//        String ipAddress = Formatter.formatIpAddress(ip);
//        int index23 = ipAddress.indexOf(".");
//        deviceIpTrimmed = ipAddress.substring(index23 + 7);
//        SERVER_IP = baseIp + deviceIpTrimmed;
//        Toast.makeText(this, SERVER_IP, Toast.LENGTH_SHORT).show();
//
//        wfiSettingBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    Intent intent = new Intent(Intent.ACTION_MAIN, null);
//                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
//                    ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.wifi.WifiSettings");
//                    intent.setComponent(cn);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                } catch (ActivityNotFoundException ignored) {
//                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
//                }
//            }
//        });
//
//        confirmWifiBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
//                info = wifiManager.getConnectionInfo();
//                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(ConfigSensorActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION
//                    }, 0);
//                } else {
//                    ssidName = wifiManager.getConnectionInfo().getSSID(); // SSID Name
//
//                    info = wifiManager.getConnectionInfo();
//
//                    String bssid = info.getBSSID(); //Mac Address
//
//                    // Connected wifi ip
//                    WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
//                    WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
//                    int ip = wifiInfo.getIpAddress();
//                    String ipAddress = Formatter.formatIpAddress(ip);
//                    int index23 = ipAddress.indexOf(".");
//                    deviceIpTrimmed = ipAddress.substring(index23 + 7);
//                    SERVER_IP = baseIp + deviceIpTrimmed;
//                    Thread1 = new Thread(new Thread1());
//                    Thread1.start();
//                }
//            }
//        });
//
//sendBtn.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        mainMessage ="wadawdwd";
//        new Thread(new Thread3(mainMessage)).start();
//    }
//});
//
//
//    }
//
//
//
//    private PrintWriter output;
//    private BufferedReader input;
//
//    class Thread1 implements Runnable {
//        public void run() {
//            Socket socket;
//            try {
//                socket = new Socket(SERVER_IP, SERVER_PORT);
//                output = new PrintWriter(socket.getOutputStream());
//                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        helpTxt.setText("متصل به دستگاه\n");
////                        ssidName.setBackgroundColor(Color.parseColor("#E0FFEB"));
//                        helpTxt.setTextColor(Color.parseColor("#0EBF01"));
//                    }
//                });
//                new Thread(new Thread2()).start();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    class Thread2 implements Runnable {
//        @Override
//        public void run() {
//            while (true) {
//                try {
//                    final String message = input.readLine();
//                    if (message != null) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
////
//                                if (message.contains("68752")) {
////                                    userManagerSharedPrefs.registered(true);
////                                    userManagerSharedPrefs.destinationAddress(simCard);
////                                    Toast.makeText(ConfigServerActivity.this, "اطلاعات با موفقیت ذخیره شد.", Toast.LENGTH_LONG).show();
////                                    Toast.makeText(ConfigServerActivity.this, "پس از ۵ ثانیه اپلیکیشن بسته میشود.", Toast.LENGTH_LONG).show();
////                                    Intent intent = new Intent(ConfigServerActivity.this, ConfigSensorActivity.class);
////                                    startActivity(intent);
////                                    finish();
//                                    Toast.makeText(ConfigSensorActivity.this, "ok", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(ConfigSensorActivity.this, "not ok", Toast.LENGTH_SHORT).show();
//
//
//                                }
//                            }
//                        });
//                    } else {
//                        Thread1 = new Thread(new Thread1());
//                        Thread1.start();
//                        return;
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    class Thread3 implements Runnable {
//        private String message;
//
//        Thread3(String message) {
//            this.message = message;
//        }
//
//        @Override
//        public void run() {
//            output.write(message);
//            output.flush();
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    helpTxt.append("client: " + message + "\n");
////                    edtSimCard.setText("");
//                    System.out.println(message);
//                    // TODO: 3/9/22 Above line
//                }
//            });
//        }
//    }
//

}