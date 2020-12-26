package com.attendo.Schedule.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.attendo.R;
import com.attendo.Schedule.Model.SubjectRoutine;

import java.util.ArrayList;

public class RoutineItemAdapter extends RecyclerView.Adapter<RoutineItemAdapter.RoutineItemAdapterHolder>{

    private ArrayList<SubjectRoutine> items;

    public RoutineItemAdapter(ArrayList<SubjectRoutine> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RoutineItemAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_card,parent,false);
        RoutineItemAdapterHolder routineItemAdapterHolder= new RoutineItemAdapterHolder(view);
        return routineItemAdapterHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineItemAdapterHolder holder, int position) {
        SubjectRoutine currentItem = items.get(position);
        holder.subject.setText(currentItem.getSubjectName());
        holder.faculty.setText(currentItem.getInstructor());
        holder.time.setText(currentItem.getTime());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class RoutineItemAdapterHolder extends RecyclerView.ViewHolder{

        public TextView subject,faculty,time;

        public RoutineItemAdapterHolder(@NonNull View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.subjectname);
            time = itemView.findViewById(R.id.time);
            faculty = itemView.findViewById(R.id.instructor);
        }
    }

}