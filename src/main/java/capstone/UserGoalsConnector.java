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
            String sql = "INSERT INTO User_Goals(Goal_Type, Target_Date, Target_Weight, Date_Created, SizeGoal_id, "
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
                int id = dataConnector.getInstance().getLastID("User_Goals");
                this.saveGoalToUserProfile(id);
            } else {
                System.out.println("Failed to insert Row into User Goals Table");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveGoalToUserProfile(int id) {
        UserProfileModel user = UserProfileModel.getInstance();
        String userName = user.getUserName();
        System.out.println("username = "+userName);
        if (userName.equals("")) {
            System.out.println("FAILED to save goals_ids to User_Profile: Username is empty");
            return;
        }

        String goalsIds = "";
        goalsIds = this.getUserGoals(userName);
        if (goalsIds.equals("")) {
            goalsIds = Integer.toString(id);
        } else {
            goalsIds = goalsIds + "-" + Integer.toString(id);
        }
        System.out.println("goals ids = "+goalsIds);
        
        if (this.updateColumn("User_Profile", userName, goalsIds, "Goals_ids")) {
            System.out.println("SUCCESS: updated goals_ids in User Profile table");
        } else {
            System.out.println("FAILED to update goals_ids in User Profile table");
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

    /*
    * To use this method: pass in GoalObject refrence after setting it with 
    * the proper string values by calling the .setSizeGoal on the GoalObject
    */
    public void saveSizeGoal(GoalObject goal) {
        int rowID = -1;
        try {
            String sql = "INSERT INTO SizeGoals( Neck_Target, Arms_Target, Waist_Target, Hips_Target, Legs_Target"
                    + ",  Neck_Initial, Arms_Initial, Waist_Initial, Hips_Initial, Legs_Initial ) VALUES"
                    + "(?,?,?,?,?,?,?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, goal.getNeckTarget());
            preparedStatement.setString(2, goal.getArmsTarget());
            preparedStatement.setString(3, goal.getWaistTarget());
            preparedStatement.setString(4, goal.getHipsTarget());
            preparedStatement.setString(5, goal.getLegsTarget());
            preparedStatement.setString(6, goal.getNeckCurrent());
            preparedStatement.setString(7, goal.getArmsCurrent());
            preparedStatement.setString(8, goal.getWaistCurrent());
            preparedStatement.setString(9, goal.getHipsCurrent());
            preparedStatement.setString(10, goal.getLegsCurrent());
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row inserted into Size Goals Table");
                rowID = this.getLastID("SizeGoals");
                this.userGoals(goal.getGoalType(), goal.getDateTarget(), "", goal.getDateCreated(), Integer.toString(rowID), "0");
            } else {
                System.out.println("FAILED to save Size Goal Card");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /*
    * To use this method: pass in GoalObject refrence after setting it with 
    * the proper string values by calling the .setStrengthGoal on the GoalObject
    */
    public void saveStrengthGoal(GoalObject goal) {
        int rowID = -1;
        try {
            String sql = "INSERT INTO StrengthGoals(BenchPress_Target,Deadlift_Target,Squats_Target,"
                    + "LegPress_Target,ShoulderPress_Target,"
                    + "BenchPress_InitialMax, Deadlift_InitialMax, Squats_InitialMax, LegPress_InitialMax, ShoulderPress_InitialMax) VALUES"
                    + "(?,?,?,?,?,?,?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, goal.getBenchPressTargetMax());
            preparedStatement.setString(2, goal.getDeadliftTargetMax());
            preparedStatement.setString(3, goal.getSquatsTargetMax());
            preparedStatement.setString(4, goal.getLegPressTargetMax());
            preparedStatement.setString(5, goal.getShoulderPressTargetMax());
            preparedStatement.setString(6, goal.getBenchPressCurrentMax());
            preparedStatement.setString(7, goal.getDeadliftCurrentMax());
            preparedStatement.setString(8, goal.getSquatsCurrentMax());
            preparedStatement.setString(9, goal.getLegPressCurrentMax());
            preparedStatement.setString(10, goal.getShoulderPressCurrentMax());
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row inserted into Strength Goals Table");
                rowID = this.getLastID("StrengthGoals");
                this.userGoals(goal.getGoalType(), goal.getDateTarget(), "", goal.getDateCreated(), "0", Integer.toString(rowID));
            } else {
                System.out.println("FAILED to save Strength goals Card");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /*
    * To use this method: pass in GoalObject refrence after setting it with 
    * the proper string values by calling the .setWeightGoal on the GoalObject
    */
    public void saveWeightGoal(GoalObject goal) {
        this.userGoals(goal.getGoalType(), goal.getDateTarget(), goal.getWeightTarget(), goal.getDateCreated(), "0", "0");
    }

    public static void main(String[] args) {
        dataConnector d = dataConnector.getInstance();
        
        UserGoalsConnector dc = new UserGoalsConnector();
       
        UserProfileModel usr = UserProfileModel.getInstance();
        usr.setUserName("johndoe");
        GoalObject goal = new GoalObject();
        //goal.setSizeGoal("10","11", "12", "13", "14", "20", "21", "22", "23", "24", "SizeGoal", "1/3/2023", "11/9/2022");
        //dc.saveSizeGoal(goal);
         goal.setStrengthGoal("100","110", "120", "130", "140", "50", "51", "52", "53", "54", "StrengthGoal", "1/30/2023", "11/9/2022");
         dc.saveStrengthGoal(goal);
        //goal.setWeightGoal("Weight-Loss", "1/10/2023","175", "11/9/22");
        //dc.saveWeightGoal(goal);
        //dc.saveWeightGoal("Weight-Gain", "1/20/2023", "175", "11/8/2022");

    }
;

}
