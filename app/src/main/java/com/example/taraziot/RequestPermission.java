package com.example.taraziot;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class RequestPermission {
    public final static String CAMERA = "CAMERA", STORAGE = "STORAGE", LOCATION = "LOCATION", VOICE_RECORD = "VOICE_RECORD";
    String[] permissionTypes;
    Activity activity;

    public static RequestPermission newInstance(Activity activity, String[] permissionTypes) {
        return new RequestPermission(activity, permissionTypes);
    }

    public RequestPermission(Activity activity, String[] permissionTypes) {
        this.permissionTypes = permissionTypes;
        this.activity = activity;
    }

    public boolean request() {

        List<String> listPermissionsNeeded = new ArrayList<>();

        for (int i = 0; i < permissionTypes.length; i++) {
            switch (permissionTypes[i]) {
                case CAMERA:
                    int permissionACCESS_CAMERA = ContextCompat.checkSelfPermission(activity,
                            Manifest.permission.CAMERA);

                    if (permissionACCESS_CAMERA != PackageManager.PERMISSION_GRANTED) {
                        listPermissionsNeeded.add(Manifest.permission.CAMERA);
                    }
                    break;
                case STORAGE:
                    int permissionACCESS_READ_STORAGE = ContextCompat.checkSelfPermission(activity,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                    if (permissionACCESS_READ_STORAGE != PackageManager.PERMISSION_GRANTED) {
                        listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                    }
                    int permissionACCESS_WRITE_STORAGE = ContextCompat.checkSelfPermission(activity,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE);

                    if (permissionACCESS_WRITE_STORAGE != PackageManager.PERMISSION_GRANTED) {
                        listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    }
                    break;
                case LOCATION:
                    int permissionACCESS_FINE_LOCATION = ContextCompat.checkSelfPermission(activity,
                            Manifest.permission.ACCESS_FINE_LOCATION);
                    if (permissionACCESS_FINE_LOCATION != PackageManager.PERMISSION_GRANTED) {
                        listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
                    }

                    int permissionACCESS_COARSE_LOCATION = ContextCompat.checkSelfPermission(activity,
                            Manifest.permission.ACCESS_COARSE_LOCATION);
                    if (permissionACCESS_COARSE_LOCATION != PackageManager.PERMISSION_GRANTED) {
                        listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
                    }
                    break;
                case VOICE_RECORD:
                    int permissionACCESS_RECORD_AUDIO = ContextCompat.checkSelfPermission(activity,
                            Manifest.permission.RECORD_AUDIO);
                    if (permissionACCESS_RECORD_AUDIO != PackageManager.PERMISSION_GRANTED) {
                        listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
                    }
                    break;
            }
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }
}
