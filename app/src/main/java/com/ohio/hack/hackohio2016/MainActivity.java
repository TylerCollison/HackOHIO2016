package com.ohio.hack.hackohio2016;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        Button saveIntButton = (Button) findViewById(R.id.inputButton);
//        saveIntButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                startActivity(new Intent(MainActivity.this, InputSkill.class));
//            }
//        });
//
//        Button saveDescButton = (Button) findViewById(R.id.descriptionButton);
//        saveDescButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                startActivity(new Intent(MainActivity.this, SkillDescription.class));
//            }
//       });
    }


    public void goToInt(View v){
        Intent intent = new Intent(MainActivity.this, InputInterest.class);
        startActivity(intent);
    }


    public void goToDes(View v){
        Intent intent = new Intent(MainActivity.this, SkillDescription.class);
        startActivity(intent);
    }

    public void test (View v) {
        Intent testIntent = new Intent(this, DashActivity.class);
        startActivity(testIntent);
    }

}


