package com.skills.models;

import com.skills.models.user.Message;
import com.skills.models.user.User;

import java.util.ArrayList;
import java.util.Collections;

public class MessageData {

    public static ArrayList<Message> usersInboxSent(User user, Iterable<Message> allMessages) {

        ArrayList<Message> usersSentMessages = new ArrayList<>();
        int currentUserId = user.getId();

        for (Message message : allMessages) {
            if (message.getSender() == currentUserId) {
                usersSentMessages.add(message);
            }
        }
        Collections.sort(usersSentMessages, Message.compareByTimeStamp);
        return usersSentMessages;
    }


    public static ArrayList<Message> usersInboxReceived(User user, Iterable<Message> allMessages) {

        ArrayList<Message> usersReceivedMessages = new ArrayList<>();
        int currentUserId = user.getId();
        for (Message message : allMessages) {
            if (message.getRecipient() == currentUserId) {
                usersReceivedMessages.add(message);
            }
        }
        Collections.sort(usersReceivedMessages, Message.compareByTimeStamp);
        return usersReceivedMessages;
    }

    public static ArrayList<Message> allUsersMessages(User user, Iterable<Message> allMessages){

        ArrayList<Message> allUsersMessages = new ArrayList<>();
        int currentUserId = user.getId();
        for (Message message : allMessages) {
            if (message.getRecipient() == currentUserId || message.getSender() == currentUserId) {
                allUsersMessages.add(message);
            }
        }
            Collections.sort(allUsersMessages, Message.compareByTimeStamp);
            return allUsersMessages;

    }


}