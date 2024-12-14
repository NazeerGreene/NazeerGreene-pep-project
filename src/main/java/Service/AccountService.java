package Service;

import DAO.AccountDao;
import Model.Account;

public class AccountService {
    private final AccountDao accountDao;

    public AccountService() {
        accountDao = new AccountDao();
    }

    public Account registerNewUser(Account account) {
        return null;
        // rules
        // username not blank
        // password 4+ char long
        // username not already taken
        // then create new account
        // else reject
    }

    public Account verifyExistingUser(Account account) {
        return null;
        // rules
        // username and password match cred in db
        // return account cred
        // else reject
    }

    private boolean verifyCredentialsRules(String username, String password) {
        return false;
    }
    
}
