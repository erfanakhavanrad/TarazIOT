package com.example.taraziot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseDeviceActivity extends AppCompatActivity {
    Button btnServerConfigPage, btnSensorConfigPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_device);
        btnSensorConfigPage = findViewById(R.id.btnSensorConfigPage);
        btnServerConfigPage = findViewById(R.id.btnServerConfigPage);

        btnServerConfigPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseDeviceActivity.this, ConfigServerActivity.class);
                startActivity(intent);
            }
        });


        btnSensorConfigPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseDeviceActivity.this, ConfigSensor22Activity.class);
                startActivity(intent);
            }
        });


    }
}