package com.gachugusville.servicedforbusiness.Registration;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Dashboard.DashboardActivity;
import com.gachugusville.servicedforbusiness.Utils.Provider;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tapadoo.alerter.Alerter;

import java.util.Objects;

public class UserAgreements extends AppCompatActivity {

    private final FirebaseFirestore providersDatabase = FirebaseFirestore.getInstance(); // database reference
    private final String userID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreements);
        MaterialButton finish_registration = findViewById(R.id.finish_registration);
        MaterialButton btn_decline_terms = findViewById(R.id.btn_decline_terms);

        Provider.getInstance().setRegistrationFinished(true);
        btn_decline_terms.setOnClickListener(v1 -> UserAgreements.super.onBackPressed());
        finish_registration.setOnClickListener(v -> {

            if (isNetworkAvailable()) {

                uploadData();
            } else {
                Alerter.create(this)
                        .enableSwipeToDismiss()
                        .setTitle("No internet")
                        .setText("check your internet connection")
                        .show();
            }
        });

    }

    private void uploadData() {

        providersDatabase.collection("Providers")
                .document(userID)
                .set(Provider.getInstance())
                .addOnSuccessListener(aVoid -> Toast.makeText(UserAgreements.this, "Account creation success", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> {
            Toast.makeText(UserAgreements.this, "Registration Failed", Toast.LENGTH_SHORT).show();
            Log.d("RegistrationFailed", e.getMessage());
        }).addOnCompleteListener(UserAgreements.this, task -> {
            startActivity(new Intent(UserAgreements.this, DashboardActivity.class));
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}