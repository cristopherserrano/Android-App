package com.bitebuilder;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class AddMealActivity extends BaseActivity {

    private GridView gridView;
    private GridViewAdapter gridViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mealsReference = database.child("meals");

        mealsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
<<<<<<< Updated upstream
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Go through meals in firebase database and get images, names, and ingredients from all
                ArrayList<FoodItem> firebaseMeals = new ArrayList<>();
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference gsReference = storage.getReferenceFromUrl("gs://bite-builder.appspot.com/");

                for(DataSnapshot mealSnapshot : dataSnapshot.getChildren()) {
                    String imageUrl = mealSnapshot.child("image").getValue().toString();
                    String name = mealSnapshot.child("name").getValue().toString();

                    String[] ingredients = new String[20];
                    for(int j = 0; j < mealSnapshot.child("ingredients").getChildrenCount(); j++) {
                        ingredients[j] = mealSnapshot.child("ingredients/" + String.valueOf(j+1)).getValue().toString();
                        Log.i("meal information", "Meal info: " + mealSnapshot.child("ingredients/" + String.valueOf(j+1)).getValue().toString());
                    }
                    FoodItem meal = new FoodItem(name, imageUrl, ingredients);
                    StorageReference imageReference = gsReference.child(meal.getImageUrl());
                    meal.setImageReference(imageReference);

                    Log.i("meal information", "Meal info: " + meal.getName());
                    Log.i("meal information", "Meal info: " + meal.getImageUrl());
                    Log.i("meal information", "Meal info: " + meal.getImageReference().toString());

                    firebaseMeals.add(meal);
                }

                gridView = (GridView) findViewById(R.id.addMealGridView);
                gridViewAdapter = new GridViewAdapter(AddMealActivity.this, R.layout.grid_add_item_layout, firebaseMeals);
                gridView.setAdapter(gridViewAdapter);

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        FoodItem item = (FoodItem) parent.getItemAtPosition(position);
                        // Add to SQLite database to appear on meal plan activity and highlight grid
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("loadPost:onCancelled", databaseError.toException().toString());
=======
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                FoodItem item = (FoodItem) parent.getItemAtPosition(position);
                selectAndUpdateMeals(position);

                DatabaseHelper mDbHelper = new DatabaseHelper(AddMealActivity.this);
                SQLiteDatabase db = mDbHelper.getWritableDatabase();

                // Add to SQLite database to appear on meal plan activity and highlight grid
                if(item.getSelected()) {
                    // Create a new map of values, where column names are the keys
                    ContentValues values = new ContentValues();
                    values.put("name", item.getName());
                    values.put("imageUrl", item.getImageUrl());
                    String ingredients = "";
                    for(String ingredient : item.getIngredients()) {
                        ingredients += ingredient + ",";
                    }
                    values.put("ingredients", ingredients);

                    // Insert the new row, returning the primary key value of the new row
                    long newRowId;
                    newRowId = db.insert(
                            "meal",
                            null,
                            values);
                }

                // Remove from SQLite database
                else {
                    db.delete("meal","name=?", new String[]{ item.getName() });
                }

<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Meals");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_meal_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done) {
            Intent doneIntent = new Intent(this, MealPlanActivity.class);
            startActivity(doneIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_add_meal;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_meal_plan;
    }

}
