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
 *
 * @author jesus
 */
public class UserProfileDataConnector {
    
    private static UserProfileDataConnector instance = null;
    Connection conn = null;
    
    private UserProfileDataConnector(){
        setUpConnection();
    }
    
    
    public static UserProfileDataConnector getInstance(){
        if(instance == null)
            instance = new UserProfileDataConnector();
        
        return instance;
    }
    
     private void setUpConnection(){
        System.out.println("User Acounts Database connected");
        String databaseURL = "";
       
        try {
            databaseURL = "jdbc:ucanaccess://.//UserAccounts.accdb";
            conn = DriverManager.getConnection(databaseURL);
        } catch (SQLException ex) {
            Logger.getLogger(UserProfileDataConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     
     
     public void insertAccountToDB(String userName, String pw, String fullName){
     //first check if valid inputs...
     
     try{
         String sql = "INSERT INTO Accounts (UserName, Password, FullName) VALUES (?,?,?)";
         PreparedStatement preparedStatement = conn.prepareStatement(sql);
         preparedStatement.setString(1,userName);
         preparedStatement.setString(2,pw);
         preparedStatement.setString(3,fullName);
         int row = preparedStatement.executeUpdate();
         if(row > 0)
         {
           System.out.println("row inserted to Accounts table");
         }
         else{
             System.out.println("failed to add");
         }

     }catch(SQLException ex){ 
          ex.printStackTrace();
          //System.out.println("excepption caught..,.");
     }
     
     
     }

    public ResultSet getResult(String userName){
           
            System.out.println("query data:");
            ResultSet result=null;
           try{
               String tableName="Accounts";
               //String x = "jesus alvarado"; 
               Statement stmt = conn.createStatement();
               result = stmt.executeQuery("select * from "+ tableName 
                       + " where UserName=\'"+ userName+ "\'");
               
               /*
               while (result.next()) 
                int id = result.getInt("ID");
                String name = result.getString("UserFullName");
                //double salary = result.getFloat("Salary");

                System.out.printf("%d %s \n", id, name);
                    */
           }catch(SQLException ex){
               ex.printStackTrace();
           }
           return result;
           
       } 
    
     public Boolean userFound(String userName){
           
            System.out.println("query data:" + userName);
            ResultSet result=null;
            boolean found = false;
           try{
               String tableName="Accounts";
               Statement stmt = conn.createStatement();
               result = stmt.executeQuery("select * from "+ tableName 
                       + " where UserName=\'"+ userName+ "\'");
               //found = result.first();
               if(result.next())
                   found = true;
         
           }catch(SQLException ex){
               ex.printStackTrace();
           }
           
           return found;
       } 
  
    
    
}
