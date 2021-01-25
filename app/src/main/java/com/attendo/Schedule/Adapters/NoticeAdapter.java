package com.attendo.Schedule.Adapters;

import android.content.Context;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.attendo.R;
import com.attendo.data.model.schedule.NoticeDetails;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.MyViewHolder> {

    private List<NoticeDetails> items;
    private Context mContext;
    On_CardClick on_cardClick;

    public NoticeAdapter(Context mContext,List<NoticeDetails> items,On_CardClick on_cardClick)
    {
        this.items = items;
        this.mContext = mContext;
        this.on_cardClick = on_cardClick;
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

        final Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        holder.notice_card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                vibrator.vibrate(100);
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext, R.style.BottomSheetDialog);
                View bottomsheet = LayoutInflater.from(mContext).inflate(R.layout.notice_bottomsheet_options,
                        (ConstraintLayout) holder.itemView.findViewById(R.id.notice_bottom_sheet));

                bottomSheetDialog.setContentView(bottomsheet);
                bottomSheetDialog.setDismissWithAnimation(true);
                bottomSheetDialog.show();

                TextView editSchedule = bottomsheet.findViewById(R.id.edit_notice_bottom_sheet);
                TextView deleteSchedule = bottomsheet.findViewById(R.id.delete_notice_bottom_sheet);

                deleteSchedule.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
                        on_cardClick.onDeleteN_Click(position,currentItem);
                    }
                });

                editSchedule.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
                        on_cardClick.onEditN_Click(position,currentItem);
                    }
                });



                return false;
            }
        });

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

    public interface On_CardClick{
        void onDeleteN_Click(int position, NoticeDetails noticeDetails);
        void onEditN_Click(int position, NoticeDetails noticeDetails);
    }
}
