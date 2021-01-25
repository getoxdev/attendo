package com.attendo.Schedule.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.attendo.R;
import com.attendo.data.model.schedule.NoticeDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoticeAdapterStudent extends RecyclerView.Adapter<NoticeAdapterStudent.MyViewHolder> {

    private List<NoticeDetails> items;
    private Context mContext;

    public NoticeAdapterStudent(Context mContext,List<NoticeDetails> items)
    {
        this.items = items;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NoticeDetails currentItem = items.get(position);
        holder.sub.setText(currentItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.Title)
        TextView sub;

        @BindView(R.id.NoticeCard)
        CardView notice_card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
