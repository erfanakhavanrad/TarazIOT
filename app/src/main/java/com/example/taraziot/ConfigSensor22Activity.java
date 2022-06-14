package com.example.taraziot;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
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
    //    EditText etIP, etPort;
    TextView ssidName;
    //    EditText serveredt;
    //    edtThirdUserPhoneNumber, edtFourthUserPhoneNumber;
    Button sendShit, wfiSettingBtn;
    String SERVER_IP, mainMessage,
            simCard, serialNumber, adminPhoneNumber, password, confirmPassword, firstUserPhoneNumber, secondUserPhoneNumber,
            thirdUserPhoneNumber, fourthUserPhoneNumber, baseIp = "192.168.0.", deviceIpTrimmed, ssidNameTrimmed, currentWifiName;


    UserManagerSharedPrefs userManagerSharedPrefs;
    int SERVER_PORT;
    Socket socket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_sensor22);

        sendShit = findViewById(R.id.sendShit);
        ssidName = findViewById(R.id.ssidName);
        wfiSettingBtn = findViewById(R.id.wfiSettingBtn);
        userManagerSharedPrefs = new UserManagerSharedPrefs(this);
        Context context = ConfigSensor22Activity.this.getApplicationContext();
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
//        WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
//        Toast.makeText(context, "first " + ip, Toast.LENGTH_SHORT).show();
// cut the last octet from ip (if you want to keep the . at the end, add 1 to the second parameter
        String firstThreeOctets = ip.substring(0, ip.lastIndexOf(".")); // 192.168.1

//        String lastOctet = ip.substring(ip.lastIndexOf(".") + 1); // 97
//        Toast.makeText(context, "Second " + lastOctet, Toast.LENGTH_SHORT).show();
        String newIp = firstThreeOctets + ".1"; // 192.168.1.100
//        Toast.makeText(context, "Third " + newIp, Toast.LENGTH_SHORT).show();
//        Toast.makeText(context, "ip: " + ip, Toast.LENGTH_SHORT).show();
//        SERVER_IP = "192.168.43.111";
//        SERVER_IP = "192.168.43.111";

//                SERVER_PORT = Integer.parseInt(etPort.getText().toString().trim());
//        SERVER_IP = newIp;
//        SERVER_IP = "192.168.0.100";

        WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
        currentWifiName = wifiMgr.getConnectionInfo().getSSID(); // SSID Name

        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip3 = wifiInfo.getIpAddress();
//        String ipAddress = Formatter.formatIpAddress(ip3);
        String ipAddress = "192.168.133.1";
        int index23 = ipAddress.indexOf(".");
//        deviceIpTrimmed = ipAddress.substring(index23 + 7);
//        SERVER_IP = baseIp + deviceIpTrimmed;
//        SERVER_IP = baseIp + deviceIpTrimmed;

        SERVER_IP = "192.168.133.1";
//        SERVER_IP =
//        SERVER_IP = serveredt.getText().toString().trim();
//        Toast.makeText(context, "in", Toast.LENGTH_SHORT).show();
        SERVER_PORT = 8888;
        Thread1 = new Thread(new ConfigSensor22Activity.Thread1());
        Thread1.start();

//        String testValue = String.valueOf(ipAddress);
//        btntest2222.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, testValue, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        btnTest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ConfigSensor22Activity.this, ConfigSensorActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

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

        sendShit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               ssidNameTrimmed = userManagerSharedPrefs.getSsidNameForSave().substring(1);
                ssidNameTrimmed = userManagerSharedPrefs.getSsidNameForSave();
//               ssidNameTrimmed = "SRV3658";
//                if (ssidNameTrimmed.charAt(ssidNameTrimmed.length() - 1) == 'x') {
//                }

//                ssidNameTrimmed = ssidNameTrimmed.substring(0, ssidNameTrimmed.length() - 1);

//                Toast.makeText(context, ssidNameTrimmed, Toast.LENGTH_SHORT).show();

//ssidNameTrimmed = "YAS2";
                String sensorNameConfirmation = currentWifiName;
                String serverNameConfirmation = ssidNameTrimmed;
                new AlertDialog.Builder(ConfigSensor22Activity.this)
                        .setTitle("تایید نهایی")
                        .setMessage(" \nاطلاعات سرور " + serverNameConfirmation + " به سنسور " + sensorNameConfirmation + " ارسال شود؟")
                        .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

//                                userManagerSharedPrefs.clearAllInformation();
//                                Intent intent = new Intent(MainActivity.this, ConfigServerActivity.class);
//                                startActivity(intent);
//                                finish();

//                                System.exit(0);
                                mainMessage = "1:" + ssidNameTrimmed + ",2:" + userManagerSharedPrefs.getServerPassword();
                                try {
                                    new Thread(new Thread3(mainMessage)).start();
                                } catch (Exception e) {
                                    System.out.println(e.toString());
                                    Toast.makeText(context, "مشکلی پیش آمد. یا هنوز به سرور متصل هستید و یا به دستگاه اشتباهی وصل شده اید.", Toast.LENGTH_LONG).show();
                                }

                            }
                        })
                        .setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.dismiss();

                            }
                        })
                        .create()
                        .show();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        userManagerSharedPrefs = new UserManagerSharedPrefs(this);
        Context context = ConfigSensor22Activity.this.getApplicationContext();
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
        currentWifiName = wifiMgr.getConnectionInfo().getSSID(); // SSID Name

//        String ipAddress = "192.168.133.1";
//        int index23 = ipAddress.indexOf(".");
//        deviceIpTrimmed = ipAddress.substring(index23 + 7);
//        SERVER_IP = baseIp + deviceIpTrimmed;
        SERVER_IP = "192.168.133.1";
        SERVER_PORT = 8888;

        if (!Thread1.isAlive()) {
            Thread Thread1 = null;
            Thread1 = new Thread(new Thread1());
            Thread1.start();
        } else{
//            Toast.makeText(context, "FUCK OFF", Toast.LENGTH_SHORT).show();
            System.out.println("Thread was already awake.");
        }

    }


    private PrintWriter output;
    private BufferedReader input;

    class Thread1 implements Runnable {
        public void run() {

            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                output = new PrintWriter(socket.getOutputStream());
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        ssidName.setText("متصل به \n"+ currentWifiName);
                        ssidName.setText("متصل به " + currentWifiName);
                        sendShit.setEnabled(true);
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

//                    new CountDownTimer(5000, 1000) {
//                        @Override
//                        public void onTick(long l) {
////                                        Toast.makeText(ConfigServerActivity.this, "now", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onFinish() {
//                            if (message == null) {
//                                Toast.makeText(ConfigSensor22Activity.this, "هیچ پاسخی از سنسور دریافت نشد. لطفا مجددا تلاش کنید.", Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    }.start();

                    if (message != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//
                                if (message.contains("95257")) {
//                                    userManagerSharedPrefs.registered(true);
//                                    userManagerSharedPrefs.destinationAddress(simCard);
                                    Toast.makeText(ConfigSensor22Activity.this, "اطلاعات با موفقیت ذخیره شد.", Toast.LENGTH_LONG).show();
                                    userManagerSharedPrefs.registeredSensor(true);
//                                    Toast.makeText(ConfigSensor22Activity.this, "پس از ۵ ثانیه اپلیکیشن بسته میشود.", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(ConfigSensor22Activity.this, MainActivity.class);
                                    startActivity(intent);
                                    try {
                                        socket.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    finish();

                                } else {
                                    Toast.makeText(ConfigSensor22Activity.this, "مشکلی پیش آمد. لطفا مجددا تلاش کنید.", Toast.LENGTH_LONG).show();
                                    userManagerSharedPrefs.registeredSensor(false);

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