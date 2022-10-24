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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jesus
 */
public class FoodLogDataConnector extends dataConnector {

    public static void main(String[] args) {
        FoodLogDataConnector fc = new FoodLogDataConnector();
        int num = fc.getLastRow();
        System.out.println("last row = " + num);
    }

    //method to add food data FoodLog table in the database
    public void insertNewFoodLog(String name, String cal, String carb, String fats, String protein, String serving) {
        String tableName = "FoodLog";
        try {
            String sql = "INSERT INTO " + tableName + "(Food_Name, Calories, Carbs, Fats, Protein, Serving_Size) VALUES"
                    + "(?, ?, ?, ?, ?, ?)";

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, cal);
            preparedStatement.setString(3, carb);
            preparedStatement.setString(4, fats);
            preparedStatement.setString(5, protein);
            preparedStatement.setString(6, serving);
            int row = preparedStatement.executeUpdate();

            if (row > 0) {
                System.out.println("**NEW food log inserted into DB");
            }
        } catch (SQLException e) {
        }
    }

    //returns the row of the food item from our Food Log Data
    public FoodItem getFoodLogRow(int id) {

        System.out.println("query data:");
        ResultSet result = null;
        FoodItem item = null;
        try {
            Statement stmt = conn.createStatement();
            result = stmt.executeQuery("select * from FoodLog"
                    + " where ID=" + id + ";");
            
            while(result.next()){
            String name = result.getString("Food_Name");
            String calories = result.getString("Calories");
            String carbs = result.getString("Carbs");
            String fats= result.getString("Fats");
            String protein = result.getString("Protein");
            String servings = result.getString("Serving_Size");
            item = new FoodItem();
            item.setName(name);
            item.setCalories(Double.valueOf(calories));
            item.setCarbs(Double.valueOf(carbs));
            item.setFats(Double.valueOf(fats));
            item.setProtein(Double.valueOf(protein));
            item.setServingSize(Double.valueOf(servings));
              
            }

        } catch (SQLException ex) {
        }
        return item;

    }

    public boolean updateFoodLogData(int id, String colName, String val) {

        try {
            String sql = "UPDATE " + "FoodLog"
                    + " SET " + colName + " = \'" + val + "\' WHERE ID= " + id + ";";

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(dataConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;

    }

    public int getLastRow() {
        int lastRowNum = 0;
        String tableName = "FoodLog";
        ResultSet result = null;
        try {
            Statement stmt = conn.createStatement();
            result = stmt.executeQuery("SELECT * FROM " + tableName);

            while (result.next()) {
                lastRowNum = result.getInt("ID");
                //System.out.println("RowId= "+rowNum);
            }

        } catch (SQLException e) {
        }
        return lastRowNum;
    }
}
