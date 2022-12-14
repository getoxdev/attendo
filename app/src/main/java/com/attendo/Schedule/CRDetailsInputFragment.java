package com.attendo.Schedule;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import com.attendo.R;
import com.attendo.Schedule.Preference.AppPreferences;
import com.attendo.data.model.schedule.CreateClass;
import com.attendo.ui.CustomLoadingDialog;
import com.attendo.viewmodel.FirebaseScheduleViewModel;
import com.attendo.viewmodel.ScheduleViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

public class CRDetailsInputFragment extends Fragment {

    private EditText name,scholarId,ClassName;
    private Button create;
    private ScheduleViewModel scheduleViewModel;
    private CrFragment crFragment;
    private String class_code;
    private String class_Id;
    private FirebaseAuth mAuth;
    private CustomLoadingDialog customLoadingDialog;
    private FirebaseScheduleViewModel firebaseScheduleViewModel;
    private AppPreferences appPreferences;
    private String fcmToken;
    private String retreiveFcmToken;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cr_details_input, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Create Class");

        mAuth = FirebaseAuth.getInstance();
        firebaseScheduleViewModel = new ViewModelProvider(this).get(FirebaseScheduleViewModel.class);
        appPreferences = AppPreferences.getInstance(getContext());

        //FCM Token
//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(getActivity(), new OnSuccessListener<InstanceIdResult>() {
//            @Override
//            public void onSuccess(InstanceIdResult instanceIdResult) {
//                fcmToken = instanceIdResult.getToken();
//            }
//        });

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(getActivity(), new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(task.isComplete()) {
                    fcmToken = task.getResult();
                }
            }
        });

        scheduleViewModel =  new ViewModelProvider(this).get(ScheduleViewModel.class);

        customLoadingDialog = new CustomLoadingDialog(getActivity());
        crFragment = new CrFragment();
        name = view.findViewById(R.id.cr_name_edittext);
        scholarId = view.findViewById(R.id.cr_scholar_id_edittext);
        ClassName = view.findViewById(R.id.cr_class_name_edittext);

        create = view.findViewById(R.id.cr_create_class_btn);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString();
                String Scholarid = scholarId.getText().toString();
                String EmailId = mAuth.getCurrentUser().getEmail().toString();
                String Class = ClassName.getText().toString();
                if(Name.length()>0 && Scholarid.length()>0 && EmailId.length()>0 && Class.length()>0){
                    if(fcmToken.length()>0) {
                        class_code = SendDataToServer();
                        customLoadingDialog.startDialog(false);
                    }
                    else{
                        Toast.makeText(getActivity(),"Please wait and try again!",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getActivity(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private String  SendDataToServer() {
        Log.i("FCM CODE :",""+fcmToken);
        CreateClass createClass = new CreateClass(name.getText().toString(),mAuth.getCurrentUser().getEmail().toString(),ClassName.getText().toString(),scholarId.getText().toString(),fcmToken);
        scheduleViewModel.setClassResponse(createClass);
        scheduleViewModel.getClassResponse().observe(getActivity(), data -> {
            if (data == null) {
                customLoadingDialog.dismissDialog();
                Toast.makeText(getActivity(),"Fail to Create",Toast.LENGTH_SHORT).show();
                Log.i("ApiCall", "Failed");
            } else {
                customLoadingDialog.dismissDialog();
                Log.i("ApiCall", "successFull");
                //SetSharedPreferenceData();
                class_code = data.get_class().getCode();
                class_Id = data.get_class().get_id();
                Log.i("classid",class_Id);
                firebaseScheduleViewModel.AddCLassCode(class_code);
                firebaseScheduleViewModel.AddClassId(class_Id);
                firebaseScheduleViewModel.AddFcmCode(fcmToken);
                appPreferences.AddFcm(fcmToken);
                appPreferences.AddClassId(class_Id);
                appPreferences.AddJoinAs("Cr");
                firebaseScheduleViewModel.AddClassJoinAs("Cr");
                Toast.makeText(getActivity(),"Class Created" +" "+ class_code,Toast.LENGTH_LONG).show();
                setFragment(crFragment);
            }
        });

        return class_code;
    }



    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frame,fragment);
        fragmentTransaction.commit();
    }


}