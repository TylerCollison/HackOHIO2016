package com.ohio.hack.hackohio2016;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.mobileconnectors.cognito.Dataset;
import com.amazonaws.mobileconnectors.cognito.Record;
import com.amazonaws.mobileconnectors.cognito.SyncConflict;
import com.amazonaws.mobileconnectors.cognito.exceptions.DataStorageException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteItemResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;

import java.lang.String;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * author: Shaina Leibovich
 * date: 11/19/16
 */
public class SkillDescription extends AppCompatActivity {

    private Dataset saveData = MainActivity.saveData;
    private AmazonDynamoDBAsyncClient dynamo = MainActivity.dynamo;

    EditText skill;
    EditText description;

    String skillString = "";
    String descriptionString = "";

    int skillIndex = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_description);

        skillIndex = getIntent().getIntExtra("skillIndex", 0);
        if (saveData.get("Skill" + String.valueOf(skillIndex)) != null) {
            skillString = saveData.get("Skill" + String.valueOf(skillIndex)).toString();
            descriptionString = saveData.get("Description" + String.valueOf(skillIndex)).toString();
        } else {
            skillString = "";
            descriptionString = "";
        }

        //set skill string to last value
        skill = (EditText)findViewById(R.id.skillTextInput);
        skill.setText(skillString);

        //set description string to last value
        description = (EditText) findViewById(R.id.descriptionText);
        description.setText(descriptionString);

        //when save button is clicked, change values of skillString and descriptionString
        final Button saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (description.getText().toString().length() > 0 &&
                        skill.getText().toString().length() > 0) {
                    saveData.put("Description" + String.valueOf(skillIndex),
                            description.getText().toString());
                    saveData.put("Skill" + String.valueOf(skillIndex), skill.getText().toString());
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
                    if (skillString != "") {
                        deleteSkill(saveData.get("Email") + skillString);
                    }
                    uploadSkill(saveData.get("Email").toString() + saveData.get("Skill"
                                    + String.valueOf(skillIndex)).toString(), saveData.get("Description"
                                    + String.valueOf(skillIndex)).toString(), saveData.get("Email").toString(),
                            saveData.get("Skill" + String.valueOf(skillIndex)).toString());
                    finish();
                }
            }
        });
    }

    private void deleteSkill (String skillID) {
        Map<String, AttributeValue> key = new HashMap<>(1);
        key.put("SkillID", new AttributeValue(skillID));
        DeleteItemRequest req = new DeleteItemRequest("Skills", key);
        dynamo.deleteItemAsync(req, new AsyncHandler<DeleteItemRequest, DeleteItemResult>() {
            @Override
            public void onError(Exception exception) {
                System.out.println("Deletion Failed");
            }

            @Override
            public void onSuccess(DeleteItemRequest request, DeleteItemResult deleteItemResult) {
                System.out.println("Deletion Succeeded!");
            }
        });
    }

    private void uploadSkill (String skillID, String description, String email, String skill) {
        Map<String, AttributeValue> skillObject = new HashMap<>();
        skillObject.put("SkillID", new AttributeValue(skillID));
        skillObject.put("Description", new AttributeValue(description));
        skillObject.put("Email", new AttributeValue(email));
        skillObject.put("Skill", new AttributeValue(skill.toLowerCase()));
        PutItemRequest req = new PutItemRequest("Skills", skillObject);
        dynamo.putItemAsync(req, new AsyncHandler<PutItemRequest, PutItemResult>() {
            @Override
            public void onError(Exception exception) {
                System.out.println("Skill upload failed");
            }

            @Override
            public void onSuccess(PutItemRequest request, PutItemResult putItemResult) {
                System.out.println("Skill upload succeeded!");
            }
        });
    }

}
