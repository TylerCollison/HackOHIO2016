package com.ohio.hack.hackohio2016;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

public class DashActivity extends AppCompatActivity {

    // Variables for Dynamo DB
    private AmazonDynamoDBAsyncClient dynamo;

    List<String> interests = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
    }

    private List<Skill> skillsQuery(String interest) {
        final List<Skill> result = new ArrayList<>();
        ScanRequest req = new ScanRequest("skills");
        req.addExpressionAttributeNamesEntry("#T", "Skill");
        req.addExpressionAttributeValuesEntry(":i", new AttributeValue(interest));
        req.setFilterExpression("#T = :i");
        dynamo.scanAsync(req, new AsyncHandler<ScanRequest, ScanResult>() {
            @Override
            public void onError(Exception e) {
                System.out.println("Error: Could not get skills request: " + e.getMessage());
            }

            @Override
            public void onSuccess(ScanRequest request, final ScanResult queryResult) {
                System.out.println("Got the request!");
                final List<Map<String, AttributeValue>> requestItems = queryResult.getItems();
                for (int i = 0; i < requestItems.size(); i++) {
                    Map<String, AttributeValue> requestItem = requestItems.get(i);
                    final Skill skill = new Skill();
                    skill.setSkill(requestItem.get("Skill").getS());
                    skill.setDescription(requestItem.get("Description").getS());
                    skill.setUsername(requestItem.get("Email").getS());
                    // Get the information of the user that owns the skill
                    Map<String, AttributeValue> key = new HashMap<>(1);
                    key.put("Email", new AttributeValue(skill.getEmail()));
                    GetItemRequest req = new GetItemRequest("Users", key);
                    dynamo.getItemAsync(req, new AsyncHandler<GetItemRequest, GetItemResult>() {
                        @Override
                        public void onError(Exception e) {
                            System.out.println("Could not get user: " + e.getMessage());
                        }

                        @Override
                        public void onSuccess(GetItemRequest request, GetItemResult getItemResult) {
                            Map<String, AttributeValue> userItem = getItemResult.getItem();
                            skill.setUsername(userItem.get("Username").getS());
                            String interests = userItem.get("Interests").getS();
                            String[] interestsArray = interests.split("~|~");
                            for (int i = 0; i < interestsArray.length; i++) {
                                skill.addInterest(interestsArray[i]);
                            }
                        }
                    });
                    result.add(skill);
                }
            }
        });
        return result;
    }
}
