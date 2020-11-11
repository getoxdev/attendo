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

public class FragmentAbout extends Fragment {

    BottomNavigationView bottomNavigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_about, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("About Us");

        //connect to developers
        CardView mentorCard, learner1, learner2, learner3, learner4, mentor2, learner5;

        //---------------------------  hooks  ---------------------------------
        mentorCard = view.findViewById(R.id.mentor1);
        learner1 = view.findViewById(R.id.learner1);
        learner2 = view.findViewById(R.id.learner2);
        learner3 = view.findViewById(R.id.learner3);
        learner4 = view.findViewById(R.id.learner4);
        mentor2 = view.findViewById(R.id.mentor2);
        learner5 = view.findViewById(R.id.learner5);

        //---------------------------------------------------------------------


        //----------------  Mentor card on click  ----------------------------
        mentorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(),R.style.BottomSheetDialog);
            View bottomsheet = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bottom_sheet,
                    (ConstraintLayout) view.findViewById(R.id.bottom_sheet_container));

            //hooks
            ImageView github = bottomsheet.findViewById(R.id.github_icon);
            ImageView linkedin = bottomsheet.findViewById(R.id.linked_icon);
            ImageView instagram = bottomsheet.findViewById(R.id.instagram_icon);
            ImageView facebook = bottomsheet.findViewById(R.id.facebook_icon);

                github.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoUrl("https://github.com/getoxdev");

                }
            });

                linkedin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoUrl("https://www.linkedin.com/in/rishiraj-paul-chowdhury-825419171/");
                }
            });

                instagram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoUrl("https://www.instagram.com/pc_rishi/");
                }
            });
                facebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoUrl("https://www.facebook.com/rishiraj.paulchowdhury.7");
                }
            });

                bottomSheetDialog.setContentView(bottomsheet);
                bottomSheetDialog.show();


        }
        });
        //----------------------------------------------------------------------

        //----------------  learner 1 card on click  ----------------------------
        learner1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(),R.style.BottomSheetDialog);
                View bottomsheet = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bottom_sheet,
                        (ConstraintLayout) view.findViewById(R.id.bottom_sheet_container));

                //hooks
                ImageView github = bottomsheet.findViewById(R.id.github_icon);
                ImageView linkedin = bottomsheet.findViewById(R.id.linked_icon);
                ImageView instagram = bottomsheet.findViewById(R.id.instagram_icon);
                ImageView facebook = bottomsheet.findViewById(R.id.facebook_icon);

                github.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoUrl("https://github.com/Rajkishan0406");

                    }
                });

                linkedin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoUrl("https://www.linkedin.com/in/raj-kishan-0a02491b8/");
                    }
                });

                instagram.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoUrl("https://www.instagram.com/rajkishan04/");
                    }
                });
                facebook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoUrl("https://www.facebook.com/raj.kishan.583");
                    }
                });

                bottomSheetDialog.setContentView(bottomsheet);
                bottomSheetDialog.show();

            }
        });
        //----------------------------------------------------------------------

        //----------------  learner 2 card on click  ----------------------------
        learner2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(),R.style.BottomSheetDialog);
                View bottomsheet = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bottom_sheet,
                        (ConstraintLayout) view.findViewById(R.id.bottom_sheet_container));

                //hooks
                ImageView github = bottomsheet.findViewById(R.id.github_icon);
                ImageView linkedin = bottomsheet.findViewById(R.id.linked_icon);
                ImageView instagram = bottomsheet.findViewById(R.id.instagram_icon);
                ImageView facebook = bottomsheet.findViewById(R.id.facebook_icon);

                github.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoUrl("https://github.com/amanaryan512");

                    }
                });

                linkedin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoUrl("https://www.linkedin.com/in/aman-aryan-92ab031a1");

                    }
                });

                instagram.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoUrl("https://www.instagram.com/amanaryan560/");
                    }
                });
                facebook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoUrl("https://www.facebook.com/profile.php?id=100040614994331");
                    }
                });

                bottomSheetDialog.setContentView(bottomsheet);
                bottomSheetDialog.show();


            }
        });
        //----------------------------------------------------------------------

        //----------------  learner 3 card on click  ----------------------------
        learner3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(),R.style.BottomSheetDialog);
                View bottomsheet = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bottom_sheet,
                        (ConstraintLayout) view.findViewById(R.id.bottom_sheet_container));

                //hooks
                ImageView github = bottomsheet.findViewById(R.id.github_icon);
                ImageView linkedin = bottomsheet.findViewById(R.id.linked_icon);
                ImageView instagram = bottomsheet.findViewById(R.id.instagram_icon);
                ImageView facebook = bottomsheet.findViewById(R.id.facebook_icon);

                github.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoUrl("https://github.com/sarma923");

                    }
                });

                linkedin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoUrl("https://www.linkedin.com/in/shristi-sarma-69619713a/");
                    }
                });

                instagram.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "What are you looking for? ", Toast.LENGTH_SHORT).show();
                    }
                });
                facebook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoUrl("https://www.facebook.com/ria.sarma.733");
                    }
                });

                bottomSheetDialog.setContentView(bottomsheet);
                bottomSheetDialog.show();

            }
        });
        //----------------------------------------------------------------------

        //----------------  learner 4 card on click  ----------------------------
        learner4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(),R.style.BottomSheetDialog);
                View bottomsheet = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bottom_sheet,
                        (ConstraintLayout) view.findViewById(R.id.bottom_sheet_container));

                //hooks
                ImageView github = bottomsheet.findViewById(R.id.github_icon);
                ImageView linkedin = bottomsheet.findViewById(R.id.linked_icon);
                ImageView instagram = bottomsheet.findViewById(R.id.instagram_icon);
                ImageView facebook = bottomsheet.findViewById(R.id.facebook_icon);

                github.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoUrl("https://github.com/JyotimoyKashyap");

                    }
                });

                linkedin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoUrl("https://www.linkedin.com/in/jyotimoy-kashyap-62a792190/");
                    }
                });

                instagram.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoUrl("https://www.instagram.com/captured.memories12/");
                    }
                });
                facebook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoUrl("https://www.facebook.com/jyotimoy.kashyap.33");
                    }
                });

                bottomSheetDialog.setContentView(bottomsheet);
                bottomSheetDialog.show();

            }
        });
        //----------------------------------------------------------------------

        //----------------  Mentor 2 card on click  ----------------------------
        mentor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(),R.style.BottomSheetDialog);
                View bottomsheet = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bottom_sheet,
                        (ConstraintLayout) view.findViewById(R.id.bottom_sheet_container));

                //hooks
                ImageView github = bottomsheet.findViewById(R.id.github_icon);
                ImageView linkedin = bottomsheet.findViewById(R.id.linked_icon);
                ImageView instagram = bottomsheet.findViewById(R.id.instagram_icon);
                ImageView facebook = bottomsheet.findViewById(R.id.facebook_icon);

                github.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoUrl("https://github.com/Nanduag0");

                    }
                });

                linkedin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoUrl("https://www.linkedin.com/in/nandini-agarwal-889217189/");
                    }
                });

                instagram.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Not Available", Toast.LENGTH_SHORT).show();
                    }
                });
                facebook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoUrl("https://www.facebook.com/nandini.agarwal.921");
                    }
                });

                bottomSheetDialog.setContentView(bottomsheet);
                bottomSheetDialog.show();


            }
        });

        //------------------- Learner 5 card ---------------------------------------------------
        learner5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(),R.style.BottomSheetDialog);
                View bottomsheet = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bottom_sheet,
                        (ConstraintLayout) view.findViewById(R.id.bottom_sheet_container));

                //hooks
                ImageView github = bottomsheet.findViewById(R.id.github_icon);
                ImageView linkedin = bottomsheet.findViewById(R.id.linked_icon);
                ImageView instagram = bottomsheet.findViewById(R.id.instagram_icon);
                ImageView facebook = bottomsheet.findViewById(R.id.facebook_icon);

                github.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoUrl("https://github.com/gauravdas014");

                    }
                });

                linkedin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoUrl("https://www.linkedin.com/in/gauravdas014/");
                    }
                });

                instagram.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoUrl("https://www.instagram.com/gaurav_das__/");
                    }
                });
                facebook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoUrl("https://www.facebook.com/gaurav.das.338211");
                    }
                });

                bottomSheetDialog.setContentView(bottomsheet);
                bottomSheetDialog.show();

            }
        });




        return view;
    }

    private void gotoUrl(String url){

        Uri uri = Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}
