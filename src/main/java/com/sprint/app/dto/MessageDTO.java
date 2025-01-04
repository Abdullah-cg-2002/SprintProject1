package com.sprint.app.dto;

import com.sprint.app.model.Users;

/**
 * Data Transfer Object (DTO) for transferring message-related data between layers of the application.
 * This DTO encapsulates the message text and the sender and receiver users involved in the message.
 */
public class MessageDTO {

    /**
     * The text content of the message.
     * Represents the actual message sent by the sender to the receiver.
     */
    private String message_text;

    /**
     * The sender of the message.
     * This represents the user who sends the message.
     */
    private Users sender;

    /**
     * The receiver of the message.
     * This represents the user who receives the message.
     */
    private Users receiver;

    /**
     * Gets the message text.
     *
     * @return the text of the message.
     */
    public String getMessage_text() {
        return message_text;
    }

    /**
     * Sets the message text.
     *
     * @param message_text the text to set for the message.
     */
    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }

    /**
     * Gets the sender of the message.
     *
     * @return the sender of the message.
     */
    public Users getSender() {
        return sender;
    }

    /**
     * Sets the sender of the message.
     *
     * @param sender the user who is the sender of the message.
     */
    public void setSender(Users sender) {
        this.sender = sender;
    }

    /**
     * Gets the receiver of the message.
     *
     * @return the receiver of the message.
     */
    public Users getReceiver() {
        return receiver;
    }

    /**
     * Sets the receiver of the message.
     *
     * @param receiver the user who is the receiver of the message.
     */
    public void setReceiver(Users receiver) {
        this.receiver = receiver;
    }
}
