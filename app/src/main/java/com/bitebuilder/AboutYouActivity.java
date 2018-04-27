package com.bitebuilder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import static com.bitebuilder.startpage.PREFS_NAME;

/**
 * Created by cristopherserrano on 4/16/18.
 */

public class AboutYouActivity extends AppCompatActivity{

    public static final String PREFS_NAME = "CoreSkillsPrefsFile";

    EditText userName;
    EditText password1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutyou);
        userName  = findViewById(R.id.username);
        password1 = findViewById(R.id.conpass);
    }

    public void Start(View view) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("password", password1.getText().toString());

        editor.putString("user", userName.getText().toString());
        editor.commit();
        Intent intent = new Intent(this, MealPlanActivity.class);
        startActivity(intent);
    }

}
