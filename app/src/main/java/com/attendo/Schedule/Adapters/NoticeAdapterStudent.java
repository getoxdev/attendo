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
import com.attendo.databinding.CalendarCardListitemBinding;
import com.attendo.databinding.NoticeBinding;
import com.attendo.ui.calendar.CalAdapter;

import java.util.List;

public class NoticeAdapterStudent extends RecyclerView.Adapter<NoticeAdapterStudent.MyViewHolder> {

    private List<NoticeDetails> items;
    private Context mContext;
    private CallBack callBack;

    public NoticeAdapterStudent(Context mContext,List<NoticeDetails> items,CallBack callBack)
    {
        this.items = items;
        this.mContext = mContext;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NoticeBinding binding = NoticeBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new NoticeAdapterStudent.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NoticeDetails currentItem = items.get(position);
        holder.binding.Title.setText(currentItem.getTitle());

        holder.binding.NoticeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onCardClick(position, currentItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        NoticeBinding binding;

        public MyViewHolder(@NonNull NoticeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface CallBack{
        void onCardClick(int position, NoticeDetails noticeDetails);
    }
}
