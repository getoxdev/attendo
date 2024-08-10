package com.attendo.Schedule;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.attendo.R;
import com.attendo.Schedule.Adapters.NoticeAdapter;
import com.attendo.Schedule.Adapters.NoticeAdapterStudent;
import com.attendo.Schedule.Preference.AppPreferences;
import com.attendo.data.model.schedule.NoticeDetails;
import com.attendo.databinding.FragmentNoticeStudentBinding;
import com.attendo.viewmodel.NoticeViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.transition.MaterialSharedAxis;

public class NoticeFragmentStudent extends Fragment implements NoticeAdapterStudent.CallBack {

    private FragmentNoticeStudentBinding binding;

    private NoticeAdapterStudent noticeAdapterStudent;
    NoticeViewModel noticeViewModel;
    AppPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noticeViewModel = new ViewModelProvider(this).get(NoticeViewModel.class);
        preferences = AppPreferences.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNoticeStudentBinding.inflate(inflater,container,false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Notice");
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setVisibility(View.GONE);

        setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.X, true));

        noticeViewModel.get_All_notice(preferences.RetrieveClassId());
        noticeViewModel.get_all_noticeResponse().observe(getViewLifecycleOwner(),data->{
            if(data!=null)
            {
                binding.searchingNoticeLottieStudent.setVisibility(View.INVISIBLE);
                binding.noticeProgressBarStudent.hide();
                if(data.getNoticeDetailsList().size() == 0){
                    binding.noNoticeStudentLottie.setVisibility(View.VISIBLE);
                    binding.noNoticeStudentTxtview.setVisibility(View.VISIBLE);
                }else{
                    binding.noNoticeStudentLottie.setVisibility(View.INVISIBLE);
                    binding.noNoticeStudentTxtview.setVisibility(View.INVISIBLE);
                }

                noticeAdapterStudent = new NoticeAdapterStudent(getContext(),data.getNoticeDetailsList(), this::onCardClick);
                binding.noticeStudentRecyclerview.setAdapter(noticeAdapterStudent);
            }
            else{
                binding.noticeProgressBarStudent.hide();
                binding.noNoticeStudentLottie.setVisibility(View.VISIBLE);
                binding.noNoticeStudentTxtview.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(),"Something went wrong. Try again !",Toast.LENGTH_SHORT).show();
            }

        });
        binding.noticeStudentRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        return binding.getRoot();
    }

    @Override
    public void onCardClick(int position, NoticeDetails noticeDetails) {
        NoticeBodyBottomSheetFragment fragment = NoticeBodyBottomSheetFragment.newInstance(noticeDetails.getTitle(), noticeDetails.getBody());
        fragment.show(getParentFragmentManager(), "notice");
    }
}