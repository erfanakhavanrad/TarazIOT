package com.example.taraziot;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@SuppressLint("SetTextI18n")
public class ConfigServerActivity extends AppCompatActivity {


    //    EditText etIP, etPort;
    TextView ssidName, slogan_name;
    EditText serveredt;
    TextInputEditText edtSimCard, edtSerialNumber, edtAdminPhoneNumber, edtPassword, edtConfirmPassword,
            edtFirstUserPhoneNumber, edtSecondUserPhoneNumber;
    //    edtThirdUserPhoneNumber, edtFourthUserPhoneNumber;
    Button btnConfig;
    //    btnmacc, btntest2222;
    String SERVER_IP, mainMessage,
            simCard, serialNumber, adminPhoneNumber, password, confirmPassword, firstUserPhoneNumber, secondUserPhoneNumber;

    UserManagerSharedPrefs userManagerSharedPrefs;
    int SERVER_PORT;
    Socket socket;
    Thread Thread1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_server);
        ssidName = findViewById(R.id.ssidName);
        edtSimCard = findViewById(R.id.edtSimCard);
        edtAdminPhoneNumber = findViewById(R.id.edtAdminPhoneNumber);
        edtPassword = findViewById(R.id.edtPassword);
        slogan_name = findViewById(R.id.slogan_name);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        edtFirstUserPhoneNumber = findViewById(R.id.edtFirstUserPhoneNumber);
        edtSecondUserPhoneNumber = findViewById(R.id.edtSecondUserPhoneNumber);
        btnConfig = findViewById(R.id.btnConfig);

        userManagerSharedPrefs = new UserManagerSharedPrefs(this);
        Context context = ConfigServerActivity.this.getApplicationContext();
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        SERVER_IP = "192.168.133.100";

        SERVER_PORT = 8888;

        Thread1 = new Thread(new Thread1());
        Thread1.start();

        edtSimCard.setText("09368810941");
        edtAdminPhoneNumber.setText("09127938973");
        edtPassword.setText("12345678");
        edtConfirmPassword.setText("12345678");
        edtFirstUserPhoneNumber.setText("09192503314");
        edtSecondUserPhoneNumber.setText("09192503314");


        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simCard = edtSimCard.getText().toString().trim();
//                 serialNumber = edtSerialNumber.getText().toString().trim();
                adminPhoneNumber = edtAdminPhoneNumber.getText().toString().trim();
                password = edtPassword.getText().toString().trim();
                confirmPassword = edtConfirmPassword.getText().toString().trim();
                firstUserPhoneNumber = edtFirstUserPhoneNumber.getText().toString().trim();
                secondUserPhoneNumber = edtSecondUserPhoneNumber.getText().toString().trim();


                String[] values = {simCard, adminPhoneNumber, password, confirmPassword};

                Boolean valid = true;
//                int validNumber = 1;
                for (int i = 0; i < values.length; i++) {
                    if (TextUtils.isEmpty(values[i])) {
                        valid = false;
//                        validNumber = 0;
                        break;
                    }
//                    System.out.println(valid);
                }


                Boolean passwordValid = false;
                //start
                if (valid) {
                    if (password.equals(confirmPassword)) {
                        passwordValid = true;

                    } else {
                        passwordValid = false;
                    }
                    if (passwordValid && valid) {
                        mainMessage = "1:" + simCard + ",2:" + adminPhoneNumber + ",3:" + firstUserPhoneNumber + ",4:" + secondUserPhoneNumber + ",5:" + password;
                        mainMessage = mainMessage.replaceAll("\\s+", "");
                        mainMessage = mainMessage.trim();
                        mainMessage = mainMessage + System.lineSeparator();
                        userManagerSharedPrefs.saveServerPassword(password);
                        new Thread(new Thread3(mainMessage)).start();
                    } else {
                        Toast.makeText(context, "مقادیر رمز یکسان نیستند", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(ConfigServerActivity.this, "فیلدهای اجباری را پر کنید", Toast.LENGTH_SHORT).show();

                    if (TextUtils.isEmpty(edtSimCard.getText())) {
                        edtSimCard.setError("اجباری");
                    }
                    if (TextUtils.isEmpty(edtAdminPhoneNumber.getText())) {
                        edtAdminPhoneNumber.setError("اجباری");
                    }
                    if (TextUtils.isEmpty(edtPassword.getText())) {
                        edtPassword.setError("اجباری");
                    }

                    if (TextUtils.isEmpty(edtConfirmPassword.getText())) {
                        edtConfirmPassword.setError("اجباری");
                    }

                }
                //end
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();


        Context context = ConfigServerActivity.this.getApplicationContext();
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        SERVER_IP = "192.168.133.100";
        SERVER_PORT = 8888;
        if (!Thread1.isAlive()) {
            Thread Thread1 = null;
        Thread1 = new Thread(new Thread1());
        Thread1.start();
        } else{
//            Toast.makeText(context, "FUCK OFF", Toast.LENGTH_SHORT).show();
            System.out.println("Thread was already awake.");
        }
//        Thread Thread1 = null;
//        Thread1 = new Thread(new Thread1());
//        Thread1.start();

//        09127938973
//        09359698705
        edtSimCard.setText("09368810941");
        edtAdminPhoneNumber.setText("09127938973");
        edtPassword.setText("12345678");
        edtConfirmPassword.setText("12345678");
        edtFirstUserPhoneNumber.setText("09192503314");
        edtSecondUserPhoneNumber.setText("09192503314");

        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simCard = edtSimCard.getText().toString().trim();
//                 serialNumber = edtSerialNumber.getText().toString().trim();
                adminPhoneNumber = edtAdminPhoneNumber.getText().toString().trim();
                password = edtPassword.getText().toString().trim();
                confirmPassword = edtConfirmPassword.getText().toString().trim();
                firstUserPhoneNumber = edtFirstUserPhoneNumber.getText().toString().trim();
                secondUserPhoneNumber = edtSecondUserPhoneNumber.getText().toString().trim();

                String[] values = {simCard, adminPhoneNumber, password, confirmPassword};

                Boolean valid = true;
//                int validNumber = 1;
                for (int i = 0; i < values.length; i++) {
                    if (TextUtils.isEmpty(values[i])) {
                        valid = false;
//                        validNumber = 0;
                        break;
                    }
//                    System.out.println(valid);
                }


                Boolean passwordValid = false;
                //start
                if (valid) {
                    if (password.equals(confirmPassword)) {
                        passwordValid = true;

                    } else {
                        passwordValid = false;
                    }
                    if (passwordValid && valid) {
                        mainMessage = "1:" + simCard + ",2:" + adminPhoneNumber + ",3:" + firstUserPhoneNumber + ",4:" + secondUserPhoneNumber + ",5:" + password;
                        mainMessage = mainMessage.replaceAll("\\s+", "");
                        mainMessage = mainMessage.trim();
                        mainMessage = mainMessage + System.lineSeparator();
                        userManagerSharedPrefs.saveServerPassword(password);
                        new Thread(new Thread3(mainMessage)).start();
                    } else {
                        Toast.makeText(context, "مقادیر رمز یکسان نیستند", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(ConfigServerActivity.this, "فیلدهای اجباری را پر کنید", Toast.LENGTH_SHORT).show();

                    if (TextUtils.isEmpty(edtSimCard.getText())) {
                        edtSimCard.setError("اجباری");
                    }
                    if (TextUtils.isEmpty(edtAdminPhoneNumber.getText())) {
                        edtAdminPhoneNumber.setError("اجباری");
                    }
                    if (TextUtils.isEmpty(edtPassword.getText())) {
                        edtPassword.setError("اجباری");
                    }

                    if (TextUtils.isEmpty(edtConfirmPassword.getText())) {
                        edtConfirmPassword.setError("اجباری");
                    }

                }
                //end
            }
        });


    }

    private PrintWriter output1;
    private BufferedReader input;

    class Thread1 implements Runnable {

        public void run() {

            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                output1 = new PrintWriter(socket.getOutputStream());
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ssidName.setText("متصل به دستگاه\n");
                        btnConfig.setEnabled(true);
//                        ssidName.setBackgroundColor(Color.parseColor("#E0FFEB"));
                        ssidName.setTextColor(Color.parseColor("#0EBF01"));

                    }
                });
                new Thread(new Thread2()).start();
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

                                if (message.contains("68752")) {
                                    WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
                                    String ssidNameForPrint = wifiMgr.getConnectionInfo().getSSID(); // SSID Name
                                    userManagerSharedPrefs.saveSSIDName(ssidNameForPrint.trim());
                                    userManagerSharedPrefs.registered(true);
                                    userManagerSharedPrefs.destinationAddress(simCard);
                                    Toast.makeText(ConfigServerActivity.this, "سرور با موفقیت تنظیم شد.", Toast.LENGTH_LONG).show();

//                                    final Handler handler = new Handler();
                                    Intent intent = new Intent(ConfigServerActivity.this, ConfigSensor22Activity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(ConfigServerActivity.this, "مشکلی پیش آمد. لطفا مجددا تلاش کنید.", Toast.LENGTH_LONG).show();
                                    userManagerSharedPrefs.registered(false);


                                }
                            }
                        });
                    } else {
                        Thread Thread1 = null;
                        Thread1 = new Thread(new Thread1());
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
//            output1.write(message);
            output1.println(message);
            output1.flush();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ssidName.append("tell me if you are the one : " + message + "\n");
//                    edtSimCard.setText("");
                    System.out.println(message);
//                    Thread Thread1 = null;
//                    Thread1 = new Thread(new Thread1());
//                    Thread1.interrupt();

                    // TODO: 3/9/22 Above line
                }
            });
        }
    }

}