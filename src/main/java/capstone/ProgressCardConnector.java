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
public class ProgressCardConnector extends dataConnector{
    
    
    
     //CODE FOR ProgressCard DB
     /**
     * userProgressCard: inserting user info into their progress card.
     */
    public void userProgressCard(String userName, String dateOfCard, String weight, String bodyMeasurement, int intakeID, int excerciseID) {
        
        try {
            String sql = "INSERT INTO Progresscard(User_Name, DateOfCard, Weight, BodyMeasurement,"
                    + "Intake_ID, Excercise_ID) VALUES"
                    + "(?,?,?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, dateOfCard);
            preparedStatement.setString(3, weight);
            preparedStatement.setString(4, bodyMeasurement);
            preparedStatement.setInt(5, intakeID);
            preparedStatement.setInt(6, excerciseID);
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
            String sql = "UPDATE Progresscard SET DateOfCard=? WHERE userName=?";
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
