package com.example.taraziot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class PermissionsActivity extends AppCompatActivity {

    MaterialButton grant, nextPage;
    List<String> permissionsList = new ArrayList<>();
    Boolean permissions;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
        grant = findViewById(R.id.grant);
        nextPage = findViewById(R.id.nextPage);


//        permissionsList.add(RequestPermission.CAMERA);
//        permissionsList.add(RequestPermission.STORAGE);
//        new String[]{RequestPermission.CAMERA, RequestPermission.LOCATION};
//        permissions = RequestPermission.newInstance(PermissionsActivity.this, new String[]{RequestPermission.LOCATION}).request();
//        while (permissions) {
//            grant.setEnabled(false);
//            nextPage.setEnabled(true);
//        }
//
//        nextPage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(PermissionsActivity.this, ConfigSensor22Activity.class);
//                startActivity(intent);
//            }
//        });
//
//        grant.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                RequestPermission.newInstance(PermissionsActivity.this, new String[]{RequestPermission.LOCATION}).request();
//                permissions = RequestPermission.newInstance(PermissionsActivity.this, new String[]{RequestPermission.LOCATION}).request();
////                Toast.makeText(PermissionsActivity.this, "clicked", Toast.LENGTH_SHORT).show();
////               permissions = RequestPermission.newInstance(PermissionsActivity.this, new String[]{RequestPermission.CAMERA, RequestPermission.LOCATION}).request();
////                permissions = RequestPermission.newInstance(PermissionsActivity.this, new String[]{RequestPermission.LOCATION}).request();
////                onRequestPermissionsResult(100, RequestPermission.newInstance(PermissionsActivity.this, new String[]{RequestPermission.LOCATION}).request(), );
//                if (permissions) {
//                    grant.setEnabled(false);
//                    nextPage.setEnabled(true);
//                } else {
//                    permissions = RequestPermission.newInstance(PermissionsActivity.this, new String[]{RequestPermission.LOCATION}).request();
//                    if (permissions) {
//                        grant.setEnabled(false);
//                        nextPage.setEnabled(true);
//                    }
//                }
//            }
//        });
    }
}