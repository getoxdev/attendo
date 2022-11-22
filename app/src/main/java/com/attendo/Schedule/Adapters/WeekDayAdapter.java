package com.attendo.Schedule.Adapters;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.attendo.R;
import com.attendo.data.model.schedule.DayOfWeek;
import com.attendo.Schedule.Interface.UpdateRecyclerView;
import com.attendo.Schedule.Preference.AppPreferences;
import com.attendo.databinding.WeekdayCardBinding;
import com.attendo.ui.main.BottomNavMainActivity;
import com.attendo.viewmodel.FirebaseScheduleViewModel;
import com.attendo.viewmodel.ScheduleViewModel;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeekDayAdapter extends RecyclerView.Adapter<WeekDayAdapter.MyViewHolder> {

    Context context;
    List<DayOfWeek> day;
    int index=0;
    UpdateRecyclerView updateRecyclerView;
    Activity activity;
    boolean check = true;
    boolean select = true;
    private ScheduleViewModel getScheduleViewModel;
    private DatabaseReference mReference;
    private FirebaseAuth mAuth;
    private String class_id;
    private FirebaseScheduleViewModel firebaseScheduleViewModel;
    private AppPreferences appPreferences;


    public class MyViewHolder extends RecyclerView.ViewHolder {


//        @BindView(R.id.weekday_card)
//        MaterialCardView daycard;
//        @BindView(R.id.day)
//        TextView dayTextView;



//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            //finding the items and storing them in view holder
//            ButterKnife.bind(this,itemView);
//
//        }

        WeekdayCardBinding binding;
        public MyViewHolder(@NonNull WeekdayCardBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
    }

    public WeekDayAdapter(Context context, List<DayOfWeek> day, Activity activity, UpdateRecyclerView updateRecyclerView) {
        this.context = context;
        this.day = day;
        this.activity = activity;
        this.updateRecyclerView = updateRecyclerView;


        appPreferences = AppPreferences.getInstance(context);

        //view model for the api call

        getScheduleViewModel = new ViewModelProvider((BottomNavMainActivity)context).get(ScheduleViewModel.class);
        mAuth = FirebaseAuth.getInstance();
        mReference = FirebaseDatabase.getInstance().getReference("Schedule");
        mReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    class_id = snapshot.child("Class_Id").getValue(String.class);
                    //Log.d("ClassIDMINE" , class_id);
                }else {
                    class_id = null;
                    //Log.d("ClassIDMINE" , class_id);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    @NonNull
    @Override
    public WeekDayAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)  {

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        WeekdayCardBinding binding =WeekdayCardBinding.inflate(layoutInflater,parent,false);
        //View view = binding.getRoot();
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekDayAdapter.MyViewHolder holder, int position) {

        holder.binding.day.setText(day.get(position).getDayofWeek());
        DayOfWeek dayOfWeek = day.get(position);

        holder.binding.weekdayCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = position;
                notifyDataSetChanged();
                updateRecyclerView.sendPosition(position);
            }
        });


        if(index == position){

            holder.binding.weekdayCard.setStrokeWidth((int) 5f);
        }else{

            holder.binding.weekdayCard.setStrokeWidth((int) 0f);
        }

        if(check){
            updateRecyclerView.sendPosition(0);
//            getScheduleViewModel.setScheduleGetResponse(appPreferences.RetrieveClassId(),"sunday");
//            getScheduleViewModel.getScheduleGetResponse().observe((LifecycleOwner) activity, data->
//            {
//                if(data==null)
//                {
//                    Toast.makeText(context,"No data",Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    updateRecyclerView.callback(0,data.getRequiredSchedule());
//                }
//            });
            check = false;
        }
    }

    @Override
    public int getItemCount() {
        return day.size();
    }






}
