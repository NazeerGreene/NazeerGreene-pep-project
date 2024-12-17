import static org.junit.Assert.assertEquals;

import java.util.List;

import Controller.SocialMediaController;
import DAO.AccountDao;
import DAO.MessageDao;
import Service.AccountService;
import Util.ConnectionUtil;
import io.javalin.Javalin;

import Model.Account;
import Model.Message;

/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
    public static void main(String[] args) {
        ConnectionUtil.resetTestDatabase();

        run();

    }
    // public static void message_dao() {
    //     MessageDao messageDao = new MessageDao();
    //     AccountService accountService = new AccountService();

    //     Account user = new Account(0, "user1", "password");

    //     System.out.println("User registered:");
    //     user = accountService.registerNewUser(user);
    //     System.out.println(user);

    //     // CREATE
    //     Message message = new Message(user.getAccount_id(), "hello world", 1000);
    //     message = messageDao.createMessage(message);
        
    //     assertEquals(2, message.getMessage_id());
    //     assertEquals(2, message.getPosted_by());
    //     assertEquals("hello world", message.getMessage_text());
    //     assertEquals(1000, message.getTime_posted_epoch());

    //     // READ

    //     // get all messages
    //     List<Message> messages = messageDao.getAllMessages();
    //     assertEquals(1, messages.size());

    //     // get message by id
    //     Message messageById = messageDao.getMessageById(2);
    //     assertEquals("hello world", messageById.getMessage_text());

        // // get message for user
        // List<Message> messageByUser = messageDao.getMessagesByUserId(user.getAccount_id());
        // assertEquals("hello world", messageByUser.get(0).getMessage_text());

        // // UPDATE 
        // message.setMessage_text("goodbye world");

        // Message updatedMessage = messageDao.updateMessage(message);
        // assertEquals("goodbye world", updatedMessage.getMessage_text());

        // // DELETE
        // Message deletedMessage = messageDao.deleteMessageById(message.getMessage_id());
        // assertEquals(2, deletedMessage.getMessage_id());
        // assertEquals("goodbye world", deletedMessage.getMessage_text());
    // }

    // public static void account_dao() {
    //     AccountDao accountDao = new AccountDao();

    //     Account newAccount1 = new Account("BruceWayne", "Batman001");
    //     Account newAccount2 = new Account("BruceWayne", "Batman002");

        
    //     Account user1 = accountDao.registerNewUser(newAccount1);
    //     Account user2 = accountDao.registerNewUser(newAccount2);

    //     System.out.println("User1: " + user1);
    //     System.out.println("User2: " + user2);
    // }

    // public static void account_service() {
    //     AccountService accountService = new AccountService();
        
    //     Account newAccount1 = new Account("BruceWayne", "Batman001");
    //     Account newAccount2 = new Account("BruceWayne", "Batman002");

    //     Account user1 = accountService.registerNewUser(newAccount1);
    //     Account user2 = accountService.registerNewUser(newAccount2);

    //     if (user1 != null) {
    //         System.out.println(user1);
    //     }
    //     if (user2 == null) {
    //         System.out.println("user2");
    //     }
    // }

    public static void run() {
        SocialMediaController controller = new SocialMediaController();
        Javalin app = controller.startAPI();
        app.start(8080);
    }
}
