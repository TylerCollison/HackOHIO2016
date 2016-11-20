package com.ohio.hack.hackohio2016;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.mobileconnectors.cognito.Dataset;
import com.amazonaws.regions.Regions;
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

    //UI Variables
    RecyclerView skillsView;

    // Variables for Dynamo DB
    private AmazonDynamoDBAsyncClient dynamo = MainActivity.dynamo;

    private Dataset saveData = MainActivity.saveData;

    SkillsViewAdapter adapter = new SkillsViewAdapter();

    List<String> interests = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        String interestsString = saveData.get("Interests");
        String[] interestsArray = interestsString.split("~~");
        for (int i = 0; i < interestsArray.length; i++) {
            interests.add(interestsArray[i]);
        }

        skillsView = (RecyclerView)findViewById(R.id.skillView);
        // setup the product view layout manager
        RecyclerView.LayoutManager productViewLayoutManager = new LinearLayoutManager(this);
        skillsView.setLayoutManager(productViewLayoutManager);
        adapter.setContext(this);
        skillsView.setAdapter(adapter);

        skillsQueryAsync.execute();
    }

    AsyncTask skillsQueryAsync = new AsyncTask() {
        @Override
        protected Object doInBackground(Object[] params) {
            for (String s : interests) {
                skillsQuery(s.toLowerCase(), adapter);
            }
            return null;
        }
    };

    private void skillsQuery(String interest, final SkillsViewAdapter skillsViewAdapter) {
        ScanRequest req = new ScanRequest("Skills");
        req.addExpressionAttributeNamesEntry("#T", "Skill");
        req.addExpressionAttributeValuesEntry(":i", new AttributeValue(interest));
        req.setFilterExpression("contains (#T, :i)");
        dynamo.scanAsync(req, new AsyncHandler<ScanRequest, ScanResult>() {
            @Override
            public void onError(Exception e) {
                System.out.println("Error: Could not get skills request: " + e.getMessage());
            }

            @Override
            public void onSuccess(ScanRequest request, final ScanResult queryResult) {
                System.out.println("Got the skills request!");
                final List<Map<String, AttributeValue>> requestItems = queryResult.getItems();
                System.out.println(requestItems.size());
                for (Map<String, AttributeValue> requestItem : requestItems) {
                    System.out.println("In the loop!");
                    final Skill skill = new Skill();
                    skill.setSkill(requestItem.get("Skill").getS().toString());
                    skill.setDescription(requestItem.get("Description").getS().toString());
                    skill.setEmail(requestItem.get("Email").getS().toString());
                    System.out.println("Still in the loop!");
                    // Get the information of the user that owns the skill
                    Map<String, AttributeValue> key = new HashMap<>(1);
                    key.put("Email", new AttributeValue(requestItem.get("Email").getS().toString()));
                    GetItemRequest req = new GetItemRequest("Users", key);
                    System.out.println("Starting user request");
                    dynamo.getItemAsync(req, new AsyncHandler<GetItemRequest, GetItemResult>() {
                        @Override
                        public void onError(Exception e) {
                            System.out.println("Could not get user: " + e.getMessage());
                        }

                        @Override
                        public void onSuccess(GetItemRequest request, GetItemResult getItemResult) {
                            System.out.println("Got the user request!");
                            Map<String, AttributeValue> userItem = getItemResult.getItem();
                            skill.setUsername(userItem.get("Username").getS().toString());
                            String interests = userItem.get("Interests").getS().toString();
                            String[] interestsArray = interests.split("~~");
                            for (int i = 0; i < interestsArray.length; i++) {
                                skill.addInterest(interestsArray[i]);
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    skillsViewAdapter.addSkill(skill);
                                    skillsViewAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    public void goToInt(View v){
        Intent intent = new Intent (this, InputInterest.class);
        startActivity(intent);
    }

    public void goToSkills (View v) {
        Intent mySkillsIntent = new Intent (this, InputSkill.class);
        startActivity(mySkillsIntent);
    }

    public void refresh (View v) {
        Intent refreshIntent = new Intent (this, DashActivity.class);
        startActivity(refreshIntent);
        finish();
    }

    public void openInbox (View v) {
        Intent inboxIntent = new Intent (this, Inbox.class);
        startActivity(inboxIntent);
    }
}
