package com.example.taraziot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class PermissionsActivity extends AppCompatActivity {

    MaterialButton grant, nextPage;
//    String[] permissions = new String[]{RequestPermission.SMS,RequestPermission.LOCATION};
    String[] permissions = new String[]{RequestPermission.LOCATION};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
        grant = findViewById(R.id.grant);
        nextPage = findViewById(R.id.nextPage);


        if (!RequestPermission.newInstance(PermissionsActivity.this, permissions).request()) {
            grant.setEnabled(true);
            nextPage.setEnabled(false);
        }else {
            grant.setEnabled(false);
            nextPage.setEnabled(true);
        }

        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PermissionsActivity.this, ConfigSensor22Activity.class);
                startActivity(intent);
            }
        });

        grant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!RequestPermission.newInstance(PermissionsActivity.this, permissions).request()) {
                    grant.setEnabled(true);
                    nextPage.setEnabled(false);
                } else {
                        grant.setEnabled(false);
                        nextPage.setEnabled(true);
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean generateAllRequests = true;
        if (requestCode==RequestPermission.REQ_CODE_PERMISSION){
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i]== PackageManager.PERMISSION_DENIED){
                    generateAllRequests = false;
                }
            }
            if (generateAllRequests){
                grant.setEnabled(false);
                nextPage.setEnabled(true);
            }
        }

    }
}