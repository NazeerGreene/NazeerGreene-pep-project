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
     * Add a new user to the system, verifies service rules.
     * @param Account with username and password to register new user
     * @return Account(id, username, password) if service rules met
    */
    public Account registerNewUser(Account account) {

        // business rules
        if(!credentialsAreValid(account.getUsername(), account.getPassword())) {
            return null;
        }

        if (usernameTaken(account.getUsername())) {
            return null;
        }

        return accountDao.registerNewUser(account);
    }

    /*
     * Verify that a user already exist in the system.
     * @param Account with username and password to verify
     * @return Account(id, username, password) if found
    */
    public Account verifyExistingUser(Account account) {
        // business rules - redudant, but could save a trip to the database
        if (!credentialsAreValid(account.getUsername(), account.getPassword())) {
            return null;
        }

        return accountDao.verifyExistingUser(account);
    }

    /*
     * Find user by their user ID.
     * @param int user ID
     * @return Account(id, username) if found
    */
    public Account getAccountUsernameByUserId(int accountId) {
        return accountDao.getUserByUserId(accountId);
    }

    /*
     * Helper: verifies credential rules
    */
    private boolean credentialsAreValid(String username, String password) {
        if (username.isBlank()) {
            return false;
        }

        if (password.length() < MIN_PASSWORD_LENGTH) {
            return false;
        } 

        return true;
    }

    private boolean usernameTaken(String username) {
        return accountDao.getUserByUsername(username) != null;
    }
    
}
