package com.gachugusville.servicedforbusiness.Registration;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Utils.Provider;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import ca.antonious.materialdaypicker.MaterialDayPicker;

public class AvailabilityActivity extends AppCompatActivity {

    private EditText edit_distance_radius;
    private CheckBox checkbox_available_countrywide, checkbox_always_available;
    private TextView txt_time_from, txt_time_to;
    private MaterialDayPicker day_picker;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int GPS_REQUEST_CODE = 1001;
    private static final int LOCATION_REQUEST_CODE = 44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availabilty);

        checkbox_available_countrywide = findViewById(R.id.checkbox_available_countrywide);
        checkbox_always_available = findViewById(R.id.checkbox_always_available);
        edit_distance_radius = findViewById(R.id.edit_distance_radius);
        LinearLayout linear_layout_distance = findViewById(R.id.linear_layout_distance);
        MaterialButton btn_done = findViewById(R.id.btn_done);
        MaterialButton btn_time_from = findViewById(R.id.btn_time_from);
        MaterialButton btn_time_to = findViewById(R.id.btn_time_to);
        day_picker = findViewById(R.id.day_picker);
        txt_time_from = findViewById(R.id.txt_time_from);
        txt_time_to = findViewById(R.id.txt_time_to);
        findViewById(R.id.back_btn).setOnClickListener(v -> AvailabilityActivity.super.onBackPressed());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

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
                locationRequest();
            } catch (ApiException e) {
                switch (e.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                            resolvableApiException.startResolutionForResult(AvailabilityActivity.this, GPS_REQUEST_CODE);
                        } catch (IntentSender.SendIntentException sendIntentException) {
                            sendIntentException.printStackTrace();
                        }
                        break;

                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });

        if (Provider.getInstance().isAlways_available()) {
            edit_distance_radius.setText("");
            linear_layout_distance.setVisibility(View.GONE);
        } else linear_layout_distance.setVisibility(View.VISIBLE);

        btn_done.setOnClickListener(v -> {
            try {
                if ((!checkbox_available_countrywide.isChecked()) && Objects.requireNonNull(edit_distance_radius.getText()).toString().trim().equals("")) {
                    Toast.makeText(this, "Distance reach required", Toast.LENGTH_SHORT).show();
                } else if (day_picker.getSelectedDays().size() <= 0) {
                    Toast.makeText(this, "Please select the days you are available", Toast.LENGTH_SHORT).show();
                } else if (!(isTimeFromSet() && isTimeToSet())) {
                    Toast.makeText(this, "You have not set your Times", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Location permission was needed", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                Log.d("AvailabilityError", e.getMessage());
            }
        });
        checkbox_always_available.setOnClickListener(v -> {
            if (checkbox_always_available.isChecked()) day_picker.selectAllDays();
        });
        btn_time_from.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.ThemeOverlay_MaterialComponents_TimePicker, (view, hourOfDay, minute) -> {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                c.setTimeZone(TimeZone.getDefault());

                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("k:mm a");
                String time = dateFormat.format(c.getTime());
                txt_time_from.setText(time);
            }, hours, minutes, DateFormat.is24HourFormat(this));
            timePickerDialog.show();
        });
        btn_time_to.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.ThemeOverlay_MaterialComponents_TimePicker, (view, hourOfDay, minute) -> {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                c.setTimeZone(TimeZone.getDefault());

                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("k:mm a");
                String time = dateFormat.format(c.getTime());
                txt_time_to.setText(time);
            }, hours, minutes, DateFormat.is24HourFormat(this));
            timePickerDialog.show();
        });

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
        if (requestCode == LOCATION_REQUEST_CODE) {
            switch (resultCode) {
                case AvailabilityActivity.RESULT_OK:
                    getLocation();
                    saveData();
                case AvailabilityActivity.RESULT_CANCELED:
                    Toast.makeText(this, "Location seriously needed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveData() {
        if (checkbox_available_countrywide.isChecked()) {
            Provider.getInstance().setAvailable_country_wide(true);
            Provider.getInstance().setReach_in_distance(0);
        } else {
            Provider.getInstance().setReach_in_distance(Integer.parseInt(Objects.requireNonNull(edit_distance_radius.getText()).toString()));
            Provider.getInstance().setAvailable_country_wide(false);
        }
        List<MaterialDayPicker.Weekday> selectedDays = day_picker.getSelectedDays();
        Provider.getInstance().setDays_available(selectedDays);
        startActivity(new Intent(this, UserAgreements.class));
    }

    public boolean isTimeFromSet() {
        try {
            String time_Selected_from = txt_time_from.getText().toString();
            String[] time_from = time_Selected_from.split(":");
            int hour = Integer.parseInt(time_from[0].trim());
            Provider.getInstance().setTime_available_from(hour);
        } catch (Exception e) {
            Log.d("TimeSetFrom", e.getMessage());
        }
        return true;
    }

    public boolean isTimeToSet() {

        try {
            String time_Selected_to = txt_time_to.getText().toString();
            String[] timeTo = time_Selected_to.split(":");
            int hour_to = Integer.parseInt(timeTo[0].trim());
            Provider.getInstance().setTime_available_to(hour_to);
        } catch (Exception e) {
            Log.d("TimeSet", e.getMessage());
        }
        return true;
    }

    public void locationRequest() {
        if (ActivityCompat.checkSelfPermission(AvailabilityActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(AvailabilityActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        }
    }

    private void getLocation() {
        fusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
            Location location = task.getResult();
            try {
                Geocoder geocoder = new Geocoder(AvailabilityActivity.this, Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                Provider.getInstance().setLatitude(addresses.get(0).getLatitude());
                Provider.getInstance().setLongitude(addresses.get(0).getLongitude());
                Provider.getInstance().setCountry(addresses.get(0).getCountryName());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).addOnFailureListener(e -> Toast.makeText(AvailabilityActivity.this, "Could not get your location", Toast.LENGTH_SHORT).show());
    }
}