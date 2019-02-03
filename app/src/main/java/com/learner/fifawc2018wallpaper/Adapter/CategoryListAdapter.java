package com.learner.fifawc2018wallpaper.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.learner.fifawc2018wallpaper.R;
import com.learner.fifawc2018wallpaper.WallpaperListActivity;
import com.learner.fifawc2018wallpaper.Model.Category;
import com.learner.fifawc2018wallpaper.Model.Common;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryHolder> {
    private Context context;
    private List<Category> categoryList;

    public CategoryListAdapter(Context context, List<Category> categoryList){
        this.categoryList = categoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.category_item,parent,false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryHolder holder, final int position) {
        Glide.with(context)
                .load(categoryList.get(position).getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.countryPhotoImageView);
        /*Picasso.get().load(categoryList.get(position).getImageUrl())
                .fit()
                .networkPolicy(NetworkPolicy.OFFLINE)
                .placeholder(R.drawable.placeholder_image)
                .into(holder.countryPhotoImageView);*/
        holder.countryNameTextView.setText(categoryList.get(position).getCountryName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.STR_SELECTED_CATEGORY_ID = categoryList.get(position).getCategoryId();
                Common.STR_SELECTED_CATEGORY = categoryList.get(position).getCountryName();
                Intent intent = new Intent(context, WallpaperListActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder{
        ImageView countryPhotoImageView;
        TextView countryNameTextView;

        public CategoryHolder(View itemView) {
            super(itemView);
            countryPhotoImageView = itemView.findViewById(R.id.country_photo_imageView);
            countryNameTextView = itemView.findViewById(R.id.country_name_textView);
        }
    }
}
