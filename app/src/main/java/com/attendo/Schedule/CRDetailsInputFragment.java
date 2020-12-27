package com.attendo.Schedule;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.attendo.R;
import com.attendo.Schedule.Model.CreateClassViewModel;
import com.attendo.data.model.CreateClass;
import com.attendo.viewmodel.ReminderViewModel;

public class CRDetailsInputFragment extends Fragment {

    private EditText name,scholarId,ClassName,Email;
    private Button create;
    private CreateClassViewModel viewModel;
    private CrFragment crFragment;

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

        viewModel = ViewModelProviders.of(getActivity()).get(CreateClassViewModel.class);;

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
                    SendDataToServer();
                }
                else{
                    Toast.makeText(getActivity(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void SendDataToServer() {
        setFragment(crFragment);
        /*CreateClass createClass = new CreateClass(name.getText().toString(),Email.getText().toString(),ClassName.getText().toString(),scholarId.getText().toString());
        viewModel.setClassData(createClass);
        viewModel.getClassResponse().observe(getActivity(), data -> {
            if (data == null) {
                Log.i("ApiCall", "Failed");
            } else {

                Log.i("ApiCall", "successFull");
            }
        });*/
    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frame,fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }


}