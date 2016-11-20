package com.ohio.hack.hackohio2016;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    String usernameStr;
    String passwordStr;
    String emailStr;

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

    public void goToSkill(View v){
        Intent intent = new Intent(MainActivity.this, InputSkill.class);
        startActivity(intent);
    }

    public void login(View v){

        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);
        EditText email = (EditText) findViewById(R.id.email);

        usernameStr = username.getText().toString();
        passwordStr = password.getText().toString();
        emailStr = email.getText().toString();

        Intent intent = new Intent(MainActivity.this, InputInterest.class);
        startActivity(intent);

    }

}


