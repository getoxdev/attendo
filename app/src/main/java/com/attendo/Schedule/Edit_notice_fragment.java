package com.attendo.Schedule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.attendo.R;
import com.attendo.Schedule.Adapters.NoticeAdapter;
import com.attendo.Schedule.Preference.AppPreferences;
import com.attendo.data.model.schedule.Notice;
import com.attendo.ui.CustomLoadingDialog;
import com.attendo.viewmodel.NoticeViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Edit_notice_fragment extends Fragment {

    String title,body,Notice_id;

    @BindView(R.id.Title)
    EditText Title;

    @BindView(R.id.body)
    EditText Body;

    @BindView(R.id.btn_send)
    Button notify;
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
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_add_notice, container, false);
        ButterKnife.bind(this, view);
        Title.setText(title);
        Body.setText(body);

        noticeFragment = new NoticeFragment();

        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customLoadingDialog.startDialog(false);
                edit_notice(Notice_id);

            }
        });


       return view;
    }

    public void edit_notice(String notice_id)
    {
        Notice notice = new Notice(Title.getText().toString(),Body.getText().toString());
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