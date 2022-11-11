/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
                System.out.println("Row ");
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

    public int getDailyExerciseID(int pID) {

        String tableName = "Progress_Cards";
        int id = -1;
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE ID = " + pID + " ;");

            while (result.next()) {
                id = result.getInt("Daily_Exercise_Id");
            }
        } catch (SQLException except) {
            except.printStackTrace();
        }
        return id;
    }

    public double getWeight(int id) {

        String tableName = "Progress_Cards";
        double w = -1;
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE ID = " + id + " ;");

            while (result.next()) {
                w = result.getDouble("Weight");
            }
        } catch (SQLException except) {
            except.printStackTrace();
        }
        return w;
    }

    public String getDateOfCard(int id) {

        String tableName = "Progress_Cards";
        String date = "";

        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE ID = " + id + " ;");

            while (result.next()) {
                date = result.getString("Date_Of_Card");
            }
        } catch (SQLException except) {
            except.printStackTrace();
        }
        return date;
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

    public void updateDailyExerciseID(int progressID, int exID) {
        try {
            String sql = "UPDATE Progress_cards SET Daily_Exercise_Id=? WHERE ID=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, exID);
            preparedStatement.setInt(2, progressID);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated for Daily_Exercise_Id in Progress_Card Table");
            }
        } catch (SQLException e) {
        }
    }

    /*
    * return an integer arraylist of a User's progress ids  in ascending order by dateOfCard 
     */
    public ArrayList<Integer> getAllProgressIds(String userName) {

        String tableName = "Progress_Cards";
        ArrayList<Integer> ids = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE User_Name = \'" + userName + "\' ORDER BY Date_Of_Card ASC;");
            while (result.next()) {
                ids.add(result.getInt("ID"));
            }
        } catch (SQLException except) {
            except.printStackTrace();
        }
        return ids;

    }

    /*
    * return an integer arraylist of a User's progress ids for cards between the given start and end dates
    *in ascending order by dateOfCard 
     */
    public ArrayList<Integer> getAllProgressIds(String userName, String startDate, String endDate) {
        String tableName = "Progress_Cards";
        ArrayList<Integer> ids = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE User_Name = \'" + userName + "\'"
                    + " AND Date_Of_Card >= \'" + startDate + "\' AND Date_Of_Card <= \'" + endDate + "\'"
                    + " ORDER BY Date_Of_Card ASC;");
            while (result.next()) {
                ids.add(result.getInt("ID"));
            }
        } catch (SQLException except) {
            except.printStackTrace();
        }
        return ids;

    }

    public static void main(String[] args) {

        ProgressCardConnector pc = new ProgressCardConnector();

        ArrayList<Integer> ans = pc.getAllProgressIds("testUser", "5/12/2022", "6/11/2022");
        String str = "";
        for (Integer i : ans) {
            str += String.valueOf(i) + ",";
            System.out.println("Weight= " + pc.getWeight(i));
            System.out.println("Day= " + pc.getDateOfCard(i));

        }
        System.out.println("ids = " + str);

    }

}
