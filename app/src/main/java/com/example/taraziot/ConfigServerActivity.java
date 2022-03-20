package com.example.taraziot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    EditText serveredt;
    TextInputEditText edtSimCard, edtSerialNumber, edtAdminPhoneNumber, edtPassword, edtConfirmPassword,
            edtFirstUserPhoneNumber, edtSecondUserPhoneNumber;
    //    edtThirdUserPhoneNumber, edtFourthUserPhoneNumber;
    Button btnConfig;
    String SERVER_IP, mainMessage,
            simCard, serialNumber, adminPhoneNumber, password, confirmPassword, firstUserPhoneNumber, secondUserPhoneNumber,
            thirdUserPhoneNumber, fourthUserPhoneNumber;


    UserManagerSharedPrefs userManagerSharedPrefs;
    int SERVER_PORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_server);
        ssidName = findViewById(R.id.ssidName);
        edtSimCard = findViewById(R.id.edtSimCard);
//        edtSerialNumber = findViewById(R.id.edtSerialNumber);
        edtAdminPhoneNumber = findViewById(R.id.edtAdminPhoneNumber);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        edtFirstUserPhoneNumber = findViewById(R.id.edtFirstUserPhoneNumber);
        edtSecondUserPhoneNumber = findViewById(R.id.edtSecondUserPhoneNumber);
//        edtThirdUserPhoneNumber = findViewById(R.id.edtThirdUserPhoneNumber);
//        edtFourthUserPhoneNumber = findViewById(R.id.edtFourthUserPhoneNumber);
        btnConfig = findViewById(R.id.btnConfig);
//        serveredt = findViewById(R.id.serveredt);
//        SERVER_IP = "192.168.43.111";

        userManagerSharedPrefs = new UserManagerSharedPrefs(this);
        Context context = ConfigServerActivity.this.getApplicationContext();
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
//        Toast.makeText(context, "first " + ip, Toast.LENGTH_SHORT).show();
// cut the last octet from ip (if you want to keep the . at the end, add 1 to the second parameter
        String firstThreeOctets = ip.substring(0, ip.lastIndexOf(".")); // 192.168.1

        String lastOctet = ip.substring(ip.lastIndexOf(".") + 1); // 97
//        Toast.makeText(context, "Second " + lastOctet, Toast.LENGTH_SHORT).show();
        String newIp = firstThreeOctets + ".1"; // 192.168.1.100
//        Toast.makeText(context, "Third " + newIp, Toast.LENGTH_SHORT).show();
//        Toast.makeText(context, "ip: " + ip, Toast.LENGTH_SHORT).show();
//        SERVER_IP = "192.168.43.111";
        SERVER_IP = "192.168.43.111";

//                SERVER_PORT = Integer.parseInt(etPort.getText().toString().trim());
//        SERVER_IP = newIp;
//        SERVER_IP = serveredt.getText().toString().trim();
//        Toast.makeText(context, "in", Toast.LENGTH_SHORT).show();
        SERVER_PORT = 8888;
        Thread1 = new Thread(new Thread1());
        Thread1.start();

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
//                 thirdUserPhoneNumber = edtThirdUserPhoneNumber.getText().toString().trim();
//                 fourthUserPhoneNumber = edtFourthUserPhoneNumber.getText().toString().trim();


//                String[] values = {simCard, adminPhoneNumber, password, confirmPassword,
//                        firstUserPhoneNumber, secondUserPhoneNumber, thirdUserPhoneNumber, fourthUserPhoneNumber};


                String[] values = {simCard, adminPhoneNumber, password, confirmPassword};

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
//                    String mainMessage = "$" + "simCard:" + simCard + ",adminPhoneNumber:" + adminPhoneNumber + ",password:" + password + ",confirmPassword:" + confirmPassword +
//                            ",firstUserPhoneNumber:" + firstUserPhoneNumber + ",secondUserPhoneNumber:" + secondUserPhoneNumber +
//                            ",thirdUserPhoneNumber:" + thirdUserPhoneNumber + ",fourthUserPhoneNumber:" + fourthUserPhoneNumber + "$";
                    mainMessage = "$" + "1:" + simCard + ",2:" + adminPhoneNumber + ",3:" + password + ",4:" + confirmPassword +
                            ",5:" + firstUserPhoneNumber + ",6:" + secondUserPhoneNumber + "$";
                    new Thread(new Thread3(mainMessage)).start();


                } else {
                    Toast.makeText(ConfigServerActivity.this, "فیلدهای اجباری را پر کنید", Toast.LENGTH_SHORT).show();
//                    passwordLayout.error = getString(R.string.error)
                    edtSimCard.setError("اجباری");
                    edtAdminPhoneNumber.setError("اجباری");
                    edtPassword.setError("اجباری");
                    edtConfirmPassword.setError("اجباری");

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
//                                ssidName.append("server: " + message + "\n");
//                                Toast.makeText(ConfigServerActivity.this, message, Toast.LENGTH_SHORT).show();

                                if (message.contains("68752")) {
                                    userManagerSharedPrefs.registered(true);
                                    userManagerSharedPrefs.destinationAddress(simCard);
                                    Toast.makeText(ConfigServerActivity.this, "اطلاعات با موفقیت ذخیره شد.", Toast.LENGTH_LONG).show();
                                    Toast.makeText(ConfigServerActivity.this, "پس از ۵ ثانیه اپلیکیشن بسته میشود.", Toast.LENGTH_LONG).show();
                                    final Handler handler = new Handler();

                                    final Runnable runnable = new Runnable() {
                                        @Override
                                        public void run() {
                                            finish();
                                            System.exit(0);
                                        }
                                    };

                                    handler.postDelayed(runnable, 5000);

                                } else {
                                    Toast.makeText(ConfigServerActivity.this, "مشکلی پیش آمد. لطفا مجددا تلاش کنید.", Toast.LENGTH_LONG).show();
                                    userManagerSharedPrefs.registered(false);


                                }
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
                    System.out.println(message);
                    // TODO: 3/9/22 Above line
                }
            });
        }
    }


//    public static String getIPAddress(boolean useIPv4) {
//        try {
//            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
//            for (NetworkInterface intf : interfaces) {
//                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
//                for (InetAddress addr : addrs) {
//                    if (!addr.isLoopbackAddress()) {
//                        String sAddr = addr.getHostAddress();
//                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
//                        boolean isIPv4 = sAddr.indexOf(':') < 0;
//
//                        if (useIPv4) {
//                            if (isIPv4)
//                                return sAddr;
//                        } else {
//                            if (!isIPv4) {
//                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
//                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (Exception ignored) {
//        } // for now eat exceptions
//        return "";
//    }


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