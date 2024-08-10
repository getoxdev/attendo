package com.attendo.Schedule.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.attendo.Schedule.Interface.UpdateRecyclerView;
import com.attendo.data.model.schedule.SubjectDetails;
import com.attendo.databinding.SubjectCardBinding;

import java.util.List;

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
        SubjectCardBinding binding = SubjectCardBinding.inflate(LayoutInflater.from(context), parent, false);
        return new RoutineItemAdapter.RoutineItemAdapterHolder(binding);
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

        SubjectCardBinding binding;

        public RoutineItemAdapterHolder(@NonNull SubjectCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}