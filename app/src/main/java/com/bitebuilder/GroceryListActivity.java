package com.bitebuilder;

import android.os.Bundle;

public class GroceryListActivity extends BaseActivity {

    @Override
    public int getContentViewId() {
        return R.layout.activity_grocery_list;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_grocery_list;
    }

}
