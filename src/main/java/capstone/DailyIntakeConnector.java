/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.sql.SQLException;

/**
 *
 * @author simra
 */
public class DailyIntakeConnector extends dataConnector {

    public void userDailyIntake(int foodLogID, String caloriesTotal, String carbsTotal, String proteinTotal,
            String fatsTotal) {
        try {
            String sql = "INSERT INTO Daily_Intake_Cards(Foo_Log_Ids, Calories_Total, Carbs_Total,"
                    + "Protein_Total, Fats_Total) VALUES"
                    + "(?,?,?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, foodLogID);
            preparedStatement.setString(2, caloriesTotal);
            preparedStatement.setString(3, carbsTotal);
            preparedStatement.setString(4, proteinTotal);
            preparedStatement.setString(5, fatsTotal);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row inserted");
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
    public void updateCaloriesTotal(int foodLogID, String caloriesTotal) {
        try {
            String sql = "UPDATE Daily_Intake_Cards SET Calories_Total=? WHERE Foo_Log_Ids=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, caloriesTotal);
            preparedStatement.setInt(2, foodLogID);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated");
            }
        } catch (SQLException e) {
        }
    }

    public void updateCarbsTotal(int foodLogID, String carbsTotal) {
        try {
            String sql = "UPDATE Daily_Intake_Cards SET Carbs_Total=? WHERE Foo_Log_Ids=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, carbsTotal);
            preparedStatement.setInt(2, foodLogID);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated");
            }
        } catch (SQLException e) {
        }
    }

    public void updateProteinTotal(int foodLogID, String proteinTotal) {
        try {
            String sql = "UPDATE Daily_Intake_Cards SET Protein_Total=? WHERE Foo_Log_Ids=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, proteinTotal);
            preparedStatement.setInt(2, foodLogID);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated");
            }
        } catch (SQLException e) {
        }
    }

    public void updateFatsTotal(int foodLogID, String fatsTotal) {
        try {
            String sql = "UPDATE Daily_Intake_Cards SET Fats_Total=? WHERE Foo_Log_Ids=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, fatsTotal);
            preparedStatement.setInt(2, foodLogID);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated");
            }
        } catch (SQLException e) {
        }
    }
}
