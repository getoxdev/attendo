package com.attendo.Schedule.Adapters;

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.attendo.R;
import com.attendo.Schedule.Model.SubjectRoutine;
import com.attendo.data.model.SubjectDetails;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoutineItemAdapterCr extends RecyclerView.Adapter<RoutineItemAdapterCr.RoutineItemAdapterCrHolder> {

    private List<SubjectDetails> items;
    private Context mContext;



    public RoutineItemAdapterCr(Context mContext,List<SubjectDetails> items)
    {
        this.items = items;
        this.mContext = mContext;

    }


    @NonNull
    @Override
    public RoutineItemAdapterCrHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_card,parent,false);
        RoutineItemAdapterCrHolder routineItemAdapterCrHolder = new RoutineItemAdapterCrHolder(view);
        return routineItemAdapterCrHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineItemAdapterCrHolder holder, int position) {
        SubjectDetails currentItem = items.get(position);
        holder.subject.setText(currentItem.getSubject());
        holder.faculty.setText(currentItem.getFaculty());
        holder.time.setText(currentItem.getTime());
        String scheduleclassid = currentItem.get_id();
        Log.e("scheduleclassid",scheduleclassid);


        final Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);




        //long pressed....
        holder.mview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) { return true;
            }
        });

        holder.subjectCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                vibrator.vibrate(100);
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext, R.style.BottomSheetDialog);
                View bottomsheet = LayoutInflater.from(mContext).inflate(R.layout.bottom_sheet_options_schedule,
                        (ConstraintLayout) holder.itemView.findViewById(R.id.schedule_bottom_sheet));

                bottomSheetDialog.setContentView(bottomsheet);
                bottomSheetDialog.setDismissWithAnimation(true);
                bottomSheetDialog.show();

                TextView editSchedule = bottomsheet.findViewById(R.id.edit_schedule_bottom_sheet);
                TextView deleteSchedule = bottomsheet.findViewById(R.id.delete_schedule_bottom_sheet);

                bottomSheetDialog.findViewById(editSchedule.getId());
                bottomSheetDialog.findViewById(deleteSchedule.getId());


                deleteSchedule.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BottomSheetDialog deletebottomsheet = new BottomSheetDialog(mContext, R.style.BottomSheetDialog);
                        View deletesheet = LayoutInflater.from(mContext).inflate(R.layout.deleteschedule_bottom_sheet,
                                (ConstraintLayout) holder.itemView.findViewById(R.id.delete_schedule_bottom_sheet));

                        bottomSheetDialog.dismiss();

                        deletebottomsheet.setContentView(deletesheet);
                        deletebottomsheet.setDismissWithAnimation(true);
                        deletebottomsheet.show();

                        Button delete = deletebottomsheet.findViewById(R.id.delete_button);
                        LottieAnimationView deleteanim = deletebottomsheet.findViewById(R.id.lottieAnimationView);

                        deleteanim.setAnimation(R.raw.delete_animation);
                    }
                });

                editSchedule.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        BottomSheetDialog bottomSheetDialogedit = new BottomSheetDialog(mContext, R.style.BottomSheetDialog);
                        View bottomsheet = LayoutInflater.from(mContext).inflate(R.layout.updateschedule_bottomsheet,
                                (ConstraintLayout) holder.itemView.findViewById(R.id.update_schedule));

                        bottomSheetDialogedit.setContentView(bottomsheet);
                        bottomSheetDialogedit.setDismissWithAnimation(true);
                        bottomSheetDialogedit.show();

                        bottomSheetDialog.dismiss();

                        EditText subjectName = bottomSheetDialogedit.findViewById(R.id.edit_subject_bottom_sheet);
                        Button update = bottomSheetDialogedit.findViewById(R.id.edit_subject_btn);
                        EditText faculty = bottomSheetDialogedit.findViewById(R.id.update_faculty);
                        TimePicker timePicker = bottomSheetDialogedit.findViewById(R.id.edit_sub_details_time_picker);
                        Spinner spinner = bottomSheetDialog.findViewById(R.id.spinner_schedule);
                        LottieAnimationView animation = bottomSheetDialogedit.findViewById(R.id.lottie_animation_edit_subject_details);





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

    public class RoutineItemAdapterCrHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.subjectname)
        TextView subject;
        @BindView(R.id.instructor)
        TextView faculty;
        @BindView(R.id.time)
        TextView time;

        @BindView(R.id.subjectCard)
        CardView subjectCard;

        View mview;

        public RoutineItemAdapterCrHolder(@NonNull View itemView) {
            super(itemView);
           ButterKnife.bind(this,itemView);
            mview = itemView;
        }
    }

}
