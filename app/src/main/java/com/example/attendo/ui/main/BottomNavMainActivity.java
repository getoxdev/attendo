package com.example.attendo.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.attendo.R;
import com.example.attendo.ui.calendar.FragmentCalender;
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
                    //inflate the fragment
                    break;

                case R.id.calendar_bottom_nav:
                    Fragment calendar = new FragmentCalender();
                    selectedFragment = calendar;
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_frame, calendar, "calendar_fragment");
                    break;

                case R.id.account_bottom_nav:
                    //inflate the fragment
                    break;

            }


            return true;
        }
    };

    @OnClick(R.id.floating_action_button)
    void add(){


        switch (selectedFragment.getTag()){

            case "calendar_fragment":
                //code goes here
                break;



        }
    }
}