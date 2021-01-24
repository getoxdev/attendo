package com.attendo.Schedule.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.attendo.R;
import com.attendo.data.model.schedule.Student;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class BatchmatesListAdapter extends RecyclerView.Adapter<BatchmatesListAdapter.MyViewHolder> {



    Context context;
    List<Student> studentList;

    public BatchmatesListAdapter(Context context, List<Student> studentList) {
        this.context = context;
        this.studentList = studentList;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.batchmates_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.nameOfStudent.setText(student.getName());
        if(student.getScholarId() == null || student.getScholarId() == ""){
            holder.scholarId.setText("XXXXXX");
        }else{
            holder.scholarId.setText(student.getScholarId());
        }
        if(!student.getCr()){
            holder.crStatus.setVisibility(View.INVISIBLE);
        }else{
            holder.crStatus.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView crStatus;
        TextView nameOfStudent, scholarId;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            crStatus = itemView.findViewById(R.id.cr_status);
            nameOfStudent = itemView.findViewById(R.id.name_of_student);
            scholarId = itemView.findViewById(R.id.scholar_id);
        }
    }
}
