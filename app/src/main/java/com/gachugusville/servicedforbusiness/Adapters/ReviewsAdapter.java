package com.gachugusville.servicedforbusiness.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Utils.Reviews;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import tm.charlie.expandabletextview.ExpandableTextView;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.Viewholder> {

    Context context;
    List<Reviews> reviewsList;

    public ReviewsAdapter(Context context, List<Reviews> reviewsList) {
        this.context = context;
        this.reviewsList = reviewsList;
    }

    @NonNull
    @Override
    public ReviewsAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.Viewholder holder, int position) {

        try {
            //Set rating
            holder.rating_bar.setRating(reviewsList.get(position).getReview_rating());
            //Set reviewer profile photo
            Picasso.get()
                    .load(reviewsList.get(position).getReviewer_profile_pic_url())
                    .into(holder.reviewer_url);
            //Set review name
            holder.reviewer_name.setText(reviewsList.get(position).getReviewer_name());
            //Set Review text
            if (holder.review_text.getText().toString().isEmpty()) {
                holder.review_text.setVisibility(View.GONE);
            } else {
                holder.review_text.setVisibility(View.VISIBLE);
                holder.review_text.setText(reviewsList.get(position).getReview_string());
                holder.review_text.setOnClickListener(v -> ((ExpandableTextView) v).toggle());
            }
            if (holder.rating_bar.getRating() > 2 && holder.rating_bar.getRating() < 4) {
                holder.review_text.setBackgroundColor(R.color.light_blue);
            } else {
                holder.review_text.setBackgroundColor(Color.BLUE);
            }
            //TODO set  review Date
        } catch (Exception e) {
            Log.d("ReviewsAdapterError", e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        CircleImageView reviewer_url;
        TextView reviewer_name, review_date;
        ExpandableTextView review_text;
        RatingBar rating_bar;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            reviewer_url = itemView.findViewById(R.id.reviewer_url);
            reviewer_name = itemView.findViewById(R.id.reviewer_name);
            review_date = itemView.findViewById(R.id.review_date);
            review_text = itemView.findViewById(R.id.review_text);
            rating_bar = itemView.findViewById(R.id.rating_bar);

        }
    }
}
