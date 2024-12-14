package DAO;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDao {

    /*
     * Return all messages in database.
     * @return List<Message> the list of messages
    */
    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();

        List<Message> messages = new ArrayList<>();

        try {
            String sql = "SELECT message_id, posted_by, message_text, time_posted_epoch FROM message;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int message_id = rs.getInt(1);
                int posted_by = rs.getInt(2);
                String message_text = rs.getString(3);
                long time_posted_epoch = rs.getLong(4);

                messages.add(new Message(message_id, posted_by, message_text, time_posted_epoch));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return messages;
    }

    /*
     * Return all messages in database according to the user ID.
     * @return List<Message> the list of messages
    */
    List<Message> getMessagesByUserId(int userId) {
        Connection connection = ConnectionUtil.getConnection();

        List<Message> messages = new ArrayList<>();

        try {
            String sql = "SELECT message_id, posted_by, message_text, time_posted_epoch FROM message WHERE posted_by = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int message_id = rs.getInt(1);
                int posted_by = rs.getInt(2);
                String message_text = rs.getString(3);
                long time_posted_epoch = rs.getLong(4);

                messages.add(new Message(message_id, posted_by, message_text, time_posted_epoch));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return messages;
    }

    /*
     * Returns a message in database according to the message ID.
     * @return Message the message
    */
    Message getMessageById(int messageId) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT message_id, posted_by, message_text, time_posted_epoch FROM message WHERE message_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, messageId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int message_id = rs.getInt(1);
                int posted_by = rs.getInt(2);
                String message_text = rs.getString(3);
                long time_posted_epoch = rs.getLong(4);

                return new Message(message_id, posted_by, message_text, time_posted_epoch);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return null;
    }

    /*
     * Creates a new message in the database.
     * @param Message(posted_by, message_text, time_posted_epoch)
     * @return Message(message_id, posted_by, message_text, time_posted_epoch) or null if insert failed
    */
    Message createMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";

            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int message_id = rs.getInt(1);
                message.setMessage_id(message_id);
                return message;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }

    /*
     * Delete a message in the database
     * @param int the message ID
     * @return Message the message to be deleted
    */
    Message deleteMessageById(int messageId) {
        Connection connection = ConnectionUtil.getConnection();

        Message message = getMessageById(messageId);

        try {
            String sql = "DELETE FROM message WHERE message_id = ?;";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, message.getMessage_id());
            
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) { // should only be 1
                return message;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }


    /*
     * Update a message in the database.
     * @param Message(message_id, posted_by, message_text, time_posted_epoch) the message to be updated
     * @return Message the update message
     * 
     * NOTE: Only message_text and time_posted_epoch will be updated; not message_id nor posted_by.
    */
    Message updateMessageById(Message message) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "UPDATE message SET message_text = ?, time_posted_epoch = ? WHERE message_id = ?;";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, message.getMessage_text());
            ps.setLong(2, message.getTime_posted_epoch());
            ps.setInt(3, message.getMessage_id());
            
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) { // should only be 1
                return message;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }

}