package com.bitebuilder;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroceryListActivity extends BaseActivity implements GroceryListAdapter.ItemClickListener {

    ArrayList<String> ingredientsList;
    ArrayList<FoodItem> meals;
    RecyclerView rvBucketList;
    GroceryListAdapter adapter;
    DatabaseHelper mDbHelper;

    LocationManager locationManager;
    private static final int TAKE_PHOTO_PERMISSION = 1;

    TextView latTextView;
    TextView lonTextView;

    Double currentLat;
    Double currentLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ingredientsList = new ArrayList<>();
        meals = new ArrayList<>();
        mDbHelper = new DatabaseHelper(this);
        downloadMeals();
        populateIngredients();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.groceryList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new GroceryListAdapter(this, ingredientsList);
        adapter.setClickListener(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_grocery_list;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_grocery_list;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    public void downloadMeals() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
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

    public void populateIngredients() {
        for(FoodItem meal : meals) {
            for(String ingredient : meal.getIngredients()) {
                if(!ingredientsList.contains(ingredient) && !ingredient.equals("null")) {
                    ingredientsList.add(ingredient);
                }
            }
        }
    }

//
//    public void startGPS(View view) {
//
//        // Here is the code to handle permissions - you should not need to edit this.
//        if ( Build.VERSION.SDK_INT >= 23 &&
//                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
//                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION }, TAKE_PHOTO_PERMISSION);
//        }
//
//        // Add code here to register the listener with the Location Manager to receive location updates
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 1, this);
//    }
//
//
//    @Override
//    public void onLocationChanged(Location location) {
//        // Add code here to do stuff when the location changes
//        currentLat = location.getLatitude();
//        currentLon = location.getLongitude();
//        latTextView.setText(Double.toString(currentLat));
//        lonTextView.setText(Double.toString(currentLon));
//    }
//
//    @Override
//    public void onStatusChanged(String s, int i, Bundle bundle) {}
//
//    @Override
//    public void onProviderEnabled(String s) {}
//
//    @Override
//    public void onProviderDisabled(String s) {}

}
