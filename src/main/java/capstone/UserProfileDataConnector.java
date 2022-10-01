/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *this is a zombie class, no longer used. It has been replaced by dataConnector class
 * @author jesus
 */
public class UserProfileDataConnector {

    private static UserProfileDataConnector instance = null;
    Connection conn = null;

    private UserProfileDataConnector() {
        setUpConnection();
    }

    public static UserProfileDataConnector getInstance() {
        if (instance == null) {
            instance = new UserProfileDataConnector();
        }

        return instance;
    }

    private void setUpConnection() {
        System.out.println("User Acounts Database connected");
        String databaseURL = "";

        try {
            databaseURL = "jdbc:ucanaccess://.//UserAccounts.accdb";
            conn = DriverManager.getConnection(databaseURL);
        } catch (SQLException ex) {
            Logger.getLogger(UserProfileDataConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertAccountToDB(String userName, String pw, String fullName) {
        //first check if valid inputs...

        try {
            String sql = "INSERT INTO Accounts (UserName, Password, FullName) VALUES (?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, pw);
            preparedStatement.setString(3, fullName);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("row inserted to Accounts table");
            } else {
                System.out.println("failed to add");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            //System.out.println("excepption caught..,.");
        }

    }

    public ResultSet getResult(String userName) {

        System.out.println("query data:");
        ResultSet result = null;
        try {
            String tableName = "Accounts";
            //String x = "jesus alvarado"; 
            Statement stmt = conn.createStatement();
            result = stmt.executeQuery("select * from " + tableName
                    + " where UserName=\'" + userName + "\'");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;

    }

    //method used to validate entered username and password
    public Boolean isValidUser(String userName, String pw) {
        System.out.println("query data:" + userName);
        ResultSet result = null;
        boolean found = false;
        try {
            String tableName = "Accounts";
            Statement stmt = conn.createStatement();
            result = stmt.executeQuery("select * from " + tableName
                    + " where UserName=\'" + userName + "\'"
                    + " AND Password=\'" + pw + "\'");

            if (result.next()) {
                found = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return found;
    }

    //method to check if the userName already exists in DB
    public Boolean userFound(String userName) {
        System.out.println("query data:" + userName);
        ResultSet result = null;
        boolean found = false;
        try {
            String tableName = "Accounts";
            Statement stmt = conn.createStatement();
            result = stmt.executeQuery("select * from " + tableName
                    + " where UserName=\'" + userName + "\'");
            if (result.next()) {
                found = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return found;
    }

    public boolean updateEmail(String userName, String email) {

        //ResultSet result=null;   
        try {
            String sql = "UPDATE Accounts "
                    + "SET Email = \'" + email + "\' WHERE UserName=\'" + userName + "\'";
            String tableName = "Accounts";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UserProfileDataConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
    
        
        public boolean updateColumn(String userName, String newStr, DBColumn col) {
        String column = "";    
        if(col == DBColumn.EMAIL)
            column = "Email";
        if(col == DBColumn.FIRSTNAME)
            column = "FullName";
        if(col == DBColumn.ADDRESS)
            column = "Address";
         if(col == DBColumn.PHONENUMBER)
            column = "PhoneNumber";
  
        try {
            String sql = "UPDATE Accounts "
                    + "SET "+column+" = \'" + newStr + "\' WHERE UserName=\'" + userName + "\'";
           
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UserProfileDataConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

}

