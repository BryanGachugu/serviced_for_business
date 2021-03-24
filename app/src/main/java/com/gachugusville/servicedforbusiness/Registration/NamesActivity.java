package com.gachugusville.servicedforbusiness.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Genesis.StartActivity;
import com.gachugusville.servicedforbusiness.Utils.Provider;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class NamesActivity extends AppCompatActivity {
    private ChipGroup chip_group;
    private LinearLayout name_layout, brand_layout;
    private EditText edt_user_name, edt_brand_name;
    private TextView txt_user_name_error, txt_brand_name_error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_names);

        //Hooks
        ImageView retailer_signUp_back_btn = findViewById(R.id.retailer_signUp_back_btn);
        chip_group = findViewById(R.id.chip_group);
        brand_layout = findViewById(R.id.brand_layout);
        name_layout = findViewById(R.id.name_layout);
        edt_user_name = findViewById(R.id.edt_user_name);
        edt_brand_name = findViewById(R.id.edt_brand_name);
        txt_user_name_error = findViewById(R.id.txt_user_name_error);
        txt_brand_name_error = findViewById(R.id.txt_brand_name_error);
        MaterialButton btn_nextActivity = findViewById(R.id.btn_nextActivity);
        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();

        retailer_signUp_back_btn.setOnClickListener(v -> startActivity(new Intent(this, StartActivity.class)));

        if (Provider.getInstance().isGoogleAuth()) {
            assert auth != null;
            Provider.getInstance().setPhone(auth.getPhoneNumber());
            Provider.getInstance().setEmail(auth.getEmail());
            String photo_url = Objects.requireNonNull(auth.getPhotoUrl()).toString();
            Provider.getInstance().setProfile_pic_url(photo_url);
            edt_user_name.setText(auth.getDisplayName());
        }

        chip_group.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.chip_individual) {
                name_layout.setVisibility(View.VISIBLE);
                brand_layout.setVisibility(View.GONE);
            } else if (checkedId == R.id.chip_Brand) {
                name_layout.setVisibility(View.GONE);
                brand_layout.setVisibility(View.VISIBLE);
            }
        });

        //Set views according to which Chip was selected
        btn_nextActivity.setOnClickListener(v -> {
            if (chip_group.getCheckedChipId() == R.id.chip_individual)
                IndividualSetUp();
            else if (chip_group.getCheckedChipId() == R.id.chip_Brand)
                brandSetUp();
            else {
                String chipError = "Identity not selected";
                Toast.makeText(this, chipError, Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void brandSetUp() {
        if (edt_brand_name.getText().toString().trim().equals("")) setError(txt_brand_name_error);
        else passBrandValues();
    }

    private void setError(TextView target_text_view) {
        new TextView(this);
        // add textView on some Layout
        String errors = "Field cannot be empty";
        target_text_view.setText(errors);
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

    private void passBrandValues() {
        Intent intent = new Intent(this, ServiceSetUpActivity.class);
        //Set brand values
        Provider.getInstance().setUser_name("");
        //Pass value for use in the next activity
        intent.putExtra("brand_name", edt_brand_name.getText().toString());
        //set Brand name
        Provider.getInstance().setBrand_name(edt_brand_name.getText().toString());
        startActivity(intent);
    }

    private void IndividualSetUp() {
        if (edt_user_name.getText().toString().trim().equals("")) setError(txt_user_name_error);
        else passIndividualValues();
    }

    private void passIndividualValues() {
        Intent intent = new Intent(this, ServiceSetUpActivity.class);
        Provider.getInstance().setBrand_name("");
        Provider.getInstance().setUser_name(edt_user_name.getText().toString());
        startActivity(intent);
    }

}