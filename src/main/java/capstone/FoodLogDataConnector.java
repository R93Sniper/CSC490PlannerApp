/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author jesus
 */
public class FoodLogDataConnector extends dataConnector{
    
    



    //method to add food data FoodLog table in the database
    public void insertNewFoodLog(String name, double cal, double carb, double fats, double protein, double serving){
    String tableName = "FoodLog";
        try {
            String sql = "INSERT INTO " + tableName + "(Food_Name, Calories, Carbs, Fats, Protein, Serving_Size) VALUES"
                    + "(?, ?, ?, ?, ?, ?)";

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setDouble(2, cal);
            preparedStatement.setDouble(3, carb);
            preparedStatement.setDouble(4, fats);
            preparedStatement.setDouble(5, protein);
            preparedStatement.setDouble(6, serving);
            int row = preparedStatement.executeUpdate();

            if (row > 0) {
                System.out.println("**NEW food log inserted into DB");
            }
        } catch (SQLException e) {
        }
    }
    
    public ResultSet getFoodLogRow(int id){
        
        System.out.println("query data:");
        ResultSet result = null;
        try {
            Statement stmt = conn.createStatement();
            result =  stmt.executeQuery("select * from FoodLog" 
                    + " where ID=" + id +";");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;

    }
    
    
    
}
