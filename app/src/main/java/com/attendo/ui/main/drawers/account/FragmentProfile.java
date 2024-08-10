package com.attendo.ui.main.drawers.account;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.attendo.databinding.FragmentProfileBinding;
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

public class FragmentProfile extends Fragment {

    private FragmentProfileBinding binding;

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
                binding.userImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Create Profile");

        binding.userProgressbar.setVisibility(View.INVISIBLE);

        //retriving data from previous fragment
        Bundle bundle = this.getArguments();
        String data = bundle.getString("name");
        binding.userName.setText(data);
        String data2 = bundle.getString("institution");
        binding.userSchool.setText(data2);
        String data3 = bundle.getString("city");
        binding.userCity.setText(data3);
        String data4 = bundle.getString("phone");
        binding.userPhone.setText(data4);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("data");
        mAuth = FirebaseAuth.getInstance();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI();
    }

    private void initUI() {
        String user_id = mAuth.getCurrentUser().getUid();
        binding.saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.userProgressbar.setVisibility(View.VISIBLE);
                String Name = binding.userName.getText().toString();
                String College = binding.userSchool.getText().toString();
                String City = binding.userCity.getText().toString();
                String contact = binding.userPhone.getText().toString().trim();
                if (Name.isEmpty() || College.isEmpty() || City.isEmpty() || contact.isEmpty()) {
                    binding.userProgressbar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    //for generating unique id may not require everytime
                    if (contact.length() != 10) {
                        binding.userProgressbar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "Enter a valid Mobile Number of length 10", Toast.LENGTH_SHORT).show();
                    } else {
                        binding.userProgressbar.setVisibility(View.VISIBLE);
                        String id = user_id;
                        ProfileData prf = new ProfileData(id, binding.userName.getText().toString().trim(), binding.userSchool.getText().toString(), binding.userCity.getText().toString().trim(), binding.userPhone.getText().toString().trim());
                        databaseReference.child(id).setValue(prf);

                        //Profile imgae is optional...
                        if (filepath != null) {
                            UploadImage();
                        } else {
                            binding.userProgressbar.setVisibility(View.INVISIBLE);
                            storageReference.child("images/" + user_id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    databaseReference.child("images/" + user_id.toString()).removeValue();
                                    Toast.makeText(getActivity(), "Account Updated " + binding.userName.getText().toString(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity(), BottomNavMainActivity.class);
                                    startActivity(intent);
                                    requireActivity().finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "Account Updated", Toast.LENGTH_SHORT).show();
                                    Log.d("Execption got", e.toString());
                                    Intent intent = new Intent(getActivity(), BottomNavMainActivity.class);
                                    startActivity(intent);
                                    requireActivity().finish();
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
                                    binding.userProgressbar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getActivity(),"Account Updated "+binding.userName.getText().toString(),Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(getActivity(), BottomNavMainActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                }
                            });
                }
            }
        });


        binding.imageCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Profile Image"),1);
            }
        });

        //SKIP ACCOUNT CREATION CODE

        binding.skipAccountCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Your Account Not Updated!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), BottomNavMainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

}
