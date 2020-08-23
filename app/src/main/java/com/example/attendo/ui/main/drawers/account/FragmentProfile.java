package com.example.attendo.ui.main.drawers.account;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.attendo.R;
import com.example.attendo.ui.main.MainActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class FragmentProfile extends Fragment {

    Button btn;
    EditText name,college,city,Contact;
    private FragmentUserProfile fragment_user_profile;
    ImageView profileimage;
    CardView profilecard;
    private Uri filepath;
    ProgressBar pB;


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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Create Profile");

        fragment_user_profile = new FragmentUserProfile();
        btn = view.findViewById(R.id.save_profile);
        name = view.findViewById(R.id.user_name);
        city = view.findViewById(R.id.user_city);
        college = view.findViewById(R.id.user_school);
        Contact = view.findViewById(R.id.user_phone);
        profileimage = view.findViewById(R.id.user_image);
        profilecard = view.findViewById(R.id.image_cardview);
        pB = view.findViewById(R.id.user_progressbar);
        pB.setVisibility(View.INVISIBLE);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("data");
        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString().trim();
                String College =college.getText().toString();
                String City = city.getText().toString();
                String contact = Contact.getText().toString();
                if(Name.isEmpty() || College.isEmpty() || City.isEmpty() || contact.isEmpty() || filepath==null) {
                    Toast.makeText(getContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
                 }
                else{
                    //for generating unique id everytime which may not require

                    pB.setVisibility(View.VISIBLE);
                    String id = user_id;
                    ProfileData prf = new ProfileData(id, name.getText().toString().trim(), college.getText().toString(), city.getText().toString().trim(), Contact.getText().toString().trim());
                    databaseReference.child(id).setValue(prf);
                    UploadImage();

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
                                  Toast.makeText(getContext(),"Account Updated "+name.getText().toString(),Toast.LENGTH_SHORT).show();
                                  Intent intent=new Intent(getActivity(), MainActivity.class);
                                  startActivity(intent);
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



        return view;
    }

}
