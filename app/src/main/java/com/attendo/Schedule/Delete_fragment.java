package com.attendo.Schedule;

import android.database.DatabaseErrorHandler;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.attendo.R;
import com.attendo.Schedule.Adapters.RoutineItemAdapterCr;
import com.attendo.Schedule.Interface.UpdateRecyclerView;
import com.attendo.Schedule.Preference.AppPreferences;
import com.attendo.data.model.ScheduleDelete;
import com.attendo.data.model.SubjectDetails;
import com.attendo.viewmodel.FirebaseScheduleViewModel;
import com.attendo.viewmodel.ScheduleViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class Delete_fragment extends BottomSheetDialogFragment implements UpdateRecyclerView {

    private FirebaseScheduleViewModel firebaseScheduleViewModel;
    private ScheduleViewModel scheduleViewModel;
    String scheduleId;
    Button delete_btn;
    private AppPreferences appPreferences;

    String ScheduleClassId,class_id,day;

    public static Delete_fragment newInstance(String scheduleClassId) {
        Delete_fragment delete_fragment = new Delete_fragment();
        Bundle args = new Bundle();
        args.putString("SCHEDULE_CLASS_ID",scheduleClassId);
        delete_fragment.setArguments(args);
        return delete_fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ScheduleClassId = getArguments().getString("SCHEDULE_CLASS_ID");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delete_schedule, container, false);

        firebaseScheduleViewModel = new ViewModelProvider(this).get(FirebaseScheduleViewModel.class);
        scheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        delete_btn = view.findViewById(R.id.delete_button);


        appPreferences = new AppPreferences(getActivity());
        scheduleId = appPreferences.retrieveScheduleId();
        class_id = appPreferences.RetrieveClassId();

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_schedule(ScheduleClassId);
            }
        });



        return view;
    }



    @Override
    public void sendPosition(int position) {
        switch (position){
            case 0:
                day = "sunday";
                break;
            case 1:
                day = "monday";
                break;
            case 2:
                day = "tuesday";
                break;
            case 3:
                day = "wednesday";
                break;
            case 4:
                day = "thursday";
                break;
            case 5:
                day = "friday";
                break;
            case 6:
                day = "saturday";
                break;
        }


    }



    public void delete_schedule(String scheduleClassId)
    {
        ScheduleDelete scheduleDelete = new ScheduleDelete(scheduleId,"wednesday",scheduleClassId);
        scheduleViewModel.DeleteSchedule(scheduleDelete);
        //Toast.makeText(getActivity(),""+scheduleId+" wednesday "+scheduleClassId,Toast.LENGTH_SHORT).show();
        scheduleViewModel.getDeleteResponse().observe(getActivity(), data->
        {
            if (data == null) {
                Toast.makeText(getActivity(),"Fail to delete Subject",Toast.LENGTH_SHORT).show();
                Log.i("ApiCall", "Failed");

            } else {
                Toast.makeText(getActivity(),"Subject deleted successfully",Toast.LENGTH_SHORT).show();
                Log.i("ApiCall", "delete successFull");
            }
        });


    }


    @Override
    public void callback(int position, List<SubjectDetails> subjectRoutines) {

    }


}
