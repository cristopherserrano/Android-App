package com.bitebuilder;

import android.os.Bundle;
import android.view.View;

public class MealPlanActivity extends BaseActivity {

    @Override
    public int getContentViewId() {
        return R.layout.activity_meal_plan;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_meal_plan;
    }

    public void addMeals(View view) {

    }
}
