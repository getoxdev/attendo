package com.attendo.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

import com.attendo.R;
import com.attendo.ui.calendar.FragmentCalender;
import com.attendo.ui.main.drawers.FragmentExamReminder;

import com.attendo.ui.main.drawers.account.FragmentAccountAndSettings;
import com.attendo.ui.main.menu.FragmentAbout;
import com.attendo.ui.sub.Fragment_Subject;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.android.material.transition.platform.MaterialFade;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setEnterTransition(new MaterialFade().setDuration(300));
        getWindow().setExitTransition(new MaterialFade().setDuration(300));
        setContentView(R.layout.activity_bottom_nav_main);
        ButterKnife.bind(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(selectedListener);
        setSupportActionBar(toolbar);



        getSupportFragmentManager().beginTransaction().replace(R.id.container_frame, new Fragment_Subject()).commit();

    }
    private Fragment selectedFragment = null;

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            MaterialSharedAxis enter = new MaterialSharedAxis(MaterialSharedAxis.Z, true);
            MaterialSharedAxis exit = new MaterialSharedAxis(MaterialSharedAxis.Z, false);
            enter.setDuration(400);
            enter.setInterpolator(new AccelerateDecelerateInterpolator());
            exit.setDuration(400);
            exit.setInterpolator(new AccelerateDecelerateInterpolator());


            switch (item.getItemId()){
                case R.id.subjects_bottom_nav:
                    Fragment subject = new Fragment_Subject();
                    selectedFragment = subject;
                    Bundle bundle = new Bundle();
                    bundle.putString("key","");
                    subject.setEnterTransition(enter);
                    subject.setExitTransition(exit);
                    subject.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_frame, subject, "subject_fragment")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();

                    break;

                case R.id.reminder_bottom_nav:
                    Fragment reminder = new FragmentExamReminder();
                    selectedFragment = reminder;
                    reminder.setEnterTransition(enter);
                    reminder.setExitTransition(exit);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_frame, reminder, "reminder_fragment")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                    break;

                case R.id.calendar_bottom_nav:
                    Fragment calendar = new FragmentCalender();
                    selectedFragment = calendar;
                    calendar.setEnterTransition(enter);
                    calendar.setExitTransition(exit);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_frame, calendar, "calendar_fragment")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                    break;

                case R.id.account_bottom_nav:
                    Fragment account = new FragmentAccountAndSettings();
                    selectedFragment = account;
                    account.setEnterTransition(enter);
                    account.setExitTransition(exit);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_frame, account, "account_fragment")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();

                    break;

            }


            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_activity_app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Fragment aboutUs = new FragmentAbout();

        MaterialSharedAxis enter = new MaterialSharedAxis(MaterialSharedAxis.Z, true);
        MaterialSharedAxis exit = new MaterialSharedAxis(MaterialSharedAxis.Z, false);

        aboutUs.setEnterTransition(enter);
        aboutUs.setExitTransition(exit);


        int id = item.getItemId();

        switch (id){
            case R.id.aboutus:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_frame, aboutUs)
                        .addToBackStack(null)
                        .commit();

                break;
            case R.id.privacypolicy:
                Uri uri = Uri.parse("https://attendo.flycricket.io/privacy.html");
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
                break;


        }

        return true;

    }
}