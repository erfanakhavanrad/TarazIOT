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

public class ConfigSensorActivity extends AppCompatActivity {
    TextView sensorNameTxt, helpTxt;
    Button wfiSettingBtn, confirmWifiBtn;
    Thread Thread5 = null;
    int SERVER_PORT;
    String SERVER_IP, ssidName, mainMessage, deviceIpTrimmed, baseIp = "192.168.0.";
    WifiManager wifiManager;
    List myWifiList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_sensor);
        sensorNameTxt = findViewById(R.id.sensorNameTxt);
        helpTxt = findViewById(R.id.helpTxt);
        wfiSettingBtn = findViewById(R.id.wfiSettingBtn);
        confirmWifiBtn = findViewById(R.id.confirmWifiBtn);
        Context context = ConfigSensorActivity.this.getApplicationContext();
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        final String[] macAddr = new String[1];

//        SERVER_IP = "192.168.0.100";
        SERVER_PORT = 8888;
        Thread5 = new Thread(new Thread5());
        Thread5.start();


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
                String result = "wadaw";
                int testip;
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ConfigSensorActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                    }, 0);

                } else {
                    ssidName = wifiManager.getConnectionInfo().getSSID();
//                    SERVER_IP = String.valueOf(wifiManager.getConnectionInfo().getIpAddress());
//                    Toast.makeText(context, SERVER_IP, Toast.LENGTH_SHORT).show();
                    String ssid = info.getSSID();

                }
//testip = wifiManager.getConnectionInfo().getIpAddress();
//                ssidName = String.valueOf(wifiInfo.getIpAddress());
//                String result = ssidName.charAt(0) + "." + x.charAt(1);
//                Toast.makeText(context, SERVER_IP, Toast.LENGTH_SHORT).show();
                WifiManager wifiMan1 = (WifiManager) context.getSystemService(
                        Context.WIFI_SERVICE);
                WifiInfo wifiInfo2 = wifiMan1.getConnectionInfo();

                String macAddr = wifiInfo2.getMacAddress();
                String bssid = wifiInfo2.getBSSID();


                // Connected wifi ip
                WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
                WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
                int ip = wifiInfo.getIpAddress();
                String ipAddress = Formatter.formatIpAddress(ip);
//                SERVER_IP = ipAddress;

//                String URL = "http://test.net/demo_form.asp?name1=stringTest";

//                int index = URL.indexOf("=");
//                String Result = URL.substring(index+1); //index+1 to skip =
//                int index23 = ipAddress.indexOf(".");
//                ipAddress = "192.168.0.0";
                int index23 = ipAddress.indexOf(".");
                deviceIpTrimmed = ipAddress.substring(index23 + 7);
                SERVER_IP = baseIp + deviceIpTrimmed;

                Toast.makeText(ConfigSensorActivity.this, ipAddress, Toast.LENGTH_SHORT).show();
                Toast.makeText(ConfigSensorActivity.this, SERVER_IP, Toast.LENGTH_SHORT).show();
                new Thread(new Thread7(mainMessage)).start();
            }
        });

    }


    private PrintWriter output;
    private BufferedReader input;

    class Thread5 implements Runnable {
        public void run() {
            Socket socket;
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                output = new PrintWriter(socket.getOutputStream());
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sensorNameTxt.setText("متصل به دستگاه\n");
//                        btnConfig.setEnabled(true);
//                        ssidName.setBackgroundColor(Color.parseColor("#E0FFEB"));
//                        ssidName.setTextColor(Color.parseColor("#0EBF01"));
                    }
                });
                new Thread(new Thread6()).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Thread6 implements Runnable {
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
                        Thread5 = new Thread(new ConfigSensorActivity.Thread5());
                        Thread5.start();
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Thread7 implements Runnable {
        private String message;

        Thread7(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            output.write(message);
            output.flush();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    sensorNameTxt.append("client: " + message + "\n");
//                    edtSimCard.setText("");
                    helpTxt.setText("changed now");
//                    System.out.println(message);
                    // TODO: 3/9/22 Above line
                }
            });
        }

    }
}