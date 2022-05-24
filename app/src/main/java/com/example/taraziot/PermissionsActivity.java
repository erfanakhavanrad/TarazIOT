package com.example.taraziot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.location.LocationManagerCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class PermissionsActivity extends AppCompatActivity {

    MaterialButton grant, nextPage;
    String[] permissions = new String[]{RequestPermission.SMS, RequestPermission.LOCATION};
    //    String[] permissions = new String[]{RequestPermission.LOCATION};
    UserManagerSharedPrefs userManagerSharedPrefs;
    Boolean permissionsState, isLocation;

    public boolean isGPSEnabled(Context mContext) {
        LocationManager lm = (LocationManager)
                mContext.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
        grant = findViewById(R.id.grant);
        nextPage = findViewById(R.id.nextPage);
        Context context = PermissionsActivity.this.getApplicationContext();
/** ******************************************** /**/

        if (isGPSEnabled(context)) {
//            Toast.makeText(context, "Enabled", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(PermissionsActivity.this);

// Setting Dialog Title
            alertDialog.setTitle("تنظیمات لوکیشن");
// Setting Dialog Message
            alertDialog.setMessage("برای این که اپلیکیشن به درستی کار کند باید لوکیشن گوشی روشن باشد.");
// On pressing Settings button
            alertDialog.setPositiveButton("تنظیمات", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });

// on pressing cancel button
            alertDialog.setNegativeButton("خروج از برنامه", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
//                    dialog.cancel();
                    System.exit(0);
                }
            });
// Showing Alert Message
            alertDialog.setCancelable(false);
            alertDialog.show();

        }

// LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
// boolean gps_enabled = false;
// boolean network_enabled = false;
//
// try {
// gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
// } catch(Exception ex) {}
//
// try {
// network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
// } catch(Exception ex) {}
//
// if(!gps_enabled && !network_enabled) {
// // notify user
// new AlertDialog.Builder(context)
// .setMessage("R.string.gps_network_not_enabled")
// .setPositiveButton("R.string.open_location_settings", new DialogInterface.OnClickListener() {
//@Override
//public void onClick(DialogInterface paramDialogInterface, int paramInt) {
//context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//}
//})
// .setNegativeButton("R.string.Cancel",null)
// .show();
// }


        /** **************************************************************************************************************************** */


        userManagerSharedPrefs = new UserManagerSharedPrefs(this);

        if (!RequestPermission.newInstance(PermissionsActivity.this, permissions).request()) {
            grant.setEnabled(true);
            nextPage.setEnabled(false);
        } else {
            grant.setEnabled(false);
            nextPage.setEnabled(true);
        }

        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLocation = isGPSEnabled(context);
                if (isLocation) {
                    userManagerSharedPrefs.allowed(true);
                    Intent intent = new Intent(PermissionsActivity.this, ConfigServerActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(PermissionsActivity.this);

// Setting Dialog Title
                    alertDialog.setTitle("تنظیمات لوکیشن");
// Setting Dialog Message
                    alertDialog.setMessage("برای این که اپلیکیشن به درستی کار کند باید لوکیشن گوشی روشن باشد.");
// On pressing Settings button
                    alertDialog.setPositiveButton("تنظیمات", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    });

// on pressing cancel button
                    alertDialog.setNegativeButton("خروج از برنامه", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
//                    dialog.cancel();
                            System.exit(0);
                        }
                    });
// Showing Alert Message
                    alertDialog.setCancelable(false);
                    alertDialog.show();

                }
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
        if (requestCode == RequestPermission.REQ_CODE_PERMISSION) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    generateAllRequests = false;
                }
            }
            if (generateAllRequests) {
                grant.setEnabled(false);
                nextPage.setEnabled(true);
            }
        }

    }

    private void savePermissionsState() {

        this.userManagerSharedPrefs = new UserManagerSharedPrefs(this);
        permissionsState = userManagerSharedPrefs.getRegistered();
    }

}