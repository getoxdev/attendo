package com.attendo.Schedule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.attendo.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoticeBodyBottomSheetFragment extends BottomSheetDialogFragment {


    @BindView(R.id.title_notice)
    TextView title;

    @BindView(R.id.body_notice)
    TextView body;


    private static final String TITLE = "title_notice";
    private static final String BODY = "body_notice";


    private String mTitle;
    private String mBody;

    public NoticeBodyBottomSheetFragment() {
        // Required empty public constructor
    }



    public static NoticeBodyBottomSheetFragment newInstance(String title, String body) {
        NoticeBodyBottomSheetFragment fragment = new NoticeBodyBottomSheetFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(BODY, body);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
            mBody = getArguments().getString(BODY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_notice_body_bottom_sheet, container, false);
        ButterKnife.bind(this, view);

        title.setText(mTitle);
        body.setText(mBody);

        return view;
    }
}