package com.gachugusville.servicedforbusiness.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Registration.TagsActivity;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategorySpinnerAdapter extends RecyclerView.Adapter<CategorySpinnerAdapter.ViewHolder> {

    Context context;
    List<ServiceCategoryList> serviceCategoryList;

    public CategorySpinnerAdapter(Context context, List<ServiceCategoryList> serviceCategoryList) {
        this.context = context;
        this.serviceCategoryList = serviceCategoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_category_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.service_category_name.setText(serviceCategoryList.get(position).getService_category_name());
        holder.category_tags = serviceCategoryList.get(position).getCategory_tags();
        holder.image_url = serviceCategoryList.get(position).getImage_url();

        holder.service_category_card.setOnClickListener(v -> {

            List<String> category_tags = new ArrayList<>(serviceCategoryList.get(position).getCategory_tags());
            Intent intent = new Intent(context, TagsActivity.class);
            //Set Category of service offered by the user
            Provider.getInstance().setService_category(serviceCategoryList.get(position).getService_category_name());
            Bundle bundle = new Bundle();

            bundle.putStringArrayList("category_tags", (ArrayList<String>) category_tags);
            bundle.putString("item_name", serviceCategoryList.get(position).getService_category_name());
            intent.putExtras(bundle);
            context.startActivity(intent);

        });
        Picasso.get()
                .load(holder.image_url)
                .fit()
                .into(holder.image_view_category);

    }

    @Override
    public int getItemCount() {
        return serviceCategoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView service_category_card;
        ImageView image_view_category;
        TextView service_category_name;
        List<String> category_tags;
        String image_url;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            service_category_name = itemView.findViewById(R.id.service_category_name);
            service_category_card = itemView.findViewById(R.id.service_category_card);
            image_view_category = itemView.findViewById(R.id.image_view_category);
        }
    }
}
