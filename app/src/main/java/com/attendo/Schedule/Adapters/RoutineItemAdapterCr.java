package com.attendo.Schedule.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.attendo.R;
import com.attendo.Schedule.CRSettingsFragment;
import com.attendo.Schedule.Delete_fragment;
import com.attendo.Schedule.Edit_schedule_fragment;
import com.attendo.Schedule.Interface.UpdateRecyclerView;
import com.attendo.Schedule.Model.SubjectRoutine;
import com.attendo.data.model.Schedule;
import com.attendo.data.model.ScheduleDelete;
import com.attendo.data.model.ScheduleEdit;
import com.attendo.data.model.SubjectDetails;
import com.attendo.ui.main.BottomNavMainActivity;
import com.attendo.viewmodel.FirebaseScheduleViewModel;
import com.attendo.viewmodel.ScheduleViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoutineItemAdapterCr extends RecyclerView.Adapter<RoutineItemAdapterCr.RoutineItemAdapterCrHolder> {

    private List<SubjectDetails> items;
    int index=0;
    private Context mContext;
    private FirebaseScheduleViewModel firebaseScheduleViewModel;
    private ScheduleViewModel scheduleViewModel;
    private Delete_fragment delete_fragment;
    private Edit_schedule_fragment edit_schedule_fragment;
    String timePickerTime;
    Activity activity;
    OnCardClick onCardClick;




    UpdateRecyclerView updateRecyclerView;






    public RoutineItemAdapterCr(Context mContext,List<SubjectDetails> items,Activity activity,UpdateRecyclerView updateRecyclerView,OnCardClick onCardClick)
    {
        this.items = items;
        this.mContext = mContext;
        this.activity = activity;
        this.updateRecyclerView = updateRecyclerView;
        this.onCardClick = onCardClick;

        firebaseScheduleViewModel = new ViewModelProvider((BottomNavMainActivity) mContext).get(FirebaseScheduleViewModel.class);
        scheduleViewModel = new ViewModelProvider((BottomNavMainActivity) mContext).get(ScheduleViewModel.class);


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
        Log.e("scheduleclassid11",scheduleclassid);
        final Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);

        holder.subjectCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                index = position;

                String sclassid = currentItem.get_id();
                Log.e("scheduleclassid22",sclassid);




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
                        onCardClick.onDeleteClick(position, currentItem);
                    }
                });

                editSchedule.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onCardClick.onEditClick(position, currentItem);
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

    public interface OnCardClick
    {
        void onDeleteClick(int position,SubjectDetails subjectDetails);
        void onEditClick(int position, SubjectDetails subjectDetails);
    }


}
