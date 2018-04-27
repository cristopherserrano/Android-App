package com.bitebuilder;

/**
 * Created by cristopherserrano on 4/15/18.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class startpage extends AppCompatActivity {

    public static final String PREFS_NAME = "CoreSkillsPrefsFile";

    EditText userNameEditText;
    EditText passwordEditText;
    String username;
    String password;
    String editTextValue;
    String editTextValue2;
    SharedPreferences settings;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startpage);

        userNameEditText = findViewById(R.id.userName);
        passwordEditText = findViewById(R.id.editText3);
        settings = getSharedPreferences(PREFS_NAME, 0);
        editTextValue = settings.getString("password", "AB345");
        editTextValue2 = settings.getString("user", "Username");
        if(!editTextValue.equals("AB345")) {
            userNameEditText.setText(editTextValue2);
            passwordEditText.setText(editTextValue);
        }

    }



    public void NewUser(View view) {
        Intent intent = new Intent(this, DietActivity.class);
        startActivity(intent);
    }





    public void verify(ArrayList<String> fusernames, ArrayList<String> fpasswords){
        username = userNameEditText.getText().toString();
        password = passwordEditText.getText().toString();
        for(int i = 0; i < fusernames.size(); i++) {
            if(fusernames.get(i).equals(username)) {
                if(fpasswords.get(i).equals(password)) {
                    Intent intent = new Intent(startpage.this, MealPlanActivity.class);
                    startActivity(intent);
                }
                else {
                    Context context = getApplicationContext();
                    CharSequence text = "There is no account that matches those credentials";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        }
    }

    public void SignIn(View view) {
        DatabaseReference database= FirebaseDatabase.getInstance().getReference().child("users");
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> users = new ArrayList<>();
                ArrayList<String> passwords = new ArrayList<>();
//                for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
//                    String username = userSnapshot.child("username").getValue().toString();
//                    String password = userSnapshot.child("password").getValue().toString();
//                    passwords.add(password);
//                    users.add(username);
//                }
                if(!editTextValue.equals("")){
                    Intent intent = new Intent(startpage.this, MealPlanActivity.class);
                    startActivity(intent);
                }

//                verify(users, passwords);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }



}
