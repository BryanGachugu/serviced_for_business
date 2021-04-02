package com.gachugusville.servicedforbusiness.Genesis;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Dashboard.DashboardActivity;
import com.gachugusville.servicedforbusiness.Utils.Provider;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String Uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    private final DocumentReference docRef = db.collection("Providers").document(Uid);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.SplashScreenTheme);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            getAllDataFromDatabase();
            Log.d("Fuck", Provider.getInstance().getRef_url1());
        } else
            startActivity(new Intent(this, StartActivity.class));
    }

    private void getAllDataFromDatabase() {
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    documentSnapshot.toObject(Provider.getInstance().getClass());
                    Provider.getInstance().setUser_name(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).getUser_name());
                    Provider.getInstance().setRegistrationFinished(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).isRegistrationFinished());
                    Provider.getInstance().setBrand_name(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).getBrand_name());
                    startActivity(new Intent(SplashScreen.this, DashboardActivity.class));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Failure", e.getLocalizedMessage());
            }
        });
    }

}