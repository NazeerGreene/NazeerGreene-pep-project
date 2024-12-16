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
        return messageDao.deleteMessageById(messageId);
    }

    public Message updateMessageById(Message message) {
        
        if (messageDao.getMessageById(message.getMessage_id()) == null) { // if not found
            return null; // then there's nothing to update
        }

        return isValidMessage(message) ? messageDao.updateMessage(message) : null;
    }

    private boolean isValidMessage(Message message) {
        String text = message.getMessage_text();

        return !text.isBlank() && 
        text.length() <= MAX_MESSAGE_LENGTH && 
        message.getPosted_by() < 1 && // user doesn't exist
        message.getTime_posted_epoch() < 0;
    }
}
