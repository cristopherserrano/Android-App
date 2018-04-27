package com.bitebuilder;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    private ArrayList<FoodItem> meals;
    private DatabaseHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        meals = new ArrayList<>();
        mDbHelper = new DatabaseHelper(this);
        downloadFoodItems();

        // Fill image references from firebase storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference gsReference = storage.getReferenceFromUrl("gs://bite-builder.appspot.com/");
        for(FoodItem meal : meals) {
            StorageReference imageReference = gsReference.child(meal.getImageUrl());
            meal.setImageReference(imageReference);
        }

        gridView = (GridView) findViewById(R.id.mealPlanGridView);
        gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, meals);
        gridView.setAdapter(gridViewAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                FoodItem item = (FoodItem) parent.getItemAtPosition(position);

                Intent intent = new Intent(MealPlanActivity.this, MealDetailActivity.class);
                intent.putExtra("name", item.getName());
                intent.putExtra("ingredients", item.getIngredients());
                intent.putExtra("imageUrl", item.getImageUrl());

                startActivity(intent);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Meal Plan");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_meal_plan;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_meal_plan;
    }

    public void downloadFoodItems() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Which columns from the database you will actually use after this query
        String[] projection = {
                "name",
                "imageUrl",
                "ingredients"
        };

        Cursor cursor = db.query(
                "meal",
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while(cursor.moveToNext()) {
            String curName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String curImageUrl = cursor.getString(cursor.getColumnIndexOrThrow("imageUrl"));
            String curIngredients = cursor.getString(cursor.getColumnIndexOrThrow("ingredients"));

            FoodItem meal = new FoodItem(curName, curImageUrl, curIngredients);
            meals.add(meal);
        }
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
                addIntent.putParcelableArrayListExtra("firebaseMeals", firebaseMeals);
                startActivity(addIntent);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("loadPost:onCancelled", databaseError.toException().toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meal_plan_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reset) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            db.execSQL("delete from "+ "meal");
            gridView.setAdapter(null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
