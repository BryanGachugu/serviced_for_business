package com.gachugusville.servicedforbusiness.Dashboard;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Genesis.StartActivity;
import com.gachugusville.servicedforbusiness.Utils.Provider;

import java.text.MessageFormat;

public class SettingsActivity extends AppCompatActivity {
    private TextView txt_app_version;

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
        setAppVersion();

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