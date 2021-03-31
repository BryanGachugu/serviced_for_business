package com.gachugusville.servicedforbusiness.Dashboard;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private Uri imageURI;
    private String myUri = "";
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

        getUserInfo();

        btn_save_profile.setOnClickListener(v -> uploadProfileImage());
        btn_change_profile.setOnClickListener(v -> CropImage.activity(imageURI).setAspectRatio(1, 1).start(this));


    }

    private void getUserInfo() {
        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    if (snapshot.hasChild("image")) {
                        String image = snapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(profile_image);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void uploadProfileImage() {
        Dialog dialog = new Dialog(this);
        dialog.startDialog();
        if (imageURI != null) {
            final StorageReference fileRef = storageProfilePIcRef.child(mAuth.getCurrentUser().getUid() + ".jpg");
            StorageTask uploadTask = fileRef.putFile(imageURI);
            uploadTask.continueWithTask((Continuation) task -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return fileRef.getDownloadUrl();
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        myUri = downloadUri.toString();
                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("image", myUri);

                        databaseReference.child(mAuth.getCurrentUser().getUid()).updateChildren(userMap);
                        FirebaseFirestore.getInstance()
                                .collection("Providers")
                                .document(mAuth.getCurrentUser().getUid())
                                .update("profile_pic_url", myUri)
                                .addOnCompleteListener(command -> {
                                    dialog.dismissDialog();
                                    Toast.makeText(EditProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                }).addOnFailureListener(command -> {
                            dialog.dismissDialog();
                            Toast.makeText(EditProfileActivity.this, "Error, try again", Toast.LENGTH_SHORT).show();
                        });
                    }
                }
            });
        } else {
            dialog.dismissDialog();
            Toast.makeText(this, "Image not selected", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageURI = result.getUri();
            profile_image.setImageURI(imageURI);
        } else {
            Toast.makeText(this, "Error, Try again", Toast.LENGTH_SHORT).show();
        }
    }
}