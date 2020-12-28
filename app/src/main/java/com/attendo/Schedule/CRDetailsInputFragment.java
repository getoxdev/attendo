package com.attendo.Schedule;

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
import com.attendo.data.model.Class;
import com.attendo.data.model.CreateClass;
import com.attendo.viewmodel.CreateClassViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.atomic.AtomicReference;

public class CRDetailsInputFragment extends Fragment {

    private EditText name,scholarId,ClassName,Email;
    private Button create;
    private CreateClassViewModel createClassViewModel;
    private CrFragment crFragment;
    private String class_code;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public CRDetailsInputFragment() {
        // Required empty public constructor
    }

    public static CRDetailsInputFragment newInstance(String param1, String param2) {
        CRDetailsInputFragment fragment = new CRDetailsInputFragment();
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
        View view = inflater.inflate(R.layout.fragment_cr_details_input, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Create Class");

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Schedule");

       createClassViewModel =  new ViewModelProvider(this).get(CreateClassViewModel.class);

        crFragment = new CrFragment();
        name = view.findViewById(R.id.cr_name_edittext);
        scholarId = view.findViewById(R.id.cr_scholar_id_edittext);
        Email = view.findViewById(R.id.cr_email_edittext);
        ClassName = view.findViewById(R.id.cr_class_name_edittext);

        create = view.findViewById(R.id.cr_create_class_btn);


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString();
                String Scholarid = scholarId.getText().toString();
                String EmailId = Email.getText().toString();
                String Class = ClassName.getText().toString();
                if(Name.length()>0 && Scholarid.length()>0 && EmailId.length()>0 && Class.length()>0){
                    class_code = SendDataToServer();
                }
                else{
                    Toast.makeText(getActivity(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
                }
            }
        });



        return view;
    }

    private String  SendDataToServer() {
        CreateClass createClass = new CreateClass(name.getText().toString(),Email.getText().toString(),ClassName.getText().toString(),scholarId.getText().toString());
        createClassViewModel.setClassResponse(createClass);
        createClassViewModel.getClassResponse().observe(getActivity(), data -> {
            if (data == null) {
                Toast.makeText(getActivity(),"Fail to Create",Toast.LENGTH_SHORT).show();
                Log.i("ApiCall", "Failed");
            } else {
                Log.i("ApiCall", "successFull");
                setFragment(crFragment);
                class_code = data.get_class().getCode();
                String UserId = mAuth.getCurrentUser().getUid();
                databaseReference.child(UserId).child("Class_Code").setValue(class_code);
                databaseReference.child(UserId).child("Join_As").setValue("Cr");
                Toast.makeText(getContext(),"Class Created" + class_code,Toast.LENGTH_LONG).show();
            }
        });

        return class_code;
    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frame,fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }


}