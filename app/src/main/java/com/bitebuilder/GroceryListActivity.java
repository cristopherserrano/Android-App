package com.bitebuilder;


import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroceryListActivity extends BaseActivity {

    ArrayList<String> bucketList;
    RecyclerView rvBucketList;

    LocationManager locationManager;
    private static final int TAKE_PHOTO_PERMISSION = 1;

    TextView latTextView;
    TextView lonTextView;

    Double currentLat;
    Double currentLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_grocery_list;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_grocery_list;
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
