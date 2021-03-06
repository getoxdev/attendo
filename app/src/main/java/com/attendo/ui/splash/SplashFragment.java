package com.attendo.ui.splash;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.attendo.R;
import com.attendo.ui.auth.login.FragmentLogin;
import com.attendo.ui.main.BottomNavMainActivity;
import com.google.android.material.transition.MaterialFade;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.airbnb.lottie.L.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;

public class SplashFragment extends Fragment {

    @BindView(R.id.splash_screen_app_icon)
    ImageView appIcon;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.Z, false));
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        ButterKnife.bind(this, view);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        Animation animation  = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);

        //hooks
        appIcon = (ImageView) view.findViewById(R.id.splash_screen_app_icon);

        appIcon.setAnimation(animation);

        //stay logged in code
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (user != null) {
                    // User is signed in
                    Intent i = new Intent(getActivity(), BottomNavMainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } else {
                    // User is signed out
                    FragmentLogin fragmentLogin = new FragmentLogin();
                    fragmentLogin.setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.Z, true));
                    setFragment(fragmentLogin);
                }
            }
        },1000);


        return view;
    }

    private void setFragment(Fragment fragment)
    {
        try
        {
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.start_frame,fragment);
            fragmentTransaction.commit();
        }
        catch (Exception e)
        {
            Log.e("Exception",e.getMessage());
        }
    }
}