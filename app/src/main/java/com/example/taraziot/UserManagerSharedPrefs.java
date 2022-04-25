package com.example.taraziot;

import android.content.Context;
import android.content.SharedPreferences;

public class UserManagerSharedPrefs {
    private SharedPreferences sharedPreferences;

    public UserManagerSharedPrefs(Context context) {
        sharedPreferences = context.getSharedPreferences("user_information", Context.MODE_PRIVATE);
    }

    public void saveUserInformation(String fullName, String email, String token, String verifiedAt) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("full_name", fullName);
        editor.putString("email", email);
        editor.putString("token", token);
        editor.putString("verifiedAt", verifiedAt);
        editor.apply();
    }

    public void saveServerPassword( String serverPassword) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("serverPassword", serverPassword);
        editor.apply();
    }

    public void saveSSIDName(String ssidNameForSave) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ssidNameForSave", ssidNameForSave);
        editor.apply();
    }


    public void statusFromServer(String statusFromServer) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("statusFromServer", statusFromServer);
        editor.apply();
    }


    public void saveAgree(Boolean AgreeToTerms) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("AgreeToTerms", AgreeToTerms);
        editor.apply();
    }


    public boolean registered(Boolean registered) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("registered", registered);
        editor.apply();
        return registered;
    }

    public String destinationAddress(String destinationAddress){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("destinationAddress", destinationAddress);
        editor.apply();
        return destinationAddress;
    }


    public String getStatusFromServer(String statusFromServer) {
        return sharedPreferences.getString("statusFromServer", statusFromServer);
    }

    public Boolean getAgreeToTerms() {
        return sharedPreferences.getBoolean("AgreeToTerms", false);
    }

    public Boolean getRegistered() {
        return sharedPreferences.getBoolean("registered", false);
    }

    public String getDestinationAddress(String destinationAddress) {
        return sharedPreferences.getString("destinationAddress", destinationAddress);
    }

    public String getVerifiedAt() {
        return sharedPreferences.getString("verifiedAt", null);
    }

    public String getFullName() {
        return sharedPreferences.getString("full_name", null);
    }

    public String getEmail() {
        return sharedPreferences.getString("email", null);
    }


    public String getSsidNameForSave() {
        return sharedPreferences.getString("ssidNameForSave", null);
    }

    public String getServerPassword() {
        return sharedPreferences.getString("serverPassword", null);
    }

    public String getToken() {
        return sharedPreferences.getString("token", null);
    }

    public void clearAllInformation() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
