package Service;

import Model.Message;

import java.util.List;

public class MessageService {
    public List<Message> getAllMessages() {
        return null;
    }

    List<Message> getMessagesByUserId(int userId) {
        return null;
        // either messages for accountId or null
    }

    Message getMessageById(int messageId) {
        return null;
        // return message by id or null
    }

    Message createMessage(Message message) {
        return null;
        // message not blank
        // message not over 255 char
        // return Message(id, content) or null
    }

    Message deleteMessageById(int messageId) {
        return null;
        // return and delete message by id
        // otherwise null
    }

    Message updateMessageById(Message message) {
        return null;
        // messageId must already exist
        // content cannot be blank
        // content cannot be over 255 characters
        // other information may be present in request body
    }
}
