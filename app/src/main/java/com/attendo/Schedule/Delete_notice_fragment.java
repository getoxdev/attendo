package com.attendo.Schedule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.attendo.R;
import com.attendo.Schedule.Preference.AppPreferences;
import com.attendo.viewmodel.NoticeViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Delete_notice_fragment extends BottomSheetDialogFragment {

    private String Notice_ID;
    NoticeViewModel noticeViewModel;
    AppPreferences appPreferences;

    @BindView(R.id.delete_notice)
    Button delete_btn;



    public Delete_notice_fragment() {
        // Required empty public constructor
    }


    public static Delete_notice_fragment newInstance(String Notice_id) {
        Delete_notice_fragment fragment = new Delete_notice_fragment();
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
        ButterKnife.bind(this,view);

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_notice(Notice_ID);
            }
        });




        return  view;
    }
    public  void delete_notice(String notice_ID){
        noticeViewModel.Delete_notice(notice_ID,appPreferences.RetrieveClassId());
        noticeViewModel.getDeleteResponse().observe(getActivity(),data->{
            if (data == null) {
                Toast.makeText(getActivity(),"Fail to delete notice",Toast.LENGTH_SHORT).show();
                Log.i("ApiCall", "Failed");
                dismiss();
            } else {
                Toast.makeText(getActivity(),"Notice deleted successfully",Toast.LENGTH_SHORT).show();
                Log.i("ApiCall", "delete successFull");
                dismiss();
            }
        });

    }
}