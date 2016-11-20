package com.ohio.hack.hackohio2016;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import java.lang.String;

public class SkillDescription extends AppCompatActivity {

    String skillString;
    String descriptionString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_description);

        EditText skill = (EditText) findViewById(R.id.enter_skill);
        skill.setText(skillString);

        EditText description = (EditText) findViewById(R.id.Description);
        description.setText(descriptionString);

        Button saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                EditText description = (EditText) findViewById(R.id.Description);
                EditText skill = (EditText) findViewById(R.id.enter_skill);

                descriptionString = description.getText().toString();
                skillString = skill.getText().toString();
            }
        });
    }

}
