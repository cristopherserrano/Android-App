package com.bitebuilder;

public class FavoritesActivity extends BaseActivity {

    @Override
    public int getContentViewId() {
        return R.layout.activity_favorites;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_favorites;
    }
}
