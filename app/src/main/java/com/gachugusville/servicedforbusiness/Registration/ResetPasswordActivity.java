package com.gachugusville.servicedforbusiness.Registration;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gachugusville.development.servicedforbusiness.R;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {
    private EditText edt_mail_reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        edt_mail_reset = findViewById(R.id.edt_mail_reset);
        findViewById(R.id.password_reset_back_btn).setOnClickListener(v -> ResetPasswordActivity.super.onBackPressed());

        findViewById(R.id.btn_reset_password).setOnClickListener(v -> resetPassword());

    }


    private void resetPassword() {
        if (edt_mail_reset.getText().toString().trim().isEmpty()) {
            edt_mail_reset.setError("Email is needed");
            edt_mail_reset.requestFocus();
        } else if (Patterns.EMAIL_ADDRESS.matcher(edt_mail_reset.getText().toString().trim()).matches()) {
            edt_mail_reset.setError("Please provide a valid email");
            edt_mail_reset.requestFocus();
        } else {
            findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.sendPasswordResetEmail(edt_mail_reset.getText().toString().trim()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(ResetPasswordActivity.this, "Check your email", Toast.LENGTH_SHORT).show();
                    findViewById(R.id.progress_bar).setVisibility(View.GONE);
                } else
                    Toast.makeText(ResetPasswordActivity.this, "Try again", Toast.LENGTH_SHORT).show();
            });
        }

    }
}