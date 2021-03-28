package com.gachugusville.servicedforbusiness.Registration;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Dashboard.DashboardActivity;
import com.gachugusville.servicedforbusiness.Utils.Provider;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tapadoo.alerter.Alerter;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class UserAgreements extends AppCompatActivity {

    private LocationRequest locationRequest;
    private final FirebaseFirestore providersDatabase = FirebaseFirestore.getInstance(); // database reference
    private final String userID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreements);
        MaterialButton finish_registration = findViewById(R.id.finish_registration);
        MaterialButton btn_decline_terms = findViewById(R.id.btn_decline_terms);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(20 * 1000);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                        txtLocation.setText(String.format(Locale.US, "%s -- %s", wayLatitude, wayLongitude));
                    }
                }
            }
        };

        Toast.makeText(this, "Entered activity", Toast.LENGTH_SHORT).show();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            try {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            } catch (Exception e) {
                Toast.makeText(this, "Location Permission denied", Toast.LENGTH_SHORT).show();
                Log.d("PermissionLocation", e.getMessage());
            }

        }
        btn_decline_terms.setOnClickListener(v1 -> UserAgreements.super.onBackPressed());
        finish_registration.setOnClickListener(v -> {

            if (isNetworkAvailable()) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    uploadData();
                } else getLocation();
            } else {
                Alerter.create(this)
                        .enableSwipeToDismiss()
                        .setTitle("No internet")
                        .setText("check your internet connection")
                        .show();
            }
        });

    }

    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
            Location location = task.getResult();
            //initiate addresses
            try {
                Geocoder geocoder = new Geocoder(UserAgreements.this, Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();
                //set Location of provider
                Provider.getInstance().setLatitude(latitude);
                Provider.getInstance().setLongitude(longitude);
                Provider.getInstance().setCountry(addresses.get(0).getCountryName());

            } catch (IOException e) {
                Log.d("LocationError", e.getMessage());
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
        }).addOnCompleteListener(UserAgreements.this, task -> startActivity(new Intent(UserAgreements.this, DashboardActivity.class)));
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}