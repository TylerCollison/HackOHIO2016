package com.ohio.hack.hackohio2016;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.mobileconnectors.cognito.Dataset;
import com.amazonaws.mobileconnectors.cognito.Record;
import com.amazonaws.mobileconnectors.cognito.SyncConflict;
import com.amazonaws.mobileconnectors.cognito.exceptions.DataStorageException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InputInterest extends AppCompatActivity {

    private Dataset saveData = MainActivity.saveData;
    private AmazonDynamoDBAsyncClient dynamo = MainActivity.dynamo;

    EditText int1Text;
    EditText int2Text;
    EditText int3Text;
    EditText int4Text;
    EditText int5Text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_interest);

        int1Text =  (EditText) findViewById(R.id.intText1);
        int2Text =  (EditText) findViewById(R.id.intText2);
        int3Text =  (EditText) findViewById(R.id.intText3);
        int4Text =  (EditText) findViewById(R.id.intText4);
        int5Text =  (EditText) findViewById(R.id.intText5);

        if (saveData.get("Interests") != null) {
            String interestsString = saveData.get("Interests");
            String[] interestsArray = interestsString.split("~~");
            int1Text.setText(interestsArray[0]);
            int2Text.setText(interestsArray[1]);
            int3Text.setText(interestsArray[2]);
            int4Text.setText(interestsArray[3]);
            int5Text.setText(interestsArray[4]);
        }
    }

    public void saveInterests (View v) {
        saveData.put("Interests", int1Text.getText().toString() + "~~" +
                int2Text.getText().toString() + "~~" + int3Text.getText().toString() +
                "~~" + int4Text.getText().toString() + "~~" + int5Text.getText().toString());
        saveData.synchronizeOnConnectivity(new Dataset.SyncCallback() {
            @Override
            public void onSuccess(Dataset dataset, List<Record> updatedRecords) {
                System.out.println("Sync Successful!");
            }

            @Override
            public boolean onConflict(Dataset dataset, List<SyncConflict> conflicts) {
                return false;
            }

            @Override
            public boolean onDatasetDeleted(Dataset dataset, String datasetName) {
                return false;
            }

            @Override
            public boolean onDatasetsMerged(Dataset dataset, List<String> datasetNames) {
                return false;
            }

            @Override
            public void onFailure(DataStorageException dse) {
                System.out.println("Sync Failed");
            }
        });
        uploadUser(saveData.get("Email").toString(), saveData.get("Interests").toString(),
                saveData.get("Username").toString());
        if (saveData.get("Skill0") == null) {
            Intent skillsInputIntent = new Intent(this, InputSkill.class);
            startActivity(skillsInputIntent);
        } else {
            finish();
        }
    }

    private void uploadUser (String email, String interests, String username) {
        Map<String, AttributeValue> user = new HashMap<>();
        user.put("Email", new AttributeValue(email));
        user.put("Interests", new AttributeValue(interests));
        user.put("Username", new AttributeValue(username));
        PutItemRequest req = new PutItemRequest("Users", user);
        dynamo.putItemAsync(req, new AsyncHandler<PutItemRequest, PutItemResult>() {
            @Override
            public void onError(Exception exception) {
                System.out.println("User upload failed");
            }

            @Override
            public void onSuccess(PutItemRequest request, PutItemResult putItemResult) {
                System.out.println("User upload Succeeded!");
            }
        });
    }

}

