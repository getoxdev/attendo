package com.attendo.Schedule.Adapters;


import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.attendo.R;
import com.attendo.Schedule.Model.DayOfWeek;
import com.attendo.Schedule.Model.SubjectRoutine;
import com.attendo.Schedule.Interface.UpdateRecyclerView;
import com.attendo.data.model.GetSchedule;
import com.attendo.data.model.SubjectDetails;
import com.attendo.ui.main.BottomNavMainActivity;
import com.attendo.viewmodel.GetScheduleViewModel;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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
    private GetScheduleViewModel getScheduleViewModel;
    private DatabaseReference mReference;
    private FirebaseAuth mAuth;
    private String class_id;


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

        //view model for the api call
        getScheduleViewModel = new ViewModelProvider((BottomNavMainActivity) context).get(GetScheduleViewModel.class);
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
                   // Log.d("ClassIDMINE" , class_id);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });
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
            //getRequestToServerAndSetRecyclerView("sunday", position);
            updateRecyclerView.sendPosition(0);
            check = false;
        }
    }

    @Override
    public int getItemCount() {
        return day.size();
    }

    private void getRequestToServerAndSetRecyclerView(String day, int position){
        getScheduleViewModel.setScheduleGetResponse(new GetSchedule(class_id, day));
        getScheduleViewModel.getScheduleGetResponse().observe((LifecycleOwner) activity, data ->{
            if(data != null){
                List<SubjectDetails> routines;
                routines = data.getRequiredSchedule();
                updateRecyclerView.callback(position, routines);
            }else{
                Toast.makeText(context, "Hurry! no class today", Toast.LENGTH_SHORT).show();
            }
        });
    }




}
