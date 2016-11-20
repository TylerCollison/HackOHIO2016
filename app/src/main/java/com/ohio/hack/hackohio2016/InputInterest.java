package com.ohio.hack.hackohio2016;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class InputInterest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_interest);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });



        Button saveIntButton = (Button) findViewById(R.id.intSaveButton);
        saveIntButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText int1Text =  (EditText) findViewById(R.id.intText1);
                EditText int2Text =  (EditText) findViewById(R.id.intText2);
                EditText int3Text =  (EditText) findViewById(R.id.intText3);
                EditText int4Text =  (EditText) findViewById(R.id.intText4);
                EditText int5Text =  (EditText) findViewById(R.id.intText5);

                String int1 = int1Text.getText().toString();
                String int2 = int2Text.getText().toString();
                String int3 = int3Text.getText().toString();
                String int4 = int4Text.getText().toString();
                String int5 = int5Text.getText().toString();


                System.out.print(int1);
                System.out.print(int2);
                System.out.print(int3);
                System.out.print(int4);
                System.out.print(int5);


                startActivity(new Intent(InputInterest.this, InputSkill.class));




            }
        });




    }

}

