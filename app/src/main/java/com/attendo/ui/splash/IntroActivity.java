package com.attendo.ui.splash;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.app.SharedElementCallback;

import android.os.Bundle;
import android.os.Handler;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.attendo.ui.auth.AuthenticationActivity;
import com.attendo.R;
import com.google.android.material.transition.platform.MaterialFade;
import com.google.android.material.transition.platform.MaterialSharedAxis;

import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private Handler handler;
    private Animation animation;
    private ImageView appIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.Z, true));
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