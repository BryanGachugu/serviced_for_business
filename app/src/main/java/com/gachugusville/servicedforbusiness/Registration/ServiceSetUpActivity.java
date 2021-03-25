package com.gachugusville.servicedforbusiness.Registration;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Utils.CategorySpinnerAdapter;
import com.gachugusville.servicedforbusiness.Utils.Provider;
import com.gachugusville.servicedforbusiness.Utils.ServiceCategoryList;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServiceSetUpActivity extends AppCompatActivity {

    TextView welcome_msg;
    ImageView back_btn;
    RecyclerView service_category_rv;
    List<ServiceCategoryList> serviceCategoryList;
    CategorySpinnerAdapter categorySpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_set_up);
        //Set Welcome message according to previous data provided
        welcome_msg = findViewById(R.id.welcome_msg);
        back_btn = findViewById(R.id.back_btn);
        service_category_rv = findViewById(R.id.service_category_rv);
        setWelcomeMsg();
        serviceCategoryList = new ArrayList<>();
        categorySpinnerAdapter = new CategorySpinnerAdapter(this, serviceCategoryList);
        service_category_rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        service_category_rv.setAdapter(categorySpinnerAdapter);

        FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();
        try {
            mFirebaseFirestore.collection("ServiceCategory")
                    .addSnapshotListener((value, error) -> {
                        if (error != null) {
                            Log.d("Error", Objects.requireNonNull(error.getMessage()));
                        }
                        assert value != null;
                        for (DocumentChange documentChange : value.getDocumentChanges()) {
                            if (documentChange.getType() == DocumentChange.Type.ADDED) {
                                final ServiceCategoryList category = documentChange.getDocument().toObject(ServiceCategoryList.class);
                                serviceCategoryList.add(category);
                                categorySpinnerAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }catch (Exception e){
            Log.d("CategoriesError", e.getMessage());
        }

        back_btn.setOnClickListener(v -> ServiceSetUpActivity.super.onBackPressed());

    }
    private void setWelcomeMsg() {
        String name = Provider.getInstance().getUser_name();
        String brand_name = Provider.getInstance().getBrand_name();
        if (name.equals(""))
            welcome_msg.setText(String.format("Welcome %s", brand_name));
        else
            welcome_msg.setText(String.format("Welcome %s", name));
    }
}