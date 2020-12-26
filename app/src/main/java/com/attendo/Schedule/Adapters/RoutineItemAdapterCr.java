package com.attendo.Schedule.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.attendo.R;
import com.attendo.Schedule.Model.SubjectRoutine;
import com.firebase.client.core.Context;

import java.util.ArrayList;

public class RoutineItemAdapterCr extends RecyclerView.Adapter<RoutineItemAdapterCr.RoutineItemAdapterCrHolder> {

    private ArrayList<SubjectRoutine> items;

    public RoutineItemAdapterCr(ArrayList<SubjectRoutine> items)
    {
        this.items = items;
    }



    @NonNull
    @Override
    public RoutineItemAdapterCrHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_card,parent,false);
        RoutineItemAdapterCrHolder routineItemAdapterCrHolder = new RoutineItemAdapterCrHolder(view);
        return routineItemAdapterCrHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineItemAdapterCrHolder holder, int position) {
        SubjectRoutine currentItem = items.get(position);
        holder.subject.setText(currentItem.getSubjectName());
        holder.faculty.setText(currentItem.getInstructor());
        holder.time.setText(currentItem.getTime());

        //long pressed....
        holder.mview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RoutineItemAdapterCrHolder extends RecyclerView.ViewHolder {

        public TextView subject,faculty,time;
        View mview;

        public RoutineItemAdapterCrHolder(@NonNull View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.subjectname);
            time = itemView.findViewById(R.id.time);
            faculty = itemView.findViewById(R.id.instructor);
            mview = itemView;
        }
    }

}
