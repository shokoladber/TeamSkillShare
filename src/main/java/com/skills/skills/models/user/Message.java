package com.skills.skills.models.user;

import com.skills.skills.models.AbstractEntity;
import jdk.jfr.Timestamp;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Message extends AbstractEntity {

    @NotBlank(message = "Message subject is required")
    @Size(max = 250, message = "Subject line must be 100 characters or less")
    private String subject;

    @NotBlank(message = "Message required.")
    @Size(max = 1000, message = "Message must be 1000 characters or less")
    private String body;

    @Timestamp
    private Long timestamp;

    @NotNull
    private int recipientId;

    @NotNull
    private int senderId;

    public Message(String subject, String body, Long timestamp, int recipientId, int senderId) {
        this.subject = subject;
        this.body = body;
        this.timestamp = timestamp;
        this.recipientId = recipientId;
        this.senderId = senderId;
    }

    public Message() {}

    public String getSubject() { return subject; }

    public void setSubject(String subject) { this.subject = subject; }

    public String getBody() { return body; }

    public void setBody(String body) { this.body = body; }

    public Long getTimestamp() { return timestamp; }

    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }

    public int getRecipient() { return recipientId; }

    public void setRecipient(User recipient) { this.recipientId = recipient.getId(); }

    public int getSender() { return senderId; }

    public void setSender(User sender) { this.senderId = sender.getId(); }
}
