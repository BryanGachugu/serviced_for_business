package com.gachugusville.servicedforbusiness.Genesis;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Dashboard.DashboardActivity;
import com.gachugusville.servicedforbusiness.Registration.AvailabilityActivity;
import com.gachugusville.servicedforbusiness.Registration.LogInActivity;
import com.gachugusville.servicedforbusiness.Registration.SignUp;
import com.gachugusville.servicedforbusiness.Utils.Provider;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {
    private static final int GPS_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.light_blue));
        getWindow().setNavigationBarColor(getResources().getColor(R.color.white));
        getWindow().setExitTransition(new Explode());
        setContentView(R.layout.activity_start);
        MaterialButton btn_signUp = findViewById(R.id.btn_signUp);

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(task -> {
            try {
                //Below unassigned variable needed
                LocationSettingsResponse response = task.getResult(ApiException.class);
            } catch (ApiException e) {
                switch (e.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                            resolvableApiException.startResolutionForResult(StartActivity.this, GPS_REQUEST_CODE);
                        } catch (IntentSender.SendIntentException sendIntentException) {
                            sendIntentException.printStackTrace();
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });

        btn_signUp.setOnClickListener(v -> nextActivity());
        findViewById(R.id.btn_logIn).setOnClickListener(v -> startActivity(new Intent(this, LogInActivity.class)));

    }

    private void nextActivity() {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPS_REQUEST_CODE) {
            switch (resultCode) {
                case AvailabilityActivity.RESULT_OK:
                    Toast.makeText(this, "GPS turned on successfully", Toast.LENGTH_SHORT).show();
                    break;
                case AvailabilityActivity.RESULT_CANCELED:
                    Toast.makeText(this, "Location permission needed", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + requestCode);
            }
        }

    }

}