package Service;

import DAO.AccountDao;
import Model.Account;

/*
 * Service rules
 *  New & Returning Users:
 *  1. username not blank
 *  2. password 4+ characters long
 *  
 *  New Users:
 *  3. username not present in database
*/
public class AccountService {
    private final AccountDao accountDao;

    private final int MIN_PASSWORD_LENGTH = 4;

    public AccountService() {
        accountDao = new AccountDao();
    }

    /*
     * @param Account with username and password to register new user
     * @return Account (id, username, password) if service rules met
    */
    public Account registerNewUser(Account account) {

        // business rules
        if(!credentialsAreValid(account.getUsername(), account.getPassword())) {
            return null;
        }

        if (accountDao.usernameTaken(account.getUsername())) {
            return null;
        }

        return accountDao.registerNewUser(account);
    }

    /*
     * @param Account with username and password to verify
     * @return Account (id, username, password) if found in database
    */
    public Account verifyExistingUser(Account account) {
        // business rules - redudant, but could save a trip to the database
        if (!credentialsAreValid(account.getUsername(), account.getPassword())) {
            return null;
        }

        return accountDao.verifyExistingUser(account);
    }

    public Account verifyAccountById(int accountId) {
        return null;
    }

    private boolean credentialsAreValid(String username, String password) {
        if (username.isBlank()) {
            return false;
        }

        if (password.length() < MIN_PASSWORD_LENGTH) {
            return false;
        } 

        return true;
    }
    
}
