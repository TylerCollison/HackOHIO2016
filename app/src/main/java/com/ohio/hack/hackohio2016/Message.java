package com.ohio.hack.hackohio2016;

/**
 * Created by Tyler Collison on 11/20/2016.
 */
public class Message {

    String receiverEmail = "";
    String message = "";
    String senderEmail = "";
    String receiverUsername = "";
    String senderUsername = "";
    String skill = "";

    public void setSkill (String newSkill) {
        skill = newSkill;
    }

    public String getSkill () {
        return skill;
    }

    public void setReceiverEmail (String email) {
        receiverEmail = email;
    }

    public void setMessage (String newMessage) {
        message = newMessage;
    }

    public void setSenderEmail (String email) {
        senderEmail = email;
    }

    public void setReceiverUsername (String username) {
        receiverUsername = username;
    }

    public void setSenderUsername (String username) {
        senderUsername = username;
    }

    public String getReceiverEmail () {
        return receiverEmail;
    }

    public String getMessage () {
        return message;
    }

    public String getSenderEmail () {
        return senderEmail;
    }

    public String getReceiverUsername () {
        return receiverUsername;
    }

    public String getSenderUsername () {
        return senderUsername;
    }

}
