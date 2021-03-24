package com.gachugusville.servicedforbusiness.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Utils.Provider;
import com.google.android.material.button.MaterialButton;
import com.hootsuite.nachos.NachoTextView;
import com.hootsuite.nachos.terminator.ChipTerminatorHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TagsActivity extends AppCompatActivity {

    private EditText edt_provider_description, note_to_user, url_ref1, url_ref2;
    private EditText edt_profession;
    private TextView txt_profession_error, txt_description_error, txt_note_to_user_error, txt_urls_error, txt_skills_error;
    private NachoTextView nacho_text_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);
        //Hooks
        nacho_text_view = findViewById(R.id.nacho_text_view);
        edt_provider_description = findViewById(R.id.edt_provider_description);
        note_to_user = findViewById(R.id.note_to_user);
        ImageView tags_back_btn = findViewById(R.id.tags_back_btn);
        url_ref1 = findViewById(R.id.url_ref1);
        url_ref2 = findViewById(R.id.url_ref2);
        txt_profession_error = findViewById(R.id.txt_profession_error);
        txt_description_error = findViewById(R.id.txt_description_error);
        txt_note_to_user_error = findViewById(R.id.txt_note_to_user_error);
        txt_urls_error = findViewById(R.id.txt_urls_error);
        txt_skills_error = findViewById(R.id.txt_skills_error);

        tags_back_btn.setOnClickListener(v -> super.onBackPressed());
        edt_profession = findViewById(R.id.edt_profession);
        MaterialButton btn_done = findViewById(R.id.btn_done);

        btn_done.setOnClickListener(v -> {
            //save data entered
            saveDataEntered();
        });

        //Get passed data from the category Adapter
        try {
            Bundle bundle = getIntent().getExtras();
            List<String> tags = new ArrayList<>();
            //noinspection CollectionAddAllCanBeReplacedWithConstructor
            tags.addAll(bundle.getStringArrayList("category_tags"));
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, tags);
            nacho_text_view.addChipTerminator(',', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_TO_TERMINATOR);
            nacho_text_view.setAdapter(adapter);
        } catch (Exception e) {
            Log.d("tags", String.valueOf(e.getMessage()));
        }

    }

    private void saveDataEntered() {
        String error = "Field cannot be empty";
        if (edt_profession.getText().toString().trim().equals("")) {
            txt_profession_error.setText(error);
        } else if (nacho_text_view.getAllChips().size() <= 0) {
            txt_skills_error.setText(R.string.skills_warning);
        } else if (edt_provider_description.getText().toString().trim().equals("")) {
            txt_description_error.setText(error);
        } else if (note_to_user.getText().toString().trim().equals("")) {
            txt_note_to_user_error.setText(error);
        } else if (url_ref1.getText().toString().trim().equals("")
                && (url_ref2.getText().toString().trim().equals(""))) {
            txt_urls_error.setText(R.string.urls_warning);
        } else try {
            Provider.getInstance().setService_identity(edt_profession.getText().toString());
            Provider.getInstance().setProvider_skills(nacho_text_view.getChipValues());
            Provider.getInstance().setPersonal_description(Objects.requireNonNull(edt_provider_description.getText()).toString());
            Provider.getInstance().setShort_note_to_users(Objects.requireNonNull(note_to_user.getText()).toString());
            Provider.getInstance().setRef_url1(Objects.requireNonNull(url_ref1.getText()).toString());
            Provider.getInstance().setRef_url2(Objects.requireNonNull(url_ref2.getText()).toString());
            startActivity(new Intent(getApplicationContext(), AvailabilityActivity.class));
        } catch (Exception e) {
            Log.d("TagsActivity", e.getMessage());
        }

    }

}