package com.attendo.ui.main.drawers.account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.transition.Explode;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.Transition;
import androidx.transition.TransitionInflater;

import com.airbnb.lottie.LottieAnimationView;
import com.ajts.androidmads.library.SQLiteToExcel;
import com.attendo.R;
import com.attendo.Schedule.CRSettingsFragment;
import com.attendo.Schedule.StudentFragment;
import com.attendo.Schedule.StudetntSettingsFragment;
import com.attendo.data.database.SubDatabase;
import com.attendo.ui.auth.AuthenticationActivity;
import com.attendo.ui.main.drawers.FragmentAppRate;
import com.attendo.ui.main.drawers.FragmentBug;
import com.attendo.ui.main.drawers.FragmentEditAttendance;
import com.attendo.ui.main.drawers.FragmentEditAttendanceCriteria;
import com.attendo.ui.main.drawers.FragmentFAQ;
import com.attendo.ui.main.drawers.FragmentHelp;
import com.attendo.ui.main.drawers.FragmentInfo;
import com.attendo.viewmodel.FirebaseScheduleViewModel;
import com.codemybrainsout.ratingdialog.RatingDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;

import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;

public class FragmentAccountAndSettings extends Fragment {

//    @BindView(R.id.adView)
//    AdView adView;


    private FragmentUserProfile fragmentUserProfile;
    private StudentFragment studentFragment;
    private FragmentHelp fragmentHelp;
    private FragmentBug fragmentBug;
    private FragmentAppRate fragmentAppRate;
    private FragmentEditAttendanceCriteria fragmentEditAttendanceCriteria;
    private FragmentEditAttendance fragmentEditAttendance;
    private FragmentFAQ fragmentFAQ;
    private FragmentInfo fragmentInfo;
    SubDatabase subDatabase;
    TextView logout,Bug,Help,AppRate,AttCritaria,name,college, theme, routine,excel;
    CardView Profile;
    BottomNavigationView bottomNavigationView;
    private FirebaseScheduleViewModel firebaseScheduleViewModel;

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    LottieAnimationView profileLottie;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_and_settings, container, false);
        ButterKnife.bind(this, view);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Settings");



        fragmentAppRate = new FragmentAppRate();
        studentFragment = new StudentFragment();
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
        firebaseScheduleViewModel = new ViewModelProvider(this).get(FirebaseScheduleViewModel.class);
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
        routine = view.findViewById(R.id.routine_settings);
        excel = view.findViewById(R.id.excel);

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


        Drawable drawable = getActivity().getDrawable(R.drawable.app_icon_middle_portion_removed);

        AppRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setFragment(fragmentAppRate);
//                bottomNavigationView.setVisibility(View.GONE);
                final RatingDialog ratingDialog = new RatingDialog.Builder(getContext())
                        .icon(drawable)
                        .threshold(2)
                        .title("How was your experience with us?")
                        .titleTextColor(R.color.text_color_primary)
                        .ratingBarColor(R.color.btn_positive_text_color)
                        .playstoreUrl("https://play.google.com/store/apps/details?id=com.attendo")
                        .onThresholdCleared(new RatingDialog.Builder.RatingThresholdClearedListener() {
                            @Override
                            public void onThresholdCleared(RatingDialog ratingDialog, float rating, boolean thresholdCleared) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(thresholdCleared && rating>3){
                                            Toast.makeText(getContext(), "Thank You So Much", Toast.LENGTH_SHORT).show();
                                            gotoUrl("https://play.google.com/store/apps/details?id=com.attendo");
                                            ratingDialog.dismiss();
                                        }
                                        else if(thresholdCleared && rating<=3){
                                            Toast.makeText(getContext(), "Please Suggest Us", Toast.LENGTH_SHORT).show();
                                            gotoUrl("https://play.google.com/store/apps/details?id=com.attendo");
                                            ratingDialog.dismiss();
                                        }
                                    }
                                },600);
                            }
                        })
                        .build();
                ratingDialog.show();

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


        routine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = firebaseScheduleViewModel.RetrieveClassJoinAs();
                if(type == null){
                    Toast.makeText(getActivity(), "Please wait !", Toast.LENGTH_SHORT).show();
                }
                else {
                    switch (type) {
                        case "Cr":
                            CRSettingsFragment settingsFragment = new CRSettingsFragment();
                            settingsFragment.show(getParentFragmentManager(), "Cr Settings");
                            break;
                        case "Student":
                            StudetntSettingsFragment studetntSettingsFragment = new StudetntSettingsFragment();
                            studetntSettingsFragment.show(getParentFragmentManager(), "Students Settings");
                            break;
                        case  "nothing":
                            Toast.makeText(getActivity(), "Please join or create a class ", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(getActivity(), "Please wait !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/Attendo/";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }


        excel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExportToExcel(directory_path);
            }
        });




        //mobile ads

//        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//
//            }
//        });
//
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);



        return view;
    }

    public void ExportToExcel(String path)
    {
        SQLiteToExcel sqLiteToExcel = new SQLiteToExcel(getApplicationContext(),subDatabase.DATABASE_NAME,path);

        sqLiteToExcel.exportSingleTable("SubjectName", "student.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {
            }
            @Override
            public void onCompleted(String filePath) {
                //Toast.makeText(getApplicationContext(),"Successfully Exported",Toast.LENGTH_SHORT).show();
                Snackbar.make(getView(),"Successfully Exported. Please check the AttendO folder in your Internal Storage", Snackbar.LENGTH_LONG)
                        .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                        .show();

            }
            @Override
            public void onError(Exception e) {
                //Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
                Snackbar.make(getView(), "Oops, something went wrong !", Snackbar.LENGTH_SHORT)
                        .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                        .show();
            }
        });
    }




    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frame,fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }

    private void gotoUrl(String url){

        Uri uri = Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

}