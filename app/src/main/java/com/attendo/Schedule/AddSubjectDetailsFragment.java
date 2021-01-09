package com.attendo.Schedule;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.attendo.R;
import com.attendo.Schedule.Interface.UpdateRecyclerView;
import com.attendo.Schedule.Model.SubjectRoutine;
import com.attendo.data.model.CreateClass;
import com.attendo.data.model.Schedule;
import com.attendo.viewmodel.AddScheduleViewModel;
import com.attendo.viewmodel.CreateClassViewModel;
import com.attendo.viewmodel.GetScheduleViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AddSubjectDetailsFragment extends BottomSheetDialogFragment implements AdapterView.OnItemSelectedListener {


    private EditText subject, faculty, time;
    private Button submit;
    private LottieAnimationView celebration;
    private Spinner spi;
    public String day;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private AddScheduleViewModel addScheduleViewModel;
    private SharedPreferences sharedPreferences;

    private ProgressBar PB;

    //class Id
    private String class_Id;

    //check if data is sent to server
    private boolean check = false;


    @Override
    public void onStart() {
        super.onStart();
        //initial setting of data
        addScheduleViewModel = new ViewModelProvider(this).get(AddScheduleViewModel.class);
        getClassId();
    }


    private void getClassId(){
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Schedule");
        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    class_Id = snapshot.child("Class_Id").getValue(String.class);
                    //Log.d("ClassIDMINE" , class_id);
                }else {
                    class_Id = null;
                    // Log.d("ClassIDMINE" , class_id);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Database Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_subject_details, container, false);



        PB = view.findViewById(R.id.progress_bar_add_subject_details);
        PB.setVisibility(View.INVISIBLE);
        spi = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.weekday, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spi.setAdapter(adapter);
        spi.setOnItemSelectedListener(this);

        celebration = view.findViewById(R.id.lottie_animation_add_subject_details);
        celebration.setVisibility(View.INVISIBLE);

        subject = view.findViewById(R.id.add_subject_bottomsheet);
        faculty = view.findViewById(R.id.add_faculty);
        time = view.findViewById(R.id.add_Time);
        submit = view.findViewById(R.id.add_subject_btn);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sub = subject.getText().toString();
                String teacher = faculty.getText().toString();
                String clock = time.getText().toString();
                if (sub.length() > 0 && teacher.length() > 0 && clock.length() > 0 && day.length() > 0) {
                    //checkUser();
                    PB.setVisibility(View.VISIBLE);
                    sendDataToServer();
                    if(check){
                        celebration.setVisibility(View.VISIBLE);
                        celebration.playAnimation();
                        dismiss();
                        check = false;
                    }else{
                        Toast.makeText(getContext(), "Sending Data...", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    PB.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(), "Please fill all details", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        day = parent.getItemAtPosition(position).toString();
        day = day.toLowerCase();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

//    private void checkUser() {
//        databaseReference.orderByKey().equalTo(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    //classid = snapshot.child(mAuth.getCurrentUser().getUid()).child("Class_Id").getValue(String.class);
//                    //Toast.makeText(getActivity(),""+classid+" & "+text+" & "+time.getText().toString()+" & "+subject.getText().toString()+" & "+faculty.getText().toString(),Toast.LENGTH_LONG).show();
//                    //Schedule schedule = new Schedule(classid, text, time.getText().toString(), subject.getText().toString(), faculty.getText().toString());
//                    //addScheduleViewModel.setScheduleResponse(schedule);
//                    addScheduleViewModel.getScheduleResponse().observe(getActivity(), data -> {
//                        if (data == null) {
//                           // Toast.makeText(getActivity(),"Fail to Add Schedule",Toast.LENGTH_SHORT).show();
//                            Log.i("ApiCall", "Failed");
//                        } else {
//                            Log.i("ApiCall", "successFull");
//                            String scheduleId = data.getSchedule().get_id();
//                            databaseReference.child(mAuth.getCurrentUser().getUid()).child("Schedule_Id").setValue(scheduleId);
//                            Toast.makeText(getActivity(),"Schedule Added Successfully",Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                } else {
//                    //Nothing to show here
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                //Toast.makeText(getActivity(),""+error,Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void sendDataToServer(){
        if(class_Id != null){
            Schedule schedule = new Schedule(class_Id, day, time.getText().toString(), subject.getText().toString(), faculty.getText().toString());
            addScheduleViewModel.setScheduleResponse(schedule);
            addScheduleViewModel.getScheduleResponse().observe(getActivity(), data -> {
                if (data == null) {
                    Toast.makeText(getActivity(),"Fail to Add Schedule",Toast.LENGTH_SHORT).show();
                    Log.i("ApiCall", "Failed");
                    PB.setVisibility(View.INVISIBLE);
                    check = true;
                } else {
                    PB.setVisibility(View.INVISIBLE);
                    Log.i("ApiCall", "successFull");
                    String scheduleId = data.getSchedule().get_id();
                    sharedPreferences = this.getActivity().getSharedPreferences("User",getContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Schedule_Id",scheduleId);
                    editor.apply();
                    Toast.makeText(getActivity(),"Schedule Added Successfully",Toast.LENGTH_SHORT).show();
                    celebration.setVisibility(View.VISIBLE);
                    celebration.playAnimation();
                    check = true;
                    Handler mhandler = new Handler();
                    mhandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();

                        }
                    },600);
                }
            });
        }else{
            Toast.makeText(getContext(), "Please Wait!", Toast.LENGTH_SHORT).show();
        }

    }

}