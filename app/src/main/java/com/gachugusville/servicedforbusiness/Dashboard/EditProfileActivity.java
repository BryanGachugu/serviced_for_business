package com.gachugusville.servicedforbusiness.Dashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Utils.Dialog;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.net.URI;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private Uri imageURI;
    private String myUr = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePIcRef;
    private CircleImageView profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        MaterialButton btn_change_profile = findViewById(R.id.btn_change_profile);
        MaterialButton btn_save_profile = findViewById(R.id.btn_save_profile);
        profile_image = findViewById(R.id.profile_image);

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
        Dialog dialog = new Dialog(this);
        dialog.startDialog();
        if (imageURI != null){
            final StorageReference fileRef = storageProfilePIcRef.child(mAuth.getCurrentUser().getUid() + ".jpg");
            uploadTask = fileRef.putFile(imageURI);
            uploadTask.continueWithTask(new Continuation() {
                @NonNull
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {

                }
            })
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE  && resultCode == RESULT_OK && data != null){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageURI = result.getUri();
            profile_image.setImageURI(imageURI);
        }else {
            Toast.makeText(this, "Error, Try again", Toast.LENGTH_SHORT).show();
        }
    }
}