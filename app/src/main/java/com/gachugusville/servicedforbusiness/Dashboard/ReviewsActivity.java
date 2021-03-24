package com.gachugusville.servicedforbusiness.Dashboard;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Adapters.ReviewsAdapter;
import com.gachugusville.servicedforbusiness.Utils.Provider;
import com.gachugusville.servicedforbusiness.Utils.Reviews;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import per.wsj.library.AndRatingBar;

public class ReviewsActivity extends AppCompatActivity {

    private TextView txt_total_reviews;
    private TextView txt_average_rating;
    private List<Reviews> reviews;
    private ScrollView reviews_scrollView;
    private AndRatingBar rating_bar_reviewsActivity;
    private ReviewsAdapter reviewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        getWindow().setNavigationBarColor(getResources().getColor(R.color.white));
        setContentView(R.layout.activity_reviews);

        txt_total_reviews = findViewById(R.id.txt_total_reviews);
        reviews_scrollView = findViewById(R.id.reviews_scrollView);
        TextView date_first_review = findViewById(R.id.date_first_review);
        //TODO (Set the date of the first review)
        txt_average_rating = findViewById(R.id.txt_average_rating);
        rating_bar_reviewsActivity = findViewById(R.id.rating_bar_reviewsActivity);
        RecyclerView reviews_recycler_view = findViewById(R.id.reviews_recycler_view);

        adjustLayout();

        reviews = new ArrayList<>();
        reviewsAdapter = new ReviewsAdapter(this, reviews);
        reviews_recycler_view.setHasFixedSize(true);
        reviews_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        reviews_recycler_view.setAdapter(reviewsAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("Providers").document(Uid).collection("reviews")
                .limit(10)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.d("Error", Objects.requireNonNull(error.getMessage()));
                    }
                    assert value != null;
                    for (DocumentChange documentChange : value.getDocumentChanges()) {
                        if (documentChange.getType() == DocumentChange.Type.ADDED) {
                            final Reviews review = documentChange.getDocument().toObject(Reviews.class);
                            reviews.add(review);
                            txt_total_reviews.setText(Provider.getInstance().getNumber_of_reviews());
                            txt_average_rating.setText(String.valueOf(Provider.getInstance().getRating()));
                            rating_bar_reviewsActivity.setRating(Provider.getInstance().getRating());
                            reviewsAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void adjustLayout() {
        //TODO if user is scrolling up, hide top layout..... if reviews are empty, show empty animation, and filter reviews according to cards

    }
}