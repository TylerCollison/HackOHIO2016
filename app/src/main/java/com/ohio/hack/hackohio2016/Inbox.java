package com.ohio.hack.hackohio2016;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.mobileconnectors.cognito.Dataset;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inbox extends AppCompatActivity {

    private AmazonDynamoDBAsyncClient dynamo = MainActivity.dynamo;
    private MessagesViewAdapter adapter = new MessagesViewAdapter();
    private Dataset saveData = MainActivity.saveData;

    RecyclerView messageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        messageView = (RecyclerView)findViewById(R.id.messageView);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        messageView.setAdapter(adapter);
        messageView.setLayoutManager(manager);

        skillsQueryAsync.execute();
    }

    AsyncTask skillsQueryAsync = new AsyncTask() {
        @Override
        protected Object doInBackground(Object[] params) {
            skillsQuery(saveData.get("Email").toString(), adapter);
            return null;
        }
    };

    private void skillsQuery(String myEmail, MessagesViewAdapter skillsViewAdapter) {
        ScanRequest req = new ScanRequest("Messages");
        req.addExpressionAttributeNamesEntry("#T", "ReceiverEmail");
        req.addExpressionAttributeValuesEntry(":i", new AttributeValue(myEmail));
        req.setFilterExpression("#T = :i");
        dynamo.scanAsync(req, new AsyncHandler<ScanRequest, ScanResult>() {
            @Override
            public void onError(Exception e) {
                System.out.println("Error: Could not get messages request: " + e.getMessage());
            }

            @Override
            public void onSuccess(ScanRequest request, final ScanResult queryResult) {
                System.out.println("Got the message request!");
                final List<Map<String, AttributeValue>> requestItems = queryResult.getItems();
                System.out.println(requestItems.size());
                for (Map<String, AttributeValue> requestItem : requestItems) {
                    System.out.println("In the loop!");
                    final Message msg = new Message();
                    msg.setSkill(requestItem.get("Skill").getS().toString());
                    msg.setSenderUsername(requestItem.get("SenderUsername").getS().toString());
                    msg.setReceiverUsername(requestItem.get("ReceiverUsername").getS().toString());
                    msg.setSenderEmail(requestItem.get("SenderEmail").getS().toString());
                    msg.setMessage(requestItem.get("Message").getS().toString());
                    msg.setReceiverEmail(requestItem.get("ReceiverEmail").toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.addMessage(msg);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }
}
