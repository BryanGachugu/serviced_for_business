package com.gachugusville.servicedforbusiness.Dashboard;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Genesis.StartActivity;
import com.gachugusville.servicedforbusiness.Utils.Provider;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;

public class SettingsActivity extends AppCompatActivity {
    private TextView txt_app_version;
    private SwitchMaterial switch_available;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        setContentView(R.layout.activity_settings);
        findViewById(R.id.settings_back_btn).setOnClickListener(v -> SettingsActivity.super.onBackPressed());
        txt_app_version = findViewById(R.id.txt_app_version);
        switch_available = findViewById(R.id.switch_available);

        setAppVersion();
        setAvailabilityStatus();
        findViewById(R.id.btn_delete_account).setOnClickListener(v -> deleteUser());

    }

    private void deleteUser() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.getCurrentUser()
                .delete()
                .addOnSuccessListener(aVoid -> FirebaseFirestore.getInstance()
                        .collection("Providers")
                        .document(auth.getCurrentUser().getUid())
                        .delete().addOnCompleteListener(command -> startActivity(new Intent(SettingsActivity.this, StartActivity.class))))
                .addOnFailureListener(e -> Toast.makeText(SettingsActivity.this, "Error", Toast.LENGTH_SHORT).show());
    }

    private void setAvailabilityStatus() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        float hour_available_from = Provider.getInstance().getTime_available_from();
        float hour_available_to = Provider.getInstance().getTime_available_to();
        switch_available.setChecked(hour > hour_available_from && hour < hour_available_to);
    }

    private void setAppVersion() {
        try {
            PackageInfo packageInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            int versionCode = packageInfo.versionCode;
            txt_app_version.setText(MessageFormat.format("Version {0}", versionCode));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            txt_app_version.setVisibility(View.GONE);
        }
    }
}