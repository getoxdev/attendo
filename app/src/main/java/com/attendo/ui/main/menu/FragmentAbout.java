package com.attendo.ui.main.menu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.attendo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class FragmentAbout extends Fragment {

    @BindView(R.id.mentor1)
    CardView mentorCard;

    @BindView(R.id.mentor2)
    CardView mentor2;

    @BindView(R.id.learner1)
    CardView learner1;

    @BindView(R.id.learner2)
    CardView learner2;

    @BindView(R.id.learner3)
    CardView learner3;

    @BindView(R.id.learner4)
    CardView learner4;

    @BindView(R.id.learner5)
    CardView learner5;
    @BindView(R.id.junior_learner01)
    CardView learner6;
    @BindView(R.id.junior_learner02)
    CardView learner7;
    @BindView(R.id.junior_learner03)
    CardView learner8;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this, view);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("About Us");


        //setting on click listener for all the cards

        mentorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetFunction("https://www.facebook.com/rishiraj.paulchowdhury.7", "https://www.instagram.com/pc_rishi/",
                        "https://www.linkedin.com/in/rishiraj-paul-chowdhury-825419171/", "https://github.com/getoxdev");

            }
        });

        learner1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetFunction("https://www.facebook.com/raj.kishan.583", "https://www.instagram.com/rajkishan04/",
                        "https://www.linkedin.com/in/raj-kishan-0a02491b8/", "https://github.com/Rajkishan0406");

            }
        });


        learner2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetFunction("https://www.facebook.com/profile.php?id=100040614994331", "https://www.instagram.com/amanaryan560/",
                        "https://www.linkedin.com/in/aman-aryan-92ab031a1", "https://github.com/amanaryan512");
            }
        });


        //----------------  learner 3 card on click  ----------------------------
        learner3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetFunction("https://www.facebook.com/ria.sarma.733", null,
                        "https://www.linkedin.com/in/shristi-sarma-69619713a/", "https://github.com/sarma923");

            }
        });
        //----------------------------------------------------------------------

        //----------------  learner 4 card on click  ----------------------------
        learner4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetFunction("https://www.facebook.com/jyotimoy.kashyap.33", "https://www.instagram.com/jyotimoy_kashyap06/",
                        "https://www.linkedin.com/in/jyotimoy-kashyap-62a792190/", "https://github.com/JyotimoyKashyap");
            }
        });
        //----------------------------------------------------------------------

        //----------------  Mentor 2 card on click  ----------------------------
        mentor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetFunction("https://www.facebook.com/nandini.agarwal.921", null,
                        "https://www.linkedin.com/in/nandini-agarwal-889217189/", "https://github.com/Nanduag0");
            }
        });

        //------------------- Learner 5 card ---------------------------------------------------
        learner5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetFunction("https://www.facebook.com/gaurav.das.338211", "https://www.instagram.com/gaurav_das__/",
                        "https://www.linkedin.com/in/gauravdas014/", "https://github.com/gauravdas014");

            }
        });

        learner6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetFunction("https://www.facebook.com/SyedSameerZaidi123/","https://www.instagram.com/_interstellar07_/","https://www.linkedin.com/in/sameer-zaidi-541261226/","https://github.com/Interstellar07");
            }
        });
        learner7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetFunction("https://www.facebook.com/profile.php?id=100080411300265","https://www.instagram.com/navneetrajkarn/","https://www.linkedin.com/in/navneet-raj-08a720228/","https://github.com/navneet098");
            }
        });
        learner8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetFunction("https://www.facebook.com/zhang.jike.311/","https://www.instagram.com/jugya__kamal07/?hl=en","https://www.linkedin.com/in/jugya-kamal-gogoi-5ab594229/","https://github.com/jugya07");
            }
        });


        return view;
    }

    private void gotoUrl(String url) {

        Uri uri = Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    private void bottomSheetFunction(String facebookUrl, String instaUrl, String linkedInUrl, String githubUrl) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialog);
        View bottomsheet = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bottom_sheet,
                (ConstraintLayout) getView().findViewById(R.id.bottom_sheet_container));

        //hooks
        ImageView github = bottomsheet.findViewById(R.id.github_icon);
        ImageView linkedin = bottomsheet.findViewById(R.id.linked_icon);
        ImageView instagram = bottomsheet.findViewById(R.id.instagram_icon);
        ImageView facebook = bottomsheet.findViewById(R.id.facebook_icon);

        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (githubUrl == null) {
                    Toast.makeText(getActivity(), "Not Available", Toast.LENGTH_SHORT);
                } else {
                    gotoUrl(githubUrl);
                }


            }
        });

        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linkedInUrl == null) {
                    Toast.makeText(getActivity(), "Not Available", Toast.LENGTH_SHORT);
                } else {
                    gotoUrl(linkedInUrl);
                }
            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (instaUrl == null) {
                    Toast.makeText(getActivity(), "Not Available", Toast.LENGTH_SHORT);
                } else {
                    gotoUrl(instaUrl);
                }
            }
        });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (facebookUrl == null) {
                    Toast.makeText(getActivity(), "Not Available", Toast.LENGTH_SHORT);
                } else {
                    gotoUrl(facebookUrl);
                }
            }
        });

        bottomSheetDialog.setContentView(bottomsheet);
        bottomSheetDialog.show();


    }


}
