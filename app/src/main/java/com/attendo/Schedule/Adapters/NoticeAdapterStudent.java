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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NoticeDetails currentItem = items.get(position);
        holder.sub.setText(currentItem.getTitle());

        holder.notice_card.setOnClickListener(new View.OnClickListener() {
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

        TextView sub;
        CardView notice_card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            notice_card = itemView.findViewById(R.id.NoticeCard);
            sub = itemView.findViewById(R.id.Title);

        }
    }

    public interface CallBack{
        void onCardClick(int position, NoticeDetails noticeDetails);
    }
}
