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
        setContentView(R.layout.activity_input_skill);
    }

    public void goToDescription1(View v){
        Intent intent = new Intent(InputSkill.this, SkillDescription.class);
        intent.putExtra("skillIndex", 0);
        startActivity(intent);
    }

    public void goToDescription2(View v){
        Intent intent = new Intent(InputSkill.this, SkillDescription.class);
        intent.putExtra("skillIndex", 1);
        startActivity(intent);
    }

    public void goToDescription3(View v){
        Intent intent = new Intent(InputSkill.this, SkillDescription.class);
        intent.putExtra("skillIndex", 2);
        startActivity(intent);
    }

    public void goToDescription4(View v){
        Intent intent = new Intent(InputSkill.this, SkillDescription.class);
        intent.putExtra("skillIndex", 3);
        startActivity(intent);
    }

    public void goToDescription5(View v){
        Intent intent = new Intent(InputSkill.this, SkillDescription.class);
        intent.putExtra("skillIndex", 4);
        startActivity(intent);
    }

    public void save (View v) {
        Intent dashIntent = new Intent(this, DashActivity.class);
        startActivity(dashIntent);
    }



}

