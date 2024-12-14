package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDao {

    /*
     * @param Account with username and password
     * @return Account (id, username, password) for a new user
    */
    public Account registerNewUser(Account account) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?);";

            PreparedStatement ps = connection.prepareStatement(sql);
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
            ps.executeQuery();

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
     * @param String username to find in database
     * @return true if in database, false otherwise
    */
    public boolean usernameTaken(String username) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT username FROM account WHERE username = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.executeQuery();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return false;
    }
    
}
