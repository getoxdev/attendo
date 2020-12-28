package com.attendo.Schedule;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.attendo.R;
import com.attendo.ui.main.BottomNavMainActivity;
import com.attendo.ui.main.BottomNavMainActivity_ViewBinding;
import com.attendo.ui.main.drawers.account.FragmentAccountAndSettings;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CRSettingsFragment extends BottomSheetDialogFragment {

    TextView text;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CRSettingsFragment() {
        // Required empty public constructor
    }
 public static CRSettingsFragment newInstance(String param1, String param2) {
        CRSettingsFragment fragment = new CRSettingsFragment();
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
        View view = inflater.inflate(R.layout.fragment_c_r_settings, container, false);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Schedule");
        text = view.findViewById(R.id.cr_leave_class_settings);

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = mAuth.getCurrentUser().getUid();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Schedule").child(userId);
                ref.removeValue();
                Toast.makeText(getActivity(),"You leaved the Schedule",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), BottomNavMainActivity.class);
                startActivity(intent);
            }
        });

        return  view;
    }
}