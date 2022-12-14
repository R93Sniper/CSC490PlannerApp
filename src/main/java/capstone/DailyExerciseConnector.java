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
 * @author jesus
 */
public class DailyExerciseConnector extends dataConnector {

    public void insertNewDailyExerciseCard(String exLogIds) {

        try {
            String sql = "INSERT INTO Daily_Exercise_Cards(Exercise_Log_Ids) VALUES"
                    + "(?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, exLogIds);

            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row inserted into Daily_Exercise_Cards Table");
            }
        } catch (SQLException e) {
        }

    }

    public void updateExerciseLogIds(int dailyExID, String logIds) {
        try {
            String sql = "UPDATE Daily_Exercise_Cards SET Exercise_Log_Ids=? WHERE ID=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, logIds);
            preparedStatement.setInt(2, dailyExID);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated for Exercise_Log_Ids in DailyExercise_Cards Table");
            }
        } catch (SQLException e) {
        }
    }

    public void insertNewExerciseLogCardio(String name, String duration, String distance, String caloriesBurned) {

        try {
            String sql = "INSERT INTO ExerciseLog(Exercise_Name, Duration, Distance, Calories_Burned) VALUES"
                    + "(?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, duration);
            preparedStatement.setString(3, distance);
            preparedStatement.setString(4, caloriesBurned);

            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row inserted into Exercise Log for cardio input");
            }
        } catch (SQLException e) {
            System.err.println(e.getSQLState());
        }

    }

    public void insertNewExerciseLogWeightLifting(String name, String weight, String sets, String reps) {

        try {
            String sql = "INSERT INTO ExerciseLog(Exercise_Name, Weight_Lifted, Num_Sets, Num_Reps) VALUES"
                    + "(?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, weight);
            preparedStatement.setString(3, sets);
            preparedStatement.setString(4, reps);

            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row inserted into Daily Exercise log for weightLifting input");
            }
        } catch (SQLException e) {
            System.err.println(e.getSQLState());
        }

    }
    public String getExerciseLogIds(int id){
        String tableName = "Daily_Exercise_Cards";
        String logIds = "";
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE ID = " + id + ";");
            while (result.next()) {
                logIds = result.getString("Exercise_Log_Ids");
            }
        } catch (SQLException except) {
            except.printStackTrace();
        }
        return logIds;
    
    }
     //returns the row of the ExercieItem from the ExerciseLog table
    public ExerciseItem getExerciseLogRow(int id) {

        ResultSet result = null;
        ExerciseItem item = null;
        try {
            Statement stmt = conn.createStatement();
            result = stmt.executeQuery("select * from ExerciseLog"
                    + " where ID=" + id + ";");
            
            while(result.next()){
            String name = result.getString("Exercise_Name");
            
            String weightLifted = result.getString("Weight_Lifted")==null ? "": result.getString("Weight_Lifted");
            String sets = result.getString("Num_Sets")==null ? "":  result.getString("Num_Sets");
            String reps = result.getString("Num_Reps")==null ? "":  result.getString("Num_Reps");
            String duration = result.getString("Duration")==null ? "":  result.getString("Duration");
            String distance = result.getString("Distance")==null ? "": result.getString("Distance") ;
            String caloriesBurned = result.getString("Calories_Burned")==null ? "":  result.getString("Calories_Burned");
            item = new ExerciseItem();
            item.name = name;
            item.weightLifted = weightLifted;
            item.sets = sets;
            item.reps = reps;
            item.duration = duration;
            item.distance = distance;
            item.caloriesBurned = caloriesBurned;
            item.rowId = id;
            }

        } catch (SQLException ex) {
        }
        return item;

    }
    
    public double getMETValue(String activityName){
    
        ResultSet result = null;
        String str = "";
        try {
            Statement stmt = conn.createStatement();
            result = stmt.executeQuery("SELECT * FROM PhysicalActivity"
                    + " WHERE Activity_Name=\'"+activityName+"\';");
            while(result.next()){
                str = result.getString("MET_Value");
                //System.out.println(i+": "+str);
               return Double.valueOf(str);
            }       
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        
        return -1;
    }
    
    
    public ResultSet getAllActivities(){
    
        ResultSet result = null;
        String str = "";
        int i =1;
        try {
            Statement stmt = conn.createStatement();
            result = stmt.executeQuery("SELECT * FROM PhysicalActivity"
                    + " ORDER BY Activity_Name ASC;");
            return result;
            /*
            while(result.next()){
                str = result.getString("Activity_Name");
                System.out.println(i+": "+str);
                i++;
            }*/
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
   
        
        
        
        return null;
    }
    
        public String getCaloriesOut(int id){
        String tableName = "Daily_Exercise_Cards";
        String outake = "";
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE ID = " + id + ";");
            while (result.next()) {
                outake = result.getString("Calories_Out");
            }
        } catch (SQLException except) {
            except.printStackTrace();
        }
        return outake;
    
    }
    
    

    public static void main(String[] args) {
        dataConnector d = dataConnector.getInstance();
        DailyExerciseConnector dc = new DailyExerciseConnector();
        dc.getAllActivities();

    }

}
