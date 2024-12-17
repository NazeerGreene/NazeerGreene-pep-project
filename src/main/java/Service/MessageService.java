package Service;

import Model.Message;
import DAO.MessageDao;

import java.util.List;

public class MessageService {
    private final MessageDao messageDao;

    private static final int MAX_MESSAGE_LENGTH = 255;

    public MessageService() {
        messageDao = new MessageDao();
    }

    public List<Message> getAllMessages() {
        return messageDao.getAllMessages();
    }

    public List<Message> getMessagesByUserId(int userId) {
        return messageDao.getMessagesByUserId(userId);
    }

    public Message getMessageById(int messageId) {
        return messageDao.getMessageById(messageId);
    }

    public Message createMessage(Message message) {
        return isValidMessage(message) ? messageDao.createMessage(message) : null;
    }

    public Message deleteMessageById(int messageId) {
        Message message = getMessageById(messageId);

        if (message == null) { // if not found
            return null; // nothing to delete
        }

        return messageDao.deleteMessageById(messageId) ? message : null;
    }

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

    private boolean isValidMessage(Message message) {
        return isValidMessageText(message.getMessage_text()) && 
        isValidMessageTime(message.getTime_posted_epoch()) && 
        message.getPosted_by() > 0;// user must exist
    }

    private boolean isValidMessageText(String text) {
        return !text.isBlank() && 
        text.length() <= MAX_MESSAGE_LENGTH;
    }

    private boolean isValidMessageTime(long time) {
        return time > 0;
    }
}
