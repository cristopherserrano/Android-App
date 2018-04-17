package com.bitebuilder;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MealDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String name = getIntent().getStringExtra("name");
        int image = getIntent().getIntExtra("image", 0);

        TextView nameTextView = (TextView) findViewById(R.id.mealName);
        nameTextView.setText(name);

        ImageView imageView = (ImageView) findViewById(R.id.mealImage);
        imageView.setImageResource(image);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_meal_detail;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_meal_plan;
    }
}