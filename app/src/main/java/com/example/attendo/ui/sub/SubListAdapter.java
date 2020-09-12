package com.example.attendo.ui.sub;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.attendo.data.CalendarEntity;
import com.example.attendo.data.SubEntity;
import com.example.attendo.R;
import com.example.attendo.ui.calendar.CalAdapter;
import com.example.attendo.ui.main.BottomNavMainActivity;
import com.example.attendo.ui.main.MainActivity;
import com.example.attendo.viewmodel.CalViewModel;
import com.example.attendo.viewmodel.SubjectViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class SubListAdapter extends RecyclerView.Adapter<SubListAdapter.SubViewHolder>  {
    private Context mContext;
    private List<SubEntity> mSubjects;
    private SubjectViewModel subjectViewModel;
    private CalViewModel calViewModel;




    public SubListAdapter(Context mContext, List<SubEntity> mSubjects) {
        this.mContext = mContext;
        this.mSubjects = mSubjects;

        subjectViewModel = new ViewModelProvider((BottomNavMainActivity)mContext).get(SubjectViewModel.class);
        calViewModel = new ViewModelProvider((BottomNavMainActivity)mContext).get(CalViewModel.class);

    }

    @NonNull
    @Override
    public SubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sub_info,parent,false);

        return new SubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubViewHolder holder, int position) {


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

                Date date = Calendar.getInstance().getTime();
                String subject = String.valueOf(holder.subItemView.getText());
                CalendarEntity calendarEntity = new CalendarEntity(date,subject);
                calViewModel.insertDate(calendarEntity);


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
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext, R.style.BottomSheetDialog);
                View bottomsheet = LayoutInflater.from(mContext).inflate(R.layout.bottom_sheet_options_menu,
                        (ConstraintLayout) holder.itemView.findViewById(R.id.bottom_sheet_options));

                bottomSheetDialog.setContentView(bottomsheet);
                bottomSheetDialog.setDismissWithAnimation(true);
                bottomSheetDialog.show();

                TextView editSub = bottomsheet.findViewById(R.id.edit_subject_bottom_sheet);
                TextView deleteSub = bottomsheet.findViewById(R.id.delete_subject_bottom_sheet);
                bottomSheetDialog.findViewById(editSub.getId());
                bottomSheetDialog.findViewById(deleteSub.getId());

                deleteSub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        BottomSheetDialog deletebottomsheet = new BottomSheetDialog(mContext,R.style.BottomSheetDialog);
                        View deletesheet = LayoutInflater.from(mContext).inflate(R.layout.delete_bottom_sheet,
                                (ConstraintLayout) holder.itemView.findViewById(R.id.delete_bottom_sheet_container));

                        bottomSheetDialog.dismiss();

                        deletebottomsheet.setContentView(deletesheet);
                        deletebottomsheet.setDismissWithAnimation(true);
                        deletebottomsheet.show();

                        Button delete = deletebottomsheet.findViewById(R.id.delete_button_delete_bottom_sheet);
                        LottieAnimationView deleteanim = deletebottomsheet.findViewById(R.id.lottieAnimationView);

                        deleteanim.setAnimation(R.raw.delete_animation);

                        delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                deleteanim.pauseAnimation();
                                deleteanim.setAnimation(R.raw.done_animation);
                                deleteanim.playAnimation();
                                deleteanim.setSpeed(2f);

                                subjectViewModel.deleteSubject(subEntity);
                                Handler mhandler = new Handler();
                                mhandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        deletebottomsheet.dismiss();

                                    }
                                }, 1000);

                            }
                        });
                    }
                });

                editSub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        BottomSheetDialog bottomSheetDialogedit = new BottomSheetDialog(mContext, R.style.BottomSheetDialog);
                        View bottomsheet = LayoutInflater.from(mContext).inflate(R.layout.fragment_bottom_sheet_add_subject,
                                (ConstraintLayout) holder.itemView.findViewById(R.id.bottom_sheet_add_subject_container));

                        bottomSheetDialogedit.setContentView(bottomsheet);
                        bottomSheetDialogedit.setDismissWithAnimation(true);
                        bottomSheetDialogedit.show();

                        bottomSheetDialog.dismiss();

                        EditText subjectName = bottomSheetDialogedit.findViewById(R.id.add_subject_bottomsheet);
                        Button addButton = bottomSheetDialogedit.findViewById(R.id.add_subject_btn);
                        TextView update = bottomSheetDialogedit.findViewById(R.id.add_subject_id);

                        update.setText("Update Subject");

                        subjectName.setText(subEntity.getSubject().toString().trim());

                        addButton.setText("Update");

                        addButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                subjectViewModel.updateSubject(subjectName.getText().toString().trim(), subEntity.getId());
                                bottomSheetDialogedit.dismiss();
                            }
                        });
                    }
                });
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

