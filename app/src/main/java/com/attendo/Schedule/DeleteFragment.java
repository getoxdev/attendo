package com.attendo.Schedule;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.attendo.R;
import com.attendo.Schedule.Interface.UpdateRecyclerView;
import com.attendo.Schedule.Preference.AppPreferences;
import com.attendo.data.model.schedule.ScheduleDelete;
import com.attendo.viewmodel.FirebaseScheduleViewModel;
import com.attendo.viewmodel.ScheduleViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class DeleteFragment extends BottomSheetDialogFragment  {

    private FirebaseScheduleViewModel firebaseScheduleViewModel;
    private ScheduleViewModel scheduleViewModel;
    String scheduleId;
    Button delete_btn;
    private AppPreferences appPreferences;
    private UpdateRecyclerView updateRecyclerView;

    private String ScheduleClassId,class_id,day;
    private int mPositionDay;

    public DeleteFragment(UpdateRecyclerView updateRecyclerView){
        this.updateRecyclerView = updateRecyclerView;
    }

    public static DeleteFragment newInstance(String scheduleClassId, int positionDay, UpdateRecyclerView mUpdateRecyclerView) {
        DeleteFragment delete_fragment = new DeleteFragment(mUpdateRecyclerView);
        Bundle args = new Bundle();
        args.putString("SCHEDULE_CLASS_ID",scheduleClassId);
        args.putInt("RVPosition", positionDay);
        delete_fragment.setArguments(args);
        return delete_fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ScheduleClassId = getArguments().getString("SCHEDULE_CLASS_ID");
            mPositionDay = getArguments().getInt("RVPosition");
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
        LottieAnimationView deleteanim = view.findViewById(R.id.lottieAnimationView);
        deleteanim.setAnimation(R.raw.delete_animation);



        appPreferences = new AppPreferences(getActivity());
        scheduleId = appPreferences.retrieveScheduleId();

        class_id = appPreferences.RetrieveClassId();
        getDay(mPositionDay);

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("s_id",scheduleId);
                Log.e("c_id",class_id);

                deleteanim.pauseAnimation();
                deleteanim.setAnimation(R.raw.done_lottie_anim);
                deleteanim.playAnimation();
                delete_schedule(ScheduleClassId);
            }
        });



        return view;   }

    public void getDay(int position)
    {
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
                day ="thursday";
                break;
            case 5:
                day="friday";
                break;
            case 6:
                day = "saturday";
                break;
        }
    }

    public void delete_schedule(String scheduleClassId)
    {
        ScheduleDelete scheduleDelete = new ScheduleDelete(scheduleId,day,scheduleClassId);
        scheduleViewModel.DeleteSchedule(scheduleDelete);
        //Toast.makeText(getActivity(),""+scheduleId+"  "+day+" "+scheduleClassId,Toast.LENGTH_SHORT).show();
        scheduleViewModel.getDeleteResponse().observe(getActivity(), data->
        {
            if (data == null) {
                Toast.makeText(getActivity(),"Fail to delete Subject",Toast.LENGTH_SHORT).show();
                Log.i("ApiCall", "Failed");
                dismiss();
            } else {
                Toast.makeText(getActivity(),"Subject deleted successfully",Toast.LENGTH_SHORT).show();
                Log.i("ApiCall", "delete successFull");
                updateRecyclerView.sendPosition(mPositionDay);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                },900);

            }
        });


    }




}
