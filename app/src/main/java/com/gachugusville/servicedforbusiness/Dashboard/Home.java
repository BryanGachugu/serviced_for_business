package com.gachugusville.servicedforbusiness.Dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Utils.Provider;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.majorik.sparklinelibrary.SparkLineLayout;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import per.wsj.library.AndRatingBar;

public class Home extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Hooks
        NavigationView navigation_view = Objects.requireNonNull(getActivity()).findViewById(R.id.navigation_view);
        LinearLayout linearLayout_reviews = view.findViewById(R.id.linearLayout_reviews);
        MaterialCardView card_profile_views = view.findViewById(R.id.card_profile_views);
        LinearLayout linear_layout_jobs_done = view.findViewById(R.id.linear_layout_jobs_done);
        navigation_view.setCheckedItem(R.id.home_nav);
        TextView number_of_reviews = view.findViewById(R.id.number_of_reviews);
        TextView number_of_views = view.findViewById(R.id.number_of_views);
        TextView estimated_earnings = view.findViewById(R.id.estimated_earnings);
        SparkLineLayout line_graph = view.findViewById(R.id.line_graph);
        AndRatingBar rating_bar = view.findViewById(R.id.rating_bar);
        TextView average_rating = view.findViewById(R.id.average_rating);
        TextView number_of_jobs = view.findViewById(R.id.number_of_jobs);
        CircleImageView profile_image = view.findViewById(R.id.profile_image);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //If user authenticated with google, set the default profile image as the profile photo
        if (Provider.getInstance().isGoogleAuth()) {
            assert user != null;
            String photo_url = Provider.getInstance().getProfile_pic_url();
            Picasso.get()
                    .load(photo_url)
                    .into(profile_image);
        } else if (!Provider.getInstance().getProfile_pic_url().isEmpty()) {
            Picasso.get().load(Provider.getInstance().getProfile_pic_url()).into(profile_image);
        } else Picasso.get().load(R.drawable.test); //TODO (Load app icon as default image)

        profile_image.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), EditProfileActivity.class));
        });

        number_of_reviews.setText(String.valueOf(Provider.getInstance().getNumber_of_reviews()));
        number_of_views.setText(String.valueOf(Provider.getInstance().getAccount_views()));
        estimated_earnings.setText(String.valueOf(Provider.getInstance().getEstimated_earnings()));
        try {
            average_rating.setText(String.valueOf(Provider.getInstance().getRating()));
            rating_bar.setRating(Provider.getInstance().getRating());
        } catch (Exception e) {
            Log.d("RatingError", e.getMessage());
            Toast.makeText(getContext(), "Error getting your average rating", Toast.LENGTH_LONG).show();
        }
        number_of_jobs.setText(String.valueOf(Provider.getInstance().getJobs_done()));

        //Take to respect activities on click
        linearLayout_reviews.setOnClickListener(v -> startActivity(new Intent(getContext(), ReviewsActivity.class)));
        card_profile_views.setOnClickListener(v -> startActivity(new Intent(getContext(), ProfileViewsActivity.class)));
        linear_layout_jobs_done.setOnClickListener(v -> startActivity(new Intent(getContext(), JobsDoneActivity.class)));


        //TODO fetch this data from the last 7 days
        ArrayList<Integer> dataset = new ArrayList<>();
        dataset.add(29);
        dataset.add(10);
        dataset.add(20);
        dataset.add(18);
        dataset.add(29);
        dataset.add(29);
        dataset.add(10);
        line_graph.setData(dataset);

        return view;
    }

}