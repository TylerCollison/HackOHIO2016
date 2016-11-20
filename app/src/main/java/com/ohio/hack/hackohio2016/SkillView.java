package com.ohio.hack.hackohio2016;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.mobileconnectors.cognito.Dataset;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient;
import com.amazonaws.services.dynamodbv2.model.AttributeAction;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateItemResult;

import java.util.HashMap;
import java.util.Map;

public class SkillView extends AppCompatActivity {

    private AmazonDynamoDBAsyncClient dynamo = MainActivity.dynamo;
    private Dataset saveData = MainActivity.saveData;

    TextView skillHeader;
    TextView usernameHeader;
    TextView emailHeader;
    TextView skillDescription;
    ListView interestsList;
    RatingBar averageRatingBar;
    RatingBar myRatingBar;

    private String[] skillArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_view);

        //Get the skill data
        String skillString = getIntent().getStringExtra("selectedSkill");
        skillArray = skillString.split("~~");

        //Connect to the UI
        skillHeader = (TextView)findViewById(R.id.skillHeader);
        usernameHeader = (TextView)findViewById(R.id.usernameHeader);
        emailHeader = (TextView)findViewById(R.id.emailHeader);
        skillDescription = (TextView)findViewById(R.id.skillDescriptionText);
        interestsList = (ListView)findViewById(R.id.interestView);
        averageRatingBar = (RatingBar)findViewById(R.id.averageRatingBar);
        myRatingBar = (RatingBar)findViewById(R.id.myRatingBar);


        //Associate the skill data with the UI elements
        skillHeader.setText(skillArray[0].toUpperCase());
        usernameHeader.setText(skillArray[1]);
        emailHeader.setText(skillArray[2]);
        skillDescription.setText(skillArray[3]);
        averageRatingBar.setIsIndicator(true);

        getRating(skillArray[2] + skillArray[0]);

        if (saveData.get(skillArray[2] + skillArray[0] + "Rating") != null) {
            myRatingBar.setIsIndicator(true);
            myRatingBar.setRating(Float.parseFloat(saveData.get(skillArray[2]
                    + skillArray[0] + "Rating")));
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        String interestsString = skillArray[4];
        String[] interestArray = interestsString.split("``");
        for (int i = 0; i < interestArray.length; i++) {
            adapter.add(interestArray[i]);
        }
        interestsList.setAdapter(adapter);
    }

    public void confirmRating (View v) {
        String skillID = skillArray[2] + skillArray[0];
        float averageRating = averageRatingBar.getRating();
        float myRating = myRatingBar.getRating();
        float newRating = (averageRating + myRating) / 2;
        updateRating(skillID, String.valueOf(newRating));
        saveData.put(skillArray[2] + skillArray[0] + "Rating", String.valueOf(myRating));
        myRatingBar.setIsIndicator(true);
    }

    private void updateRating (String skillID, final String newRatingValue) {
        Map<String, AttributeValue> key = new HashMap<>(1);
        key.put("SkillID", new AttributeValue(skillID));
        Map<String, AttributeValueUpdate> updatedValues = new HashMap<>(1);
        AttributeValueUpdate update = new AttributeValueUpdate(new AttributeValue(newRatingValue), AttributeAction.PUT);
        updatedValues.put("Rating", update);
        UpdateItemRequest req = new UpdateItemRequest("Skills", key, updatedValues);
        dynamo.updateItemAsync(req, new AsyncHandler<UpdateItemRequest, UpdateItemResult>() {
            @Override
            public void onError(Exception exception) {
                System.out.println("Rating Update Failed");
            }

            @Override
            public void onSuccess(UpdateItemRequest request, UpdateItemResult updateItemResult) {
                System.out.println("Rating Update Succeeded!");
            }
        });
    }

    private void getRating (String skillID) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("SkillID", new AttributeValue(skillID));
        GetItemRequest getReq = new GetItemRequest("Skills", key);
        dynamo.getItemAsync(getReq, new AsyncHandler<GetItemRequest, GetItemResult>() {
            @Override
            public void onError(Exception exception) {
                System.out.println("Failed to get Rating");
            }

            @Override
            public void onSuccess(GetItemRequest request, GetItemResult getItemResult) {
                final String rating = getItemResult.getItem().get("Rating").getS().toString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        averageRatingBar.setRating(Float.parseFloat(rating));
                        averageRatingBar.refreshDrawableState();
                    }
                });
            }
        });
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
