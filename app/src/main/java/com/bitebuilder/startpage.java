package com.bitebuilder;

/**
 * Created by cristopherserrano on 4/15/18.
 */

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class startpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startpage);
    }

    public void NewUser(View view) {
        Intent intent = new Intent(this, DietActivity.class);
        startActivity(intent);
    }

    public void SignIn(View view) {
        Intent intent = new Intent(this, MealPlanActivity.class);
        startActivity(intent);
    }

}
