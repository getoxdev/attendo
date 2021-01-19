package com.attendo.Schedule.Adapters;

import android.app.Activity;
import android.content.Context;
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

    UpdateRecyclerView updateRecyclerView;






    public RoutineItemAdapterCr(Context mContext,List<SubjectDetails> items,Activity activity,UpdateRecyclerView updateRecyclerView)
    {
        this.items = items;
        this.mContext = mContext;
        this.activity = activity;
        this.updateRecyclerView = updateRecyclerView;

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
        String scheduleid = firebaseScheduleViewModel.RetrieveSchdeuleId();




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

                index = position;
                updateRecyclerView.sendPosition(position);
                updateRecyclerView.getscheduleClassId(scheduleclassid);
                String sclassid = currentItem.get_id();
                Log.e("scheduleclassid",sclassid);


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
                    }
                });

                editSchedule.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //example to open bottom sheet dialog fragment from adapter itself
                        Edit_schedule_fragment schedule_fragment = new Edit_schedule_fragment();
                        schedule_fragment.show(((AppCompatActivity) activity).getSupportFragmentManager(), "Edit");
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
