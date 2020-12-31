package com.attendo.Schedule.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.attendo.R;
import com.attendo.Schedule.Model.SubjectRoutine;
import com.attendo.data.model.SubjectDetails;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoutineItemAdapter extends RecyclerView.Adapter<RoutineItemAdapter.RoutineItemAdapterHolder>{

    private List<SubjectDetails> items;

    public RoutineItemAdapter(List<SubjectDetails> items)
    {
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
        SubjectDetails currentItem = items.get(position);
        holder.subject.setText(currentItem.getSubject());
        holder.faculty.setText(currentItem.getFaculty());
        holder.time.setText(currentItem.getTime());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class RoutineItemAdapterHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.subjectname)
        TextView subject;
        @BindView(R.id.instructor)
        TextView faculty;
        @BindView(R.id.time)
        TextView time;

        public RoutineItemAdapterHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}