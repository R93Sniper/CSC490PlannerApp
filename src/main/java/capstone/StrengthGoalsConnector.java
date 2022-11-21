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
public class StrengthGoalsConnector extends dataConnector {

    public void userStrengthGoalsConnector(String benchPressTarget, String deadliftTarget, String squatsTarget, String legPressTarget,
            String shoulderPressTarget, String benchPressInitialMax, String dealLiftInitialMax, String squatsInitialMax, String legsPressInitialMax,
            String shoulderPressInitialMax) {
        try {
            String sql = "INSERT INTO StrengthGoals(BenchPress_Target, DeadLift_Target, Squats_Target,"
                    + " LegPress_Target, ShoulderPress_Target, BenchPress_InitialMax, DeadLift_InitialMax,"
                    + "Squats_InitialMax, LegPress_InitialMax, ShoulderPress_InitialMax) VALUES"
                    + "(?,?,?,?,?,?,?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, benchPressTarget);
            preparedStatement.setString(2, deadliftTarget);
            preparedStatement.setString(3, squatsTarget);
            preparedStatement.setString(4, legPressTarget);
            preparedStatement.setString(5, shoulderPressTarget);
            preparedStatement.setString(6, benchPressInitialMax);
            preparedStatement.setString(7, dealLiftInitialMax);
            preparedStatement.setString(8, squatsInitialMax);
            preparedStatement.setString(9, legsPressInitialMax);
            preparedStatement.setString(10, shoulderPressInitialMax);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row inserted into StrengthGoals Table");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBenchPressTarget(int id, String benchPressTarget) {
        try {
            String sql = "UPDATE StrengthGoals SET BenchPress_Target=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, benchPressTarget);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated");
            }
        } catch (SQLException e) {
        }
    }

    public void updateDeadLiftTarget(int id, String deadLiftTarget) {
        try {
            String sql = "UPDATE StrengthGoals SET DeadLiftTarget_Target=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, deadLiftTarget);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated");
            }
        } catch (SQLException e) {
        }
    }

    public void updateSquatsTarget(int id, String squatsTarget) {
        try {
            String sql = "UPDATE StrengthGoals SET Squats_Target=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, squatsTarget);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated");
            }
        } catch (SQLException e) {
        }
    }

    public void updateLegPressTarget(int id, String legPressTarget) {
        try {
            String sql = "UPDATE StrengthGoals SET LegPress_Target=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, legPressTarget);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated");
            }
        } catch (SQLException e) {
        }
    }

    public void updateShoulderPressTarget(int id, String shoulderPressTarget) {
        try {
            String sql = "UPDATE StrengthGoals SET ShoulderPress_Target=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, shoulderPressTarget);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated");
            }
        } catch (SQLException e) {
        }
    }

    public void updateBenchPressInitialMax(int id, String benchPressInitialMax) {
        try {
            String sql = "UPDATE StrengthGoals SET BenchPress_InitialMax=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, benchPressInitialMax);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated");
            }
        } catch (SQLException e) {
        }
    }

    public void updateDeadLiftInitialMax(int id, String deadLiftInitialMax) {
        try {
            String sql = "UPDATE StrengthGoals SET DeadLift_InitialMax=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, deadLiftInitialMax);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated");
            }
        } catch (SQLException e) {
        }
    }

    public void updatesquatsInitialMax(int id, String squatsInitialMax) {
        try {
            String sql = "UPDATE StrengthGoals SET Squats_InitialMax=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, squatsInitialMax);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated");
            }
        } catch (SQLException e) {
        }
    }

    public void updateLegInitialMax(int id, String legPressInitial) {
        try {
            String sql = "UPDATE StrengthGoals SET LegPress_Initial=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, legPressInitial);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated");
            }
        } catch (SQLException e) {
        }
    }

    public void updateShoulderPressInitialMax(int id, String shoulderPressInitialMax) {
        try {
            String sql = "UPDATE StrengthGoals SET ShoulderPress_InitialMax=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, shoulderPressInitialMax);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated");
            }
        } catch (SQLException e) {
        }
    }
}
