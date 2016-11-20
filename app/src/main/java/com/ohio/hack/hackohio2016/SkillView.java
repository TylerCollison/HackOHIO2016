package com.ohio.hack.hackohio2016;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SkillView extends AppCompatActivity {

    TextView skillHeader;
    TextView usernameHeader;
    TextView emailHeader;
    TextView skillDescription;
    ListView interestsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_view);

        //Get the skill data
        String skillString = getIntent().getStringExtra("selectedSkill");
        String[] skillArray = skillString.split("~~");

        //Connect to the UI
        skillHeader = (TextView)findViewById(R.id.skillHeader);
        usernameHeader = (TextView)findViewById(R.id.usernameHeader);
        emailHeader = (TextView)findViewById(R.id.emailHeader);
        skillDescription = (TextView)findViewById(R.id.skillDescriptionText);
        interestsList = (ListView)findViewById(R.id.interestView);

        //Associate the skill data with the UI elements
        skillHeader.setText(skillArray[0].toUpperCase());
        usernameHeader.setText(skillArray[1]);
        emailHeader.setText(skillArray[2]);
        skillDescription.setText(skillArray[3]);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        String interestsString = skillArray[4];
        String[] interestArray = interestsString.split("``");
        for (int i = 0; i < interestArray.length; i++) {
            adapter.add(interestArray[i]);
        }
        interestsList.setAdapter(adapter);
    }

    public void openEmailActivity (View v) {
        TextView textView = (TextView)v;
        String toEmail = textView.getText().toString();
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{toEmail});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, skillHeader.getText());
        this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }
}
