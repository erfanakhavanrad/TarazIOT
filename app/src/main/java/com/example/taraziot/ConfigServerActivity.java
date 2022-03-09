package com.example.taraziot;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@SuppressLint("SetTextI18n")
public class ConfigServerActivity extends AppCompatActivity {

    Thread Thread1 = null;
    //    EditText etIP, etPort;
    TextView ssidName;
    TextInputEditText edtSimCard, edtSerialNumber, edtAdminPhoneNumber, edtPassword, edtConfirmPassword,
            edtFirstUserPhoneNumber, edtSecondUserPhoneNumber, edtThirdUserPhoneNumber, edtForthUserPhoneNumber;
    Button btnConfig;
    String SERVER_IP;
    int SERVER_PORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_server);
        ssidName = findViewById(R.id.ssidName);
        edtSimCard = findViewById(R.id.edtSimCard);
        edtSerialNumber = findViewById(R.id.edtSerialNumber);
        edtAdminPhoneNumber = findViewById(R.id.edtAdminPhoneNumber);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        edtFirstUserPhoneNumber = findViewById(R.id.edtFirstUserPhoneNumber);
        edtSecondUserPhoneNumber = findViewById(R.id.edtSecondUserPhoneNumber);
        edtThirdUserPhoneNumber = findViewById(R.id.edtThirdUserPhoneNumber);
        edtForthUserPhoneNumber = findViewById(R.id.edtForthUserPhoneNumber);
        btnConfig = findViewById(R.id.btnConfig);
        SERVER_IP = "192.168.43.111";
//                SERVER_PORT = Integer.parseInt(etPort.getText().toString().trim());
        SERVER_PORT = 8888;
        Thread1 = new Thread(new Thread1());
        Thread1.start();

        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String simCard = edtSimCard.getText().toString().trim();
                String serialNumber = edtSerialNumber.getText().toString().trim();
                String adminPhoneNumber = edtAdminPhoneNumber.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String confirmPassword = edtConfirmPassword.getText().toString().trim();
                String firstUserPhoneNumber = edtFirstUserPhoneNumber.getText().toString().trim();
                String secondUserPhoneNumber = edtSecondUserPhoneNumber.getText().toString().trim();
                String thirdUserPhoneNumber = edtThirdUserPhoneNumber.getText().toString().trim();
                String forthUserPhoneNumber = edtForthUserPhoneNumber.getText().toString().trim();


                String[] values = {simCard, serialNumber, adminPhoneNumber, password, confirmPassword,
                        firstUserPhoneNumber, secondUserPhoneNumber, thirdUserPhoneNumber, forthUserPhoneNumber};
                Boolean valid = true;
                for (int i = 0; i < values.length; i++) {
                    if (TextUtils.isEmpty(values[i])) {
                        valid = false;
                        break;
                    }
//                    System.out.println(valid);
                }


                if (valid) {
//                    String message = edtSimCard.getText().toString().trim();
                    String mainMessage = "$" + "simCard:" + simCard + ",serialNumber:" + serialNumber + ",adminPhoneNumber:" + adminPhoneNumber + ",password:" + password + ",confirmPassword:" + confirmPassword +
                            ",firstUserPhoneNumber:" + firstUserPhoneNumber + ",secondUserPhoneNumber:" + secondUserPhoneNumber +
                            ",thirdUserPhoneNumber:" + thirdUserPhoneNumber + ",forthUserPhoneNumber:" + forthUserPhoneNumber + "$:";

                    new Thread(new Thread3(mainMessage)).start();
                } else {
                    Toast.makeText(ConfigServerActivity.this, "تمام فیلدها را پر کنید", Toast.LENGTH_SHORT).show();
                }
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
                                ssidName.append("server: " + message + "\n");

                            }
                        });
                    } else {
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
            output.write(message);
            output.flush();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ssidName.append("client: " + message + "\n");
//                    edtSimCard.setText("");
                    // TODO: 3/9/22 Above line
                }
            });
        }
    }
}

//        webView = findViewById(R.id.webView);
//
//        WebSettings wbset = webView.getSettings();
//        wbset.setJavaScriptEnabled(true);
//        webView.setWebViewClient(new WebViewClient());
//        String url = "http://google.com/";
//
////        System.out.println(getdeviceid());
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl(url);