package com.attendo.Schedule.Adapters;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
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
import com.attendo.Schedule.Model.DayOfWeek;
import com.attendo.Schedule.Interface.UpdateRecyclerView;
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
    private FirebaseScheduleViewModel viewModelFirebase;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.weekday_card)
        MaterialCardView daycard;
        @BindView(R.id.day)
        TextView dayTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //finding the items and storing them in view holder
            ButterKnife.bind(this,itemView);
        }
    }

    public WeekDayAdapter(Context context, List<DayOfWeek> day, Activity activity, UpdateRecyclerView updateRecyclerView) {
        this.context = context;
        this.day = day;
        this.activity = activity;
        this.updateRecyclerView = updateRecyclerView;
        this.class_id = class_id;

        //view model for the api call
        getScheduleViewModel = new ViewModelProvider((BottomNavMainActivity) context).get(ScheduleViewModel.class);
        viewModelFirebase = new ViewModelProvider((BottomNavMainActivity)context).get(FirebaseScheduleViewModel.class);
        class_id = viewModelFirebase.RetrieveClassId();

        Log.d("ClassIdRVWEEKDAY", class_id + "  :retrieved");
    }

    @NonNull
    @Override
    public WeekDayAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.weekday_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekDayAdapter.MyViewHolder holder, int position) {
        holder.dayTextView.setText(day.get(position).getDayofWeek());

        holder.daycard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = position;
                notifyDataSetChanged();

                updateRecyclerView.sendPosition(position);
            }
        });

        if(index == position){

            holder.daycard.setStrokeWidth((int) 5f);
        }else{

            holder.daycard.setStrokeWidth((int) 0f);
        }

        if(check){
            getScheduleViewModel.setScheduleGetResponse(class_id, "sunday");
            getScheduleViewModel.getScheduleGetResponse().observe((LifecycleOwner) activity, data->{
                if(data == null){
                    Toast.makeText(context, "No data", Toast.LENGTH_SHORT).show();
                }else{
                    updateRecyclerView.callback(0, data.getRequiredSchedule());
                }
            });
            check = false;
        }
    }

    @Override
    public int getItemCount() {
        return day.size();
    }






}
