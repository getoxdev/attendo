package com.attendo.Schedule.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.attendo.R;
import com.attendo.Schedule.Interface.UpdateRecyclerView;
import com.attendo.data.model.schedule.SubjectDetails;
import com.attendo.databinding.SubjectCardBinding;
import com.attendo.databinding.WeekdayCardBinding;
import com.firebase.client.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoutineItemAdapter extends RecyclerView.Adapter<RoutineItemAdapter.RoutineItemAdapterHolder>{

    private List<SubjectDetails> items;
    private UpdateRecyclerView updateRecyclerView;
    private Context context;

    public RoutineItemAdapter(List<SubjectDetails> items, Context context, UpdateRecyclerView updateRecyclerView)
    {
        this.items = items;
        this.context = context;
        this.updateRecyclerView = updateRecyclerView;


    }

    @NonNull
    @Override
    public RoutineItemAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SubjectCardBinding binding= SubjectCardBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
//        View view = binding.getRoot();
//        RoutineItemAdapterHolder routineItemAdapterHolder= new RoutineItemAdapterHolder(view);
        return new RoutineItemAdapterHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineItemAdapterHolder holder, int position) {
        SubjectDetails currentItem = items.get(position);
        holder.binding.subjectname.setText(currentItem.getSubject());
        holder.binding.instructor.setText(currentItem.getFaculty());
        holder.binding.time.setText(currentItem.getTime());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class RoutineItemAdapterHolder extends RecyclerView.ViewHolder{

//        @BindView(R.id.subjectname)
//        TextView subject;
//        @BindView(R.id.instructor)
//        TextView faculty;
//        @BindView(R.id.time)
//        TextView time;

//        public RoutineItemAdapterHolder(@NonNull View itemView) {
//            super(itemView);
//            ButterKnife.bind(this,itemView);
//        }
        SubjectCardBinding binding;
        public RoutineItemAdapterHolder(@NonNull SubjectCardBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
    }

}