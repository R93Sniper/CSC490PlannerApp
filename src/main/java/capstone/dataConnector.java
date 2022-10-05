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
    //PlannerDB
    public Connection conn;
    public PreparedStatement preparedStatement;
     

     //CODE FOR PlannerDB
     /**
     * getConnectionDB: retrieve data from the database using a JDBC connector.
     */
    public void getConnectionDB() {
        try {
            String databaseURL = "jdbc:ucanaccess://.//PlannerDB.accdb";
            conn = DriverManager.getConnection(databaseURL);
        } catch (SQLException ex) {
            ;
        }
    }

    /**
     * newUserSignup: when new user sign up
     */
    public void newUserSignup(String userName, String userPassword) {
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

    /**
     * newUserProfileSignup - setting up new user profile information and storing their data.
     */
    public void newUserProfileSignup(String userName, String userPassword, String secQ1, String secQ2, String secQ3,
            String secAns1, String secAns2, String secAns3, String fullName, String height, String dob, String gender, String bodytype) {
        //call the getConnectionDB method
        getConnectionDB();
        try {
            String sql = "INSERT INTO User(userName,userPassword,secQ1,secQ2,secQ3,secAns1,secAns2,secAns3, "
                    + "fullName, height, dob, gender, bodytype) VALUES"
                    + "(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, userPassword);
            preparedStatement.setString(3, secQ1);
            preparedStatement.setString(4, secQ2);
            preparedStatement.setString(5, secQ3);
            preparedStatement.setString(6, secAns1);
            preparedStatement.setString(7, secAns2);
            preparedStatement.setString(8, secAns3);
            preparedStatement.setString(9, fullName);
            preparedStatement.setString(10, height);
            preparedStatement.setString(11, dob);
            preparedStatement.setString(12, gender);
            preparedStatement.setString(13, bodytype);

            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row inserted");
            }
        } catch (SQLException e) {
        }
    }

    /**
     * existingUserLogin: existing user login
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
     * checkSecurityAnswers - check if the user entered answers and answers stored in 
     * the database match
     * @param userName
     * @param secAns1
     * @param secAns2
     * @param secAns3
     * @return 
     */
    public boolean checkSecurityAnswers(String userName, String secAns1, String secAns2,
            String secAns3) {
        getConnectionDB();
        String ans1 = null, ans2 = null, ans3 = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM User WHERE userName = '" + userName + "'");
            while (result.next()) {
                ans1 = result.getString("secAns1");
                ans2 = result.getString("secAns2");
                ans3 = result.getString("secAns3");
            }
        } catch (SQLException except) {
        }
        if (secAns1.equals(ans1) && secAns2.equals(ans2) && secAns3.equals(ans3)) {
            System.out.println("Answered Correctly!!");
            return true;
        } else {
            System.out.println("Some answered incorrectly!!");
            return false;
        }
    }

    /**
     * forgetPassword: if user forget their password store the updated password
     * in the back-end database but before that check if the user answered all security questions
     * correctly.
     *
     * @param uName
     * @param uPswd
     * @param secAns1
     * @param secAns2
     * @param secAns3
     */
    public boolean forgetPassword(String uName, String uPswd,
            String secAns1, String secAns2, String secAns3) {
        getConnectionDB();
        boolean flag = true;
        // check if the user entered right answers
        if (existingUser(uName, secAns1, secAns2, secAns3) == flag) {
            try {
                String sql = "UPDATE User SET userPassword=? WHERE userName=?";
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, uPswd);
                preparedStatement.setString(2, uName);
                int row = preparedStatement.executeUpdate();
                if (row > 0) {
                    System.out.println("Row updated");
                    flag = true;
                }
            } catch (SQLException e) {
            }
        } else {
            flag = false;
        }
        return flag;
    }
  
     /**
     * userProgressCard: inserting user info into their progress card.
     */
    public void userProgressCard(String userName, String dateOfCard, String weight, String diet, String bodyFat) {
        getConnectionDB();

        try {
            String sql = "INSERT INTO Progresscard(userName,dateOfCard,weight,diet,bodyFat) VALUES"
                    + "(?,?,?,?,?)";
            // preparedStatement = conn.prepareStatement(sql);
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, dateOfCard);
            preparedStatement.setString(3, weight);
            preparedStatement.setString(4, diet);
            preparedStatement.setString(5, bodyFat);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row inserted");
            }
        } catch (SQLException e) {
        }
    }
     
     /**
     * updateDateOfCard: if registered user want to update their date of card entry.
     */
    public void updateDateOfCard(String uName, String dateOfCard) {
        getConnectionDB();
        try {
            String sql = "UPDATE Progresscard SET dateOfCard=? WHERE userName=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, dateOfCard);
            preparedStatement.setString(2, uName);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated");
            }
        } catch (SQLException e) {
        }
    }
    
    /**
     * updateWeight: if registered user want to update their date of card entry.
     */
  public void updateWeight(String uName, String weight) {
        getConnectionDB();
        try {
            String sql = "UPDATE Progresscard SET weight=? WHERE userName=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, weight);
            preparedStatement.setString(2, uName);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated");
            }
        } catch (SQLException e) {
        }
    }
     
    /**
     * updateWeight: if registered user want to update their date of card entry.
     */
    public void updateBodyFat(String uName, String bodyFat) {
        getConnectionDB();
        try {
            String sql = "UPDATE Progresscard SET bodyFat=? WHERE userName=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, bodyFat);
            preparedStatement.setString(2, uName);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated");
            }
        } catch (SQLException e) {
        }
    }
}
