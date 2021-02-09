package com.attendo.Schedule;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.attendo.R;
import com.attendo.Schedule.Preference.AppPreferences;
import com.attendo.data.model.schedule.FcmToken;
import com.attendo.data.model.schedule.JoinClass;
import com.attendo.ui.CustomLoadingDialog;
import com.attendo.ui.main.BottomNavMainActivity;
import com.attendo.viewmodel.FirebaseScheduleViewModel;
import com.attendo.viewmodel.ScheduleViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class StudentDetailsInputFragment extends Fragment {

    private EditText name,scholarid,classcode;
    private Button btn;
    private StudentFragment studentFragment;
    private ScheduleViewModel scheduleViewModel;
    private FirebaseAuth mAuth;
    private CustomLoadingDialog customLoadingDialog;
    private FirebaseScheduleViewModel firebaseScheduleViewModel;
    private AppPreferences appPreferences;

    private String fcmToken;
    private String retreiveFcmToken;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student_details_input, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Join Class");

        mAuth = FirebaseAuth.getInstance();
        firebaseScheduleViewModel = new ViewModelProvider(this).get(FirebaseScheduleViewModel.class);
        appPreferences = AppPreferences.getInstance(getContext());

        //FCM Token
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(getActivity(), new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                fcmToken = instanceIdResult.getToken();
            }
        });

        scheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);

        customLoadingDialog = new CustomLoadingDialog(getActivity());
        studentFragment = new StudentFragment();
        name = view.findViewById(R.id.student_name_edittext);
        scholarid = view.findViewById(R.id.student_scholar_id_edittext);
        classcode = view.findViewById(R.id.student_class_code_edittext);
        btn = view.findViewById(R.id.student_join_class_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString();
                String Scholarid = scholarid.getText().toString();
                String EmailId = mAuth.getCurrentUser().getEmail().toString();
                String Class = classcode.getText().toString();
                if(Name.length()>0 && Scholarid.length()>0 && EmailId.length()>0 && Class.length()>0){
                    if(fcmToken.length() == 0){
                        Toast.makeText(getActivity(),"Please wait and try again!",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        SendDataToServer();
                        customLoadingDialog.startDialog(false);
                    }
                }
                else{
                    Toast.makeText(getActivity(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void SendDataToServer() {
        JoinClass joinClass = new JoinClass(classcode.getText().toString(),name.getText().toString(),mAuth.getCurrentUser().getEmail().toString(),scholarid.getText().toString(),fcmToken);
        scheduleViewModel.setJoinResponse(joinClass);
        scheduleViewModel.getJoinResponse().observe(getActivity(), data -> {
            if (data == null) {
                customLoadingDialog.dismissDialog();
                Toast.makeText(getActivity(),"Failed to join wrong class code",Toast.LENGTH_SHORT).show();
                Log.i("ApiCall", "Failed");
            } else {
                AddFcmToServer(fcmToken);
                customLoadingDialog.dismissDialog();
                String class_Id = data.get_class().get_id();
                firebaseScheduleViewModel.AddClassId(class_Id);
                firebaseScheduleViewModel.AddClassJoinAs("Student");
                firebaseScheduleViewModel.AddFcmCode(fcmToken);
                appPreferences.AddFcm(fcmToken);
                appPreferences.AddJoinAs("Student");
                appPreferences.AddClassId(class_Id);
                firebaseScheduleViewModel.AddCLassCode(classcode.getText().toString());
                Log.i("ApiCall", "successFull");
                Toast.makeText(getActivity(),"" + data.getMessage(),Toast.LENGTH_SHORT).show();
                setFragment(studentFragment);
            }
        });
    }

    private void AddFcmToServer(String FCMTOKEN) {
        String email = mAuth.getCurrentUser().getEmail();
        FcmToken fcmToken = new FcmToken(email,FCMTOKEN);
        scheduleViewModel.updateFcm(fcmToken);
        scheduleViewModel.updateFcmResponse().observe(getActivity(), data -> {
            if (data == null) {
                Toast.makeText(getActivity(),"Something went wrong please try again later",Toast.LENGTH_SHORT).show();
                Log.i("ApiCall", "Failed");
            } else {
                Log.i("ApiCall", "successFull");
                appPreferences.AddClassScheduleId(firebaseScheduleViewModel.RetrieveSchdeuleId());
                firebaseScheduleViewModel.AddFcmCode(FCMTOKEN);
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frame,fragment);
        fragmentTransaction.commit();
    }
}