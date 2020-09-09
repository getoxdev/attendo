package com.example.attendo.ui.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendo.R;
import com.example.attendo.data.CalendarEntity;
import com.example.attendo.data.SubEntity;
import com.example.attendo.ui.main.MainActivity;
import com.example.attendo.ui.sub.SubjectActivity;
import com.example.attendo.viewmodel.CalViewModel;
import com.example.attendo.viewmodel.SubjectViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalAdapter extends RecyclerView.Adapter<CalAdapter.CalViewHolder> {

    private Context mContext;
    private List<CalendarEntity> mDatalist;
    private CalViewModel calViewModel;

    public CalAdapter(Context mContext, List<CalendarEntity> mDatalist) {
        this.mContext = mContext;
        this.mDatalist = mDatalist;
        calViewModel = new ViewModelProvider((MainActivity)mContext).get(CalViewModel.class);
    }

    @NonNull
    @Override
    public CalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.calendar_card_listitem,parent,false);
        return new CalViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CalViewHolder holder, int position) {
        final CalendarEntity calendarEntity = mDatalist.get(position);

        Date date = Calendar.getInstance().getTime();
        String sub = calViewModel.getSub(date);
        holder.caltextView.setText(sub);



    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void set(List<CalendarEntity> itemlist) {
        mDatalist = itemlist;
        notifyDataSetChanged();

    }



    public class CalViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.cal_textview)
        TextView caltextView;




        public CalViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
