package com.attendo.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ajts.androidmads.library.SQLiteToExcel;
import com.attendo.NewFeatureReleaseFragment;
import com.attendo.R;
import com.attendo.Schedule.CrFragment;
import com.attendo.Schedule.CreateAndJoinClassBottomSheetDialogFragment;
import com.attendo.Schedule.StudentFragment;
import com.attendo.data.database.SubDatabase;
import com.attendo.ui.calendar.FragmentCalender;
import com.attendo.ui.main.drawers.reminder.FragmentExamReminder;

import com.attendo.ui.main.drawers.account.FragmentAccountAndSettings;
import com.attendo.ui.main.menu.FragmentAbout;
import com.attendo.ui.sub.Fragment_Subject;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.android.material.transition.platform.MaterialFade;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomNavMainActivity extends AppCompatActivity {



    @BindView(R.id.bottom_nav_bar)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.container_frame)
    FrameLayout frameLayout;

    @BindView(R.id.toolbar_bottom_nav)
    Toolbar toolbar;

    SubDatabase subDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private CrFragment crFragment;
    private StudentFragment studentFragment;
    private EditText joinas;

    private String joinasData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setEnterTransition(new MaterialFade().setDuration(300));
        getWindow().setExitTransition(new MaterialFade().setDuration(300));
        setContentView(R.layout.activity_bottom_nav_main);
        ButterKnife.bind(this);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Schedule");
        crFragment = new CrFragment();
        studentFragment = new StudentFragment();
        joinas = findViewById(R.id.Join_As);

        bottomNavigationView.setOnNavigationItemSelectedListener(selectedListener);
        setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction().replace(R.id.container_frame, new Fragment_Subject()).commit();

        try {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // this will request for permission from the user if not yet granted
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {

                System.out.println("ERROR!!");
            }
        }catch (Exception e){
            System.out.println(e);
        }


        //for checking the snapshot for user.
        checkUserJoinedAs();

    }

    private void checkUserJoinedAs() {
        databaseReference.orderByKey().equalTo(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    joinasData = snapshot.child(mAuth.getCurrentUser().getUid()).child("Join_As").getValue(String.class);
                    //Log.d("Join", joinasData);

                } else {
                    joinasData = "nothing";
                    //Log.d("Join As Data", joinasData);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BottomNavMainActivity.this,""+error,Toast.LENGTH_SHORT).show();
            }
        });
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

                case R.id.schedule_bottom_nav:
                    Fragment newfeatureFragment = new NewFeatureReleaseFragment();
                    setFragment(newfeatureFragment);
/*                    if(!isConnected()){
                        showCustomDialog();
                    }else{
                        if(joinasData == null){
                            Toast.makeText(BottomNavMainActivity.this, "Please wait !", Toast.LENGTH_SHORT).show();
                        }else{
                            switch (joinasData){
                                case "Cr":
                                    setFragment(crFragment);
                                    break;
                                case "Student":
                                    setFragment(studentFragment);
                                    break;
                                case "nothing":
                                    CreateAndJoinClassBottomSheetDialogFragment joinClassBottomSheetDialogFragment = new CreateAndJoinClassBottomSheetDialogFragment();
                                    joinClassBottomSheetDialogFragment.show(getSupportFragmentManager(), "Create Class and Join Class");
                                    break;
                                default:
                                    Toast.makeText(BottomNavMainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }


                    }*/
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {

        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/Attendo/";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }

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

    private void setFragment(Fragment fragment) {
        MaterialSharedAxis enter = new MaterialSharedAxis(MaterialSharedAxis.Z, true);
        MaterialSharedAxis exit = new MaterialSharedAxis(MaterialSharedAxis.Z, false);
        enter.setDuration(400);
        enter.setInterpolator(new AccelerateDecelerateInterpolator());
        exit.setDuration(400);
        exit.setInterpolator(new AccelerateDecelerateInterpolator());
        fragment.setEnterTransition(enter);
        fragment.setEnterTransition(exit);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frame,fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected()) ){
            return true;
        }else{
            return false;
        }
    }

    private void showCustomDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please connect to internet to get access to routine");
        builder.setTitle("No Data Connection");
        builder.setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        builder.show();
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
                Toast.makeText(getApplicationContext(),"Successfully Exported",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
            }
        });
    }

}