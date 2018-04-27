package com.bitebuilder;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ToggleButton;

import java.lang.reflect.Array;


/**
 * Created by cristopherserrano on 4/16/18.
 */

public class AllergyActivity extends AppCompatActivity{

    Boolean dairy=false;
    Boolean egg=false;
    Boolean gluten=false;
    Boolean nuts=false;
    Boolean shellfish=false;
    Boolean soy=false;
    Boolean fish=false;

    String diet;

    ToggleButton dairyb;
    ToggleButton eggb;
    ToggleButton glutenb;
    ToggleButton nutsb;
    ToggleButton shellfishb;
    ToggleButton soyb;
    ToggleButton fishb;

    String[] allergies= new String[7];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allergies);
        dairyb=(ToggleButton) findViewById(R.id.dairy);
        eggb=(ToggleButton) findViewById(R.id.Egg);
        glutenb=(ToggleButton) findViewById(R.id.Gluten);
        nutsb=(ToggleButton) findViewById(R.id.Nuts);
        shellfishb=(ToggleButton) findViewById(R.id.Shellfish);
        soyb=(ToggleButton) findViewById(R.id.soy);
        fishb=(ToggleButton) findViewById(R.id.FIsh);
        diet=getIntent().getStringExtra("diet");
    }



    public void diary_clicked(View view){
        dairy = dairyb.isChecked();
    }

    public void egg_clicked(View view){
        egg = eggb.isChecked();
    }
    public void gluten_clicked(View view){
        gluten = glutenb.isChecked();

    }
    public void nuts_clicked(View view){
        nuts = nutsb.isChecked();

    }
    public void shellfish_clicked(View view){
        shellfish = shellfishb.isChecked();

    }
    public void soy_clicked(View view){
        soy = soyb.isChecked();
    }

    public void fish_clicked(View view){
        fish = fishb.isChecked();
    }

    public void next2(View view) {
        allergies[0]="null";
        allergies[1]="null";
        allergies[2]="null";
        allergies[3]="null";
        allergies[4]="null";
        allergies[5]="null";
        allergies[6]="null";
        allergies[0]="null";


        if (egg==true){
            allergies[1]="eggs";
        }
        if (gluten==true){
            allergies[2]="gluten";
        }
        if (nuts==true){
            allergies[3]="nuts";
        }
        if (shellfish==true){
            allergies[4]="shellfish";
        }
        if (soy==true){
            allergies[5]="soy";
        }
        if (fish==true){
            allergies[6]="fish";
        }



        Intent intent = new Intent(this, AboutYouActivity.class);
        intent.putExtra("allergies",allergies);
        intent.putExtra("diet",diet);
        startActivity(intent);
    }


}
