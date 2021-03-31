package com.gachugusville.servicedforbusiness.Dashboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gachugusville.development.servicedforbusiness.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.net.URI;

public class EditProfileActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private URI imageURI;
    private String myUr = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePIcRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        MaterialButton btn_change_profile = findViewById(R.id.btn_change_profile);
        MaterialButton btn_save_profile = findViewById(R.id.btn_save_profile);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
        storageProfilePIcRef = FirebaseStorage.getInstance().getReference().child("Profile Pic");

        btn_save_profile.setOnClickListener(v -> {
            uploadProfileImage();
        });
        btn_change_profile.setOnClickListener(v -> {
            CropImage.activity().setAspectRatio(1, 1).start(EditProfileActivity.this);
        });


    }

    private void uploadProfileImage() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE  && resultCode == REA)
    }
}