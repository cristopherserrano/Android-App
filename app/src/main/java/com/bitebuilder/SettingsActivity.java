package com.bitebuilder;

import android.content.Intent;
import android.view.View;

public class SettingsActivity extends BaseActivity {

    @Override
    public int getContentViewId() {
        return R.layout.activity_settings;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_settings;
    }

    public void theEnd(View view){
        Intent intent = new Intent(this, startpage.class);
        startActivity(intent);
    }

}
