package com.sprint.app.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Represents a message in the system.
 * This class defines the structure of a message object, which includes message text, 
 * timestamp, sender, and receiver.
 * It is mapped to a database entity and follows the JPA (Java Persistence API) annotations.
 */
@Entity
public class Messages {

    /**
     * The unique identifier for the message.
     * This is the primary key for the message entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageID;

    /**
     * The content of the message.
     * This field cannot be blank and must contain between 2 and 100 characters.
     */
    @NotBlank
    @Size(min = 2, max = 100, message = "message should contain at least 2 characters and at most 100 characters")
    private String message_text;

    /**
     * The timestamp when the message was created.
     * This is used to track when the message was sent.
     */
    private LocalDateTime timestamp;

    /**
     * The sender of the message.
     * This is a many-to-one relationship with the Users entity. 
     * The sender's user ID is referenced from the Users table.
     * The sender field is ignored in JSON serialization to prevent circular reference.
     */
    @ManyToOne
    @JoinColumn(name = "senderID", referencedColumnName = "userID")
    @JsonIgnore
    private Users sender;

    /**
     * The receiver of the message.
     * This is a many-to-one relationship with the Users entity. 
     * The receiver's user ID is referenced from the Users table.
     * The receiver field is ignored in JSON serialization to prevent circular reference.
     */
    @ManyToOne
    @JoinColumn(name = "receiverID", referencedColumnName = "userID")
    @JsonIgnore
    private Users receiver;

    /**
     * Gets the unique identifier of the message.
     *
     * @return the message ID.
     */
    public int getMessageID() {
        return messageID;
    }

    /**
     * Gets the text content of the message.
     *
     * @return the message text.
     */
    public String getMessage_text() {
        return message_text;
    }

    /**
     * Sets the text content of the message.
     *
     * @param message_text the message text.
     */
    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }

    /**
     * Gets the timestamp of when the message was sent.
     *
     * @return the timestamp.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp for the message.
     *
     * @param timestamp the timestamp when the message is created.
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Gets the sender of the message.
     *
     * @return the sender (a {@link Users} object).
     */
    public Users getSender() {
        return sender;
    }

    /**
     * Sets the sender of the message.
     *
     * @param sender the sender (a {@link Users} object).
     */
    public void setSender(Users sender) {
        this.sender = sender;
    }

    /**
     * Gets the receiver of the message.
     *
     * @return the receiver (a {@link Users} object).
     */
    public Users getReceiver() {
        return receiver;
    }

    /**
     * Sets the receiver of the message.
     *
     * @param receiver the receiver (a {@link Users} object).
     */
    public void setReceiver(Users receiver) {
        this.receiver = receiver;
    }
}
