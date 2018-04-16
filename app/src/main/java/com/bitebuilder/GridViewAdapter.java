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
        Log.i("Check that shit", ";asldkjf");
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
            Log.i("Check that shit", holder.imageName.toString());
        }

        FoodItem item = data.get(position);
        holder.imageName.setText(item.getName());
        holder.image.setImageResource(item.getImage());
        return row;
    }

    static class ViewHolder {
        TextView imageName;
        ImageView image;
    }
}