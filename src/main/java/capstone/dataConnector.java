package capstone;

import java.math.BigInteger;

import java.nio.charset.StandardCharsets;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Connector object
 *
 * This acts as an intermediate between the database and the code All major
 * functions that called against the database backend go here This should be a
 * pure retrieval based object, no logic Logic should be handled in the classes
 * where needed
 *
 * @author Wahab Quazi, Simranjit ----------- -----------
 */
public class dataConnector {

    //PlannerDB
    protected Connection conn;
    protected PreparedStatement preparedStatement;

    //ProgressCard DB
    public PreparedStatement preparedStatement1;
    public Connection conn1 = null;

    /**
     * getConnectionDB: retrieve data from the database using a JDBC connector.
     */
    private static dataConnector instance = null;
    private final String accessConnStr = "jdbc:ucanaccess://.//SeniorProjectDB.accdb";
    private final String connectionStr = "jdbc:sqlserver://fitnessappserver.database.windows.net:1433;"
            + "database=FitnessAppDB;"
            + "user=alvaj29@fitnessappserver;"
            + "password=Seniorproject1;"
            + "encrypt=true;"
            + "trustServerCertificate=false;"
            + "hostNameInCertificate=*.database.windows.net;"
            + "loginTimeout=30;";

    protected dataConnector() {
        getConnectionDB();
    }

    public static dataConnector getInstance() {
        if (instance == null) {
            instance = new dataConnector();
        }
        return instance;
    }

    private void getConnectionDB() {
        try {
            conn = DriverManager.getConnection(accessConnStr);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void closeConnectionDB() throws SQLException {
        conn.close();
    }

    /**
     * newUserSignup: when new user sign up
     *
     * @param userName
     * @param userPassword
     */
    public void newUserSignup(String userName, String userPassword) throws NoSuchAlgorithmException {
        //call the getConnectionDB method
        //getConnectionDB();

        String tableName = "User_Profile";
        String hashedPass = returnHashPassword(userPassword);
        try {
            String sql = "INSERT INTO " + tableName + "(User_Name, User_Password) VALUES"
                    + "(?, ?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, hashedPass);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("**NEW USER inserted into DB");
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
    public boolean existingUser(String userName) {

        String tableName = "User_Profile";
        String uName = null, uPswd = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE User_Name = \'" + userName + "\'");

            while (result.next()) {
                uName = result.getString("User_Name");
                //uPswd = result.getString("User_Password");
            }
        } catch (SQLException except) {
            except.printStackTrace();
        }
        return userName.equals(uName);
    }

    /**
     * verifiedUserInstance: verified if user login info is correct
     *
     * @param uName
     * @param uPswd
     * @return
     */
    public boolean verifiedUserInstance(String uName, String uPswd) {

        String tableName = "User_Profile";
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM " + tableName
                    + " where User_Name=\'" + uName + "\'"
                    + " AND User_Password=\'" + uPswd + "\'");
            while (resultSet.next()) {
                String name = resultSet.getString("User_Name");
                String password = resultSet.getString("User_Password");
                if (uName.equals(name) && uPswd.equals(password)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * checkSecurityAnswers - check if the user entered answers and answers
     * stored in the database match
     *
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
     * in the back-end database but before that check if the user answered all
     * security questions correctly.
     *
     * @param uName
     * @param uPswd
     * @param secAns1
     * @param secAns2
     * @param secAns3
     */
    public void forgetPassword(String uName, String uPswd,
            String secAns1, String secAns2, String secAns3) throws NoSuchAlgorithmException {
        getConnectionDB();

        // check if the user entered right answers
        String hashedPass = returnHashPassword(uPswd);
        if (checkSecurityAnswers(uName, secAns1, secAns2, secAns3) == true) {
            try {
                String sql = "UPDATE User SET userPassword=? WHERE userName=?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, hashedPass);
                preparedStatement.setString(2, uName);
                int row = preparedStatement.executeUpdate();
                if (row > 0) {
                    System.out.println("Row updated");
                }
            } catch (SQLException e) {
            }
        } else {
            System.out.println("Error!!");
        }
    }

    public ResultSet getResult(String userName, String tableName) {

        ResultSet result = null;
        try {
            Statement stmt = conn.createStatement();
            result = stmt.executeQuery("select * from " + tableName
                    + " where User_Name=\'" + userName + "\'");
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;

    }

    public boolean updateColumn(String tableName, String userName, String newStr, String col) {
        try {
            String sql = "UPDATE" + tableName 
                    + " SET " + col + " = \'" + newStr + "\' WHERE User_Name=\'" + userName + "\'";

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(dataConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public String getSecq(int id) {
        String tableName = "Security_Questions";
        ResultSet result = null;
        String returnStr = "";
        try {
            Statement stmt = conn.createStatement();
            result = stmt.executeQuery("select * from " + tableName
                    + " where SecQ_id= " + id);
            while (result.next()) {
                returnStr = result.getString("SecQ_desc");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return returnStr;
    }

    public ResultSet getAllSecQs() {

        ResultSet result = null;
        try {
            Statement stmt = conn.createStatement();
            result = stmt.executeQuery("select * from " + "Security_Questions");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;

    }

    public boolean updateUserSecQID(String userName, int qId, String col) {
        //String column = col.toString();
        try {
            String sql = "UPDATE " + "User_Profile"
                    + " SET " + col + " = " + qId + " WHERE User_Name=\'" + userName + "\'";

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(dataConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * takes the user password and hashes it using SHA-256 bit hashing, a one
     * way hash function for security
     *
     * @param password
     * @return
     */
    public String returnHashPassword(String password) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] messageDigest = md.digest(password.getBytes());

        BigInteger no = new BigInteger(1, messageDigest);

        String hashPassword = no.toString(16);

        while (hashPassword.length() < 32) {
            hashPassword = "0" + hashPassword;
        }
        System.out.println(hashPassword + "------");

        return hashPassword;
    }

    public int getLastRow(String tableName) {
        int lastRowNum = -1;
        //String tableName = "Daily_Intake_Cards";
        ResultSet result = null;
        try {
            Statement stmt = conn.createStatement();
            result = stmt.executeQuery("SELECT * FROM " + tableName);

            while (result.next()) {
                lastRowNum = result.getInt("ID");
                //System.out.println("RowId= "+rowNum);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastRowNum;
    }

    //MEDICAL-CONDITION
    public String getMedicalCondition(String userName) {
        String tableName = "Medical_Condition";
        ResultSet result = null;
        String returnStr = "";
        try {
            Statement stmt = conn.createStatement();
            result = stmt.executeQuery("select * from " + tableName
                    + " where Medical_Conditions_ids= " + userName);
            while (result.next()) {
                returnStr = result.getString("Medical_Conditions_ids");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return returnStr;
    }

    public boolean updateUserMedicalCOnditionID(String userName, int medicalId, String col) {
        try {
            String sql = "UPDATE " + "User_Profile"
                    + " SET " + col + " = " + medicalId + " WHERE User_Name=\'" + userName + "\'";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(dataConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //USER-GOALS
    public String getUserGoals(String userName) {
        String tableName = "User_Goals";
        ResultSet result = null;
        String returnStr = "";
        try {
            Statement stmt = conn.createStatement();
            result = stmt.executeQuery("select * from " + tableName
                    + " where Goals_ids= " + userName);
            while (result.next()) {
                returnStr = result.getString("Goals_ids");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return returnStr;
    }

    public boolean updateUserGoalsID(String userName, int goalId, String col) {
        try {
            String sql = "UPDATE " + "User_Goals"
                    + " SET " + col + " = " + goalId + " WHERE User_Name=\'" + userName + "\'";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(dataConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}

enum DB_Col {
    User_Name,
    User_Password,
    First_Name,
    Last_Name,
    Email,
    Body_Type,
    Address,
    Gender,
    Height,
    Date_Of_Birth,
    SecurityQ1_id,
    SecurityQ2_id,
    SecurityQ3_id,
    SecurityA1,
    SecurityA2,
    SecurityA3,
    DateOfCard,
    Weight,
    BodyMeasurement,
    Intake_ID,
    Excercise_ID,
    MedicalCondition_ID
}
