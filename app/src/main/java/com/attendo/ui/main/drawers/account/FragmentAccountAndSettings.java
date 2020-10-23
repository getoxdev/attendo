package com.attendo.ui.main.drawers.account;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.Transition;
import androidx.transition.TransitionInflater;

import android.transition.Explode;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.attendo.BuildConfig;
import com.attendo.R;
import com.attendo.ui.auth.AuthenticationActivity;
import com.attendo.ui.main.drawers.FragmentAppRate;
import com.attendo.ui.main.drawers.FragmentBug;
import com.attendo.ui.main.drawers.FragmentEditAttendance;
import com.attendo.ui.main.drawers.FragmentEditAttendanceCriteria;
import com.attendo.ui.main.drawers.FragmentFAQ;
import com.attendo.ui.main.drawers.FragmentHelp;
import com.attendo.ui.main.drawers.FragmentInfo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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
    private FragmentFAQ fragmentFAQ;
    private FragmentInfo fragmentInfo;
    TextView logout,Bug,Help,AppRate,AttCritaria,Att,name,college, aboutsettings, theme;
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
        fragmentFAQ = new FragmentFAQ();
        fragmentInfo = new FragmentInfo();
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
        Profile = view.findViewById(R.id.view_account);
        AttCritaria = view.findViewById(R.id.edit_attendance_criterion);
        Help = view.findViewById(R.id.help_settings);
        logout = view.findViewById(R.id.logout_settings);
        theme = view.findViewById(R.id.theme);

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

        fragmentInfo.setEnterTransition(enter);
        fragmentInfo.setExitTransition(exit);




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
            }
        });

        Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(fragmentFAQ);
                bottomNavigationView.setVisibility(View.GONE);
            }
        });






        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Logout",Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        //shared preferences for saving the radio button state of theme selection by user
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MYPREF" , Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();

        SharedPreferences load = getActivity().getSharedPreferences("MYPREF" , Context.MODE_PRIVATE);


        theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //theme selection by user code goes here

                BottomSheetDialog themeSelect = new BottomSheetDialog(getContext(), R.style.BottomSheetDialog);
                View themeBottomSheet = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_theme_select,
                        (ConstraintLayout) view.findViewById(R.id.theme_select_bottom_sheet_container));
                themeSelect.setContentView(themeBottomSheet);
                themeSelect.show();
                themeSelect.setDismissWithAnimation(true);

                RadioGroup themeSelectRadioGroup = themeSelect.findViewById(R.id.theme_radio_group);
                RadioButton darkTheme = themeSelect.findViewById(R.id.dark_theme_select);
                RadioButton lightTheme = themeSelect.findViewById(R.id.light_theme_select);
                RadioButton systemDefaultTheme = themeSelect.findViewById(R.id.system_default_theme_select);

                //show the previous setting to the user
                int selectedRadioButton = load.getInt("check_theme", systemDefaultTheme.getId());
                themeSelectRadioGroup.check(selectedRadioButton);

                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
                    systemDefaultTheme.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                    darkTheme.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                    lightTheme.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                }


                themeSelectRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        i = radioGroup.getCheckedRadioButtonId();
                        switch (i){
                            case R.id.dark_theme_select:
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                                themeSelect.dismiss();
                                break;
                            case R.id.light_theme_select:
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                                themeSelect.dismiss();
                                break;
                            case R.id.system_default_theme_select:
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                                themeSelect.dismiss();
                                break;

                        }
                        editor.putInt("check_theme" , i);
                        editor.commit();
                    }
                });

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