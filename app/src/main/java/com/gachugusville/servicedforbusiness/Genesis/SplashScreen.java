package com.gachugusville.servicedforbusiness.Genesis;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Dashboard.DashboardActivity;
import com.gachugusville.servicedforbusiness.Utils.Provider;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.SplashScreenTheme);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
           docRef = db.collection("Providers").document(uid);
            getAllDataFromDatabase();
        } else
            startActivity(new Intent(this, StartActivity.class));
    }

    private void getAllDataFromDatabase() {
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Provider.getInstance().setUser_name(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).getUser_name());
                Provider.getInstance().setRegistrationFinished(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).isRegistrationFinished());
                Provider.getInstance().setBrand_name(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).getBrand_name());
                Provider.getInstance().setService_category(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).getService_category());
                Provider.getInstance().setService_identity(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).getService_identity());
                Provider.getInstance().setPersonal_description(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).getPersonal_description());
                Provider.getInstance().setShort_note_to_users(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).getShort_note_to_users());
                Provider.getInstance().setPhone(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).getPhone());
                Provider.getInstance().setRef_url2(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).getRef_url2());
                Provider.getInstance().setRef_url1(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).getRef_url1());
                Provider.getInstance().setEmail(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).getEmail());
                Provider.getInstance().setCountry(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).getCountry());
                Provider.getInstance().setReviews(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).getReviews());
                Provider.getInstance().setProvider_skills(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).getProvider_skills());
                Provider.getInstance().setDays_available(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).getDays_available());
                Provider.getInstance().setProfile_pic_url(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).getProfile_pic_url());
                Provider.getInstance().setAvailable_country_wide(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).isAvailable_country_wide());
                Provider.getInstance().setAlways_available(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).isAlways_available());
                Provider.getInstance().setGoogleAuth(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).isGoogleAuth());
                Provider.getInstance().setTotal_rating(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).getTotal_rating());
                Provider.getInstance().setRating(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).getRating());
                Provider.getInstance().setTime_available_from((int) Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).getTime_available_from());
                Provider.getInstance().setTime_available_to((int) Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).getTime_available_to());
                Provider.getInstance().setReach_in_distance((int) Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).getReach_in_distance());
                Provider.getInstance().setJobs_done(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).getJobs_done());
                Provider.getInstance().setAccount_views(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).getAccount_views());
                Provider.getInstance().setNumber_of_reviews(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).getNumber_of_reviews());
                Provider.getInstance().setNumber_of_profile_likes(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).getNumber_of_profile_likes());
                Provider.getInstance().setEstimated_earnings(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).getEstimated_earnings());
                Provider.getInstance().setLatitude(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).getLatitude());
                Provider.getInstance().setLatitude(Objects.requireNonNull(documentSnapshot.toObject(Provider.getInstance().getClass())).getLongitude());
                startActivity(new Intent(SplashScreen.this, DashboardActivity.class));
            }
            startActivity(new Intent(SplashScreen.this, DashboardActivity.class));
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Failure", e.getLocalizedMessage());
                startActivity(new Intent(SplashScreen.this, DashboardActivity.class));
            }
        });
    }

}