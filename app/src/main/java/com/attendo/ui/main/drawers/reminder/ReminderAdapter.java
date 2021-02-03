package com.attendo.ui.main.drawers.reminder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.attendo.R;
import com.attendo.data.rem.RemEntity;
import com.attendo.ui.main.BottomNavMainActivity;
import com.attendo.viewmodel.ReminderViewModel;
import com.google.android.material.button.MaterialButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {

    private List<RemEntity> reminders=new ArrayList<>();
    private ReminderViewModel viewModel;
    private Context context;

    public ReminderAdapter(Context context) {
        this.context = context;
        viewModel = new ViewModelProvider((BottomNavMainActivity) context).get(ReminderViewModel.class);
    }

    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from((parent.getContext())).inflate(R.layout.reminder_item,parent,false);
        return new ReminderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderViewHolder holder, int position) {
        RemEntity currentRem=reminders.get(position);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:00'Z'", Locale.getDefault());
        SimpleDateFormat fd = new SimpleDateFormat("hh:mm a",Locale.getDefault());
        SimpleDateFormat gd = new SimpleDateFormat("HHmm",Locale.getDefault());
        Date date = null;
        try {
            date = sd.parse(currentRem.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String timeShow = fd.format(date);
        String requestCodeString = gd.format(date);
        Integer requestCode = Integer.valueOf(requestCodeString);
        holder.time.setText(timeShow);
        holder.label.setText(currentRem.getLabel());

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.cancelReminder(requestCode,currentRem.getLabel());
                viewModel.delete(currentRem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    public void setReminders(List<RemEntity> remEntityList)
    {
        reminders=remEntityList;
        notifyDataSetChanged();
    }

    public List<RemEntity> getReminders()
    {
        return  reminders;
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
