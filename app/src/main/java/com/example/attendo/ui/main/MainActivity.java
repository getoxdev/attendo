package com.example.attendo.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.os.Handler;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import com.codemybrainsout.ratingdialog.RatingDialog;

import com.example.attendo.ui.auth.AuthenticationActivity;
import com.example.attendo.ui.main.menu.FragmentAbout;
import com.example.attendo.ui.main.drawers.FragmentAppRate;
import com.example.attendo.ui.main.drawers.FragmentBug;
import com.example.attendo.ui.main.drawers.FragmentEditAttendance;
import com.example.attendo.ui.main.drawers.FragmentEditAttendanceCriteria;
import com.example.attendo.ui.main.drawers.FragmentExamReminder;
import com.example.attendo.ui.main.drawers.FragmentHelp;
import com.example.attendo.ui.main.menu.FragmentSettings;
import com.example.attendo.ui.main.drawers.account.FragmentUserProfile;
import com.example.attendo.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView sub;
    private FragmentAbout fragmentAbout;
    private FragmentAppRate fragmentAppRate;
    private FragmentBug fragmentBug;
    private FragmentEditAttendance fragmentEditAttendance;
    private FragmentEditAttendanceCriteria fragmentEditAttendanceCriteria;
    private FragmentExamReminder fragmentExamReminder;
    private FragmentHelp fragmentHelp;
    private FragmentSettings fragmentSetting;
    private FragmentHome fragmentHome;
    private FragmentUserProfile fragment_user_profile;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentAbout = new FragmentAbout();
        fragmentAppRate = new FragmentAppRate();
        fragmentBug = new FragmentBug();
        fragmentEditAttendance = new FragmentEditAttendance();
        fragmentEditAttendanceCriteria = new FragmentEditAttendanceCriteria();
        fragmentExamReminder = new FragmentExamReminder();
        fragmentHelp = new FragmentHelp();
        fragmentSetting = new FragmentSettings();
        fragmentHome = new FragmentHome();
        fragment_user_profile = new FragmentUserProfile();

        auth = FirebaseAuth.getInstance();

        setFragment(fragmentHome);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dashboard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.settings)
        {
            setFragment(fragmentSetting);
        }
        else if(id==R.id.share)
        {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String body = "https://github.com/getoxdev/attendo";
            String subject = "Attendo_App";
            intent.putExtra(Intent.EXTRA_TEXT,body);
            intent.putExtra(Intent.EXTRA_SUBJECT,subject);
            startActivity(Intent.createChooser(intent,"Share Using"));
        }
        else if(id==R.id.about)
        {
            setFragment(fragmentAbout);
        }
        else if(id==R.id.exit)
        {
            Toast.makeText(MainActivity.this,"Logout",Toast.LENGTH_SHORT).show();
            auth.signOut();
            finish();
            Intent intent = new Intent(MainActivity.this, AuthenticationActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")  //see this later how it works!!!
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case  R.id.nav_login:
                        setFragment(fragment_user_profile);
                break;
            case  R.id.nav_home:
                        setFragment(fragmentHome);
                break;
            case  R.id.nav_att:
                        setFragment(fragmentEditAttendance);
                break;
            case  R.id.nav_attctr:
                        setFragment(fragmentEditAttendanceCriteria);
                break;
            case  R.id.nav_erem:
                        setFragment(fragmentExamReminder);
                break;
            case  R.id.nav_rate:
                        final RatingDialog ratingDialog = new RatingDialog.Builder(this)
                                .threshold(3)
                                .onThresholdFailed(new RatingDialog.Builder.RatingThresholdFailedListener() {
                                    @Override
                                    public void onThresholdFailed(RatingDialog ratingDialog, float rating, boolean thresholdCleared) {
                                        Toast.makeText(MainActivity.this, "Suggest Us ", Toast.LENGTH_SHORT).show();
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                setFragment(fragmentAppRate);
                                                ratingDialog.dismiss();

                                            }
                                        },1000);

                                    }
                                })
                                .onThresholdCleared(new RatingDialog.Builder.RatingThresholdClearedListener() {
                                    @Override
                                    public void onThresholdCleared(RatingDialog ratingDialog, float rating, boolean thresholdCleared) {
                                        Toast.makeText(MainActivity.this, "Thank You so much  ", Toast.LENGTH_SHORT).show();

                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                setFragment(fragmentAppRate);
                                                ratingDialog.dismiss();

                                            }
                                        },1000);


                                    }
                                })
                                .build();
                        ratingDialog.show();
                break;
            case R.id.nav_help:
                        setFragment(fragmentHelp);
                 break;
            case R.id.nav_bug:
                        setFragment(fragmentBug);
                 break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
            return true;
    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }
}
