package capstone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * Data Connector object
 *
 * This acts as an intermediate between the database and the code
 * All major functions that called against the database backend go here
 * This should be a pure retrieval based object, no logic
 * Logic should be handled in the classes where needed
 *
 * @author Wahab Quazi, Simranjit
 *         -----------
 *         -----------
 */
public class dataConnector {

    private Connection conn;
 
     /**
     * getConnectionDB: retrieve data from the database using a JDBC connector.
     */
    public void getConnectionDB() {
        try {
            String databaseURL = "jdbc:ucanaccess://.//UserAccounts.accdb";
            conn = DriverManager.getConnection(databaseURL);
        } catch (SQLException ex) {
            ;
        }
    }

    public Connection getConnectionDBoutside() {
        try {
            String databaseURL = "jdbc:ucanaccess://.//UserAccounts.accdb";
            conn = DriverManager.getConnection(databaseURL);
        } catch (SQLException ex) {
            ;
        }
        
        return conn;
    }
    /**
     * newUserSignup: when new user sign up
     *
     * @param userName
     * @param userPassword
     */
    public  void newUserSignup(String userName, String userPassword) {
        //call the getConnectionDB method
        getConnectionDB();
        try {
            String sql = "INSERT INTO User(userName,userPassword) VALUES"
                    + "(?, ?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, userPassword);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row inserted");
            }
        } catch (SQLException e) {
        }
    }

    public void newUserProfile(String userName, String userPassword, String fullName, String address, String phoneNumber,
            String dob, String gender, int height, float weight, String bodytype) {
        //call the getConnectionDB method
        getConnectionDB();
        try {
            String sql = "INSERT INTO User(userName,userPassword,fullName,address, phoneNumber,dob,"
                    + "gender,height,weight,bodytype) VALUES"
                    + "(?, ?,?,?,?,?,?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, userPassword);
            preparedStatement.setString(3, fullName);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, phoneNumber);
            preparedStatement.setString(6, dob);
            preparedStatement.setString(7, gender);
            preparedStatement.setInt(8, height);
            preparedStatement.setFloat(9, weight);
            preparedStatement.setString(10, bodytype);
            
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row inserted");
            }
        } catch (SQLException e) {
        }
    }

    /**
     * existingUserLogin: existing user login
     *
     * @param userName
     * @param userPassword
     * @return
     */
    public boolean existingUserLogin(String userName, String userPassword) {
        getConnectionDB();
        String uName = null, uPswd = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM User WHERE userName = '" + userName + "'");
            while (result.next()) {
                uName = result.getString("userName");
                uPswd = result.getString("userPassword");
            }
        } catch (SQLException except) {
        }
        return (userName.equals(uName) && userPassword.equals(uPswd));
    }

    /**
     * verifiedUserInstance: verified if user login info is correct
     *
     * @param uName
     * @param uPswd
     * @return
     */
    public String verifiedUserInstance(String uName, String uPswd) {
        getConnectionDB();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT userName, userPassword FROM User");
            while (resultSet.next()) {
                String name = resultSet.getString("userName");
                String password = resultSet.getString("userPassword");
                if (existingUserLogin(uName, uPswd) == true) {
                    return "Login Successfully";
                } else {
                    return "INFORMATION PROVIDED IS INCORRECT.";
                }
            }
        } catch (SQLException e) {
        }
        return "";
    }

    /**
     * forgetPassword: if user forget their password store the updated password
     * in the back-end database
     *
     * @param uName
     * @param uPswd
     */
    public void forgetPassword(String uName, String uPswd) {
        getConnectionDB();
        try {
            String sql = "UPDATE User SET userPassword=? WHERE userName=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, uPswd);
            preparedStatement.setString(2, uName);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated");
            }
        } catch (SQLException e) {
        }
    }
}
