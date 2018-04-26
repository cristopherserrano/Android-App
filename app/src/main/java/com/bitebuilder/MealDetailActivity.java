package com.bitebuilder;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MealDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String name = getIntent().getStringExtra("name");
        String[] ingredients = getIntent().getStringArrayExtra("ingredients");
        String imageUrl = getIntent().getStringExtra("imageUrl");

        TextView nameTextView = (TextView) findViewById(R.id.mealName);
        nameTextView.setText(name);

        ImageView imageView = (ImageView) findViewById(R.id.mealImage);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference gsReference = storage.getReferenceFromUrl("gs://bite-builder.appspot.com/");
        StorageReference imageReference = gsReference.child(imageUrl);
        GlideApp.with(this).load(imageReference).into(imageView);



        for(String ingredient : ingredients) {
            TextView ingredientView = new TextView(this);
            ingredientView.setTextSize(20);
            ingredientView.setText(" - " + ingredient);

            RelativeLayout layout = findViewById(R.id.detail_container);

            RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.BELOW, R.id.ingredientsTitle);
            ingredientView.setLayoutParams(params);

            layout.addView(ingredientView);
        }
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