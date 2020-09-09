package com.example.attendo.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.example.attendo.ui.sub.Fragment_Subject;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BottomNavMainActivity extends AppCompatActivity {

    @BindView(R.id.bottom_nav_bar)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.container_frame)
    FrameLayout frameLayout;

    @BindView(R.id.toolbar_bottom_nav)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav_main);
        ButterKnife.bind(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(selectedListener);
        setSupportActionBar(toolbar);


    }
    private Fragment selectedFragment = null;

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            switch (item.getItemId()){
                case R.id.subjects_bottom_nav:
                    Fragment subject = new Fragment_Subject();
                    selectedFragment = subject;
                    Bundle bundle = new Bundle();
                    bundle.putString("key","");
                    subject.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                            .replace(R.id.container_frame, subject, "reminder_fragment")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null)
                            .commit();
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

}