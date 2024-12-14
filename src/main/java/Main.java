import Controller.SocialMediaController;
import DAO.AccountDao;
import Service.AccountService;
import Util.ConnectionUtil;
import io.javalin.Javalin;

import Model.Account;

/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
    public static void main(String[] args) {
        ConnectionUtil.resetTestDatabase();

    }

    public static void account_dao() {
        AccountDao accountDao = new AccountDao();

        Account newAccount1 = new Account("BruceWayne", "Batman001");
        Account newAccount2 = new Account("BruceWayne", "Batman002");

        
        Account user1 = accountDao.registerNewUser(newAccount1);
        Account user2 = accountDao.registerNewUser(newAccount2);

        System.out.println("User1: " + user1);
        System.out.println("User2: " + user2);
    }

    public static void account_service() {
        AccountService accountService = new AccountService();
        
        Account newAccount1 = new Account("BruceWayne", "Batman001");
        Account newAccount2 = new Account("BruceWayne", "Batman002");

        Account user1 = accountService.registerNewUser(newAccount1);
        Account user2 = accountService.registerNewUser(newAccount2);

        if (user1 != null) {
            System.out.println(user1);
        }
        if (user2 == null) {
            System.out.println("user2");
        }
    }

    public static void basic_run() {
        SocialMediaController controller = new SocialMediaController();
        Javalin app = controller.startAPI();
        app.start(8080);
    }
}
