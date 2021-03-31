package com.gachugusville.servicedforbusiness.Registration;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Dashboard.DashboardActivity;
import com.gachugusville.servicedforbusiness.Genesis.StartActivity;
import com.gachugusville.servicedforbusiness.Utils.Dialog;
import com.gachugusville.servicedforbusiness.Utils.Provider;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.tapadoo.alerter.Alerter;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogInActivity extends AppCompatActivity {
    private EditText mail_logIn_field, password_logIn;
    private TextView txt_email_login_error, txt_password_login_error;
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth auth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        mail_logIn_field = findViewById(R.id.mail_logIn_field);
        password_logIn = findViewById(R.id.password_logIn);
        txt_email_login_error = findViewById(R.id.txt_email_login_error);
        txt_password_login_error = findViewById(R.id.txt_password_login_error);
        TextView txt_signUp = findViewById(R.id.txt_signUp);
        MaterialButton btn_logIn = findViewById(R.id.btn_logIn);
        MaterialButton btn_googleSignUp = findViewById(R.id.btn_googleSignUp);
        auth = FirebaseAuth.getInstance();
        createRequest();
        btn_googleSignUp.setOnClickListener(v -> {
            if (isNetworkAvailable()) {
                signIn();
            } else {
                Alerter.create(this).setTitle("No internet")
                        .setText("Check your internet connection")
                        .enableSwipeToDismiss()
                        .enableVibration(true)
                        .show();
            }
        });
        txt_signUp.setOnClickListener(v -> startActivity(new Intent(this, SignUp.class)));
        findViewById(R.id.log_in_back_btn).setOnClickListener(v -> LogInActivity.super.onBackPressed());
        findViewById(R.id.txt_forgotPassword).setOnClickListener(v -> startActivity(new Intent(this, ResetPasswordActivity.class)));

        btn_logIn.setOnClickListener(v -> logInUser());

    }

    private void logInUser() {
        String email_log_in = mail_logIn_field.getText().toString().trim();
        String password_log_in = password_logIn.getText().toString().trim();
        if (!isEmailValid(email_log_in)) {
            setError(txt_email_login_error, "Invalid email");
        } else if (password_log_in.trim().length() < 8) {
            setError(txt_password_login_error, "Invalid password");
        } else {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(email_log_in, password_log_in).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    startActivity(new Intent(LogInActivity.this, DashboardActivity.class));
                } else {
                    Toast.makeText(this, "Check your credentials", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(e -> Toast.makeText(LogInActivity.this, "Failed, Try again", Toast.LENGTH_SHORT).show());
        }
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;
        //regex to validate input email address
        String expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    private void setError(TextView target_text_view, String error) {
        new TextView(this);
        // add textView on some Layout
        target_text_view.setText(error);
        new CountDownTimer(3000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                target_text_view.setText("");
            }
        }.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("GoogleSignIn", "firebaseAuthWithGoogle:" + account.getId());
                Dialog dialog = new Dialog(LogInActivity.this);
                dialog.startDialog();
                firebaseAuthWithGoogle(account.getIdToken());
                dialog.dismissDialog();
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("GoogleSignIn", "Google sign in failed", e);
            }
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, StartActivity.class));
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                            startActivity(new Intent(LogInActivity.this, DashboardActivity.class));
                        }
                        startActivity(new Intent(LogInActivity.this, NamesActivity.class));
                        Provider.getInstance().setPhone(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber());
                        Provider.getInstance().setGoogleAuth(true);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("GoogleSignIn", "signInWithCredential:failure", task.getException());
                        Toast.makeText(LogInActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }

                    // ...
                });
    }

    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}