package com.bitebuilder;


import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by cristopherserrano on 4/16/18.
 */

public class DietActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);
    }

    public void next1(View view) {
        Intent intent = new Intent(this, AllergyActivity.class);
        startActivity(intent);
    }
}
