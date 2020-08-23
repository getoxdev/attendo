package com.example.attendo.data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendo.ui.sub.SubActivity;
import com.example.attendo.ui.sub.Activity_edit;
import com.example.attendo.R;

import java.util.List;


public class SubListAdapter extends RecyclerView.Adapter<SubListAdapter.NoteViewHolder>  {

    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<SubEntity> mNotes;

    private List<SubEntity> mDataList;

    public SubListAdapter(Context context, SubActivity listener) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;

    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.sub_info, parent, false);
        NoteViewHolder viewHolder = new NoteViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {

        //animation
        Animation cardanim = AnimationUtils.loadAnimation(mContext,R.anim.fade_card);
        holder.card.setAnimation(cardanim);


        if (mNotes != null) {
            SubEntity note = mNotes.get(position);
            holder.setData(note.getNote(), position);

        } else {
            // Covers the case of data not being ready yet.
            holder.noteItemView.setText("No Subject");
        }

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                                    Intent intent = new Intent(mContext, Activity_edit.class);
                    intent.putExtra("note_id", mNotes.get(position).getId());
                    ((Activity)mContext).startActivityForResult(intent, SubActivity.UPDATE_NOTE_ACTIVITY_REQUEST_CODE);

            }
        });

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pre1 = Integer.parseInt((String) holder.tv1.getText());
               int total1 = Integer.parseInt((String) holder.total.getText());
                pre1++;
                total1++;
                String r1 = String.valueOf(pre1);
                String r2 = String.valueOf(total1);
                holder.tv1.setText(r1);
                holder.total.setText(r2);


            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int total1 = Integer.parseInt((String) holder.total.getText());
                //int ab = 0;
                // pre1 = Integer.parseInt((String) holder.tv1.getText());
                total1++;
                String r3 = String.valueOf(total1);
               // ab++;
                holder.total.setText(r3);


            }
        });



    }

    @Override
    public int getItemCount() {
        if (mNotes != null)
            return mNotes.size();
        else return 0;
    }

    public void setNotes(List<SubEntity> notes) {
        mNotes = notes;
        notifyDataSetChanged();
    }

    public SubEntity getNoteAt(int position)
    {
        return mNotes.get(position);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder  {

        private TextView noteItemView,total,tv1,percent;
        private int mPosition;
        private Button imgDelete, imgEdit;
        private CardView card;
        NoteViewHolder noteViewHolder;


        public NoteViewHolder(View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.subName);
            imgDelete 	 = itemView.findViewById(R.id.bAbsent);
            imgEdit 	 = itemView.findViewById(R.id.bPresent);
            card = itemView.findViewById(R.id.card1);
            total=itemView.findViewById(R.id.total);
            tv1 = itemView.findViewById(R.id.tv1);
            percent = itemView.findViewById(R.id.percent);


        }

        public void setData(String note, int position) {
            noteItemView.setText(note);
            mPosition = position;


        }





    }


}

