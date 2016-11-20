package com.ohio.hack.hackohio2016;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InputSkill extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_interest);


        Button ski1 = (Button) findViewById(R.id.skiButton1);
        ski1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

//                EditText ski1Text =  (EditText) findViewById(R.id.skiText1);
//                EditText ski2Text =  (EditText) findViewById(R.id.skiText2);
//                EditText ski3Text =  (EditText) findViewById(R.id.skiText3);
//                EditText ski4Text =  (EditText) findViewById(R.id.skiText4);
//                EditText ski5Text =  (EditText) findViewById(R.id.skiText5);
//
//                String ski1 = ski1Text.getText().toString();
//                String ski2 = ski2Text.getText().toString();
//                String ski3 = ski3Text.getText().toString();
//                String ski4 = ski4Text.getText().toString();
//                String ski5 = ski5Text.getText().toString();





            }
        });

        Button ski2 = (Button) findViewById(R.id.skiButton2);
        ski2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivity(new Intent(InputSkill.this, SkillDescription.class));



            }
        });

        Button ski3 = (Button) findViewById(R.id.skiButton3);
        ski2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivity(new Intent(InputSkill.this, SkillDescription.class));


            }
        });

        Button ski4 = (Button) findViewById(R.id.skiButton4);
        ski2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                startActivity(new Intent(InputSkill.this, SkillDescription.class));

            }
        });

        Button ski5 = (Button) findViewById(R.id.skiButton5);
        ski2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivity(new Intent(InputSkill.this, SkillDescription.class));


            }
        });

    }

}

