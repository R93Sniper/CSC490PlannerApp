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
 * @author jesus and Simranjit
 */
public class ProgressCardConnector extends dataConnector {

    public void userProgressCard(String userName, String dateOfCard, String weight, int intakeID,
            int excerciseID, String neckInches, String waistInches) {
        try {
            String sql = "INSERT INTO Progress_Cards(User_Name, Date_Of_Card, Weight,"
                    + "Daily_Intake_Id, Daily_Exercise_Id,Neck_Inches,Waist_Inches) VALUES"
                    + "(?,?,?,?,?,?,?)";
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
                System.out.println("Row inserted into Progress Card Table");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            String sql = "UPDATE Progress_cards SET Date_Of_Card=? WHERE User_Name=?";
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
    public void updateWeight(int id, String weight) {
        try {
            String sql = "UPDATE Progress_cards SET Weight=? WHERE ID=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, weight);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated for WEIGHT in Progress_Card Table");
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
            String sql = "UPDATE Progress_cards SET Neck_Inches=? WHERE User_Name=?";
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
            String sql = "UPDATE Progress_cards SET Waist_Inches=? WHERE User_Name=?";
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

    public int getProgressID(String userName, String targetDate) {

        String tableName = "Progress_Cards";
        //String uName = null, uPswd = null;
        int id = -1;
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE User_Name = \'" + userName + "\' AND "
                    + "Date_Of_Card = \'" + targetDate + "\' ;");

            while (result.next()) {
                id = result.getInt("ID");
                //uPswd = result.getString("User_Password");
            }
        } catch (SQLException except) {
            except.printStackTrace();
        }
        return id;
    }

    public int getDailyIntakeID(int pID) {

        String tableName = "Progress_Cards";
        int id = -1;
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE ID = " + pID + " ;");

            while (result.next()) {
                id = result.getInt("Daily_Intake_Id");
            }
        } catch (SQLException except) {
            except.printStackTrace();
        }
        return id;
    }

    public int getWeight(int id) {

        String tableName = "Progress_Cards";
        int w = -1;
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE ID = " + id + " ;");

            while (result.next()) {
                w = result.getInt("Weight");
            }
        } catch (SQLException except) {
            except.printStackTrace();
        }
        return w;
    }

    public void updateDailyIntakeID(int progressID, int intakeID) {
        try {
            String sql = "UPDATE Progress_cards SET Daily_Intake_Id=? WHERE ID=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, intakeID);
            preparedStatement.setInt(2, progressID);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated for Daily_Intake_Id in Progress_Card Table");
            }
        } catch (SQLException e) {
        }
    }

}
