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

    EditText userName;
    EditText password1;
    String username;
    String password;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startpage);

        userName  = findViewById(R.id.userName);
        password1 = findViewById(R.id.editText3);
        username= findViewById(R.id.userName).toString();
        password= findViewById(R.id.userName).toString();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String editTextValue = settings.getString("password", "none");
        String editTextValue2 = settings.getString("user", "none");
        userName.setText(editTextValue2);
        password1.setText(editTextValue);

    }



    public void NewUser(View view) {
        Intent intent = new Intent(this, DietActivity.class);
        startActivity(intent);
    }




//    public void verify(ArrayList<String> user, ArrayList<String> pass){
////        String user1=userName.getText().toString();
////        String pass1=password1.getText().toString();
////        for (String x: user){
////            if (x.equals(user1)) {
////                for (String y: pass){
////                    if (y.equals(pass1)) {
////                        Intent intent = new Intent(startpage.this, MealPlanActivity.class);
////                        startActivity(intent);
////                    }
////                    else{
////                        Context context = getApplicationContext();
////                        CharSequence text = "There is no account that matches those credentials";
////                        int duration = Toast.LENGTH_SHORT;
////                        Toast toast = Toast.makeText(context, text, duration);
////                        toast.show();
////                    }
////                }
////            }
////        }
//        }
    public void verify(String fuser, String fpass){
        if(fuser.equals(username)) {
            if (fpass.equals(password)) {
                Intent intent = new Intent(startpage.this, MealPlanActivity.class);
                startActivity(intent);
            } else {
                Context context = getApplicationContext();
                CharSequence text = "There is no account that matches those credentials";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
        else {
            Context context = getApplicationContext();
            CharSequence text = "There is no account that matches those credentials";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    public void SignIn(View view) {


        final DatabaseReference database= FirebaseDatabase.getInstance().getReference().child("users");

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Object users=dataSnapshot.child(username).child("username").getValue();
                Object pass=dataSnapshot.child(username).child("password").getValue();



//                ArrayList<String> users= new ArrayList<>();
//                ArrayList<String> pass= new ArrayList<>();

//                for(DataSnapshot user : dataSnapshot.getChildren()){
//                    String password=user.child("password").getValue().toString();
//                    pass.add(password);
//                    String username=user.child("username").getValue().toString();
//                    users.add(username);
//                }
                verify(users.toString(),pass.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    public void loadFromSharedPreferences(View view) {

        // Add your code here to load

    }

}
