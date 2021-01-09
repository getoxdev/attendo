package com.attendo.Schedule;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private SharedPreferences sharedPreferences;

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
               deleteSharedpreference();
               // String userId = mAuth.getCurrentUser().getUid();
               // DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Schedule").child(userId);
                //ref.removeValue();
                Toast.makeText(getActivity(),"You left the Schedule",Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        return  view;
    }

    private void deleteSharedpreference() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("User",getContext().MODE_PRIVATE);
        sharedPreferences = this.getActivity().getSharedPreferences("User",getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Class_Code","----------");
        editor.putString("Class_Id","");
        editor.putString("Join_As","----------");
        editor.putString("Schedule_Id","");
        editor.apply();
    }
}