package com.attendo.ui.main.drawers.reminder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.attendo.R;
import com.attendo.data.rem.RemEntity;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;


public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {

    private List<RemEntity> reminders=new ArrayList<>();
    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from((parent.getContext())).inflate(R.layout.reminder_item,parent,false);
        return new ReminderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderViewHolder holder, int position) {
    RemEntity currentRem=reminders.get(position);
    holder.time.setText(currentRem.getTime());
    holder.label.setText(currentRem.getLabel());

    holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    });


    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }
    public void setReminders(List<RemEntity> remEntityList){
        reminders=remEntityList;
        notifyDataSetChanged();
    }

    public RemEntity getRemAt(int position){
        return reminders.get(position);
    }

    class ReminderViewHolder extends RecyclerView.ViewHolder {

        private TextView time;
        private TextView label;
        private MaterialButton deleteBtn;

        public ReminderViewHolder(@NonNull View itemView) {
            super(itemView);
            time=itemView.findViewById(R.id.time_show);
            label=itemView.findViewById(R.id.label_show);
            deleteBtn = itemView.findViewById(R.id.alarm_delete);

        }
    }
}
