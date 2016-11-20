package com.ohio.hack.hackohio2016;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.cognito.CognitoSyncManager;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

public class MainActivity extends AppCompatActivity {

    public static CognitoSyncManager syncM;
    public static CognitoCachingCredentialsProvider credentialsProvider;

    String usernameStr;
    String passwordStr;
    String emailStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),    /* get the context for the application */
                "us-east-1:d467e43d-84f9-4df3-9b40-6ba8c224fd4e",    /* Identity Pool ID */
                Regions.US_EAST_1           /* Region for your identity pool--US_EAST_1 or EU_WEST_1*/
        );


        syncM = new CognitoSyncManager(
                getApplicationContext(),    /* get the context for the application */
                Regions.US_EAST_1 ,    /* Identity Pool ID */
                credentialsProvider/* Region for your identity pool--US_EAST_1 or EU_WEST_1*/
        );

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


