package com.example.attendo.ui.main.drawers.account;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.Transition;
import androidx.transition.TransitionInflater;

import android.transition.Explode;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.attendo.R;
import com.example.attendo.ui.auth.AuthenticationActivity;
import com.example.attendo.ui.main.BottomNavMainActivity;
import com.example.attendo.ui.main.MainActivity;
import com.example.attendo.ui.main.drawers.FragmentAppRate;
import com.example.attendo.ui.main.drawers.FragmentBug;
import com.example.attendo.ui.main.drawers.FragmentEditAttendance;
import com.example.attendo.ui.main.drawers.FragmentEditAttendanceCriteria;
import com.example.attendo.ui.main.drawers.FragmentHelp;
import com.example.attendo.ui.main.menu.FragmentAbout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class FragmentAccountAndSettings extends Fragment {


    private FragmentUserProfile fragmentUserProfile;
    private FragmentHelp fragmentHelp;
    private FragmentBug fragmentBug;
    private FragmentAppRate fragmentAppRate;
    private FragmentEditAttendanceCriteria fragmentEditAttendanceCriteria;
    private FragmentEditAttendance fragmentEditAttendance;
    TextView logout,Bug,Help,AppRate,AttCritaria,Att,name,college;
    CardView Profile;
    BottomNavigationView bottomNavigationView;

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    LottieAnimationView profileLottie;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentAccountAndSettings() {
        // Required empty public constructor
    }


    public static FragmentAccountAndSettings newInstance(String param1, String param2) {
        FragmentAccountAndSettings fragment = new FragmentAccountAndSettings();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_and_settings, container, false);




        fragmentAppRate = new FragmentAppRate();
        fragmentBug = new FragmentBug();
        fragmentEditAttendance = new FragmentEditAttendance();
        fragmentUserProfile = new FragmentUserProfile();
        fragmentEditAttendanceCriteria = new FragmentEditAttendanceCriteria();
        fragmentHelp = new FragmentHelp();
        name = view.findViewById(R.id.profile_name);
        college = view.findViewById(R.id.profile_college);
        profileLottie = view.findViewById(R.id.lottieanimationprofile);
        bottomNavigationView = getActivity().findViewById(R.id.bottom_nav_bar);

        bottomNavigationView.setVisibility(View.VISIBLE);

        mAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("data");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {
                    name.setText(snapshot.child(user_id).child("username").getValue(String.class));
                    college.setText(snapshot.child(user_id).child("college").getValue(String.class));
                }
                else{
                    name.setText("user_name");
                    college.setText("institute_name");
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        AppRate = view.findViewById(R.id.rate_app);
        Bug = view.findViewById(R.id.report_bug);
        Att = view.findViewById(R.id.edit_attendance);
        Profile = view.findViewById(R.id.view_account);
        AttCritaria = view.findViewById(R.id.edit_attendance_criterion);
        Help = view.findViewById(R.id.help_settings);
        logout = view.findViewById(R.id.logout_settings);

        Transition transition = TransitionInflater.from(getContext()).inflateTransition(R.transition.card_transition);

        MaterialSharedAxis enter = new MaterialSharedAxis(MaterialSharedAxis.Z, true);
        MaterialSharedAxis exit = new MaterialSharedAxis(MaterialSharedAxis.Z, false);

        Explode explode = new Explode();
        explode.setDuration(400);
        explode.setInterpolator(new AccelerateDecelerateInterpolator());

        Fade fade = new Fade();
        fade.setDuration(400);
        fade.setInterpolator(new AccelerateDecelerateInterpolator());

        enter.setInterpolator(new AccelerateDecelerateInterpolator());
        enter.setDuration(400);

        exit.setInterpolator(new AccelerateDecelerateInterpolator());
        exit.setDuration(400);


        fragmentAppRate.setEnterTransition(enter);
        fragmentAppRate.setExitTransition(exit);

        fragmentHelp.setEnterTransition(enter);
        fragmentHelp.setExitTransition(exit);

        fragmentBug.setEnterTransition(enter);
        fragmentBug.setExitTransition(exit);

        fragmentEditAttendance.setEnterTransition(enter);
        fragmentEditAttendance.setExitTransition(exit);

        fragmentEditAttendanceCriteria.setEnterTransition(enter);
        fragmentEditAttendanceCriteria.setExitTransition(exit);




        AppRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(fragmentAppRate);
                bottomNavigationView.setVisibility(View.GONE);
            }
        });

        Bug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(fragmentBug);
                bottomNavigationView.setVisibility(View.GONE);
            }
        });

        Att.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(fragmentEditAttendance);
                bottomNavigationView.setVisibility(View.GONE);
            }
        });

        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentManager = getFragmentManager().beginTransaction();
                fragmentManager.replace(R.id.container_frame, fragmentUserProfile)
                        .addSharedElement(Profile, "card_expand_to_fit_screen")
                        .addSharedElement(profileLottie, "profileExpand")
                        .addToBackStack(null)
                        .commit();

                bottomNavigationView.setVisibility(View.GONE);

                fragmentUserProfile.setSharedElementEnterTransition(transition);
            }
        });

        AttCritaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(fragmentEditAttendanceCriteria);
                bottomNavigationView.setVisibility(View.GONE);
            }
        });

        Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(fragmentHelp);
                bottomNavigationView.setVisibility(View.GONE);
            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Logout",Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                startActivity(intent);
            }
        });



        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Settings");
        return view;
    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frame,fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }

}