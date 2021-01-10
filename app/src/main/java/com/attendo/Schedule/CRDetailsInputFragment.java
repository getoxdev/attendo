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
import com.attendo.data.model.CreateClass;
import com.attendo.ui.CustomLoadingDialog;
import com.attendo.viewmodel.AddScheduleViewModel;
import com.attendo.viewmodel.CreateClassViewModel;
import com.attendo.viewmodel.FirebaseScheduleViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CRDetailsInputFragment extends Fragment {

    private EditText name,scholarId,ClassName;
    private Button create;
    private CreateClassViewModel createClassViewModel;
    private CrFragment crFragment;
    private String class_code;
    private String class_Id;
    private FirebaseAuth mAuth;
    private CustomLoadingDialog customLoadingDialog;
    private SharedPreferences sharedPreferences;
    private FirebaseScheduleViewModel firebaseScheduleViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cr_details_input, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Create Class");

        mAuth = FirebaseAuth.getInstance();
        firebaseScheduleViewModel = new ViewModelProvider(this).get(FirebaseScheduleViewModel.class);

        createClassViewModel =  new ViewModelProvider(this).get(CreateClassViewModel.class);

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
                    class_code = SendDataToServer();
                    customLoadingDialog.startDialog(false);
                }
                else{
                    Toast.makeText(getActivity(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
                }
            }
        });



        return view;
    }

    private String  SendDataToServer() {
        CreateClass createClass = new CreateClass(name.getText().toString(),mAuth.getCurrentUser().getEmail().toString(),ClassName.getText().toString(),scholarId.getText().toString());
        createClassViewModel.setClassResponse(createClass);
        createClassViewModel.getClassResponse().observe(getActivity(), data -> {
            if (data == null) {
                customLoadingDialog.dismissDialog();
                Toast.makeText(getActivity(),"Fail to Create",Toast.LENGTH_SHORT).show();
                Log.i("ApiCall", "Failed");
            } else {
                customLoadingDialog.dismissDialog();
                Log.i("ApiCall", "successFull");
                class_code = data.get_class().getCode();
                class_Id = data.get_class().get_id();
                firebaseScheduleViewModel.AddCLassCode(class_code);
                firebaseScheduleViewModel.AddClassId(class_Id);
                firebaseScheduleViewModel.AddClassJoinAs("Cr");
                //sharedPreferences = this.getActivity().getSharedPreferences("User",getContext().MODE_PRIVATE);
                //SharedPreferences.Editor editor = sharedPreferences.edit();
                //editor.putString("Class_Code",class_code);
                //editor.putString("Class_Id",class_Id);
                //editor.putString("Join_As","CR");
                //editor.apply();
                //databaseReference.child(UserId).child("Class_Code").setValue(class_code);
                //databaseReference.child(UserId).child("Class_Id").setValue(class_Id);
                //databaseReference.child(UserId).child("Join_As").setValue("Cr");
                Toast.makeText(getContext(),"Class Created" +" "+ class_code,Toast.LENGTH_LONG).show();
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