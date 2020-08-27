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

import com.example.attendo.ui.sub.SubjectActivity;
import com.example.attendo.ui.sub.Activity_Edit_Subject;
import com.example.attendo.R;
import com.example.attendo.viewmodel.SubjectViewModel;

import java.text.DecimalFormat;
import java.util.List;


public class SubListAdapter extends RecyclerView.Adapter<SubListAdapter.SubViewHolder>  {

    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<SubEntity> mSubjects;
    private SubEntity subEntity;
    private SubjectViewModel subjectViewModel;
    private onclick monclick;




    public SubListAdapter(Context context, SubjectActivity listener,onclick monclick) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
        this.monclick = monclick;



    }

    @NonNull
    @Override
    public SubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.sub_info, parent, false);
        SubViewHolder viewHolder = new SubViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubViewHolder holder, int position) {

        //animation
        Animation cardanim = AnimationUtils.loadAnimation(mContext,R.anim.fade_card);
        holder.card.setAnimation(cardanim);


        if (mSubjects != null) {
            SubEntity SUBJECT = mSubjects.get(position);
            holder.setData(SUBJECT.getSubject(), position);

        } else {
            // Covers the case of data not being ready yet.
            holder.subItemView.setText("No Subject");
        }

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                                    Intent intent = new Intent(mContext, Activity_Edit_Subject.class);
                    intent.putExtra("note_id", mSubjects.get(position).getId());
                    ((Activity)mContext).startActivityForResult(intent, SubjectActivity.UPDATE_SUBJECT_ACTIVITY_REQUEST_CODE);

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
               int id = subEntity.getId();



                //To set the percentage using the getPercentage method
                holder.percent.setText(getPercentage(pre1,total1)  + " %");
                monclick.present(v,position,id);







            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int total1 = Integer.parseInt((String) holder.total.getText());
                int ab = 0;
                int pre1 = Integer.parseInt((String) holder.tv1.getText());
                total1++;
                String r3 = String.valueOf(total1);

                ab++;
                holder.total.setText(r3);
                String r4 = String.valueOf(ab);
                String sub = String.valueOf(holder.subItemView.getText());
               int id = subEntity.getId();




                //To set the percentage using the getPercentage meth
                holder.percent.setText(getPercentage(pre1, total1) + " %");
               monclick.absent(v,position,id);





            }
        });



    }

    @Override
    public int getItemCount() {
        if (mSubjects != null)
            return mSubjects.size();
        else return 0;
    }

    public void setmSubjects(List<SubEntity> subjects) {
        mSubjects = subjects;
        notifyDataSetChanged();
    }

    public SubEntity getSubjectAt(int position)
    {
        return mSubjects.get(position);
    }

    public class SubViewHolder extends RecyclerView.ViewHolder  {

        private TextView subItemView,total,tv1,percent;
        private int mPosition;
        private Button imgDelete, imgEdit;
        private CardView card;
        SubViewHolder subViewHolder;


        public SubViewHolder(View itemView) {
            super(itemView);
            subItemView = itemView.findViewById(R.id.subName);
            imgDelete 	 = itemView.findViewById(R.id.bAbsent);
            imgEdit 	 = itemView.findViewById(R.id.bPresent);
            card = itemView.findViewById(R.id.card1);
            total=itemView.findViewById(R.id.total);
            tv1 = itemView.findViewById(R.id.tv1);
            percent = itemView.findViewById(R.id.percentage);


        }

        public void setData(String subject, int position) {
            subItemView.setText(subject);
            mPosition = position;


        }





    }

    // Method to get percentage of attendance from the present and total data provided
    //--------------------------------------------------------------------------------------

    private String  getPercentage(int present, int total){
        double presentdouble , totaldouble;
        presentdouble = Double.valueOf(present);
        totaldouble  = Double.valueOf(total);
        DecimalFormat df = new DecimalFormat("#.##");
        double percentage = (presentdouble/totaldouble)*100;
        return df.format(percentage);


    }

    //----------------------------------------------------------------------------------------

    public interface onclick
    {
        void present(View v,int position,int id );
        void absent(View v,int position ,int id);
    }





}

