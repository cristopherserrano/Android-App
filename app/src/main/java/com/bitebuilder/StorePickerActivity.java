package com.bitebuilder;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

public class StorePickerActivity extends BaseActivity implements LocationListener {

    private LocationManager locationManager;
    private Location location;
    private Double lat, lon;
    private TextView nearestText, storeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lat = 0.0;
        lon = 0.0;
        nearestText = (TextView) findViewById(R.id.nearestText);
        storeText = (TextView) findViewById(R.id.storeText);
        location = getLocation();
        loadNearByStores();

    }

    public void getLocationOnClick(View view) {
        nearestText.setText("Nearest store: ");
        storeText.setText("Foods of all Nations");
        getLocation();
    }

    private Location getLocation() {
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION }, 1);
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
////        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 1, this);
//        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        lat = location.getLatitude();
//        lon = location.getLongitude();

        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location loc = locationManager.getLastKnownLocation(provider);
            if (loc == null) {
                continue;
            }
            if (bestLocation == null || loc.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = loc;
            }
        }
        return bestLocation;
    }

    private void loadNearByStores() {
        Log.i("location", "location : " + lat + ", " + lon);
        Intent i = getIntent();
        String type = "grocery";

        StringBuilder googlePlacesUrl =
                new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=").append(38.0301064).append(",").append(-78.5055744);
        googlePlacesUrl.append("&radius=").append(500);
        googlePlacesUrl.append("&types=").append(type);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" +  "AIzaSyBjVnNH6ghkFCgpw3g495YncCIFnifthtA");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, googlePlacesUrl.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject result) {
                        Log.i("", "onResponse: Result= " + result.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(StorePickerActivity.this);
        queue.add(request);
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                }
                return;
            }
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    @Override
    public void onProviderEnabled(String s) {}

    @Override
    public void onProviderDisabled(String s) {}

    @Override
    public int getContentViewId() {
        return R.layout.activity_store_picker;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_store;
    }
}