package com.example.attendo.ui.sub;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendo.data.SubEntity;
import com.example.attendo.R;
import com.example.attendo.viewmodel.SubjectViewModel;

import java.text.DecimalFormat;
import java.util.List;


public class SubListAdapter extends RecyclerView.Adapter<SubListAdapter.SubViewHolder>  {
    private Context mContext;
    private List<SubEntity> mSubjects;
    private SubjectViewModel subjectViewModel;




    public SubListAdapter(Context mContext, List<SubEntity> mSubjects) {
        this.mContext = mContext;
        this.mSubjects = mSubjects;

        subjectViewModel = new ViewModelProvider((SubjectActivity)mContext).get(SubjectViewModel.class);
    }

    @NonNull
    @Override
    public SubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sub_info,parent,false);

        return new SubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubViewHolder holder, int position) {

        //animation
//        Animation cardanim = AnimationUtils.loadAnimation(mContext,R.anim.fade_card);
//        holder.card.setAnimation(cardanim);

        final SubEntity subEntity = mSubjects.get(position);
        holder.subItemView.setText(subEntity.getSubject());
        holder.tvPres.setText(String.valueOf(subEntity.getPresent()));
        holder.tvTotal.setText(String.valueOf(subEntity.getTotal()));
        holder.percent.setText(getPercentage(subEntity.getPresent(),subEntity.getTotal())+"%");

        final Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);




        holder.btnPres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = mSubjects.get(position).getId();

                int pre = Integer.parseInt((String) holder.tvPres.getText());
                int total = Integer.parseInt((String) holder.tvTotal.getText());
                pre++;
                total++;

                subjectViewModel.updatePresent(pre,id);
                subjectViewModel.updateTotal(total,id);
            }
        });

        holder.btnAbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = mSubjects.get(position).getId();

                int total = Integer.parseInt((String) holder.tvTotal.getText());
                int pre = Integer.parseInt((String) holder.tvPres.getText());
                int ab=total-pre;
                ab++;
                total++;

                subjectViewModel.updateAbsent(ab,id);
                subjectViewModel.updateTotal(total,id);
            }
        });


        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                //vibrator
                vibrator.vibrate(100);
                //dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Delete Subject");
                builder.setMessage("Do you want to permanently delete the subject?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        subjectViewModel.deleteSubject(subEntity);
                    }
                }).setNegativeButton("No",null);
                builder.setCancelable(false);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);

                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mSubjects != null)
            return mSubjects.size();
        else return 0;
    }

    public void setSubjects(List<SubEntity> subjects) {
        mSubjects = subjects;
        notifyDataSetChanged();

    }

    public SubEntity getSubjectAt(int position)
    {
        return mSubjects.get(position);
    }

    public class SubViewHolder extends RecyclerView.ViewHolder  {

        private TextView subItemView,tvTotal,tvPres,percent;
        private Button btnAbs, btnPres;
        private CardView card;

        public SubViewHolder(View itemView) {
            super(itemView);
            subItemView = itemView.findViewById(R.id.subName);
            btnAbs = itemView.findViewById(R.id.bAbsent);
            btnPres = itemView.findViewById(R.id.bPresent);
            card = itemView.findViewById(R.id.card1);
            tvTotal=itemView.findViewById(R.id.tvTotal);
            tvPres = itemView.findViewById(R.id.tvPres);
            percent = itemView.findViewById(R.id.percentage);
        }
    }

    // Method to get percentage of attendance from the present and total data provided
    //--------------------------------------------------------------------------------------

    private String getPercentage(int present, int total){
        double presentdouble , totaldouble;
        presentdouble = Double.valueOf(present);
        totaldouble  = Double.valueOf(total);
        DecimalFormat df = new DecimalFormat("#.##");

        if(totaldouble==Double.valueOf(0))
            return df.format(0);

        double percentage = (presentdouble/totaldouble)*100;
        return df.format(percentage);
    }

    //----------------------------------------------------------------------------------------


}

