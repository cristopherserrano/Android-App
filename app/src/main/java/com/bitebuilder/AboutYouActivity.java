package com.bitebuilder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static com.bitebuilder.startpage.PREFS_NAME;

/**
 * Created by cristopherserrano on 4/16/18.
 */

public class AboutYouActivity extends AppCompatActivity{

    public static final String PREFS_NAME = "CoreSkillsPrefsFile";

    private DatabaseReference databaseReference;
    EditText userName;
    EditText password1;
    EditText conpass;
    String diet;
    String[] allergies= new String[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutyou);
        userName  = findViewById(R.id.username);
        password1 = findViewById(R.id.password);
        conpass   = findViewById(R.id.conpass);
        diet=getIntent().getStringExtra("diet");
        allergies=getIntent().getStringArrayExtra("allergies");
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

    }

    public void Start(View view) {

        if((conpass.getText().toString()).equals(password1.getText().toString())){

            //        Shared Preferences
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("password", password1.getText().toString());
            editor.putString("user", userName.getText().toString());
            editor.commit();

            //        Firebase
//            Map<String, String> map = new HashMap<>();
//                map.put("diet",diet);
//                map.put("password",password1.getText().toString());
//                map.put("username",userName.getText().toString());
//                for(int x=0; x<allergies.length; x++){
//                    map.put("allergy",allergies[x]);
//                }
//
//            databaseReference.setValue(map);
//            Log.d("cris",allergies[0]);


            databaseReference.push().setValue(userName.getText().toString());
            databaseReference.child(userName.getText().toString()).child("diet").setValue(diet);
            databaseReference.child(userName.getText().toString()).child("password").setValue(password1.getText().toString());
            databaseReference.child(userName.getText().toString()).child("username").setValue(userName.getText().toString());
//            databaseReference.child("users").child(userName.getText().toString()).child("allergy").setValue();
            databaseReference.child(userName.getText().toString()).child("allergy").setValue("");
            for(int x=0; x<allergies.length;x++){
                if(!allergies[x].equals("null")){
                    databaseReference.child(userName.getText().toString()).child("allergy").child(String.valueOf(x)).setValue(allergies[x]);

                }
            }

//            make a new reference with this one it
//          userName.getText().toString()/allergy


            //         Enter App
            Intent intent = new Intent(this, MealPlanActivity.class);
            startActivity(intent);
        }
        else {
            Context context = getApplicationContext();
            CharSequence text = "Password Don't Match";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }

}
