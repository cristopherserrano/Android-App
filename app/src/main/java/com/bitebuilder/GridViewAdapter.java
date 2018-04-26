package com.bitebuilder;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter<FoodItem> {
    private Context context;
    private int layoutResourceId;
    private ArrayList<FoodItem> data = new ArrayList<>();

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList<FoodItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageName = (TextView) row.findViewById(R.id.mealText);
            holder.image = (ImageView) row.findViewById(R.id.mealImage);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        FoodItem item = data.get(position);
        holder.imageName.setText(item.getName());
        if(item.getImage() != 0) {
            holder.image.setImageResource(item.getImage());
        }
        else {
            GlideApp.with(this.context).load(item.getImageReference()).into(holder.image);
        }
<<<<<<< Updated upstream
=======

        if(layoutResourceId == R.layout.grid_add_item_layout && item.getSelected()) {
            holder.layout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
            holder.addImage.setImageResource(R.drawable.ic_check);
            holder.addImage.setBackgroundColor(Color.WHITE);
        }
        else if(layoutResourceId == R.layout.grid_add_item_layout) {
            holder.layout.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
            holder.addImage.setImageResource(R.drawable.ic_add);
            holder.addImage.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }
>>>>>>> Stashed changes
        return row;
    }

    static class ViewHolder {
        TextView imageName;
        ImageView image;
    }
}