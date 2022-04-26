package com.example.taraziot;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 1000;

    //Variables
    Animation topAnim, bottomAnim;
    ImageView imageSplash1;
    TextView textSlogan;
    UserManagerSharedPrefs userManagerSharedPrefs;
    //    String token, verifiedAt;
//    Boolean agreed = false;
    Boolean registered, allowed, registeredSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        configSharedP();
        configSharedP2();
        configSharedP3();

        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        //hooks
        imageSplash1 = findViewById(R.id.imageSplash1);
        textSlogan = findViewById(R.id.textSlogan);
        imageSplash1.setAnimation(topAnim);
        textSlogan.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentSplash = new Intent(SplashScreenActivity.this, MainActivity.class);
//                startActivity(intent1);
//                finish();
                if (!registered && allowed) {
//                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    Intent intent = new Intent(SplashScreenActivity.this, ConfigServerActivity.class);
                    startActivity(intent);
                    finish();

                } else if (registered && !registeredSensor){
                    Intent intent = new Intent(SplashScreenActivity.this, ConfigSensor22Activity.class);
                    startActivity(intent);
                    finish();
                }

                else if (!allowed && !registered) {
                    Intent intent = new Intent(SplashScreenActivity.this, PermissionsActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    if (registered) {
                        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Pair[] pairs = new Pair[2];
                        pairs[0] = new Pair<View, String>(imageSplash1, "logo_image");
                        pairs[1] = new Pair<View, String>(textSlogan, "logo_text");
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreenActivity.this, pairs);
                        startActivity(intentSplash, options.toBundle());
                        finish();
                    }
                }
//Intent intent = new Intent(OldActivity.this, NewActivity.class);
// set the new task and clear flags
//                intent .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
            }
        }, SPLASH_SCREEN);


    }


    private void configSharedP() {

        this.userManagerSharedPrefs = new UserManagerSharedPrefs(this);
        registered = userManagerSharedPrefs.getRegistered();
    }


    private void configSharedP2() {

        this.userManagerSharedPrefs = new UserManagerSharedPrefs(this);
        allowed = userManagerSharedPrefs.getPermissionsState();
    }

    private void configSharedP3() {

        this.userManagerSharedPrefs = new UserManagerSharedPrefs(this);
        registeredSensor = userManagerSharedPrefs.getRegisteredSensor();
    }

}