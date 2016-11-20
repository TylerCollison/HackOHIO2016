package com.ohio.hack.hackohio2016;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tyler Collison on 11/19/2016.
 */
public class MessagesViewAdapter extends RecyclerView.Adapter<MessagesViewAdapter.ViewHolder> {

    Context owningContext;
    List<Message> messages = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView subjectSkillText;
        TextView senderUsernameText;
        TextView messageText;
        Button reply;

        public ViewHolder(View itemView) {
            super(itemView);
            subjectSkillText = (TextView)itemView.findViewById(R.id.subjectSkillText);
            senderUsernameText = (TextView)itemView.findViewById(R.id.senderUsernameText);
            messageText = (TextView)itemView.findViewById(R.id.messageText);
            reply = (Button)itemView.findViewById((R.id.replyButton));
        }

//        @Override
//        public void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.message_card);
//        }


    }

    @Override
    public MessagesViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // set the skill card as the layout for recycler view items
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MessagesViewAdapter.ViewHolder holder, int position) {
        // Bind the skill data to the ViewHolder
        holder.subjectSkillText.setText(messages.get(position).getSkill());
        holder.senderUsernameText.setText(messages.get(position).getSenderUsername());
        holder.messageText.setText(messages.get(position).getMessage());

        holder.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSendMessageActivity(view);
            }
        });
    }

    public void addMessage (Message message) {
        messages.add(message);
        System.out.println("message size = " + messages.size());
    }


    public void setContext (Context newContext) {
        owningContext = newContext;
    }

    public void openSendMessageActivity (View v) {
        Intent sendMessageIntent = new Intent(owningContext, SendMessage.class);
        Message m = messages.get(messages.size() - 1);
        String params = m.getSenderEmail() + "~~" +
                m.getReceiverEmail() + "~~" + m.getSkill();
        sendMessageIntent.putExtra("messageParams", params);
        owningContext.startActivity(sendMessageIntent);
    }

    public void clear () {
        messages.clear();
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
