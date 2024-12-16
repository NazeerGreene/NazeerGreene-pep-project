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

    public Message updateMessage(Message message) {
        Message previouMessage = messageDao.getMessageById(message.getMessage_id());

        if (previouMessage == null) { // if not found
            return null; // then there's nothing to update
        }

        // todo: check if time has changed, update accordingly
        // check if text has changed, update accordingly
        // states Message could enter
        // M(..,text, time) - valid
        // M(.., "", time) - valid: only time update
        // M(.., text, 0) - valid: only text update
        // M(.., "", 0) invalid - should delete

        if (!isValidMessage(message)) {

        }

        return isValidMessage(message) ? messageDao.updateMessage(message) : null;
    }

    private boolean isValidMessage(Message message) {
        String text = message.getMessage_text();

        return !text.isBlank() && 
        text.length() <= MAX_MESSAGE_LENGTH && 
        message.getPosted_by() > 0 ; // user must exist
        //message.getTime_posted_epoch() > 0;
    }
}
