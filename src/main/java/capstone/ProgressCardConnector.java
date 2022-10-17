/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.sql.SQLException;

/**
 *
 * @author jesus
 */
public class ProgressCardConnector extends dataConnector {

    public void userProgressCard(String userName, String dateOfCard, String weight, int intakeID,
            int excerciseID, String neckInches, String waistInches) {
        try {
            String sql = "INSERT INTO Progress_Cards(User_Name, Date_Of_Card, Weight,"
                    + "Daily_Intake_Id, Daily_Excercise_ID,Neck_Inches,Waist_Inches) VALUES"
                    + "(?,?,?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, dateOfCard);
            preparedStatement.setString(3, weight);
            preparedStatement.setInt(4, intakeID);
            preparedStatement.setInt(5, excerciseID);
            preparedStatement.setString(6, neckInches);
            preparedStatement.setString(7, waistInches);
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
    public void updateDateOfCard(String uName, String dateOfCard) {
        try {
            String sql = "UPDATE Progress_cards SET DateOfCard=? WHERE userName=?";
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
        try {
            String sql = "UPDATE Progress_cards SET weight=? WHERE userName=?";
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
     * updateNeckInches
     *
     * @param uName
     * @param neckInches
     */
    public void updateNeckInches(String uName, String neckInches) {
        try {
            String sql = "UPDATE Progress_cards SET Neck_Inches=? WHERE userName=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, neckInches);
            preparedStatement.setString(2, uName);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated");
            }
        } catch (SQLException e) {
        }
    }

    /**
     * updateWaistInches
     *
     * @param uName
     * @param waistInches
     */
    public void updateWaistInches(String uName, String waistInches) {
        try {
            String sql = "UPDATE Progress_cards SET Waist_Inches=? WHERE userName=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, waistInches);
            preparedStatement.setString(2, uName);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated");
            }
        } catch (SQLException e) {
        }
    }
}
