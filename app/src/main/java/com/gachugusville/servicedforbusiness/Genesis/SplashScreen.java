package com.gachugusville.servicedforbusiness.Genesis;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gachugusville.servicedforbusiness.Dashboard.DashboardActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       startActivity(new Intent(this, StartActivity.class));
       finish();
    }

}