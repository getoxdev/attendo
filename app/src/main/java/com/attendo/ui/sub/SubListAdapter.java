package com.attendo.ui.sub;

import android.content.Context;
import android.os.Handler;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.attendo.data.calendar.CalendarEntity;
import com.attendo.data.DateConverter;
import com.attendo.data.sub.SubEntity;
import com.attendo.R;
import com.attendo.databinding.FragmentBottomSheetAddSubjectBinding;
import com.attendo.databinding.SubCardNewBinding;
import com.attendo.ui.main.BottomNavMainActivity;
import com.attendo.ui.main.drawers.FragmentEditAttendanceCriteria;
import com.attendo.viewmodel.CalViewModel;
import com.attendo.viewmodel.SubjectViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.Math.ceil;
import static java.lang.Math.floor;


public class SubListAdapter extends RecyclerView.Adapter<SubListAdapter.SubViewHolder> {
    private Context mContext;
    private List<SubEntity> mSubjects;
    private SubjectViewModel subjectViewModel;
    private CalViewModel calViewModel;
    private DateConverter dateConverter;
    private FragmentEditAttendanceCriteria fragmentEditAttendanceCriteria;
    private String key;

    public SubListAdapter(Context mContext, List<SubEntity> mSubjects,String key) {
        this.mContext = mContext;
        this.mSubjects = mSubjects;
        this.key = key;

        subjectViewModel = new ViewModelProvider((BottomNavMainActivity) mContext).get(SubjectViewModel.class);
        calViewModel = new ViewModelProvider((BottomNavMainActivity) mContext).get(CalViewModel.class);
    }

    @NonNull
    @Override
    public SubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
        SubCardNewBinding binding=SubCardNewBinding.inflate(layoutInflater,parent,false);
        //View view=binding.getRoot();
        //View view = LayoutInflater.from(mContext).inflate(R.layout.sub_card_new, parent, false);
        dateConverter = new DateConverter();

        return new SubViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubViewHolder holder, int position) {


        final SubEntity subEntity = mSubjects.get(position);
        int id = mSubjects.get(position).getId();
        holder.binding.cardSubjectName.setText(subEntity.getSubject());
        holder.binding.presentTxt.setText(String.valueOf(subEntity.getPresent()));
        holder.binding.totalTextSubjectCard.setText(String.valueOf(subEntity.getTotal()));
        holder.binding.percentageSubjectCardItem.setText(getPercentage(subEntity.getPresent(), subEntity.getTotal()) + "%");

        holder.binding.statusCounterTxt.setText(Status(key,getPercentage(subEntity
                .getPresent(),subEntity.getTotal()),subEntity.getPresent(),subEntity.getAbsent()));

        final Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);

        Double cardPerent = Double.valueOf(getPercentage(subEntity.getPresent(), subEntity.getTotal()));
        Double cardCriterion = Double.valueOf(key);


        if(cardPerent < cardCriterion){
            holder.binding.subjectItemCard.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.below_criterion_color));
        }
        else{
            holder.binding.subjectItemCard.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        }


        holder.binding.presentBtnCardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = mSubjects.get(position).getId();

                int pre = Integer.parseInt((String) holder.binding.presentTxt.getText());
                int total = Integer.parseInt((String) holder.binding.totalTextSubjectCard.getText());
                pre++;
                total++;

                Date date = new Date();
                String subDate = formatter(dateConverter.fromTimestamp(date.getTime()));
                String subject = String.valueOf(holder.binding.cardSubjectName.getText());
                CalendarEntity calendarEntity = new CalendarEntity(subDate, subject);
                calViewModel.insertDate(calendarEntity);
                subjectViewModel.updatePresent(pre, id);
                subjectViewModel.updateTotal(total, id);

                if(cardPerent < cardCriterion){
                    holder.binding.subjectItemCard.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.below_criterion_color));
                }
                else{
                    holder.binding.subjectItemCard.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
                }
            }
        });

        holder.binding.absentBtnCardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = mSubjects.get(position).getId();

                int total = Integer.parseInt((String) holder.binding.totalTextSubjectCard.getText());
                int pre = Integer.parseInt((String) holder.binding.presentTxt.getText());
                int ab = total - pre;
                ab++;
                total++;


                subjectViewModel.updateAbsent(ab, id);
                subjectViewModel.updateTotal(total, id);

                if(cardPerent < cardCriterion){
                    holder.binding.subjectItemCard.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.below_criterion_color));
                }
                else{
                    holder.binding.subjectItemCard.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
                }

            }
        });

        holder.binding.subjectItemCard.setOnLongClickListener(new View.OnLongClickListener() {
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
                TextView editAttendance = bottomsheet.findViewById(R.id.edit_attendance_subject_bottom_sheet);
                bottomSheetDialog.findViewById(editSub.getId());
                bottomSheetDialog.findViewById(deleteSub.getId());

                deleteSub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        BottomSheetDialog deletebottomsheet = new BottomSheetDialog(mContext, R.style.BottomSheetDialog);
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
                                deleteanim.setAnimation(R.raw.done_lottie_anim);
                                deleteanim.playAnimation();

                                subjectViewModel.deleteSubject(subEntity);
                                Handler mhandler = new Handler();
                                mhandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        deletebottomsheet.dismiss();

                                    }
                                }, 900);

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
                        LottieAnimationView animation = bottomSheetDialogedit.findViewById(R.id.lottie);


                        update.setText("Update Subject");
                        animation.setAnimation(R.raw.study_confused);

                        subjectName.setText(subEntity.getSubject().toString().trim());

                        addButton.setText("Update");

                        subjectName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                            @Override
                            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                                switch (i){
                                    case EditorInfo.IME_ACTION_DONE:
                                        subjectViewModel.updateSubject(subjectName.getText().toString().trim(), subEntity.getId());
                                        bottomSheetDialogedit.dismiss();
                                        break;

                                }
                                return false;
                            }
                        });

                        addButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                subjectViewModel.updateSubject(subjectName.getText().toString().trim(), subEntity.getId());
                                bottomSheetDialogedit.dismiss();
                            }
                        });
                    }
                });

                editAttendance.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BottomSheetDialog editAttend = new BottomSheetDialog(mContext, R.style.BottomSheetDialog);
                        View editattendbottomsheet = LayoutInflater.from(mContext).inflate(R.layout.bottom_sheet_edit_attendance,
                                (ConstraintLayout) holder.itemView.findViewById(R.id.edit_attendance_bottom_sheet_container));

                        editAttend.setContentView(editattendbottomsheet);
                        editAttend.setDismissWithAnimation(true);
                        editAttend.show();

                        bottomSheetDialog.dismiss();

                        EditText presentEditText = editAttend.findViewById(R.id.preset_update);
                        EditText totalEditText = editAttend.findViewById(R.id.total_update);
                        Button updateAttendance =  editAttend.findViewById(R.id.updateAttendance);
                        TextInputLayout presetnEditTextInputlayout = editAttend.findViewById(R.id.present_editText_field);
                        TextInputLayout totalEditTextInputLayout = editAttend.findViewById(R.id.total_editText_field);




                        updateAttendance.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if(presentEditText.getText().toString().length() == 0 || totalEditText.getText().toString().length() == 0)
                                {
                                    presetnEditTextInputlayout.setError("Field cannot be empty");
                                    totalEditTextInputLayout.setError("Field cannot be empty");
                                }

                                else
                                {
                                    int pre = Integer.parseInt(presentEditText.getText().toString());
                                    int tot = Integer.parseInt(totalEditText.getText().toString());
                                    if(pre>tot)
                                    {
                                        Toast.makeText(mContext,"Present classes should be less than total classes",Toast.LENGTH_SHORT).show();

                                    }
                                    else
                                    {
                                    holder.binding.presentTxt.setText(String.valueOf(pre));
                                    holder.binding.totalTextSubjectCard.setText(String.valueOf(tot));
                                    subjectViewModel.updatePresent(pre,id);
                                    subjectViewModel.updateTotal(tot,id);
                                        editAttend.dismiss();
                                    }
                                }



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

    public class SubViewHolder extends RecyclerView.ViewHolder {


//        @BindView(R.id.card_subject_name)
//        TextView subItemView;
//
//        @BindView(R.id.total_text_subject_card)
//        TextView tvTotal;
//
//        @BindView(R.id.present_txt)
//        TextView tvPres;
//
//        @BindView(R.id.percentage_subject_card_item)
//        TextView percent;
//
//        @BindView(R.id.status_counter_txt)
//        TextView status;
//
//        @BindView(R.id.textView10) @Nullable
//        TextView criteria2;
//
//        @BindView(R.id.absent_btn_card_item)
//        Button btnAbs;
//
//        @BindView(R.id.present_btn_card_item)
//        Button btnPres;
//
//        @BindView(R.id.updateAttendance) @Nullable
//        Button upadt;
//
//        @BindView(R.id.subject_item_card)
//        CardView card;
        SubCardNewBinding binding;

//        public SubViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this,itemView);
//
//        }

        public SubViewHolder(SubCardNewBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
    }

    // Method to get percentage of attendance from the present and total data provided
    //--------------------------------------------------------------------------------------

    private String getPercentage(int present, int total) {
        double presentdouble, totaldouble;
        presentdouble = Double.valueOf(present);
        totaldouble = Double.valueOf(total);
        DecimalFormat df = new DecimalFormat("#.##");

        if (totaldouble == Double.valueOf(0))
            return df.format(0);

        double percentage = (presentdouble / totaldouble) * 100;
        return df.format(percentage);
    }

    private String Status(String criteria,String percentage,int present,int absent)
    {
        String status;
        double presentdouble, absentdouble,percentagedouble,criteriadouble;
        presentdouble = Double.valueOf(present);
        absentdouble = Double.valueOf(absent);
        percentagedouble = Double.valueOf(percentage);
        criteriadouble = Double.valueOf(criteria);



        if(percentagedouble>criteriadouble)
        {
            double value;
            value=floor(presentdouble - (criteriadouble*(presentdouble + absentdouble))/100);
            value = value;
            if(value==0.0)
                status="Dont't miss next 1 lecture";
            else
                if(value==1)
                {status="You can skip next "+String.format("%.0f",Math.abs(value))+" lecture";}
                else
                {status="You can skip next "+String.format("%.0f",Math.abs(value))+" lectures";}
        }
        else if(percentagedouble<criteriadouble)
        {
            if(criteriadouble==100)
            {
                if(presentdouble==0.0)
                {
                    status = "You must attend next 1 lecture";
                }
                else
                {
                    status = "Impossible";
                }
            }
           else {
                double value;
                value = ceil(((criteriadouble * (presentdouble + absentdouble)) / 100 - presentdouble));
                value = value + 1;
                if (value == 1) {
                    status = "You must attend next " + String.format("%.0f", Math.abs(value)) + " lecture";
                } else {
                    status = "You must attend next " + String.format("%.0f", Math.abs(value)) + " lectures";
                }
            }

        }

        else
        {
            status="Don't miss next 1 lecture";
        }
        return status;
    }






    //----------------------------------------------------------------------------------------

    public static String formatter(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String subDate = dateFormat.format(date);

        return subDate;
    }



}