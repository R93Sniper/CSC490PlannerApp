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
public class DailyMeasurementsConnector extends dataConnector {
    
    public void dailyMeasurements(String neckSize, String armsSize, String waistSize, String hipsSize, String legsSize) {
        try {
            String sql = "INSERT INTO Daily_Measurements_Cards(Neck_Size, Arms_Size, Waist_Size,"
                    + " Hips_Size, Legs_Size) VALUES"
                    + "(?,?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, neckSize);
            preparedStatement.setString(2, armsSize);
            preparedStatement.setString(3, waistSize);
            preparedStatement.setString(4, hipsSize);
            preparedStatement.setString(5, legsSize);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row inserted into Daily Measurements Cards Table");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateNeckSize(int id, String necksize) {
        try {
            String sql = "UPDATE Daily_Measurements_Cards SET Neck_Size=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, necksize);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated for Neck_Size");
            }
        } catch (SQLException e) {
        }
    }

    public void updateArmsSize(int id, String armsSize) {
        try {
            String sql = "UPDATE Daily_Measurements_Cards SET Arms_Size=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, armsSize);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated for Arms_Size");
            }
        } catch (SQLException e) {
        }
    }

    public void updateWaistSize(int id, String waistSize) {
        try {
            String sql = "UPDATE Daily_Measurements_Cards SET Waist_Size=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, waistSize);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated for Waist_Size");
            }
        } catch (SQLException e) {
        }
    }

    public void updateHipsSize(int id, String hipsSize) {
        try {
            String sql = "UPDATE Daily_Measurements_Cards SET Hips_Size=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, hipsSize);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated for Hips_Size");
            }
        } catch (SQLException e) {
        }
    }

    public void updateLegsSize(int id, String legsSize) {
        try {
            String sql = "UPDATE Daily_Measurements_Cards SET Legs_Size=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, legsSize);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated for Legs_Size");
            }
        } catch (SQLException e) {
        }
    }

}
