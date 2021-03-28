package com.gachugusville.servicedforbusiness.Registration;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.transition.Slide;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Utils.Dialog;
import com.gachugusville.servicedforbusiness.Utils.Provider;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.google.android.gms.auth.api.credentials.HintRequest;
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

public class SignUp extends AppCompatActivity {

    private static final int CREDENTIAL_PICKER_REQUEST = 1;
    private static final int RC_SIGN_IN = 123;
    private EditText phone_signUp, edt_email_sign_up, password_signUp, password_confirm;
    private TextView phone_signUp_error, txt_email_signUp_error, short_password_signUp_error, txt_password_match_error;
    private FirebaseAuth auth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupWindowAnimations();
        setContentView(R.layout.activity_sign_up);
        phone_signUp = findViewById(R.id.phone_signUp);
        phone_signUp_error = findViewById(R.id.phone_signUp_error);
        edt_email_sign_up = findViewById(R.id.edt_email_sign_up);
        txt_email_signUp_error = findViewById(R.id.txt_email_signUp_error);
        password_signUp = findViewById(R.id.password_signUp);
        password_confirm = findViewById(R.id.password_confirm);
        short_password_signUp_error = findViewById(R.id.short_password_signUp_error);
        txt_password_match_error = findViewById(R.id.txt_password_match_error);
        MaterialButton btn_toSecondSignUp = findViewById(R.id.btn_toSecondSignUp);
        MaterialButton btn_continue_with_google = findViewById(R.id.btn_continue_with_google);
        ImageView back_btn = findViewById(R.id.back_btn);
        auth = FirebaseAuth.getInstance(); // Initializing firebase authentication

        back_btn.setOnClickListener(v -> SignUp.super.onBackPressed());


        createRequest();
        btn_continue_with_google.setOnClickListener(v -> {
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

        HintRequest hintRequest = new HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)
                .build();

        PendingIntent intent = Credentials.getClient(getApplicationContext()).getHintPickerIntent(hintRequest);
        try {
            startIntentSenderForResult(intent.getIntentSender(), CREDENTIAL_PICKER_REQUEST, null, 0, 0, 0, new Bundle());
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }


        btn_toSecondSignUp.setOnClickListener(v -> {
            try {
                nextActivity();
            } catch (Exception e) {
                Log.d("phoneActivityNextBtn", e.getMessage());
            }
        });

    }

    private void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void setupWindowAnimations() {
        getWindow().setEnterTransition(null);
        Slide slide = new Slide();
        slide.setDuration(300);
        getWindow().setReturnTransition(slide);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == RESULT_OK) {
            // Obtain the phone number from the result
            Credential credentials = data.getParcelableExtra(Credential.EXTRA_KEY);
            //get the selected phone number
            // String phone_number = "+" + country_picker.getFullNumber() + credentials.getId();
            phone_signUp.setText(credentials.getId());
            //Do what ever you want to do with your selected phone number here

        } else if (requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == CredentialsApi.ACTIVITY_RESULT_NO_HINTS_AVAILABLE) {
            // *** No phone numbers available ***
            Toast.makeText(this, "No phone numbers found", Toast.LENGTH_LONG).show();
        } else if (requestCode == RC_SIGN_IN) {
            Dialog dialog = new Dialog(this);
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                dialog.startDialog();
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("GoogleSignIn", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("GoogleSignIn", "Google sign in failed", e);
                // ...
            }
            if (task.isCanceled()){
                dialog.dismissDialog();
            }
        }

    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        startActivity(new Intent(SignUp.this, NamesActivity.class));
                        Provider.getInstance().setPhone(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber());
                        Provider.getInstance().setGoogleAuth(true);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("GoogleSignIn", "signInWithCredential:failure", task.getException());
                        Toast.makeText(SignUp.this, "Failed", Toast.LENGTH_SHORT).show();
                    }

                    // ...
                });
    }

    public void nextActivity() {
        String error = "Field cannot be empty";
        if (phone_signUp.getText().toString().isEmpty()) {
            setError(phone_signUp_error, error);
        } else if (!isEmailValid(edt_email_sign_up.getText().toString().trim())) {
            setError(txt_email_signUp_error, "Invalid email");
        } else if (password_signUp.getText().toString().length() < 8) {
            String shortPasswordError = "Password should be a minimum of 8 characters";
            setError(short_password_signUp_error, shortPasswordError);
        } else if (!(password_signUp.getText().toString().trim().equals(password_confirm.getText().toString().trim()))) {
            setError(txt_password_match_error, "Passwords do not match");
        } else {
            if (isNetworkAvailable()) {
                Provider.getInstance().setPhone(phone_signUp.getText().toString());
                Provider.getInstance().setEmail(edt_email_sign_up.getText().toString());
                SignUpUserWithEmailAndPassword();
            } else {
                Alerter.create(this).setTitle("No internet")
                        .setText("Check your internet connection")
                        .enableSwipeToDismiss()
                        .enableVibration(true)
                        .show();
            }
        }
    }

    private void SignUpUserWithEmailAndPassword() {
        if (isNetworkAvailable()) {
            final Dialog dialog = new Dialog(this);
            dialog.startDialog();
            auth.createUserWithEmailAndPassword(edt_email_sign_up.getText().toString(), password_confirm.getText().toString())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            dialog.dismissDialog();
                            startActivity(new Intent(SignUp.this, NamesActivity.class));
                            Provider.getInstance().setGoogleAuth(false);
                        } else {
                            // If sign in fails, display a message to the user.
                            dialog.dismissDialog();
                            Log.w("SignUpFailed", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    });
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

    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}