package com.example.attendo.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;

import com.example.attendo.R;
import com.example.attendo.ui.calendar.FragmentCalender;
import com.example.attendo.ui.main.drawers.AlarmReminder;
import com.example.attendo.ui.main.drawers.FragmentExamReminder;
import com.example.attendo.ui.main.drawers.account.FragmentAccountAndSettings;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BottomNavMainActivity extends AppCompatActivity {

    @BindView(R.id.bottom_nav_bar)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.appbar_bottom_nav)
    AppBarLayout appBarLayout;

    @BindView(R.id.container_frame)
    FrameLayout frameLayout;

    @BindView(R.id.floating_action_button)
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav_main);
        ButterKnife.bind(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(selectedListener);


    }
    private Fragment selectedFragment = null;

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            switch (item.getItemId()){
                case R.id.subjects_bottom_nav:
                    //inflate the fragment
                    break;

                case R.id.reminder_bottom_nav:
                    Fragment reminder = new FragmentExamReminder();
                    selectedFragment = reminder;
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                            .replace(R.id.container_frame, reminder, "reminder_fragment")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null)
                            .commit();
                    break;

                case R.id.calendar_bottom_nav:
                    Fragment calendar = new FragmentCalender();
                    selectedFragment = calendar;
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                            .replace(R.id.container_frame, calendar, "calendar_fragment")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null)
                            .commit();
                    break;

                case R.id.account_bottom_nav:
                    Fragment account = new FragmentAccountAndSettings();
                    selectedFragment = account;
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                            .replace(R.id.container_frame, account, "account_fragment")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null)
                            .commit();

                    break;

            }


            return true;
        }
    };

    @OnClick(R.id.floating_action_button)
    void add(){

        PropertyValuesHolder holderYhide = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f);
        PropertyValuesHolder holderXhide = PropertyValuesHolder.ofFloat(View.SCALE_X, 0f);


        ObjectAnimator animatorhide = ObjectAnimator.ofPropertyValuesHolder(floatingActionButton, holderXhide, holderYhide);
        animatorhide.setDuration(300);
        animatorhide.setInterpolator(new AnticipateOvershootInterpolator());


        switch (selectedFragment.getTag()){
            case "subject_fragment":
                animatorhide.start();
                floatingActionButton.hide();
                break;

            case "calendar_fragment":
                animatorhide.start();
                floatingActionButton.hide();
                break;

            case "reminder_fragment":
                animatorhide.start();
                floatingActionButton.hide();
                break;




        }
    }
}