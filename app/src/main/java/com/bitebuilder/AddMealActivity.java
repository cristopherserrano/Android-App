package com.bitebuilder;

import android.content.Intent;
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
                Log.i("selected", "selected: " + String.valueOf(item.getSelected()));
                selectAndUpdateMeals(position);
                // Add to SQLite database to appear on meal plan activity and highlight grid
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
