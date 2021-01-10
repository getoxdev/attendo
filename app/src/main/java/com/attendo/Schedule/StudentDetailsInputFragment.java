package com.attendo.Schedule;

import android.content.SharedPreferences;
import android.graphics.Paint;
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
import com.attendo.data.model.CreateClass;
import com.attendo.data.model.JoinClass;
import com.attendo.ui.CustomLoadingDialog;
import com.attendo.viewmodel.CreateClassViewModel;
import com.attendo.viewmodel.FirebaseScheduleViewModel;
import com.attendo.viewmodel.JoinClassViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentDetailsInputFragment extends Fragment {

    private EditText name,scholarid,classcode;
    private Button btn;
    private StudentFragment studentFragment;
    private JoinClassViewModel joinClassViewModel;
    private FirebaseAuth mAuth;
    private CustomLoadingDialog customLoadingDialog;
    private FirebaseScheduleViewModel firebaseScheduleViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student_details_input, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Join Class");

        mAuth = FirebaseAuth.getInstance();
        firebaseScheduleViewModel = new ViewModelProvider(this).get(FirebaseScheduleViewModel.class);

        studentFragment = new StudentFragment();
        joinClassViewModel = new ViewModelProvider(this).get(JoinClassViewModel.class);
        customLoadingDialog = new CustomLoadingDialog(getActivity());

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
                    SendDataToServer();
                    customLoadingDialog.startDialog(false);
                }
                else{
                    Toast.makeText(getActivity(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void SendDataToServer() {
        JoinClass joinClass = new JoinClass(classcode.getText().toString(),name.getText().toString(),mAuth.getCurrentUser().getEmail().toString(),scholarid.getText().toString());
        joinClassViewModel.setJoinResponse(joinClass);
        joinClassViewModel.getJoinResponse().observe(getActivity(), data -> {
            customLoadingDialog.dismissDialog();
            if (data == null) {
                Toast.makeText(getActivity(),"Failed to join wrong class code",Toast.LENGTH_SHORT).show();
                Log.i("ApiCall", "Failed");
            } else {
                String class_Id = data.get_class().get_id();
                firebaseScheduleViewModel.AddClassId(class_Id);
                firebaseScheduleViewModel.AddClassJoinAs("Student");
                firebaseScheduleViewModel.AddCLassCode(classcode.getText().toString());
                Log.i("ApiCall", "successFull");
                Toast.makeText(getContext(),"" + data.getMessage(),Toast.LENGTH_SHORT).show();
                setFragment(studentFragment);
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frame,fragment);
        fragmentTransaction.commit();
    }

}