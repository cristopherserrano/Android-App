package com.bitebuilder;


import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by cristopherserrano on 4/16/18.
 */

public class DietActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    CheckBox done_checkbox;
    CheckBox done_lowcarb;
    CheckBox done_paleo;
    CheckBox done_pescatarian;
    CheckBox done_vegetarian;
    CheckBox done_vegan;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }



    public void classic_clicked(View view){
//        databaseReference.child("user").child("david").child("diet").setValue("classic");
        Intent intent = new Intent(this, AllergyActivity.class);
        intent.putExtra("diet", "classic");
        startActivity(intent);

    }

    public void lowcarb_clicked(View view){
//        databaseReference.child("user").child("diet").setValue("lowcarb");
        Intent intent = new Intent(this, AllergyActivity.class);
        intent.putExtra("diet", "lowcarb");
        startActivity(intent);

    }
    public void paleo_clicked(View view){
//        databaseReference.child("user").child("diet").setValue("paleo");
        Intent intent = new Intent(this, AllergyActivity.class);
        intent.putExtra("diet", "paleo");
        startActivity(intent);

    }
    public void pescatarian_clicked(View view){
//        databaseReference.child("user").child("diet").setValue("pescatarian");
        Intent intent = new Intent(this, AllergyActivity.class);
        intent.putExtra("diet", "pescatarian");
        startActivity(intent);

    }
    public void vegetarian_clicked(View view){
//        databaseReference.child("user").child("diet").setValue("vegetarian");
        Intent intent = new Intent(this, AllergyActivity.class);
        intent.putExtra("diet", "vegetarian");
        startActivity(intent);

    }
    public void vegan_clicked(View view){
//        databaseReference.child("user").child("diet").setValue("vegan");
        Intent intent = new Intent(this, AllergyActivity.class);
        intent.putExtra("diet", "vegan");
        startActivity(intent);
    }

}
