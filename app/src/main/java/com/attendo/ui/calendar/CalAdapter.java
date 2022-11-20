package com.attendo.ui.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.attendo.R;

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
        View view = LayoutInflater.from(mContext).inflate(R.layout.calendar_card_listitem,parent,false);
        return new CalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalViewHolder holder, int position) {
        final String data = mDatalist.get(position);
        holder.caltextView.setText(data);
    }

    @Override
    public int getItemCount()
    {
        if (mDatalist != null)
            return mDatalist.size();
        else return 0;
    }

    public class CalViewHolder extends RecyclerView.ViewHolder
    {

        TextView caltextView;

        public CalViewHolder(@NonNull View itemView) {
            super(itemView);
            caltextView = itemView.findViewById(R.id.cal_textview);
            ButterKnife.bind(this,itemView);
        }
    }

}
