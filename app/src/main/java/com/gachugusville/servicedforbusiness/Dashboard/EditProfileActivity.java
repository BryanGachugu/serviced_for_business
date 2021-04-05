package com.gachugusville.servicedforbusiness.Dashboard;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gachugusville.development.servicedforbusiness.R;
import com.google.android.material.button.MaterialButton;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        MaterialButton btn_change_profile = findViewById(R.id.btn_change_profile);
        MaterialButton btn_save_profile = findViewById(R.id.btn_save_profile);
        findViewById(R.id.dp_back_btn).setOnClickListener(v -> EditProfileActivity.super.onBackPressed());

    }
}