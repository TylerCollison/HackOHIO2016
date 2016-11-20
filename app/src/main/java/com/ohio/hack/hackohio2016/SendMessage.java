package com.ohio.hack.hackohio2016;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.mobileconnectors.cognito.Dataset;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;

import java.util.HashMap;
import java.util.Map;

public class SendMessage extends AppCompatActivity {

    private AmazonDynamoDBAsyncClient dynamo = MainActivity.dynamo;
    private Dataset saveData = MainActivity.saveData;

    EditText messageText;
    Button sendButton;

    private String[] paramsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        // receiverEmail~~receiverUsername~~skill
        String params = getIntent().getStringExtra("messageParams");
        paramsArray = params.split("~~");

        messageText = (EditText)findViewById(R.id.sendMessageText);
        sendButton = (Button)findViewById(R.id.sendButton);
    }

    public void sendMessage (View v) {
        String senderEmail = saveData.get("Email");
        String senderUsername = saveData.get("Username");
        uploadMessage(paramsArray[0] + senderEmail, paramsArray[0], messageText.getText().toString(), senderEmail,
               paramsArray[1], senderUsername, paramsArray[2]);
        finish();
    }

    private void uploadMessage (String messageID, String receiverEmail, String message,
                                String senderEmail, String receiverUsername, String senderUsername,
                                String skill) {
        Map<String, AttributeValue> messageObject = new HashMap<>();
        messageObject.put("MessageID", new AttributeValue(messageID));
        messageObject.put("ReceiverEmail", new AttributeValue(receiverEmail));
        messageObject.put("Message", new AttributeValue(message));
        messageObject.put("SenderEmail", new AttributeValue(senderEmail));
        messageObject.put("ReceiverUsername", new AttributeValue(receiverUsername));
        messageObject.put("SenderUsername", new AttributeValue(senderUsername));
        messageObject.put("Skill", new AttributeValue(skill));
        PutItemRequest req = new PutItemRequest("Messages", messageObject);
        dynamo.putItemAsync(req, new AsyncHandler<PutItemRequest, PutItemResult>() {
            @Override
            public void onError(Exception exception) {
                System.out.println("Message Upload Failed");
            }

            @Override
            public void onSuccess(PutItemRequest request, PutItemResult putItemResult) {
                System.out.println("Message upload was successful!");
            }
        });
    }
}
