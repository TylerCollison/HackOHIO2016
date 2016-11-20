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


//        Button ski1 = (Button) findViewById(R.id.skiButton1);
//        ski1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                startActivity(new Intent(InputSkill.this, SkillDescription.class));
//            }
//        });
//
//        Button ski2 = (Button) findViewById(R.id.skiButton2);
//        ski2.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                startActivity(new Intent(InputSkill.this, SkillDescription.class));
//
//
//            }
//        });
//
//        Button ski3 = (Button) findViewById(R.id.skiButton3);
//        ski2.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                startActivity(new Intent(InputSkill.this, SkillDescription.class));
//
//
//            }
//        });
//
//        Button ski4 = (Button) findViewById(R.id.skiButton4);
//        ski2.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//
//                startActivity(new Intent(InputSkill.this, SkillDescription.class));
//
//            }
//        });
//
//        Button ski5 = (Button) findViewById(R.id.skiButton5);
//        ski2.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                startActivity(new Intent(InputSkill.this, SkillDescription.class));
//
//
//            }
//        });

    }

    public void goToDescription(View v){
        Intent intent = new Intent(InputSkill.this, SkillDescription.class);
        startActivity(intent);
    }



}

