package Service;

import Model.Message;
import DAO.MessageDao;

import java.util.List;

/*
 * Service rules
 *  All Messages:
 *  1. message_text must not be blank and not over 255 characters. 
 *  
 *  New Messages:
 *  1. posted_by must refer to a real,existing user
 * 
 *  Updated Messages:
 *  1. message_id must already exist in the database
*/
public class MessageService {
    private final MessageDao messageDao;

    private static final int MAX_MESSAGE_LENGTH = 255;

    public MessageService() {
        messageDao = new MessageDao();
    }

    /*
     * Return all messages in the database
     * @return List<Message> the messages
     */
    public List<Message> getAllMessages() {
        return messageDao.getAllMessages();
    }

    /*
     * Return all messages related to the user ID
     * @param userId the user's ID
     * @return List<Message> the messages related to the user
     */
    public List<Message> getMessagesByUserId(int userId) {
        return messageDao.getMessagesByUserId(userId);
    }

    /*
     * Return a message according to its ID
     * @param messageId the message ID
     * @return Message the message
     */
    public Message getMessageById(int messageId) {
        return messageDao.getMessageById(messageId);
    }

    /*
     * Create a new message in the database
     * @param Message(message_text, posted_by, time_posted_epoch) the new message
     * @return Message(message_id, message_text, posted_by, time_posted_epoch) the new message if it meets all service rules
     */
    public Message createMessage(Message message) {
        return isValidMessage(message) ? messageDao.createMessage(message) : null;
    }

    /*
     * Deletes a message from the database according to its message ID
     * @param messageId the message ID
     * @return Message(message_id, message_text, posted_by, time_posted_epoch) the deleted message
     */
    public Message deleteMessageById(int messageId) {
        Message message = getMessageById(messageId);

        if (message == null) { // if not found
            return null; // nothing to delete
        }

        return messageDao.deleteMessageById(messageId) ? message : null;
    }

    /*
     * Updated a message that already exists in the database
     * @param Message(message_id, [message_text], [time_posted_epoch]) where only message_id is mandatory
     * @return Message(message_id, message_text, posted_by, time_posted_epoch) the updated message
     * 
     * NOTE: Only message_text and time_posted_epoch can be updated.
     */
    public Message updateMessage(Message message) {
        // first let's verify the message
        Message previouMessage = getMessageById(message.getMessage_id());

        if (previouMessage == null) { // if not found
            return null; // then there's nothing to update
        }

        message.setPosted_by(previouMessage.getPosted_by());

        if (message.getMessage_text() == null) { // no change to text from user
            message.setMessage_text(previouMessage.getMessage_text()); // keep old text
        }

        if (message.getTime_posted_epoch() == 0) { // no change to time from user
            message.setTime_posted_epoch(previouMessage.getTime_posted_epoch()); // keep old time
        }

        if (!isValidMessage(message)) {
            return null;
        }

        // message has been verified by this point
        return messageDao.updateMessage(message) ? getMessageById(message.getMessage_id()) : null;
    }

    /*
     * Helpers to validate MessageService rules
     */
    private boolean isValidMessage(Message message) {
        return isValidMessageText(message.getMessage_text()) && 
        message.getTime_posted_epoch() > 0 && // valid time
        message.getPosted_by() > 0;// user must exist
    }

    private boolean isValidMessageText(String text) {
        return text != null &&
        !text.isBlank() && 
        text.length() <= MAX_MESSAGE_LENGTH;
    }
}
