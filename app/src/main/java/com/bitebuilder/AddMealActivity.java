package com.bitebuilder;

import android.content.Intent;
import android.os.Bundle;
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

public class AddMealActivity extends BaseActivity {

    private GridView gridView;
    private GridViewAdapter gridViewAdapter;
    private final ArrayList<FoodItem> meals = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        downloadMeals();
        getMealImages();

        gridView = (GridView) findViewById(R.id.addMealGridView);
        gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, meals);
        gridView.setAdapter(gridViewAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                FoodItem item = (FoodItem) parent.getItemAtPosition(position);
            }
        });

    }


    @Override
    public int getContentViewId() {
        return R.layout.activity_add_meal;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_meal_plan;
    }

    private void getMealImages() {
        ArrayList<StorageReference> images = new ArrayList<>();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference gsReference = storage.getReferenceFromUrl("gs://bite-builder.appspot.com/");

        for(int i = 0; i < meals.size(); i++) {
            StorageReference imageReference = gsReference.child(meals.get(i).getImageUrl());
            meals.get(i).setImageReference(imageReference);
        }
    }

    private void downloadMeals() {
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mealsReference = database.child("meals");

        mealsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Go through meals in firebase database and get images, names, and ingredients from all
                for(int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
                    DataSnapshot mealSnapshot = dataSnapshot.child("meal" + String.valueOf(i+1));
                    String imageUrl = mealSnapshot.child("image").getValue().toString();
                    String name = mealSnapshot.child("name").getValue().toString();

                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageReference = storage.getReferenceFromUrl("gs://bite-builder.appspot.com/");
                    StorageReference urlReference = storageReference.child(imageUrl);
                    urlReference.

                    String[] ingredients = new String[10];
                    for(int j = 0; j < mealSnapshot.child("ingredients").getChildrenCount(); j++) {
                        ingredients[j] = mealSnapshot.child("ingredients/" + String.valueOf(j+1)).getValue().toString();
                    }
                    FoodItem meal = new FoodItem(name, imageUrl, ingredients);
                    meals.add(meal);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("loadPost:onCancelled", databaseError.toException().toString());
            }
        });
    }
}
