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
public class DailyStrengthCardsConnector extends dataConnector {

    public void dailyStrengthCards(String benchpressMax, String deadliftMax, String squatsMax, String legpressmax, String shoulderpressMax) {
        try {
            String sql = "INSERT INTO Daily_Strength_Cards(Benchpress_Max, Deadlift_Max, Squats_Max,"
                    + " Legpress_Max, Shoulderpress_Max) VALUES"
                    + "(?,?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, benchpressMax);
            preparedStatement.setString(2, deadliftMax);
            preparedStatement.setString(3, squatsMax);
            preparedStatement.setString(4, legpressmax);
            preparedStatement.setString(5, shoulderpressMax);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row inserted into Daily Strength Cards Table");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBenchPressMax(int id, String benchpressMax) {
        try {
            String sql = "UPDATE Daily_Strength_Cards SET Benchpress_Max=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, benchpressMax);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated for BenchPress_Max");
            }
        } catch (SQLException e) {
        }
    }

    public void updateDeadliftMax(int id, String deadliftMax) {
        try {
            String sql = "UPDATE Daily_Strength_Cards SET Deadlift_Max=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, deadliftMax);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated for Deadlift_Max");
            }
        } catch (SQLException e) {
        }
    }

    public void updateSquatsMax(int id, String squatsMax) {
        try {
            String sql = "UPDATE Daily_Strength_Cards SET Squats_Max=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, squatsMax);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated for Squats_Max");
            }
        } catch (SQLException e) {
        }
    }

    public void updateLegpressMax(int id, String legpressMax) {
        try {
            String sql = "UPDATE Daily_Strength_Cards SET Legpress_Max=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, legpressMax);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated for Legpress_Max");
            }
        } catch (SQLException e) {
        }
    }

    public void updateShoulderpressMax(int id, String shoulderpressMax) {
        try {
            String sql = "UPDATE Daily_Strength_Cards SET Shoulderpress_Max=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, shoulderpressMax);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated for Shoulderpress_Max");
            }
        } catch (SQLException e) {
        }
    }
}
