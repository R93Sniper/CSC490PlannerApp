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

     /**
     * getConnectionDB: retrieve data from the database using a JDBC connector.
     */
    
    public Connection conn;
    private static dataConnector instance = null;
    
    private final String connectionStr = "jdbc:sqlserver://fitnessappserver.database.windows.net:1433;"
            +"database=FitnessAppDB;"
            + "user=alvaj29@fitnessappserver;"
            + "password=Seniorproject1;"
            + "encrypt=true;"
            + "trustServerCertificate=false;"
            + "hostNameInCertificate=*.database.windows.net;"
            + "loginTimeout=30;";
    
    
    private dataConnector(){
        getConnectionDB();
    }
    
    public static dataConnector getInstance(){
        if(instance == null)
            instance = new dataConnector();
        
        return instance;
    }
    
    
    
    private void getConnectionDB() {
        try {
            //String databaseURL = "jdbc:ucanaccess://.//PlannerDB.accdb";
            conn = DriverManager.getConnection(connectionStr);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * newUserSignup: when new user sign up
     *
     * @param userName
     * @param userPassword
     */
    public void newUserSignup(String userName, String userPassword) {
        //call the getConnectionDB method
        //getConnectionDB();
         String tableName = "User_Profile";
        try {
            String sql = "INSERT INTO "+tableName+"(User_Name, User_Password) VALUES"
                    + "(?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, userPassword);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row inserted into DB");
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
        //getConnectionDB();
        try {
            String sql = "INSERT INTO User(userName,userPassword,secQ1,secQ2,secQ3,secAns1,secAns2,secAns3, "
                    + "fullName, height, dob, gender, bodytype) VALUES"
                    + "(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
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
     *
     * @param userName
     * @param userPassword
     * @return
     */
    public boolean existingUser(String userName) {
        //getConnectionDB();
        String tableName = "User_Profile";
        String uName = null, uPswd = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM "+tableName+ " WHERE User_Name = \'" + userName + "\'");
            
            while (result.next()) {
                uName = result.getString("User_Name");
                //uPswd = result.getString("User_Password");
            }
        } catch (SQLException except) {
            except.printStackTrace();
        }
        return userName.equals(uName) ;
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
    public void forgetPassword(String uName, String uPswd,
            String secAns1, String secAns2, String secAns3) {
        getConnectionDB();

        // check if the user entered right answers
        if (checkSecurityAnswers(uName, secAns1, secAns2, secAns3) == true) {
            try {
                String sql = "UPDATE User SET userPassword=? WHERE userName=?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, uPswd);
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

        System.out.println("query data:");
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
     
     public boolean updateColumn(String tableName, String userName, String newStr, DBColumn col) {
        String column = "";    
        if(col == DBColumn.EMAIL)
            column = "Email";
        if(col == DBColumn.FIRSTNAME)
            column = "First_Name";
        if(col == DBColumn.LASTNAME)
            column = "Last_Name";
        if(col == DBColumn.ADDRESS)
            column = "Address";
        if(col == DBColumn.PHONENUMBER)
            column = "PhoneNumber";
        if(col == DBColumn.GENDER)
            column = "Gender";
        
        if(col == DBColumn.HEIGHT)
            column = "Height";
  
        try {
            String sql = "UPDATE "+ tableName
                    + " SET "+column+" = \'" + newStr + "\' WHERE User_Name=\'" + userName + "\'";
           
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UserProfileDataConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
     
     
     public String getSecq(int id){
        String tableName = "Security_Questions";
        ResultSet result = null;
        String returnStr = "";
        try {
            Statement stmt = conn.createStatement();
            result = stmt.executeQuery("select * from " + tableName
                    + " where SecQ_id= " + id );
            while (result.next()) {
                returnStr = result.getString("SecQ_desc");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
     
         return returnStr;
     }
     
     
 } 

    enum DBColumn{
    FIRSTNAME,
    LASTNAME,
    EMAIL,
    PHONENUMBER,
    ADDRESS,
    GENDER,
    HEIGHT

}
    
