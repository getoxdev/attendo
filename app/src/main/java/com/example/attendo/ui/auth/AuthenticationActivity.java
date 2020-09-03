package com.example.attendo.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.attendo.R;
import com.example.attendo.ui.auth.login.FragmentLogin;

public class AuthenticationActivity extends AppCompatActivity {

   private FragmentLogin fragmentLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);





        fragmentLogin = new FragmentLogin();
        setFragment(fragmentLogin);
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.start_frame,fragment);
        fragmentTransaction.commit();
    }

}