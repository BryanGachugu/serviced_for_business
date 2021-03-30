package com.gachugusville.servicedforbusiness.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Dashboard.DashboardActivity;
import com.gachugusville.servicedforbusiness.Genesis.StartActivity;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {
    private EditText mail_logIn_field, password_logIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        mail_logIn_field = findViewById(R.id.mail_logIn_field);
        password_logIn = findViewById(R.id.password_logIn);
        TextView txt_signUp = findViewById(R.id.txt_signUp);
        MaterialButton btn_logIn = findViewById(R.id.btn_logIn);
        MaterialButton btn_googleSignUp = findViewById(R.id.btn_googleSignUp);
        btn_googleSignUp.setOnClickListener(v -> {
            SignUp signUp = new SignUp();
            signUp.signIn();
        });
        txt_signUp.setOnClickListener(v -> startActivity(new Intent(this, SignUp.class)));
        findViewById(R.id.log_in_back_btn).setOnClickListener(v -> LogInActivity.super.onBackPressed());
        findViewById(R.id.txt_forgotPassword).setOnClickListener(v -> startActivity(new Intent(this, ResetPasswordActivity.class)));

        btn_logIn.setOnClickListener(v -> logInUser());

    }

    private void logInUser() {
        String email_log_in = mail_logIn_field.getText().toString().trim();
        String password_log_in = password_logIn.getText().toString().trim();
        if (email_log_in.isEmpty()) {
            mail_logIn_field.setError("Email is required");
            mail_logIn_field.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email_log_in).matches()) {
            mail_logIn_field.setError("Invalid email");
            mail_logIn_field.requestFocus();
        } else if (password_log_in.isEmpty()) {
            password_logIn.setError("Password is required");
            password_logIn.requestFocus();
        } else {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(email_log_in, password_log_in).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    startActivity(new Intent(LogInActivity.this, DashboardActivity.class));
                } else {
                    Toast.makeText(this, "Check your credentials", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, StartActivity.class));
    }
}