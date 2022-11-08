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
public class UserGoalsConnector extends dataConnector {

    private void userGoals(String goaltype, String targetDate, String targetWeight, String dateCreated, String sizeGoalID,
            String strengthGoalID) {
        try {
            String sql = "INSERT INTO User_Goals(Goal_Type, Target_Date, Target_Weight, Date_Created, SizeGoal_id"
                    + "StrengthGoal_id) VALUES"
                    + "(?,?,?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, goaltype);
            preparedStatement.setString(2, targetDate);
            preparedStatement.setString(3, targetWeight);
            preparedStatement.setString(4, dateCreated);
            preparedStatement.setString(5, sizeGoalID);
            preparedStatement.setString(6, strengthGoalID);
            
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row inserted into User Goals Table");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateGoalType(int id, String goaltype) {
        try {
            String sql = "UPDATE User_Goals SET Goal_Type=? WHERE ID=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, goaltype);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated for Goal_Type in User Goals Table");
            }
        } catch (SQLException e) {
        }
    }

    public void updateTargetDate(int id, String targetDate) {
        try {
            String sql = "UPDATE User_Goals SET Target_Date=? WHERE ID=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, targetDate);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated for Target_Date in User Goals Table");
            }
        } catch (SQLException e) {
        }
    }

    private void updateDailyCalorieTarget(int id, String dailyCalorieTarget) {
        try {
            String sql = "UPDATE User_Goals SET Daily_Calorie_Target=? WHERE ID=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, dailyCalorieTarget);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated for Daily_Calorie_Target in User Goals Table");
            }
        } catch (SQLException e) {
        }
    }

    private void updateDietPlan(int id, String dietPlan) {
        try {
            String sql = "UPDATE User_Goals SET Diet_Plan=? WHERE ID=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, dietPlan);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated for Diet_Plan in User Goals Table");
            }
        } catch (SQLException e) {
        }
    }

    private void updateWorkoutPlan(int id, String workoutPlan) {
        try {
            String sql = "UPDATE User_Goals SET Workout_Plan=? WHERE ID=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, workoutPlan);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated for Workout_Plan in User Goals Table");
            }
        } catch (SQLException e) {
        }
    }

    public void saveSizeGoal(String Neck_Target, String Arms_Target, String Waist_Target, String Hips_Target, String Legs_Target
                            , String goalType, String targetDate, String dateCreated) {
        int rowID = -1;
        try {
            String sql = "INSERT INTO SizeGoals( insert column names here  ) VALUES"
                    + "(?,?,?,?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, "temp");
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row inserted into Size Goals Table");
                rowID = this.getLastID("SizeGoals");
                this.userGoals(goalType, targetDate, "", dateCreated, Integer.toString(rowID), "0");
            }else{
            System.out.println("FAILED to save Size Goal Card");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
    public void saveStrengthGoal(String BenchPress_Target, String DeadLift_Target, String Squats_Target
                , String LegPress_Target, String ShoulderPress_Target, String goalType, String targetDate, String dateCreated) {
        int rowID = -1;
        try {
            String sql = "INSERT INTO StrengthGoals( insert column names here  ) VALUES"
                    + "(?,?,?,?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, "temp");
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row inserted into Strength Goals Table");
                rowID = this.getLastID("SizeGoals");
                this.userGoals(goalType, targetDate, "", dateCreated, "0", Integer.toString(rowID));
            }else{
            System.out.println("FAILED to save Strength goals Card");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
     
    public void saveWeightGoal(String Goal_Type, String Target_Date, String Target_Weight, String Date_Created){
        
        this.userGoals(Goal_Type, Target_Date, Target_Weight, Date_Created, "0", "0");
    
    }


    

}
