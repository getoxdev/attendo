package com.attendo.Schedule;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.attendo.R;
import com.attendo.ui.main.BottomNavMainActivity;
import com.attendo.viewmodel.FirebaseScheduleViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudetntSettingsFragment extends BottomSheetDialogFragment {

    private TextView text;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;
    private FirebaseScheduleViewModel firebaseScheduleViewModel;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_studetnt_settings, container, false);

        mAuth = FirebaseAuth.getInstance();
        firebaseScheduleViewModel = new ViewModelProvider(this).get(FirebaseScheduleViewModel.class);
        databaseReference = FirebaseDatabase.getInstance().getReference("Schedule");
        text = view.findViewById(R.id.leaveClass_student_settings);

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = mAuth.getCurrentUser().getUid();
                deleteSharedpreference();
               // DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Schedule").child(userId);
                //ref.removeValue();
                Toast.makeText(getActivity(),"You left the Schedule",Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        return view;
    }

    private void deleteSharedpreference() {
        firebaseScheduleViewModel.DeleteShedule();
        //SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("User",getContext().MODE_PRIVATE)
        // sharedPreferences = this.getActivity().getSharedPreferences("User",getContext().MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putString("Class_Code","----------");
        //editor.putString("Class_Id","");
        //editor.putString("Join_As","----------");
        //editor.putString("Schedule_Id","");
        //editor.apply();
    }

}