package com.bitebuilder;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
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

        int i = 0;
        String ingredient = ingredients[0];
        String ingredientsList = "";
        while(!ingredient.equals("null")) {
            ingredientsList += " &#160;&#160;-&#160;&#160; " + ingredient + "<br/>";
            i++;
            ingredient = ingredients[i];
        }
        TextView ingredientsTextView = (TextView) findViewById(R.id.ingredientsText);
        ingredientsTextView.setText(Html.fromHtml(ingredientsList));
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