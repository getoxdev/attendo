package com.attendo.Schedule;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.attendo.R;
import com.attendo.Schedule.Adapters.NoticeAdapter;
import com.attendo.Schedule.Preference.AppPreferences;
import com.attendo.data.model.schedule.Notice;
import com.attendo.databinding.FragmentAddNoticeBinding;
import com.attendo.ui.CustomLoadingDialog;
import com.attendo.viewmodel.NoticeViewModel;


public class Edit_notice_fragment extends Fragment {

    private FragmentAddNoticeBinding binding;

    String title,body,Notice_id;

    NoticeViewModel noticeViewModel;
    AppPreferences appPreferences;
    private NoticeFragment noticeFragment;
    private NoticeAdapter.On_CardClick on_cardClick;
    private CustomLoadingDialog customLoadingDialog;

    public Edit_notice_fragment(NoticeAdapter.On_CardClick on_cardClick) {
        this.on_cardClick = on_cardClick;
    }



    public static Edit_notice_fragment newInstance(String title, String body, String Notice_id, NoticeAdapter.On_CardClick on_cardClick) {

        Edit_notice_fragment fragment = new Edit_notice_fragment(on_cardClick);
        Bundle args = new Bundle();
        args.putString("TITLE", title);
        args.putString("BODY", body);
        args.putString("Notice_Id",Notice_id);
        fragment.setArguments(args);
        return fragment;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString("TITLE");
            body = getArguments().getString("BODY");
            Notice_id = getArguments().getString("Notice_Id");
        }
        noticeViewModel = new ViewModelProvider(this).get(NoticeViewModel.class);
        appPreferences = AppPreferences.getInstance(getContext());
        customLoadingDialog = new CustomLoadingDialog(getActivity());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddNoticeBinding.inflate(inflater, container, false);

        binding.Title.setText(title);
        binding.body.setText(body);

        noticeFragment = new NoticeFragment();

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customLoadingDialog.startDialog(false);
                edit_notice(Notice_id);

            }
        });

        return binding.getRoot();
    }

    public void edit_notice(String notice_id)
    {
        Notice notice = new Notice(binding.Title.getText().toString(), binding.body.getText().toString());
        noticeViewModel.edit_notice(notice_id,notice);
        noticeViewModel.get_Notice_Response().observe(getViewLifecycleOwner(),data->{
            if (data == null) {
                customLoadingDialog.dismissDialog();
                Toast.makeText(getActivity(),"Fail to edit Schedule",Toast.LENGTH_SHORT).show();
                Log.i("ApiCall", "Failed");
            } else {
                customLoadingDialog.dismissDialog();
                Toast.makeText(getActivity(),"Notice edited",Toast.LENGTH_SHORT).show();
                getParentFragmentManager().popBackStack();

            }
        });
    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frame,fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
}