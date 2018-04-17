package com.bitebuilder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by cristopherserrano on 4/16/18.
 */

public class AboutYouActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutyou);
    }

    public void Start(View view) {
        Intent intent = new Intent(this, MealPlanActivity.class);
        startActivity(intent);
    }
}
