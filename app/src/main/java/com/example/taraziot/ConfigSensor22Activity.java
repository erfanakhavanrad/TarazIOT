package com.example.taraziot;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@SuppressLint("SetTextI18n")
public class ConfigSensor22Activity extends AppCompatActivity {

    Thread Thread1 = null;
    TextView sensorNameTxt, helpTxt, ssidName;
    Button wfiSettingBtn, confirmWifiBtn, sendBtn;
    int SERVER_PORT = 8888;
    String SERVER_IP, ssidName1, mainMessage, deviceIpTrimmed, baseIp = "192.168.0.", ssid;
    WifiManager wifiManager;
    WifiInfo info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_sensor22);
        sensorNameTxt = findViewById(R.id.sensorNameTxt);
        helpTxt = findViewById(R.id.helpTxt);
        wfiSettingBtn = findViewById(R.id.wfiSettingBtn);
        confirmWifiBtn = findViewById(R.id.confirmWifiBtn);
        sendBtn = findViewById(R.id.btnConfig);
        Context context = ConfigSensor22Activity.this.getApplicationContext();
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        String firstThreeOctets = ip.substring(0, ip.lastIndexOf(".")); // 192.168.1

        WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int wifIp = wifiInfo.getIpAddress();
        String ipAddress = Formatter.formatIpAddress(wifIp);
        int index23 = ipAddress.indexOf(".");
        deviceIpTrimmed = ipAddress.substring(index23 + 7);
        SERVER_IP = baseIp + deviceIpTrimmed;
        Toast.makeText(context, wifIp, Toast.LENGTH_SHORT).show();
        Toast.makeText(context, SERVER_IP, Toast.LENGTH_SHORT).show();
        Thread1 = new Thread(new ConfigSensor22Activity.Thread1());
        Thread1.start();


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainMessage = "One ring to rule them all.";
                new Thread(new ConfigSensor22Activity.Thread3(mainMessage)).start();
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
                        ssidName.setText("متصل به دستگاه\n");
//                        btnConfig.setEnabled(true);
//                        ssidName.setBackgroundColor(Color.parseColor("#E0FFEB"));
                        ssidName.setTextColor(Color.parseColor("#0EBF01"));
                    }
                });
                new Thread(new ConfigSensor22Activity.Thread2()).start();
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
                                    Toast.makeText(ConfigSensor22Activity.this, "اطلاعات با موفقیت ذخیره شد.", Toast.LENGTH_LONG).show();
                                    Toast.makeText(ConfigSensor22Activity.this, "پس از ۵ ثانیه اپلیکیشن بسته میشود.", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(ConfigSensor22Activity.this, ConfigSensorActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(ConfigSensor22Activity.this, "مشکلی پیش آمد. لطفا مجددا تلاش کنید.", Toast.LENGTH_LONG).show();
//                                    userManagerSharedPrefs.registered(false);


                                }
                            }
                        });
                    } else {
                        Thread1 = new Thread(new ConfigSensor22Activity.Thread1());
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
                    ssidName.append("client: " + message + "\n");
//                    edtSimCard.setText("");
                    System.out.println(message);
                    // TODO: 3/9/22 Above line
                }
            });
        }
    }


}