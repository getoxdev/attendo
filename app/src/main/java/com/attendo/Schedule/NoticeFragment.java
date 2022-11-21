package com.attendo.Schedule;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.attendo.R;
import com.attendo.Schedule.Adapters.NoticeAdapter;
import com.attendo.Schedule.Preference.AppPreferences;
import com.attendo.data.model.schedule.NoticeDetails;
import com.attendo.databinding.FragmentNoticeBinding;
import com.attendo.viewmodel.NoticeViewModel;
import com.attendo.viewmodel.ScheduleViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transition.MaterialArcMotion;
import com.google.android.material.transition.MaterialContainerTransform;
import com.google.android.material.transition.MaterialSharedAxis;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoticeFragment extends Fragment implements NoticeAdapter.On_CardClick{


    private NoticeAdapter noticeAdapter;
    private FloatingActionButton fb;
    NoticeViewModel noticeViewModel;
    private AddNoticeFragment addNoticeFragment;
    AppPreferences preferences;

//    @BindView(R.id.notice_recyclerview)
//    RecyclerView notice_recyclerview;
//
//    @BindView(R.id.notice_swipe_refresh)
//    SwipeRefreshLayout refreshLayout;
//
//    @BindView(R.id.notice_progress_bar_cr)
//    ContentLoadingProgressBar ProgressBar;
//
//    @BindView(R.id.no_notice_lottie)
//    LottieAnimationView lottieAnimationView;
//
//    @BindView(R.id.no_notice_txtview)
//    TextView noNoticeTextView;
//
//    @BindView(R.id.searching_notice_lottie_cr)
//    LottieAnimationView searchingLottie;

    private FragmentNoticeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noticeViewModel = new ViewModelProvider(this).get(NoticeViewModel.class);
        preferences = AppPreferences.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentNoticeBinding.inflate(getLayoutInflater(),container,false);
        //View view = inflater.inflate(R.layout.fragment_notice, container, false);
        View view=binding.getRoot();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Notice");
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setVisibility(View.GONE);

        setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.X, true));

        //ButterKnife.bind(this,view);


        MaterialContainerTransform transform = new MaterialContainerTransform();
        transform.setPathMotion(new MaterialArcMotion());
        transform.setDuration(400);

        addNoticeFragment = new AddNoticeFragment(this);
        addNoticeFragment.setSharedElementReturnTransition(transform);

        fb = view.findViewById(R.id.Add_Notice);

        noticeViewModel.get_All_notice(preferences.RetrieveClassId());
        noticeViewModel.get_all_noticeResponse().observe(getViewLifecycleOwner(),data->{
            if(data!=null)
            {
                binding.searchingNoticeLottieCr.setVisibility(View.INVISIBLE);
                binding.noticeProgressBarCr.hide();
                if(data.getNoticeDetailsList().size() == 0){
                    binding.noNoticeLottie.setVisibility(View.VISIBLE);
                    binding.noNoticeTxtview.setVisibility(View.VISIBLE);
                }else{
                    binding.noNoticeLottie.setVisibility(View.INVISIBLE);
                    binding.noNoticeTxtview.setVisibility(View.INVISIBLE);
                }
                noticeAdapter = new NoticeAdapter(getContext(),data.getNoticeDetailsList(),this);
                binding.noticeRecyclerview.setAdapter(noticeAdapter);
            }
            else{
                binding.noticeProgressBarCr.hide();

                binding.noNoticeLottie.setVisibility(View.VISIBLE);
                binding.noNoticeTxtview.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(),"No notice",Toast.LENGTH_SHORT).show();
            }

        });
        binding.noticeRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        binding.noticeSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                update_OnRefresh();
            }
        });


        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(addNoticeFragment);

            }
        });



    return view;
    }

    public  void update_OnRefresh()
    {
        noticeViewModel.get_All_notice(preferences.RetrieveClassId());
        noticeViewModel.get_all_noticeResponse().observe(getViewLifecycleOwner(),data->{
            if(data!=null) {
                binding.noticeProgressBarCr.hide();
                binding.noticeSwipeRefresh.setRefreshing(false);
                noticeAdapter = new NoticeAdapter(getContext(), data.getNoticeDetailsList(), this);
                noticeAdapter.notifyDataSetChanged();
                binding.noticeRecyclerview.setAdapter(noticeAdapter);
            }
            else{
                binding.noticeProgressBarCr.hide();
                Toast.makeText(getActivity(),"Something went wrong! please try again",Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void setFragment(Fragment fragment) {
        fragment.setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.Z, true));
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frame,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onDeleteN_Click(int position, NoticeDetails noticeDetails) {
        Delete_notice_fragment delete_notice_fragment = Delete_notice_fragment.newInstance(noticeDetails.get_id(), this);
        delete_notice_fragment.show(getParentFragmentManager(),"delete");

    }

    @Override
    public void onEditN_Click(int position, NoticeDetails noticeDetails) {
        Edit_notice_fragment edit_notice_fragment = Edit_notice_fragment.newInstance(noticeDetails.getTitle(),
                noticeDetails.getBody(),noticeDetails.get_id(), this);
        setFragment(edit_notice_fragment);

    }

    @Override
    public void refreshOnUpdateAndDelete() {
        binding.noticeSwipeRefresh.setRefreshing(true);
        noticeViewModel.get_All_notice(preferences.RetrieveClassId());
        noticeViewModel.get_all_noticeResponse().observe(getViewLifecycleOwner(),data->{
            if(data!=null)
            {
                binding.noticeSwipeRefresh.setRefreshing(false);
                noticeAdapter = new NoticeAdapter(getContext(),data.getNoticeDetailsList(),this);
                noticeAdapter.notifyDataSetChanged();
                binding.noticeRecyclerview.setAdapter(noticeAdapter);
            }

        });
    }

    @Override
    public void CardSingleClick(int position, NoticeDetails noticeDetails) {
        NoticeBodyBottomSheetFragment bottomSheetFragment = NoticeBodyBottomSheetFragment.newInstance(noticeDetails.getTitle(), noticeDetails.getBody());
        bottomSheetFragment.show(getParentFragmentManager(), "Notice");
    }
}