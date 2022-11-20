package com.attendo.Schedule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.attendo.R;
import com.attendo.Schedule.Adapters.NoticeAdapter;
import com.attendo.Schedule.Preference.AppPreferences;
import com.attendo.databinding.FragmentDeleteNoticeBinding;
import com.attendo.viewmodel.NoticeViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;



public class Delete_notice_fragment extends BottomSheetDialogFragment {
    FragmentDeleteNoticeBinding binding;

    private String Notice_ID;
    NoticeViewModel noticeViewModel;
    AppPreferences appPreferences;

    private NoticeAdapter.On_CardClick cardClick;

    Button delete_btn;
    LottieAnimationView deleteAnim;



    public Delete_notice_fragment(NoticeAdapter.On_CardClick cardClick) {
        this.cardClick = cardClick;
    }


    public static Delete_notice_fragment newInstance(String Notice_id, NoticeAdapter.On_CardClick cardClick) {
        Delete_notice_fragment fragment = new Delete_notice_fragment(cardClick);
        Bundle args = new Bundle();
        args.putString("NoticeId", Notice_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Notice_ID = getArguments().getString("NoticeId");
        }
        noticeViewModel = new ViewModelProvider(this).get(NoticeViewModel.class);
        appPreferences = AppPreferences.getInstance(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_delete_notice, container, false);
        binding.deleteNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.lottieAnimationViewDelete.pauseAnimation();
                binding.lottieAnimationViewDelete.setAnimation(R.raw.done_lottie_anim);
                binding.lottieAnimationViewDelete.playAnimation();
                delete_notice(Notice_ID);
            }
        });




        return  binding.getRoot();
    }
    public  void delete_notice(String notice_ID){
        noticeViewModel.Delete_notice(notice_ID,appPreferences.RetrieveClassId());
        noticeViewModel.getDeleteResponse().observe(getActivity(),data->{
            if (data == null) {
                Toast.makeText(getActivity(),"Fail to delete notice",Toast.LENGTH_SHORT).show();
                Log.i("ApiCall", "Failed");
                dismiss();
            } else {
                Log.i("ApiCall", "delete successFull");
                cardClick.refreshOnUpdateAndDelete();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                },900);

            }
        });

    }
}