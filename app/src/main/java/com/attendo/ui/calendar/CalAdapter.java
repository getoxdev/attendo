package com.attendo.ui.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.attendo.R;
import com.attendo.databinding.CalendarCardListitemBinding;
import com.attendo.databinding.FragmentCalenderBinding;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalAdapter extends RecyclerView.Adapter<CalAdapter.CalViewHolder> {

    private Context mContext;
    private List<String> mDatalist;

    public CalAdapter(Context mContext, List<String> mDatalist)
    {
        this.mContext = mContext;
        this.mDatalist = mDatalist;
    }

    public void setData(List<String> data) {
        mDatalist = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        CalendarCardListitemBinding binding = CalendarCardListitemBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new CalViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CalViewHolder holder, int position) {
        final String data = mDatalist.get(position);
        holder.binding.calTextview.setText(data);
    }

    @Override
    public int getItemCount() {
        if (mDatalist != null)
            return mDatalist.size();
        else return 0;
    }

    public static class CalViewHolder extends RecyclerView.ViewHolder{
        CalendarCardListitemBinding binding;
        public CalViewHolder(@NonNull CalendarCardListitemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
