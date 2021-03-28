package com.gachugusville.servicedforbusiness.Genesis;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Dashboard.DashboardActivity;
import com.gachugusville.servicedforbusiness.Registration.LogInActivity;
import com.gachugusville.servicedforbusiness.Registration.SignUp;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        getWindow().setNavigationBarColor(getResources().getColor(R.color.white));
        getWindow().setExitTransition(new Explode());
        setContentView(R.layout.activity_start);
        MaterialButton btn_signUp = findViewById(R.id.btn_signUp);

        

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(this, DashboardActivity.class));
        } else
            btn_signUp.setOnClickListener(v -> nextActivity());
        findViewById(R.id.btn_logIn).setOnClickListener(v -> startActivity(new Intent(this, LogInActivity.class)));

    }

    private void nextActivity() {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

}