package com.bitebuilder;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    private ArrayList<FoodItem> meals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        meals = getIntent().getParcelableArrayListExtra("firebaseMeals");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference gsReference = storage.getReferenceFromUrl("gs://bite-builder.appspot.com/");
        for(FoodItem meal : meals) {
            StorageReference imageReference = gsReference.child(meal.getImageUrl());
            meal.setImageReference(imageReference);
        }

        gridView = (GridView) findViewById(R.id.addMealGridView);
        gridViewAdapter = new GridViewAdapter(this, R.layout.grid_add_item_layout, meals);
        gridView.setAdapter(gridViewAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                FoodItem item = (FoodItem) parent.getItemAtPosition(position);
                selectAndUpdateMeals(position);

                DatabaseHelper mDbHelper = new DatabaseHelper(AddMealActivity.this);
                SQLiteDatabase db = mDbHelper.getWritableDatabase();

                // Add to SQLite database to appear on meal plan activity and highlight grid
                if(item.getSelected()) {
                    String query = "SELECT * FROM " + "meal";
                    Cursor cursor = db.rawQuery(query, null);
                    int columnNumber = cursor.getColumnIndex("name");
                    boolean exists = false;

                    while (cursor.moveToNext()) {
                        String num = cursor.getString(columnNumber);
                        if(num.equals(item.getName())) {
                            exists = true;
                        }
                    }
                    cursor.close();

                    if(!exists) {
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
                }

                // Remove from SQLite database
                else {
                    db.delete("meal","name=?", new String[]{ item.getName() });
                }

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

    public void selectAndUpdateMeals(int position) {
        meals.get(position).toggleSelected();
        gridViewAdapter.notifyDataSetChanged();
    }
}