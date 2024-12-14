package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDao {

    /*
     * Add a new user to the database.
     * @param Account with username and password
     * @return Account (id, username, password) for a new user
    */
    public Account registerNewUser(Account account) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?);";

            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int account_id = rs.getInt(1);
                return new Account(account_id, account.getUsername(), account.getPassword());
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }

    /*
     * Query the database for an account that matches Account(username, password).
     * @param Account with username and password
     * @return Account (id, username, password) if user already in database
    */
    public Account verifyExistingUser(Account account) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = 
            "SELECT account_id, username, password " +
            "FROM account " +
            "WHERE username = ? AND password = ?;";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int account_id = rs.getInt(1);
                return new Account(account_id, account.getUsername(), account.getPassword());
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return null;
    }

    /*
     * Query the database for a user by username.
     * @param String username to find in database
     * @return Account(id, username) if in database, null otherwise
    */
    public Account getUserByUsername(String username) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT account_id, username FROM account WHERE username = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int account_id = rs.getInt(1);
                return new Account(account_id, username, null);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return null;
    }

    /*
     * Query the database for a user by the user ID.
     * @param int user ID to find in database
     * @return Account(id, username) if in database, null otherwise
    */
    public Account getUserByUserId(int userId) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT account_id, username FROM account WHERE account_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String username = rs.getString(2);
                return new Account(userId, username, null);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return null;
    }
    
}
