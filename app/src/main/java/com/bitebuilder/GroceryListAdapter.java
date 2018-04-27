package com.bitebuilder;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.ViewHolder> {

    private ArrayList<String> ingredientsList;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;

    public GroceryListAdapter(Context context, ArrayList<String> ingredientsList) {
        this.inflater = LayoutInflater.from(context);
        this.ingredientsList = ingredientsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.grocery_list_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String ingredient = ingredientsList.get(position);
        holder.ingredientTextView.setText(ingredient);
    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView ingredientTextView;

        ViewHolder(View itemView) {
            super(itemView);
            ingredientTextView = itemView.findViewById(R.id.ingredient);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    String getItem(int id) {
        return ingredientsList.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
