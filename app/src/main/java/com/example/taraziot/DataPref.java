package com.example.taraziot;

import android.content.Context;
import android.content.SharedPreferences;

public class DataPref {

    public static final String SHP_NAME = "APP_SETTING";
    public static final String SERVICE_STATUS = "SERVICE_STATUS";

    private DataPref() {
    }

    public static void saveServiceStatus(Context context, Boolean status) {
        SharedPreferences shp = context.getSharedPreferences(SHP_NAME, Context.MODE_PRIVATE);
        shp.edit().putBoolean(SERVICE_STATUS, status).apply();
    }

    public static Boolean getServiceStatus(Context context) {
        SharedPreferences shp = context.getSharedPreferences(SHP_NAME, Context.MODE_PRIVATE);
        return shp.getBoolean(SERVICE_STATUS, true);
    }

}
