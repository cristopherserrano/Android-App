package com.bitebuilder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MealPlanActivity extends BaseActivity {

    private GridView gridView;
    private GridViewAdapter gridViewAdapter;
    public static ArrayList<FoodItem> meals = getFoodItems();

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

    private static ArrayList<FoodItem> getFoodItems() {
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

    public void addMeals(View v) {
        DatabaseReference mealsReference = FirebaseDatabase.getInstance().getReference().child("meals");

        mealsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Go through meals in firebase database and get images, names, and ingredients from all
                ArrayList<FoodItem> firebaseMeals = new ArrayList<>();

                for(DataSnapshot mealSnapshot : dataSnapshot.getChildren()) {
                    String imageUrl = mealSnapshot.child("image").getValue().toString();
                    String name = mealSnapshot.child("name").getValue().toString();

                    String[] ingredients = new String[20];
                    for(int j = 0; j < mealSnapshot.child("ingredients").getChildrenCount(); j++) {
                        ingredients[j] = mealSnapshot.child("ingredients/" + String.valueOf(j+1)).getValue().toString();
                    }
                    FoodItem meal = new FoodItem(name, imageUrl, ingredients);

                    firebaseMeals.add(meal);
                }
                Intent addIntent = new Intent(MealPlanActivity.this, AddMealActivity.class);
                Log.i("meal plan", "meal plan: " + firebaseMeals.get(0));
                addIntent.putParcelableArrayListExtra("firebaseMeals", firebaseMeals);
                startActivity(addIntent);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("loadPost:onCancelled", databaseError.toException().toString());
            }
        });
    }
}
