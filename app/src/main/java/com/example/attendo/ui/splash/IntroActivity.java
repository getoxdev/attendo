package com.example.attendo.ui.splash;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.attendo.ui.auth.AuthenticationActivity;
import com.example.attendo.R;

public class IntroActivity extends AppCompatActivity {

    private Handler handler;
    private Animation animation;
    private ImageView appIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        getSupportActionBar().hide();

        //hooks
        appIcon = (ImageView) findViewById(R.id.splash_screen_app_icon);


        //animation
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
        appIcon.setAnimation(animation);

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(IntroActivity.this, AuthenticationActivity.class);
                startActivity(intent);
                finish();
            }
        },1500);
    }
}