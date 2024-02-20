package com.attendo.Schedule.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.attendo.R;
import com.attendo.Schedule.DeleteFragment;
import com.attendo.Schedule.Edit_schedule_fragment;
import com.attendo.Schedule.Interface.UpdateRecyclerView;
import com.attendo.data.model.schedule.SubjectDetails;
import com.attendo.databinding.SubjectCardBinding;
import com.attendo.ui.main.BottomNavMainActivity;
import com.attendo.viewmodel.FirebaseScheduleViewModel;
import com.attendo.viewmodel.ScheduleViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import java.util.List;

public class RoutineItemAdapterCr extends RecyclerView.Adapter<RoutineItemAdapterCr.RoutineItemAdapterCrHolder> {

    private List<SubjectDetails> items;
    int index=0;
    private Context mContext;
    private FirebaseScheduleViewModel firebaseScheduleViewModel;
    private ScheduleViewModel scheduleViewModel;
    private DeleteFragment delete_fragment;
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
        SubjectCardBinding binding = SubjectCardBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new RoutineItemAdapterCrHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineItemAdapterCrHolder holder, @SuppressLint("RecyclerView") int position) {
        SubjectDetails currentItem = items.get(position);
        holder.binding.subjectname.setText(currentItem.getSubject());
        holder.binding.instructor.setText(currentItem.getFaculty());
        holder.binding.time.setText(currentItem.getTime());
        // String scheduleclassid = currentItem.get_id();

        final Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);

        holder.binding.subjectCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                index = position;

                // String sclassid = currentItem.get_id();

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
                        bottomSheetDialog.dismiss();

                        onCardClick.onDeleteClick(position, currentItem);
                    }
                });

                editSchedule.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();

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
        SubjectCardBinding binding;
        public RoutineItemAdapterCrHolder(@NonNull SubjectCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnCardClick
    {
        void onDeleteClick(int position,SubjectDetails subjectDetails);
        void onEditClick(int position, SubjectDetails subjectDetails);
    }


}
