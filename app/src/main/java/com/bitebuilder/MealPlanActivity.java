package com.bitebuilder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MealPlanActivity extends BaseActivity {

    private GridView gridView;
    private GridViewAdapter gridViewAdapter;
    private ArrayList<FoodItem> meals = getFoodItems();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gridView = (GridView) findViewById(R.id.mealPlanGridView);
        gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, meals);
        gridView.setAdapter(gridViewAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                FoodItem item = (FoodItem) parent.getItemAtPosition(position);

                Intent intent = new Intent(MealPlanActivity.this, MealDetailActivity.class);
                intent.putExtra("name", item.getName());
                intent.putExtra("image", item.getImage());

                startActivity(intent);
            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_meal_plan;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_meal_plan;
    }

    public void addMeals(View view) {

    }

    private ArrayList<FoodItem> getFoodItems() {
        ArrayList<FoodItem> meals = new ArrayList<>();
        FoodItem meal1 = new FoodItem(R.drawable.paleo_curry_salmon_trifecta, "Curry Salmon Trifecta");
        FoodItem meal2 = new FoodItem(R.drawable.beef_with_broccoli, "Beef with Broccoli");
        FoodItem meal3 = new FoodItem(R.drawable.tuscan_beef_stew, "Tuscan Beef Stew");
        FoodItem meal4 = new FoodItem(R.drawable.shrimp_and_zucchini_ribbons, "Shrimp and Zucchini Ribbons");

        meals.add(meal1);
        meals.add(meal2);
        meals.add(meal3);
        meals.add(meal4);

        return meals;
    }
}
