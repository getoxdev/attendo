package com.attendo.Schedule.Adapters;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.attendo.R;
import com.attendo.Schedule.Model.DayOfWeek;
import com.attendo.Schedule.Model.SubjectRoutine;
import com.attendo.Schedule.Interface.UpdateRecyclerView;
import com.google.android.material.card.MaterialCardView;

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


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.weekday_card)
        MaterialCardView daycard;
        @BindView(R.id.day)
        TextView dayTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //finding the items and storing them in viewholder
            ButterKnife.bind(this,itemView);
        }
    }

    public WeekDayAdapter(Context context, List<DayOfWeek> day, Activity activity, UpdateRecyclerView updateRecyclerView) {
        this.context = context;
        this.day = day;
        this.activity = activity;
        this.updateRecyclerView = updateRecyclerView;
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

                if(position == 0){
                    ArrayList<SubjectRoutine> routines = new ArrayList<SubjectRoutine>();
                    routines.add(new SubjectRoutine("Maths", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Maths", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Maths", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Maths", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Maths", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Maths", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Maths", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Maths", "7:00 pm", "Jyotimoy"));

                    updateRecyclerView.callback(position, routines);
                }
                else if(position == 1 ){
                    ArrayList<SubjectRoutine> routines = new ArrayList<SubjectRoutine>();
                    routines.add(new SubjectRoutine("English", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("English", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("English", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("English", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("English", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("English", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("English", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("English", "7:00 pm", "Jyotimoy"));

                    updateRecyclerView.callback(position, routines);
                }
                else if(position == 2){
                    ArrayList<SubjectRoutine> routines = new ArrayList<SubjectRoutine>();
                    routines.add(new SubjectRoutine("Science", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Science", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Science", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Science", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Science", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Science", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Science", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Science", "7:00 pm", "Jyotimoy"));

                    updateRecyclerView.callback(position, routines);
                }else if(position == 3){
                    ArrayList<SubjectRoutine> routines = new ArrayList<SubjectRoutine>();
                    routines.add(new SubjectRoutine("Biology", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Biology", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Biology", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Biology", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Biology", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Biology", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Biology", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Biology", "7:00 pm", "Jyotimoy"));

                    updateRecyclerView.callback(position, routines);
                }else if(position == 4){
                    ArrayList<SubjectRoutine> routines = new ArrayList<SubjectRoutine>();
                    routines.add(new SubjectRoutine("Physics", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Physics", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Physics", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Physics", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Physics", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Physics", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Physics", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Physics", "7:00 pm", "Jyotimoy"));

                    updateRecyclerView.callback(position, routines);
                }else if(position == 5){
                    ArrayList<SubjectRoutine> routines = new ArrayList<SubjectRoutine>();
                    routines.add(new SubjectRoutine("Hindi", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Hindi", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Hindi", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Hindi", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Hindi", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Hindi", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Hindi", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Hindi", "7:00 pm", "Jyotimoy"));

                    updateRecyclerView.callback(position, routines);
                }else if(position == 6){
                    ArrayList<SubjectRoutine> routines = new ArrayList<SubjectRoutine>();
                    routines.add(new SubjectRoutine("Chemistry", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Chemistry", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Chemistry", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Chemistry", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Chemistry", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Chemistry", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Chemistry", "7:00 pm", "Jyotimoy"));
                    routines.add(new SubjectRoutine("Chemistry", "7:00 pm", "Jyotimoy"));

                    updateRecyclerView.callback(position, routines);
                }
            }
        });

        if(index == position){
            holder.daycard.setBackgroundResource(R.drawable.selected_item);
        }
        else {
            holder.daycard.setBackgroundResource(R.drawable.unselected_item);
        }

        if(check){
            ArrayList<SubjectRoutine> routines = new ArrayList<SubjectRoutine>();
            routines.add(new SubjectRoutine("Maths", "7:00 pm", "Jyotimoy"));
            routines.add(new SubjectRoutine("Maths", "7:00 pm", "Jyotimoy"));
            routines.add(new SubjectRoutine("Maths", "7:00 pm", "Jyotimoy"));
            routines.add(new SubjectRoutine("Maths", "7:00 pm", "Jyotimoy"));
            routines.add(new SubjectRoutine("Maths", "7:00 pm", "Jyotimoy"));
            routines.add(new SubjectRoutine("Maths", "7:00 pm", "Jyotimoy"));
            routines.add(new SubjectRoutine("Maths", "7:00 pm", "Jyotimoy"));
            routines.add(new SubjectRoutine("Maths", "7:00 pm", "Jyotimoy"));

            updateRecyclerView.callback(position, routines);
            check = false;
        }
    }

    @Override
    public int getItemCount() {
        return day.size();
    }


}
