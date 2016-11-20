package com.ohio.hack.hackohio2016;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import java.lang.String;


/**
 * author: Shaina Leibovich
 * date: 11/19/16
 */
public class SkillDescription extends AppCompatActivity {

    String skillString;
    String descriptionString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_description);

        //set skill string to last value
        EditText skill = (EditText) findViewById(R.id.skillText);
        skill.setText(skillString);

        //set description string to last value
        EditText description = (EditText) findViewById(R.id.descriptionText);
        description.setText(descriptionString);

        //when save button is clicked, change values of skillString and descriptionString
        Button saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                EditText description = (EditText) findViewById(R.id.descriptionText);
                EditText skill = (EditText) findViewById(R.id.skillText);

                descriptionString = description.getText().toString();
                skillString = skill.getText().toString();
            }
        });
    }

}
