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
import com.amazonaws.mobileconnectors.cognito.Dataset;
import com.amazonaws.mobileconnectors.cognito.Record;
import com.amazonaws.mobileconnectors.cognito.SyncConflict;
import com.amazonaws.mobileconnectors.cognito.exceptions.DataStorageException;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static CognitoSyncManager syncM;
    private CognitoCachingCredentialsProvider credentialsProvider;
    public static AmazonDynamoDBAsyncClient dynamo;
    public static Dataset saveData;

    EditText username;
    EditText password;
    EditText email;

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

        saveData = syncM.openOrCreateDataset("saveData");

        // Link DynamoDB Client to the Cognito credentials
        dynamo = new AmazonDynamoDBAsyncClient(credentialsProvider);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);

        if(saveData.get("Username") != null) {
            openDashActivity();
            finish();
        }
    }

    private void openDashActivity () {
        Intent dashIntent = new Intent(this, DashActivity.class);
        startActivity(dashIntent);
    }

    public void goToInt(View v){
        Intent intent = new Intent(MainActivity.this, InputInterest.class);
        startActivity(intent);
    }

    public void login(View v){
        saveData.put("Username", username.getText().toString());
        saveData.put("Password", password.getText().toString());
        saveData.put("Email", email.getText().toString());
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

        Intent intent = new Intent(MainActivity.this, InputInterest.class);
        startActivity(intent);
    }



}


