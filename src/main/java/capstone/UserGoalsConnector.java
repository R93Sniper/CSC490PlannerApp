/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.sql.SQLException;

/**
 *
 * @author Simranjit
 */
public class UserGoalsConnector extends dataConnector {

    public void userGoals(String goaltype, String targetDate, String dailyCalorieTarget, String dietPlan,
            String workoutPlan) {
        try {
            String sql = "INSERT INTO User_Goals(Goal_Type, Target_Date, Daily_Calorie_Target,"
                    + "Diet_Plan, Workout_Plan) VALUES"
                    + "(?,?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, goaltype);
            preparedStatement.setString(2, targetDate);
            preparedStatement.setString(3, dailyCalorieTarget);
            preparedStatement.setString(4, dietPlan);
            preparedStatement.setString(5, workoutPlan);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row inserted into User Goals Table");
            }
        } catch (SQLException e) {
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

    public void updateDailyCalorieTarget(int id, String dailyCalorieTarget) {
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

    public void updateDietPlan(int id, String dietPlan) {
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

    public void updateWorkoutPlan(int id, String workoutPlan) {
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

}
