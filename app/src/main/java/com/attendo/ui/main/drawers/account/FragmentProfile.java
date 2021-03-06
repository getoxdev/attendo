package com.attendo.ui.main.drawers.account;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.attendo.R;
import com.attendo.ui.main.BottomNavMainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class FragmentProfile extends Fragment {

    @BindView(R.id.skip_account_creation)
    TextView SkipAccountCreation;
    @BindView(R.id.user_name)
    EditText name;
    @BindView(R.id.user_school)
    EditText college;
    @BindView(R.id.user_city)
    EditText city;
    @BindView(R.id.user_phone)
    EditText Contact;
    @BindView(R.id.user_image)
    ImageView profileimage;
    @BindView(R.id.save_profile)
    Button btn;
    @BindView(R.id.user_progressbar)
    ProgressBar pB;

    private FragmentUserProfile fragment_user_profile;
    CardView profilecard;
    private Uri filepath;


    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filepath = data.getData();

            try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),filepath);
                    profileimage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment__profile, container, false);
        ButterKnife.bind(this,view);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Create Profile");

        fragment_user_profile = new FragmentUserProfile();
        profilecard = view.findViewById(R.id.image_cardview);
        pB.setVisibility(View.INVISIBLE);

        //retriving data from previous fragment
        Bundle bundle = this.getArguments();
        String data = bundle.getString("name");
        name.setText(data);
        String data2 = bundle.getString("institution");
        college.setText(data2);
        String data3 = bundle.getString("city");
        city.setText(data3);
        String data4 = bundle.getString("phone");
        Contact.setText(data4);



        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("data");
        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pB.setVisibility(View.VISIBLE);
                String Name = name.getText().toString();
                String College =college.getText().toString();
                String City = city.getText().toString();
                String contact = Contact.getText().toString().trim();
                if(Name.isEmpty() || College.isEmpty() || City.isEmpty() || contact.isEmpty()) {
                    if(Name.isEmpty() || College.isEmpty() || City.isEmpty() || contact.isEmpty()) {
                        pB.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
                    }
                 }
                else {
                    //for generating unique id may not require everytime
                    if (contact.length() != 10) {
                        pB.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "Enter a valid Mobile Number of length 10", Toast.LENGTH_SHORT).show();
                    } else {
                        pB.setVisibility(View.VISIBLE);
                        String id = user_id;
                        ProfileData prf = new ProfileData(id, name.getText().toString().trim(), college.getText().toString(), city.getText().toString().trim(), Contact.getText().toString().trim());
                        databaseReference.child(id).setValue(prf);

                        //Profile imgae is optional...
                        if (filepath != null) {
                            UploadImage();
                        } else {
                            pB.setVisibility(View.INVISIBLE);
                            storageReference.child("images/" + user_id.toString()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    databaseReference.child("images/" + user_id.toString()).removeValue();
                                    Toast.makeText(getActivity(), "Account Updated " + name.getText().toString(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity(), BottomNavMainActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "Account Updated", Toast.LENGTH_SHORT).show();
                                    Log.d("Execption got", e.toString());
                                    Intent intent = new Intent(getActivity(), BottomNavMainActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                }
                            });
                        }
                    }
                }
            }
            private void UploadImage() {
                if(filepath !=null){
                  StorageReference reference = storageReference.child("images/" + user_id.toString());
                  reference.putFile(filepath)
                          .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                              @Override
                              public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                  pB.setVisibility(View.INVISIBLE);
                                  Toast.makeText(getActivity(),"Account Updated "+name.getText().toString(),Toast.LENGTH_SHORT).show();
                                  Intent intent=new Intent(getActivity(), BottomNavMainActivity.class);
                                  startActivity(intent);
                                  getActivity().finish();
                              }
                          });
                }
            }
        });


        profilecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Profile Image"),1);
            }
        });

        //SKIP ACCOUNT CREATION CODE

        SkipAccountCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Your Account Not Updated!",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getActivity(), BottomNavMainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });




        return view;
    }

}
