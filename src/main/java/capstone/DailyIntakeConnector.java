/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Simranjit
 */
public class DailyIntakeConnector extends dataConnector {

    public void userDailyIntake(String foodLogIds, String caloriesTotal, String carbsTotal, String proteinTotal,
            String fatsTotal) {
        try {
            String sql = "INSERT INTO Daily_Intake_Cards(Food_Log_Ids, Calories_Total, Carbs_Total,"
                    + "Protein_Total, Fats_Total) VALUES"
                    + "(?,?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, foodLogIds);
            preparedStatement.setString(2, caloriesTotal);
            preparedStatement.setString(3, carbsTotal);
            preparedStatement.setString(4, proteinTotal);
            preparedStatement.setString(5, fatsTotal);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row inserted into Daily Intake Cards Table");
            }
        } catch (SQLException e) {
        }
    }

    /**
     * updateDateOfCard: if registered user want to update their date of card
     *
     * @param uName
     * @param dateOfCard
     */
    public void updateCaloriesTotal(int dailyIntakeID, String caloriesTotal) {
        try {
            String sql = "UPDATE Daily_Intake_Cards SET Calories_Total=? WHERE ID=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, caloriesTotal);
            preparedStatement.setInt(2, dailyIntakeID);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated for Calories_Total in DailyIntake Table");
            }
        } catch (SQLException e) {
        }
    }

    public void updateCarbsTotal(int dailyIntakeID, String carbsTotal) {
        try {
            String sql = "UPDATE Daily_Intake_Cards SET Carbs_Total=? WHERE ID=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, carbsTotal);
            preparedStatement.setInt(2, dailyIntakeID);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated for Carbs_Total in DailyIntake Table");
            }
        } catch (SQLException e) {
        }
    }

    public void updateProteinTotal(int dailyIntakeID, String proteinTotal) {
        try {
            String sql = "UPDATE Daily_Intake_Cards SET Protein_Total=? WHERE ID=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, proteinTotal);
            preparedStatement.setInt(2, dailyIntakeID);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated for Protein_Total in DailyIntake Table");
            }
        } catch (SQLException e) {
        }
    }

    public void updateFatsTotal(int dailyIntakeID, String fatsTotal) {
        try {
            String sql = "UPDATE Daily_Intake_Cards SET Fats_Total=? WHERE ID=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, fatsTotal);
            preparedStatement.setInt(2, dailyIntakeID);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated for Fats_Total in DailyIntake Table");
            }
        } catch (SQLException e) {
        }
    }
    
    public void updateFoodLogIds(int dailyIntakeID, String foodLogIds){
    try {
            String sql = "UPDATE Daily_Intake_Cards SET Food_Log_Ids=? WHERE ID=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, foodLogIds);
            preparedStatement.setInt(2, dailyIntakeID);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated for Food_Log_Ids in DailyIntake Table");
            }
        } catch (SQLException e) {
        }
    }

}
